class Solution {
    static class FenwickTree {
        int[] bit;
        int n;

        FenwickTree(int n) {
            this.n = n;
            bit = new int[n + 1];
        }

        void update(int idx, int val) {
            idx++;
            while (idx <= n) {
                bit[idx] += val;
                idx += idx & -idx;
            }
        }

        int query(int idx) {
            idx++;
            int sum = 0;
            while (idx > 0) {
                sum += bit[idx];
                idx -= idx & -idx;
            }
            return sum;
        }
    }

    long sol1(int[] nums, int a, int b) { 
        int n = nums.length;
        long []pre = new long[n+1];

        long sum = 0;
        for(int i=0; i<n; i++){
            if(nums[i]%2 == 0) sum += b;
            else sum -= a;
            pre[i+1] = sum;
        }

        long[] sorted = pre.clone();
        Arrays.sort(sorted);

        int m = 0;
        for (long x : sorted) {
            if (m == 0 || x != sorted[m - 1]) {
                sorted[m++] = x;
            }
        }
        long[] uniqueP = Arrays.copyOf(sorted, m);


        FenwickTree ft = new FenwickTree(uniqueP.length);
        long validSubarraysCount = 0;

        for (int j = 0; j <= n; j++) {
            int r = Arrays.binarySearch(uniqueP, pre[j]);

            validSubarraysCount += j - ft.query(r - 1);
            ft.update(r, 1);
        }

        return validSubarraysCount;

    }

    long sol2(int[] nums, int a, int b) { 
        int n = nums.length;
        long []pre = new long[n+1];

        long sum = 0;
        for(int i=0; i<n; i++){
            if(nums[i]%2 == 0) sum += b;
            else sum -= a;
            pre[i+1] = sum;
        }

        long[] uniqueP = Arrays.stream(pre).distinct().sorted().toArray();
        Map<Long, Integer> rank = new HashMap<>();
        for (int i = 0; i < uniqueP.length; i++) {
            rank.put(uniqueP[i], i+1 );
        }


        FenwickTree ft = new FenwickTree(uniqueP.length);

        long validSubarraysCount = 0;

        for(int j=0; j<=n; j++){
            int r = rank.get(pre[j]);

            // j = total elements processed so far
            // query(tree, r - 1) = elements strictly smaller than P[j]
            validSubarraysCount += (j - ft.query( r - 1));

            ft.update(r,1);
        }

        return validSubarraysCount;

    }

    public long countRatioSubarrays(int[] nums, int a, int b) { 
        
        return sol1(nums,a,b);
    }
}
