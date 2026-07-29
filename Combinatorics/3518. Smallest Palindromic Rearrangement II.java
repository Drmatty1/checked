class Solution {
    static long maxK = 1000001;
    
    public long multinomial(int[] counts) {
        int tot = 0;
        for (int cnt : counts) {
            tot += cnt;
        }
        long res = 1;
        for (int i = 0; i < 26; i++) {
            int cnt = counts[i];
            res = res * binom(tot, cnt);
            if (res >= maxK)
                return maxK;
            tot -= cnt;
        }
        return res;
    }

    // filter is imp for leaving largets largest 
    public long multinomial1(int[] counts) {
        int tot = 0;
        int largest = 0;

        for (int cnt : counts) {
            tot += cnt;
            largest = Math.max(largest, cnt);
        }
        
        long res = 1;
        boolean skipped = false;

        for (int i = 0; i < 26; i++) {

            int cnt = counts[i];

             if (!skipped && cnt == largest) {
                skipped = true;
                continue;
            }

            //m-1  - best**
            // res = res * binom(tot, cnt);
            
            //m-2   , see last comment
            for(int j=1; j<=cnt; j++){
                res = (res*tot)/j;
        
                if (res >= maxK)
                    return maxK;
                tot = tot-1;
            }

        }
        return res;
    }
    
    public long binom(int n, int k) {
        if (k > n) return 0;
        if (k > n - k) k = n - k;
        long result = 1;
        for (int i = 1; i <= k; i++) {
            result = result * (n - i + 1) / i;
            if (result >= maxK) return maxK;
        }
        return result;
    }

    public String smallestPalindrome(String inputStr, int K) {
        int[] frequency = new int[26];
        for (int i = 0; i < inputStr.length(); i++) {
            char ch = inputStr.charAt(i);
            frequency[ch - 'a']++;
        }
        char mid = 0;
        for (int i = 0; i < 26; i++) {
            if (frequency[i] % 2 == 1) {
                mid = (char) ('a' + i);
                frequency[i]--;
                break;
            }
        }
        int[] halfFreq = new int[26];
        int halfLength = 0;
        for (int i = 0; i < 26; i++) {
            halfFreq[i] = frequency[i] / 2;
            halfLength += halfFreq[i];
        }

        long totalPerms = multinomial(halfFreq);
        if (K > totalPerms) return "";

        StringBuilder firstHalfBuilder = new StringBuilder();
        
        for (int i = 0; i < halfLength; i++) {
            for (int c = 0; c < 26; c++) {
                if (halfFreq[c] > 0) {
                    halfFreq[c]--;
                    long perms = multinomial(halfFreq);
                    if (perms >= K) {
                        firstHalfBuilder.append((char) ('a' + c));
                        break;
                    } else {
                        K -= perms;
                        halfFreq[c]++;
                    }
                }
            }
        }
        String firstHalf = firstHalfBuilder.toString();
        String revHalf = new StringBuilder(firstHalf).reverse().toString();
        String result;
        if (mid == 0) {
            result = firstHalf + revHalf;
        } else {
            result = firstHalf + mid + revHalf;
        }
        return result;
    }
    
}

/**
To compute:

y = n! / (x1! × x2! × ... × xk!), where Σxi = n

1. Reorder xi so the largest count is last (ignore xk).
2. Initialize y = 1.
3. For i = 1 to k-1:
      For j = 1 to xi:
          y = (y * n) / j
          n--
          if (y > K) stop (value already exceeds K)

Why this works:
- Division is always exact (y stays integer).
- Intermediate values of y never decrease.
- Early termination saves time if y > K.

Note:
- This method doesn't allow direct use of precomputed factorials.
- Using logarithms is possible but may introduce floating-point errors.
 */
