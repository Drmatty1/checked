class Solution {
    public String shortestCommonSupersequence(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m + 1][n + 1];

        // 1. Build the DP table for Longest Common Subsequence (LCS)
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

      
        // Length of SCS = n1+n2-LCS(s1,s2);      
        // 2. Trace back from the bottom-right to build the supersequence
        StringBuilder sb = new StringBuilder();
        int i = m, j = n;

        while (i > 0 && j > 0) {
            // If characters match, include it once and move diagonally
            if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                sb.append(s1.charAt(i - 1));
                i--;
                j--;
            } 
            // If they don't match, move in the direction of the greater DP value
            else if (dp[i - 1][j] > dp[i][j - 1]) {
                sb.append(s1.charAt(i - 1));
                i--;
            } else {
                sb.append(s2.charAt(j - 1));
                j--;
            }
        }

        // 3. If any characters are left in s1, append them
        while (i > 0) {
            sb.append(s1.charAt(i - 1));
            i--;
        }

        // 4. If any characters are left in s2, append them
        while (j > 0) {
            sb.append(s2.charAt(j - 1));
            j--;
        }

        // 5. We built the string backwards, so reverse it
        return sb.reverse().toString();
    }
}
