class Solution {

    // Sparse Table O(nlogn) -> RMQ Family
    int[] sol_1(int[] nums, int k) {
        int n = nums.length;
        int []log = new int[n+1];
        for(int i=2; i<=n; i++) log[i] = log[i/2]+1;
        int levels = log[n]+1;

        int [][] s = new int[n][levels];
        for(int i=0; i<n; i++) s[i][0] = nums[i];

        for(int j=1; j<levels; j++){
            for(int i = 0; i<(n-(1<<j)+1); i++ ){
                s[i][j] = Math.max(s[i][j-1],s[i+(1<<(j-1))][j-1]);
            }
        }

        // for(int j=0; j<levels; j++){
        //     for(int i=0; i<n; i++) System.out.print(s[i][j]+" ");
        //     System.out.println();
        // }

        int J = log[k];
        int []ans = new int[n-k+1];
        for(int i=0; i<n-k+1; i++){
            int r = i+k-1;
            ans[i] = Math.max(s[i][J],s[r-(1<<J)+1][J]); 
        }

        return ans;
    }

    // PriorityQueue O(nlogn) ->  Searching/Sorting family
    int[] sol_2(int[] nums, int k) {
        int n = nums.length;
        int []ans = new int[n-k+1];

        PriorityQueue<int []> pq = new PriorityQueue<>((a,b)->b[0]-a[0]);
        for(int i=0; i<n; i++){
            pq.add(new int[]{nums[i],i});
            if(i>=k-1){
                while(pq.peek()[1] < (i-k+1)) pq.poll();
                ans[i-k+1] = pq.peek()[0];
            }
        }
        return ans;
    }
    // TreeMap O(nlogn) ->  Searching/Sorting family
    int[] sol_3(int[] nums, int k) {
        int n = nums.length;
        int []ans = new int[n-k+1];

        TreeMap<Integer,Integer> freq = new TreeMap<>();
        for(int i=0; i<n; i++){
            int c = nums[i];
            freq.put(c, freq.getOrDefault(c,0)+1);
            if(i>=k){
                int p = nums[i-k];
                int oldFreq = freq.get(p);
                freq.put(p, oldFreq-1);
                if(oldFreq==1) freq.remove(p);
            }
            if(i>=k-1) ans[i-k+1] = freq.lastKey();
        }
        return ans;
    }

    // Deque O(n) !! Best -> Maintain Monotonicity
    int[] sol_4(int[] nums, int k) {
        int n = nums.length;
        int []ans = new int[n-k+1];

        Deque<Integer> dq = new ArrayDeque<>();

        for(int i=0; i<n; i++){

            int c = nums[i];
            while( !dq.isEmpty() && nums[dq.peekLast()] < c) 
                dq.pollLast();
            dq.addLast(i);

            while( dq.peekFirst() <= (i-k) ) dq.pollFirst();
            
            if(i>=k-1) ans[i-k+1] = nums[dq.peekFirst()];

        }
        return ans;
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int []ans = new int[n-k+1];

        Deque<Integer> dq = new ArrayDeque<>();

        for(int i=0; i<n; i++){

            int c = nums[i];
            while( !dq.isEmpty() && nums[dq.peekLast()] < c) 
                dq.pollLast();
            dq.addLast(i);

            while( dq.peekFirst() <= (i-k) ) dq.pollFirst();
            
            if(i>=k-1) ans[i-k+1] = nums[dq.peekFirst()];

        }
        return ans;
    }
}
