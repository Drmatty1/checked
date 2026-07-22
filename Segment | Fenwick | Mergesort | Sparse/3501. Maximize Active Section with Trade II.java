class Solution {

    List<List<Integer>> st = new ArrayList<>();

    void sparseTable(List<Integer> arr) {

        st.clear();

        int n = arr.size();
        if (n == 0) return;

        st.add(new ArrayList<>(arr));

        int maxLog = 31 - Integer.numberOfLeadingZeros(n);

        for (int k = 1; k <= maxLog; k++) {
            List<Integer> prev = st.get(k - 1);
            List<Integer> curr = new ArrayList<>();

            int len = 1 << (k - 1);

            for (int i = 0; i + (1 << k) <= n; i++) {
                curr.add(Math.max(prev.get(i), prev.get(i + len)));
            }

            st.add(curr);
        }
    }

    int query(int L, int R) {
        if (L > R) return Integer.MIN_VALUE;

        int len = R - L + 1;
        int k = 31 - Integer.numberOfLeadingZeros(len);

        return Math.max(
                st.get(k).get(L),
                st.get(k).get(R - (1 << k) + 1)
        );
    }

    int lowerBound(List<Integer> list, int target) {
        int lo = 0, hi = list.size();

        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (list.get(mid) >= target)
                hi = mid;
            else
                lo = mid + 1;
        }

        return lo;
    }

    int upperBound(List<Integer> list, int target) {
        int lo = 0, hi = list.size();

        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (list.get(mid) > target)
                hi = mid;
            else
                lo = mid + 1;
        }

        return lo;
    }

    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {

        int n = s.length();

        int cnt1 = 0;
        for (char c : s.toCharArray())
            if (c == '1')
                cnt1++;

        List<Integer> zeroBlocks = new ArrayList<>();
        List<Integer> blockLeft = new ArrayList<>();
        List<Integer> blockRight = new ArrayList<>();

        int i = 0;

        while (i < n) {
            int start = i;

            while (i < n && s.charAt(i) == s.charAt(start))
                i++;

            if (s.charAt(start) == '0') {
                zeroBlocks.add(i - start);
                blockLeft.add(start);
                blockRight.add(i - 1);
            }
        }

        int m = zeroBlocks.size();

        List<Integer> ans = new ArrayList<>();

        if (m < 2) {
            for (int t = 0; t < queries.length; t++)
                ans.add(cnt1);
            return ans;
        }

        List<Integer> tmpSum = new ArrayList<>();

        for (i = 0; i < m - 1; i++)
            tmpSum.add(zeroBlocks.get(i) + zeroBlocks.get(i + 1));

        sparseTable(tmpSum);

        for (int[] q : queries) {

            int l = q[0];
            int r = q[1];

            int leftIdx = lowerBound(blockRight, l);
            int rightIdx = upperBound(blockLeft, r) - 1;

            if (leftIdx >= m || rightIdx < 0 || leftIdx >= rightIdx) {
                ans.add(cnt1);
                continue;
            }

            int firstLen =
                    blockRight.get(leftIdx)
                            - Math.max(blockLeft.get(leftIdx), l)
                            + 1;

            int lastLen =
                    Math.min(blockRight.get(rightIdx), r)
                            - blockLeft.get(rightIdx)
                            + 1;

            if (leftIdx + 1 == rightIdx) {
                ans.add(cnt1 + firstLen + lastLen);
                continue;
            }

            int val1 = firstLen + zeroBlocks.get(leftIdx + 1);
            int val2 = zeroBlocks.get(rightIdx - 1) + lastLen;
            int val3 = query(leftIdx + 1, rightIdx - 2);

            int bestGain = Math.max(val1, Math.max(val2, val3));

            ans.add(cnt1 + bestGain);
        }

        return ans;
    }
}
