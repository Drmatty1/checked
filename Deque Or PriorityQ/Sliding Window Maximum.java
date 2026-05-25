class Solution {
    
    int[] sol1(int[] nums, int k){
        
        int n = nums.length;
        int []ans = new int[n-k+1];
        PriorityQueue<Integer> pq = new PriorityQueue<>((a,b)->nums[b]-nums[a]);
 
        for(int i = 0; i<n; i++){
            
            // add in current window
            pq.add(i);

            // remove form current window
            while(!pq.isEmpty() && pq.peek() <= i-k ){
                pq.poll();
            }
            
            // store maximum
            if(i>=k-1)
                ans[i-k+1] = nums[pq.peek()];

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
