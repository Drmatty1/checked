class Solution {

    public int[] maxSlidingWindow(int[] nums, int k) {
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
}
