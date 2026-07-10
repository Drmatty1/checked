class Solution {

    public int[] sol(int n, int[] nums, int maxDiff, int[][] queries) {
        int[] maxJump = new int[n];

        int j = 0;
        for (int i = 0; i < n; i++) {
            if (j < i) j = i;
            while (j + 1 < n && nums[j + 1] - nums[i] <= maxDiff) {
                j++;
            }
            maxJump[i] = j;
        }

        int LOG = 1;
        while ((1 << LOG) <= n) LOG++;

        int[][] up = new int[n][LOG];
        for (int i = 0; i < n; i++) {
            up[i][0] = maxJump[i];
        }

        for (int k = 1; k < LOG; k++) {
            for (int i = 0; i < n; i++) {
                up[i][k] = up[up[i][k - 1]][k - 1];
            }
        }

        int m = queries.length;
        int[] ans = new int[m];

        for (int q = 0; q < m; q++) {
            int u = Math.min(queries[q][0], queries[q][1]);
            int v = Math.max(queries[q][0], queries[q][1]);

            if (u == v) {
                ans[q] = 0;
                continue;
            }

            if (maxJump[u] == u) {
                ans[q] = -1;
                continue;
            }

            int cur = u;
            int steps = 0;

            for (int k = LOG - 1; k >= 0; k--) {
                int nxt = up[cur][k];

                if(nxt == cur) break;  // loop

                if (nxt < v ) {
                    cur = nxt;
                    steps += (1 << k);
                }
            }

            if (up[cur][0] >= v) ans[q] = steps + 1;
            else ans[q] = -1;
        }

        return ans;
    }

    public int[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        int[][] arr = new int[n][2];
        for (int i = 0; i < n; i++) arr[i] = new int[]{nums[i], i};

        Arrays.sort(arr, Comparator.comparingInt(a -> a[0]));

        int[] inv = new int[n];
        int[] sortedNums = new int[n];
        for (int i = 0; i < n; i++) {
            sortedNums[i] = arr[i][0];
            inv[arr[i][1]] = i;
        }

        for (int i = 0; i < queries.length; i++) {
            queries[i][0] = inv[queries[i][0]];
            queries[i][1] = inv[queries[i][1]];
        }

        return sol(n, sortedNums, maxDiff, queries);
    }
}
