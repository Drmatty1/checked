class Solution {
    int sol(int []arr, int k, int i){
        if(i>=arr.length) return 0;
        int score = -10001;
        for(int j=i+1; j<=i+k; j++){
            score = Math.max(score,arr[i]+sol(arr,k,j));
        }
        return score;
    }
    int sol1(int []arr, int k){

        int n = arr.length;
        int []dp = new int[n+1];

        for(int i = n-1; i>=0; i--){

            int score = -10001;
            for(int j=i+1; j<=i+k; j++){
                if(j <= n)
                    score = Math.max(score,arr[i]+dp[j]);
            }
            dp[i] = score;
        }
        return dp[0];
    }

    int sol2(int []arr, int k){

        int n = arr.length;
        int []dp = new int[n+1];
        TreeMap<Integer,Integer> tm = new TreeMap<>();

        tm.put(arr[n-1],1);
        dp[n-1] = arr[n-1];

        for(int i = n-2; i>=0; i--){

            dp[i] = arr[i]+tm.lastKey();

            tm.put(dp[i], tm.getOrDefault(dp[i],0)+1);

            if(i+k < n){
                int toRem = dp[i+k];

                tm.put(toRem, tm.get(toRem)-1); 
                if(tm.get(toRem) == 0){
                    tm.remove(toRem);
                }
            }

        }
        return dp[0];
    }

    int sol21(int []arr, int k){

        int n = arr.length;
        int []dp = new int[n+1];

        PriorityQueue<Integer> pq = new PriorityQueue<>((a,b)->dp[b]-dp[a]);

        pq.add(n-1);
        dp[n-1] = arr[n-1];

        for(int i = n-2; i>=0; i--){

            while( pq.peek() > i+k ){
                pq.poll();
            }

            dp[i] = arr[i]+dp[pq.peek()];

            pq.add(i);

        }
        return dp[0];
    }

    int sol3(int []arr, int k){

        int n = arr.length;
        int []dp = new int[n+1];

        Deque<Integer> dq = new ArrayDeque<>();
        dq.add(n-1);
        dp[n-1] = arr[n-1];

        for(int i = n-2; i>=0; i--){

            while( dq.peek() > i+k ){
                dq.pollFirst();
            }

            dp[i] = arr[i]+dp[dq.peekFirst()];

            while(!dq.isEmpty() && dp[dq.peekLast()] <= dp[i]) 
                dq.pollLast();

            dq.addLast(i);

        }
        return dp[0];
    }

    public int maxResult(int[] nums, int k) {
        // return sol(nums, k, 0);

        // return sol1(nums, k);

        // return sol2(nums,k);

        // return sol21(nums,k);

        return sol3(nums,k);
    }
}
