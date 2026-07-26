class Solution {
    int MOD = 1000000007;
    
    // dp soln Too Slow
    int f(int k, int n, boolean c){
        if(k == n) return c?1:0;
        if( k > n ) return 0;

        int ans = 0;
        for(int j=1; j<=n; j++){
            ans += f(k-1,n-j, c || j%2==0 );
        }

        return ans;
    }

    private long nCr(int n, int r) {
        if (r < 0 || r > n) return 0;
        if (r == 0 || r == n) return 1;
        if (r > n - r) r = n - r; 
        
        long num = 1;
        long den = 1;
        for (int i = 1; i <= r; i++) {
            num = (num * (n - i + 1)) % MOD;
            den = (den * i) % MOD;
        }
        
        return (num * modInverse(den, MOD)) % MOD;
    }
    
    
    private long modInverse(long a, int m) {
        return power1(a, m - 2, m);
    }
    
    private long power(long base, long exp, int m) {
        long res = 1;
        base %= m;
        while (exp > 0) {
            if (exp % 2 == 1) res = (res * base) % m;
            base = (base * base) % m;
            exp /= 2;
        }
        return res;
    }

    private long power1(long base, long exp, int m) {
        if(exp == 0) return 1L;

        long res = power1(base,exp/2,m);
        res = (res*res)%MOD;
        if(exp%2 != 0 ){
            res = (res*base)%MOD;
        }
        return res;
    }
    
    public int countValidSequences(int n, int k) {
        if(k >= n) return 0;

        long allSeq = nCr(n-1,k-1);

        long oddSeq = 0;
        if( (n+k)%2 == 0 ){
            int m = (n+k)/2;
            oddSeq = nCr(m-1,k-1);
        }

        long ans = (allSeq - oddSeq + MOD)%MOD;

        System.out.println(power1(2,4,MOD));

        return (int)ans;
        
    }
}





