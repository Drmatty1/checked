class Solution {
    
    int sol(String s, int i, int j){
        if( i == j ) return 1;
        if( i > j ) return 0;

        int c1 = s.charAt(i), c2 = s.charAt(j);
        int ans =0;
        if( c1 == c2 ){
            ans = 2+sol(s, i+1, j-1);
        }
        else{
            int a = sol(s, i+1,j);
            int b = sol(s, i,j-1);
            ans = Math.max(a,b);
        }
        return ans;

    }

    int sol1(String s, int i, int j, int[][]dp){
        if( i == j ) return 1;
        if( i > j ) return 0;

        if( dp[i][j] != -1 ) return dp[i][j];

        int c1 = s.charAt(i), c2 = s.charAt(j);
        int ans =0;
        if( c1 == c2 ){
            ans = 2+sol1(s, i+1, j-1,dp);
        }
        else{
            int a = sol1(s, i+1,j,dp);
            int b = sol1(s, i,j-1,dp);
            ans = Math.max(a,b);
        }
        return dp[i][j] = ans;

    }

    int sol2(String s){

        int l = s.length();
        int [][]dp = new int[l][l];

        for( int i=0; i<l; i++ ) dp[i][i]=1;

        for( int sCol=1; sCol<l; sCol++ ){
            for( int i=0,j=sCol; i<l&&j<l; i++,j++ ){
                
                int c1 = s.charAt(i), c2 = s.charAt(j);
                int ans =0;
                if( c1 == c2 ){
                    ans = 2+dp[i+1][j-1];
                }
                else{
                    int a = dp[i+1][j];
                    int b = dp[i][j-1];
                    ans = Math.max(a,b);
                }
                dp[i][j] = ans;
            }
        }

        return dp[0][l-1];

    }

    //Simulated Diagonal Traversal -> Can be Space Optimized
    int sol2BEST(String s){

        int l = s.length();
        int [][]dp = new int[l][l];

        for( int i=0; i<l; i++ ) dp[i][i]=1;

        for (int i = l - 1; i >= 0; i--) {
            for (int j = i + 1; j < l; j++) {
                
                int c1 = s.charAt(i), c2 = s.charAt(j);
                int ans =0;
                if( c1 == c2 ){
                    ans = 2+dp[i+1][j-1];
                }
                else{
                    int a = dp[i+1][j];
                    int b = dp[i][j-1];
                    ans = Math.max(a,b);
                }
                dp[i][j] = ans;
            }
        }

        return dp[0][l-1];

    }

    
    int sol3BEST(String s){

        int l = s.length();
        int []next = new int[l];
        int []curr = new int[l];

        for (int i = l - 1; i >= 0; i--) {
            
            curr[i]=1;

            for (int j = i + 1; j < l; j++) {
                
                int c1 = s.charAt(i), c2 = s.charAt(j);
                int ans =0;
                if( c1 == c2 ){
                    ans = 2+next[j-1];
                }
                else{
                    int a = next[j];
                    int b = curr[j-1];
                    ans = Math.max(a,b);
                }
                curr[j] = ans;
            }
            next = curr.clone();
        }

        return next[l-1];

    }

    int sol4BEST(String s){

        int l = s.length();
        // int []next = new int[l];
        int []curr = new int[l];

        for (int i = l - 1; i >= 0; i--) {
            
            int prev = 0 ;  // dp[i+1][j-1]
            curr[i]=1;

            for (int j = i + 1; j < l; j++) {

                int temp = curr[j];  // rep dp[i+1][j]
                
                int c1 = s.charAt(i), c2 = s.charAt(j);
                int ans =0;
                if( c1 == c2 ){
                    ans = 2+prev;   // 2+next[j-1];
                }
                else{
                    int a = curr[j];     //next[j];
                    int b = curr[j-1];    //curr[j-1];
                    ans = Math.max(a,b);
                }
                curr[j] = ans;
                prev = temp;
            }
          
        }

        return curr[l-1];

    }


    public int longestPalindromeSubseq(String s) {
        // return sol(s,0,s.length()-1);

        // int l = s.length();
        // int [][]dp = new int[l][l];
        // for( int i=0; i<l; i++ )Arrays.fill(dp[i],-1);
        // return sol1(s,0,s.length()-1,dp);

        
        // return sol2BEST(s);


        // return sol3BEST(s);


        return sol4BEST(s);


    }
}
