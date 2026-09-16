class Solution {
    int n;
    Integer [][][]memo;
    int MOD = 1000000007;
    int sol(int i, int k, int currSeg){
        
        // k is remK

        if(k==0) return 1;
        if(i>=n) return 0;

        if(memo[i][k][currSeg] != null) return memo[i][k][currSeg];

        int ans = 0;
        if(currSeg == 1){
            ans += sol(i+1,k,1); //extend
            ans += sol(i,k-1,0);   //end
        }
        else{
            ans += sol(i+1,k,1);  //start
            ans += sol(i+1,k,0);    //skip
        }

        return memo[i][k][currSeg] = ans%MOD;
    }


    int sol2(int n, int k) {
        long[][] dp = new long[n][k + 1];

        // 0 segments -> exactly 1 way (draw nothing)
        for (int i = 0; i < n; i++) {
            dp[i][0] = 1;
        }

        for (int j = 1; j <= k; j++) {
            long sum = 0;
            for (int i = 1; i < n; i++) {
                // Add ways where the j-th segment starts at (i - 1)
                sum = (sum + dp[i - 1][j - 1]) % MOD;

                // Option 1: Segment does not end at i -> dp[i - 1][j]
                // Option 2: Segment ends at i -> sum
                dp[i][j] = (dp[i - 1][j] + sum) % MOD;
            }
        }

        return (int) dp[n - 1][k];
    }


    long quickPow(long a, long e) {
        long result = 1;
        while (e > 0) {
            if ((e & 1) != 0) result = (result * a) % MOD;
            a = (a * a) % MOD;
            e >>= 1;
        }
        return result;
    }
    int solOP(int n, int k) {
        int m = 2 * k;
        long numerator = 1, denominator = 1;
        for (int i = 1; i <= m; i++) {
            numerator = (numerator * (n + k - i)) % MOD;
            denominator = (denominator * i) % MOD;
        }
        return (int) ((numerator * quickPow(denominator, MOD - 2)) % MOD);
    }


    public int numberOfSets(int n, int k) {
        this.n = n;
        // memo = new Integer[n][k+1][2];
        // return sol(0,k,0);

        return sol2(n,k);
    }
}
