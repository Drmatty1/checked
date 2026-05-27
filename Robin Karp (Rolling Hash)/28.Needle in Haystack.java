class Solution {
    long power(int b, int e){
        if (e == 0) return 1;
        int mod = 1_000_000_007;

        long temp = power(b, e / 2);

        if (e % 2 == 0)  return (temp * temp)%mod;
        return (b * temp * temp)%mod;
    }
    public int strStr(String haystack, String needle) {

        int len = needle.length();

        //adv hashing
        int base = 29;
        int mod = 1_000_000_007;
        long hash = 0;

        for(char c: needle.toCharArray()){
            int val = c-'a'+1;
            hash = ( hash*base + val ) % mod;
        }

        int n = haystack.length();
        char []arr = haystack.toCharArray();
        long code = 0;

        long pow = power(base,len-1); /// to be used in loop

        for(int i=0; i<n; i++ ){

            char c = arr[i];
            int val = c-'a'+1;
            
            if(i-len >= 0){
                int rem = arr[i-len]-'a'+1;
                code = (code - rem*pow + mod) % mod;
            }

            code = (code*base + val) % mod;

            if( i >= len-1){
                if(code == hash){
                    boolean res = needle.equals(haystack.substring(i-len+1,i+1));
                    if(res == true){
                        return i-len+1;
                    }
                }
            }
        }
        return -1;
    }
}
