class Solution {
    int sol( String a, String b, int i, int j ){
        if( i == a.length() || j == b.length() ){
            return 0;
        }
        char p = a.charAt(i), q = b.charAt(j);
        if( p == q ){
            //pick
            int op1 = 1 + sol(a,b,i+1,j+1);
            
            //skip
            int op2 = sol(a,b,i+1,j);

            return Math.max(op1,op2);
        }
        int op1 = sol(a,b,i+1,j);
        int op2 = sol(a,b,i,j+1);
        return Math.max(op1,op2);

    }

    // TOP-DOWN
    int sol1( String a, String b, int i, int j, int[][]dp ){
        if( i == a.length() || j == b.length() ){
            return 0;
        }

        if( dp[i][j] != -1 ) return dp[i][j];

        char p = a.charAt(i), q = b.charAt(j);
        if( p == q ){
            //pick
            int op1 = 1 + sol1(a,b,i+1,j+1, dp);
            
            //skip
            int op2 = sol1(a,b,i+1,j, dp);

            return dp[i][j] = Math.max(op1,op2);
        }
        int op1 = sol1(a,b,i+1,j,dp);
        int op2 = sol1(a,b,i,j+1,dp);
        return dp[i][j] = Math.max(op1,op2);

    }

    // BOTTOM-UP
    int sol2( String a, String b ){
        int l1 = a.length(), l2 = b.length();

        int [][]dp =  new int[l1+1][l2+1];

        for( int i=l1-1; i>=0; i-- ){

            for( int j=l2-1; j>=0; j-- ){
                
                char p = a.charAt(i), q = b.charAt(j);
                if( p == q ){
                    //pick
                    int op1 = 1 + dp[i+1][j+1];
                    
                    //skip
                    int op2 = dp[i+1][j];

                    dp[i][j] = Math.max(op1,op2);
                }
                else{
                    int op1 = dp[i+1][j];
                    int op2 = dp[i][j+1];
                    dp[i][j] = Math.max(op1,op2);
                }

            }

        }

        return dp[0][0];

    }

    //BOTTOM_UP OP
    int sol3( String a, String b ){
        int l1 = a.length(), l2 = b.length();

        // int [][]dp =  new int[l1+1][l2+1];
        int []next = new int[l2+1];

        for( int i=l1-1; i>=0; i-- ){
            
            int[] curr = new int[l2+1];

            for( int j=l2-1; j>=0; j-- ){
                
                char p = a.charAt(i), q = b.charAt(j);
                if( p == q ){
                    

                    curr[j] =  1 + next[j+1];
                }
                else{
                    int op1 = next[j];
                    int op2 = curr[j+1];
                    curr[j] = Math.max(op1,op2);
                }

            }
            next = curr;
        }

        return next[0];

    }

    public int longestCommonSubsequence(String text1, String text2) {
        
        // int op1 = sol(text1, text2, 0, 0);
        // return op1;


        // int [][]dp =  new int[1001][1001];
        // for( int i=0; i<1001; i++ ){
        //     for( int j=0; j<1001; j++ ){
        //         dp[i][j] = -1;
        //     }
        // }
        
        // int op1 = sol1(text1, text2, 0, 0, dp);
        // return op1;


        // return sol2(text1,text2);

        int res = 0;
        if( text1.length() > text2.length() )
            res = sol3(text1, text2);
        else
            res = sol3(text2, text1);
        return res;
    }
}
