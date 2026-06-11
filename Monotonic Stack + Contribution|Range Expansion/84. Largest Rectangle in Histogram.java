class Solution {
    int sol(int []heights){
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
            res = Math.max(res, (r-l-1)*arr[i]);
        }

        return res;
    }

    int sol1(int []nums){

        int n = nums.length;
        Deque<Integer> st = new ArrayDeque<>();

        int ans = 0;
        for(int i=0; i<=n; i++){

            while(
                !st.isEmpty() && 
                ((i==n) || nums[i]<nums[st.peekLast()])
            ){
                int h = nums[st.pollLast()];
                int left = st.isEmpty()?(-1):st.peekLast();
                int right = i;
                ans = Math.max(ans, h*(right-left-1));
            }

            st.addLast(i);
        }
        return ans;
    }

    int sol2(int []nums){

        int n = nums.length;
        // Deque<Integer> st = new ArrayDeque<>();
        int []st = new int[n];
        int top = -1;

        int ans = 0;
        for(int i=0; i<=n; i++){

            while(
                top != -1 && 
                ((i==n) || nums[i]<nums[st[top]])
            ){
                int h = nums[st[top--]];
                int left = (top == -1)?(-1):st[top];
                int right = i;
                ans = Math.max(ans, h*(right-left-1));
            }

            st[++top] = i;
        }
        return ans;
    }

    public int largestRectangleArea(int[] heights) {
        
        // return sol1(heights);

        return sol2(heights);
       
    }
}
