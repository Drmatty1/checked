class Solution {
    int mod = 1000000007;
    long sol2(int []nums){

        int n = nums.length;
        long []pre = new long[n+1];
        for(int i=0; i<n; i++) pre[i+1] = pre[i]+nums[i];

        // Deque<Integer> st = new ArrayDeque<>();
        int []st = new int[n];
        int top = -1;

        long ans = 0;
        for(int i=0; i<=n; i++){

            while(
                top != -1 && 
                ((i==n) || nums[i]<nums[st[top]])
            ){
                int h = nums[st[top--]];
                int left = (top == -1)?(-1):st[top];
                int right = i;
                long width = pre[right]-pre[left+1];
                ans = Math.max(ans, h*width);
            }

            st[++top] = i;
        }
        return ans%mod;
    }
    public int maxSumMinProduct(int[] nums) {
        return (int)sol2(nums);
    }
}
