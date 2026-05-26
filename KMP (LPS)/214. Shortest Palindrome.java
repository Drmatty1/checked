class Solution {

    int getLPSLength(char []s) {
        
        int n = s.length;
        int suf = 0;       //suffix
        int pre=1;      // prefix
        int []lps = new int[n];
        lps[0] = 0;
        
        
        while(pre<n){
            
            if( s[pre] == s[suf] ){
                lps[pre] = suf+1;
                suf++;
                pre++;
            }
            else{
                if(suf != 0){
                    suf = lps[suf-1];
                }
                else{
                    lps[pre] = 0;
                    pre ++ ;
                }
            }
            
        }
        
        return lps[n-1];
        
    }
    public String shortestPalindrome(String s) {

        String rev = new StringBuilder(s).reverse().toString();
        String pattern = s+"-"+rev;

        int lps = getLPSLength(pattern.toCharArray());

        StringBuilder res = new StringBuilder();
        for(int i=s.length()-1; i>=lps; i--){
            res.append(s.charAt(i));
        }
        
        res.append(s);
        return new String(res);
    }
}
