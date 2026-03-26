class Solution {
    static int sol( int []arr, int i, int j ){
        if( i == j ) return 0;
        int ans = Integer.MAX_VALUE;
        for( int k=i; k<j; k++ ){
            int temp = sol(arr,i,k)+sol(arr,k+1,j)+arr[i-1]*arr[k]*arr[j];
            ans = Math.min(ans,temp);
        }
        return ans;
    }
    
    static int sol1( int []arr, int i, int j, int[][]dp ){
        if( i == j ) return 0;
        
        int ans = Integer.MAX_VALUE;
        if( dp[i][j] != 0 ) return dp[i][j];
        
        for( int k=i; k<j; k++ ){
            int temp = sol1(arr,i,k,dp)+sol1(arr,k+1,j,dp)+arr[i-1]*arr[k]*arr[j];
            ans = Math.min(ans,temp);
        }
        return dp[i][j] = ans;
    }
    
    static int sol2( int []arr ){
        
        int l = arr.length;
        int [][]dp = new int[l+1][l+1];
        
        for( int len = 2; len<l; len++ ){
            
            for( int i=1; i<=l-len; i++ ){
                
                    int j = i+len-1;
                    
                    int ans = Integer.MAX_VALUE;

                    for( int k=i; k<j; k++ ){
                        int temp = dp[i][k]+dp[k+1][j]+arr[i-1]*arr[k]*arr[j];
                        ans = Math.min(ans,temp);
                    }
                    
                    dp[i][j] = ans;
                     
            }
        }
        
        return dp[1][l-1];
    }
    
    static int matrixMultiplication(int arr[]) {
        
        // return sol(arr,1,arr.length-1);
        
        // int l = arr.length;
        // int [][]dp = new int[l][l];
        // return sol1(arr, 1, l-1, dp);
        
        return sol2(arr);
    }
}
