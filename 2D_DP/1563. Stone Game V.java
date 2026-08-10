class Solution {
    int []pre;
    int sol(int i, int j, int []arr){
        if(i==j) return 0;
        int ans = 0;
        for(int m=i; m<j; m++){
            int lsum = pre[m+1]-pre[i];
            int rsum = pre[j+1]-pre[m+1];
            int temp = 0;
            if(lsum < rsum){
                temp = lsum + sol(i,m,arr);
            }
            else if(lsum > rsum){
                temp = rsum + sol(m+1,j,arr);
            }
            else{
                temp = Math.max(
                    lsum + sol(i,m,arr),
                    rsum + sol(m+1,j,arr)
                );
            }
            ans = Math.max(ans,temp);
        }
        return ans;
    }
    int sol1(int i, int j, int []arr, int [][]dp){
        if(i==j) return 0;
        if(dp[i][j] != -1) return dp[i][j];
        int ans = 0;
        for(int m=i; m<j; m++){
            int lsum = pre[m+1]-pre[i];
            int rsum = pre[j+1]-pre[m+1];
            int temp = 0;
            if(lsum < rsum){
                temp = lsum + sol1(i,m,arr,dp);
            }
            else if(lsum > rsum){
                temp = rsum + sol1(m+1,j,arr,dp);
            }
            else{
                temp = Math.max(
                    lsum + sol1(i,m,arr,dp),
                    rsum + sol1(m+1,j,arr,dp)
                );
            }
            ans = Math.max(ans,temp);
        }
        return dp[i][j] = ans;
    }

    // bootomm up ->  digaonal traversal
    int sol21( int []arr){
        int n = arr.length;

        int [][]dp = new int[n][n];
        for(int i=0; i<n; i++){
            dp[i][i] = 0;
        }

        for(int len=1; len<n; len++){
            for(int i=0; i+len<n; i++){
                int j=i+len;

                int ans = 0;
                for(int m=i; m<j; m++){
                    int lsum = pre[m+1]-pre[i];
                    int rsum = pre[j+1]-pre[m+1];
                    int temp = 0;
                    if(lsum < rsum){
                        temp = lsum + dp[i][m];
                    }
                    else if(lsum > rsum){
                        temp = rsum + dp[m+1][j];
                    }
                    else{
                        temp = Math.max(
                            lsum + dp[i][m],
                            rsum + dp[m+1][j]
                        );
                    }
                    ans = Math.max(ans,temp);
                }
                dp[i][j] = ans;
            }
        }
        return dp[0][n-1];
    }

    // bootom up -> classicl  !!!!
    int sol22( int []arr){
        int n = arr.length;

        int [][]dp = new int[n][n];
        for(int i=0; i<n; i++){
            dp[i][i] = 0;
        }

        for(int i=n-1; i>=0; i--){
            for(int j=i+1; j<n; j++){

                int ans = 0;
                for(int m=i; m<j; m++){
                    int lsum = pre[m+1]-pre[i];
                    int rsum = pre[j+1]-pre[m+1];
                    int temp = 0;
                    if(lsum < rsum){
                        temp = lsum + dp[i][m];
                    }
                    else if(lsum > rsum){
                        temp = rsum + dp[m+1][j];
                    }
                    else{
                        temp = Math.max(
                            lsum + dp[i][m],
                            rsum + dp[m+1][j]
                        );
                    }
                    ans = Math.max(ans,temp);
                }
                dp[i][j] = ans;
            }
        }
        return dp[0][n-1];
    }


    int search(int[] pre, int l, int r) {
        int sum = pre[r+1] - pre[l], L = l;
        while(l < r) {
            int m = l + ((r - l) >> 1);
            if(((pre[m+1] - pre[L]) << 1) >= sum) {
                r = m;
            } else {
                l = m + 1;
            }
        }
        return l;
    }
    // O(n^2logn) -> diogonal (binary searh indep. of order trav)
    int sol31(int[] stoneValue) {
        int n = stoneValue.length;
        int[] pre = new int[n+1];
        for(int i = 1; i <= n; i++) {
            pre[i] = pre[i-1] + stoneValue[i-1];
        }
        int[][] dp = new int[n][n], left = new int[n][n], right = new int[n][n];
        for(int i = 0; i < n; i++) {
            left[i][i] = right[i][i] = stoneValue[i];
        }
        for(int l = 1; l < n; l++) {
            for(int i = 0; i < n-l; i++) {
                int j = i+l, k = search(pre, i, j);
                int sum = pre[j+1] - pre[i], leftHalf = pre[k+1] - pre[i];
                if((leftHalf << 1) == sum) {    // equal parts
                    dp[i][j] = Math.max(left[i][k], right[k+1][j]);
                } else {    // left half > right half
                    dp[i][j] = Math.max(k == i ? 0 : left[i][k-1], k == j ? 0 : right[k+1][j]);
                }
                left[i][j] = Math.max(left[i][j-1], sum + dp[i][j]);
                right[i][j] = Math.max(right[i+1][j], sum + dp[i][j]);
            }
        }
        return dp[0][n-1];
    }
    // O(n^2logn) -> bottom-up (binary searh indep. of order trav)
    int sol32(int[] stoneValue) {
        int n = stoneValue.length;
        int[] pre = new int[n+1];
        for(int i = 1; i <= n; i++) {
            pre[i] = pre[i-1] + stoneValue[i-1];
        }
        int[][] dp = new int[n][n], left = new int[n][n], right = new int[n][n];
        for(int i = 0; i < n; i++) {
            left[i][i] = right[i][i] = stoneValue[i];
        }
        for(int i=n-1; i>=0; i--) {
            for(int j = i+1; j < n; j++) {

                int k = search(pre, i, j);
                int sum = pre[j+1] - pre[i], leftHalf = pre[k+1] - pre[i];
                if((leftHalf << 1) == sum) {    // equal parts
                    dp[i][j] = Math.max(left[i][k], right[k+1][j]);
                } else {    // left half > right half
                    dp[i][j] = Math.max(k == i ? 0 : left[i][k-1], k == j ? 0 : right[k+1][j]);
                }
                left[i][j] = Math.max(left[i][j-1], sum + dp[i][j]);
                right[i][j] = Math.max(right[i+1][j], sum + dp[i][j]);
            }
        }
        return dp[0][n-1];
    }


    // only 1 version -> NO dialgonal
    int sol4(int[] stoneValue) {
        int n = stoneValue.length;
        int[] pre = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            pre[i] = pre[i - 1] + stoneValue[i - 1];
        }

        int[][] dp = new int[n][n];
        int[][] left = new int[n][n];
        int[][] right = new int[n][n];

        // Base cases
        for (int i = 0; i < n; i++) {
            left[i][i] = right[i][i] = stoneValue[i];
        }

        // Process from back to front for i, and left to right for j
        for (int i = n - 1; i >= 0; i--) {
            int k = i; // Pointer k initialized at i for current row
            for (int j = i + 1; j < n; j++) {
                int sum = pre[j + 1] - pre[i];

                // Advance k forward while left half < right half
                while ((pre[k + 1] - pre[i]) << 1 < sum) {
                    k++;
                }

                int leftHalf = pre[k + 1] - pre[i];

                // Exact condition requested:
                if ((leftHalf << 1) == sum) {    // Equal parts
                    dp[i][j] = Math.max(left[i][k], right[k + 1][j]);
                } else {    // Left half > right half at index k
                    dp[i][j] = Math.max(k == i ? 0 : left[i][k - 1], k == j ? 0 : right[k + 1][j]);
                }

                // Update helper tables
                left[i][j] = Math.max(left[i][j - 1], sum + dp[i][j]);
                right[i][j] = Math.max(right[i + 1][j], sum + dp[i][j]);
            }
        }

        return dp[0][n - 1];
    }


    public int stoneGameV(int[] stoneValue) {
        int n = stoneValue.length;
        pre = new int[n+1];
        for(int i=0; i<n; i++)
            pre[i+1] = pre[i]+stoneValue[i];
        
        // return sol(0,n-1,stoneValue);

        // int [][]dp = new int[n][n];
        // for(int i=0; i<n; i++)Arrays.fill(dp[i],-1);
        // return sol1(0,n-1,stoneValue,dp);

        // return sol21(stoneValue);
        // return sol22(stoneValue);

        // return sol31(stoneValue);
        // return sol32(stoneValue);

        return sol4(stoneValue);
    }
}

// best O(n^2 logn) even O(n^2) best
/**
class Solution {
    // returns first index where sum of left half >= sum of right half
    private int search(int[] pre, int l, int r) {
        int sum = pre[r+1] - pre[l], L = l;
        while(l < r) {
            int m = l + ((r - l) >> 1);
            if(((pre[m+1] - pre[L]) << 1) >= sum) {
                r = m;
            } else {
                l = m + 1;
            }
        }
        return l;
    }
    public int stoneGameV(int[] stoneValue) {
        int n = stoneValue.length;
        int[] pre = new int[n+1];
        for(int i = 1; i <= n; i++) {
            pre[i] = pre[i-1] + stoneValue[i-1];
        }
        int[][] dp = new int[n][n], left = new int[n][n], right = new int[n][n];
        for(int i = 0; i < n; i++) {
            left[i][i] = right[i][i] = stoneValue[i];
        }
        for(int l = 1; l < n; l++) {
            for(int i = 0; i < n-l; i++) {
                int j = i+l, k = search(pre, i, j);
                int sum = pre[j+1] - pre[i], leftHalf = pre[k+1] - pre[i];
                if((leftHalf << 1) == sum) {    // equal parts
                    dp[i][j] = Math.max(left[i][k], right[k+1][j]);
                } else {    // left half > right half
                    dp[i][j] = Math.max(k == i ? 0 : left[i][k-1], k == j ? 0 : right[k+1][j]);
                }
                left[i][j] = Math.max(left[i][j-1], sum + dp[i][j]);
                right[i][j] = Math.max(right[i+1][j], sum + dp[i][j]);
            }
        }
        return dp[0][n-1];
    }
}
 */
