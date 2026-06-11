class Solution {
    
    int[] sol1(int[] nums, int k){
        
        int n = nums.length;
        int []ans = new int[n-k+1];
        TreeMap<Integer,Integer> tm = new TreeMap<>();
 
        for(int i = 0; i<n; i++){
            
            // add in current window
            tm.put(nums[i], tm.getOrDefault(nums[i],0)+1);

            // remove form current window
            if(!tm.isEmpty() && i-k>=0 ){
                int toRem = nums[i-k];

                tm.put(toRem, tm.get(toRem)-1); 
                if(tm.get(toRem) == 0){
                    tm.remove(toRem);
                }
            }
            
            // store maximum
            if(i>=k-1)
                ans[i-k+1] = tm.lastKey();

        }

        return ans;
    }

    int[] sol2(int[] nums, int k){
        
        int n = nums.length;
        int []ans = new int[n-k+1];
        Deque<Integer> dq = new ArrayDeque<>();
 
        for(int i = 0; i<n; i++){
            
            // add in current window
            while(!dq.isEmpty() && nums[dq.peekLast()] < nums[i]) 
                dq.pollLast();

            dq.addLast(i);

            // remove form current window
            while(!dq.isEmpty() && dq.peek() <= i-k ){
                dq.pollFirst();
            }
            
            // store maximum
            if(i>=k-1)
                ans[i-k+1] = nums[dq.peekFirst()];

        }

        return ans;
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        return sol1(nums,k);

        // return sol2(nums,k);
        
    }
}
