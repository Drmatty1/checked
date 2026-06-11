class Solution {
    int largestRectangleArea(int[] heights, int k) {
        int []arr = heights;
        int n = heights.length;
        int []suf = new int[n];
        int []pre = new int[n];

        Deque<Integer> st = new ArrayDeque<>();

        for(int i=0; i<n; i++){
            while(!st.isEmpty() && arr[st.peekLast()] >= arr[i] ){
                st.pollLast();
            }
            
            if( st.isEmpty()) pre[i] = -1;
            else pre[i] = st.peekLast();

            st.addLast(i);
        }
        
        st.clear();

        for(int i=n-1; i>=0; i--){
            while(!st.isEmpty() && arr[st.peekLast()] >= arr[i] ){
                st.pollLast();
            }
            
            if( st.isEmpty()){ 
                suf[i] = n;
            }
            else suf[i] = st.peekLast();

            st.addLast(i);
        }

        int res = 0;
        for(int i=0; i<n; i++){
            int l = pre[i];
            int r = suf[i];
            if(r-1>=k && l+1<=k)
                res = Math.max(res, (r-l-1)*arr[i]);
        }

        return res;
       
    }

    int solOP_TwoPointer(int []nums, int k){
        int i=k, j=k, n = nums.length;
        int ans = nums[k];
        int min = nums[k];
        while( i>=0 && j<n){
            ans = Math.max(ans,(j-i+1)*min);
            if( i == 0 ){
                j++;
                if(j<n) min = Math.min(min,nums[j]);
            }
            else if( j==n-1){
                i--;
                if(i>=0) min = Math.min(min,nums[i]);
            }
            else{
                if( nums[i-1] > nums[j+1]){
                    i--;
                    min = Math.min(min,nums[i]);
                }
                else {
                    j++;
                    min = Math.min(min,nums[j]);
                }
            }
            
        }
        return ans;
    }

    int solBS(int[] nums, int k) {
        int n = nums.length;
        int[] leftMin = new int[n];
        int[] rightMin = new int[n];

        // 1. Build leftMin (minimums from k down to 0)
        leftMin[k] = nums[k];
        for (int i = k - 1; i >= 0; i--) {
            leftMin[i] = Math.min(leftMin[i + 1], nums[i]);
        }

        // 2. Build rightMin (minimums from k up to n-1)
        rightMin[k] = nums[k];
        for (int i = k + 1; i < n; i++) {
            rightMin[i] = Math.min(rightMin[i - 1], nums[i]);
        }

        int maxScore = 0;

        // Scenario 1: Left side dictates the minimum
        for (int i = 0; i <= k; i++) {
            int L = leftMin[i];
            
            // Binary search to find the furthest valid 'j' on the right
            int low = k, high = n - 1, bestJ = k;
            while (low <= high) {
                int mid = low + (high - low) / 2;
                if (rightMin[mid] >= L) {
                    bestJ = mid; // This mid is valid, but can we go further right?
                    low = mid + 1;
                } else {
                    high = mid - 1; // Invalid, pull back left
                }
            }
            maxScore = Math.max(maxScore, L * (bestJ - i + 1));
        }

        // Scenario 2: Right side dictates the minimum
        for (int j = k; j < n; j++) {
            int R = rightMin[j];
            
            // Binary search to find the furthest valid 'i' on the left
            int low = 0, high = k, bestI = k;
            while (low <= high) {
                int mid = low + (high - low) / 2;
                // Note: leftMin goes up from 0 to k, so we want the earliest index
                if (leftMin[mid] >= R) {
                    bestI = mid; // This mid is valid, but can we go further left?
                    high = mid - 1; 
                } else {
                    low = mid + 1; // Invalid, pull back right
                }
            }
            maxScore = Math.max(maxScore, R * (j - bestI + 1));
        }

        return maxScore;
    }
    
    int solEfficientPointer(int[] nums, int k) {
        int n = nums.length;
        int[] leftMin = new int[n];
        int[] rightMin = new int[n];

        
        leftMin[k] = nums[k];
        for (int i = k - 1; i >= 0; i--) {
            leftMin[i] = Math.min(leftMin[i + 1], nums[i]);
        }

        rightMin[k] = nums[k];
        for (int i = k + 1; i < n; i++) {
            rightMin[i] = Math.min(rightMin[i - 1], nums[i]);
        }

        int maxScore = 0;

        // Scenario 1: Left side dictates the minimum
        int j = n-1;
        for (int i = 0; i <= k ; i++) {
            int L = leftMin[i];
            
            while(j>k && rightMin[j] < L ) j--;

            maxScore = Math.max(maxScore, L * (j - i + 1));
        }
        
        int i=k;
        for ( j = k; j < n; j++) {
            int R = rightMin[j];
            
            while(i>0 && leftMin[i-1] >= R ) i--;

            maxScore = Math.max(maxScore, R * (j - i + 1));
        }

        return maxScore;
    }

    public int maximumScore(int[] nums, int k) {
        // return largestRectangleArea(nums, k);
        // return solOP(nums,k);
        return solBS(nums, k);
    }
}
