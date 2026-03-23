class Solution {

    int sol(int[] coins, int amount){
        
        if( amount == 0 ) return 0;
        if( amount < 0 ) return -1;

        int ans = Integer.MAX_VALUE;
        for( int c : coins ){
            int temp = 1+sol(coins,amount-c);
            if( temp != 0 )
                ans = Math.min(ans,temp);
        }
    
        return ans;
    }
    int solTD(int[] coins, int amount, int []dp){
        
        if( amount == 0 ) return 0;
        if( amount < 0 ) return -1;

        if( dp[amount] != 0 ) return dp[amount];

        int ans = Integer.MAX_VALUE;
        for( int c : coins ){
            int temp = 1+solTD(coins,amount-c,dp);
            if( temp != 0 )
                ans = Math.min(ans,temp);
        }
        
        int res = ans==Integer.MAX_VALUE?-1:ans;
        return dp[amount] = res;
    }

    int solBU(int[] coins, int amount){

        int []dp = new int[10001];

        dp[0] = 0;
        
        for( int a = 1; a<=amount; a++ ){

            int ans = Integer.MAX_VALUE;
            for( int c : coins ){
                int temp = 1+((a-c)>=0?dp[a-c]:-1);
                if( temp != 0 )
                    ans = Math.min(ans,temp);
            }
            dp[a] = ans==Integer.MAX_VALUE?-1:ans;
        }
        return dp[amount];
    }

    public int coinChange(int[] coins, int amount) {
    
        // return sol( coins, amount );

        // int []dp = new int[10001];
        // return solTD(coins, amount, dp);

        return solBU(coins, amount);

    }
}
