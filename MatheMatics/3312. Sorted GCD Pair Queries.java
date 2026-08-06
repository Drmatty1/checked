class Solution {
    int bs(long[]p, long q, int max){
        int i = 1, j = max;
        int ps = 0;
        while(i<=j){
            int mid = (j-i)/2+i;
            if(p[mid] <= q){
                ps = mid;
                i = mid+1;
            }
            else{
                j = mid-1;
            }
        }
        return ps+1;
    }
    public int[] gcdValues(int[] nums, long[] queries) {
        int n = nums.length;

        int max = 0;
        for(int e: nums) if(e>max) max=e;

        int []freq = new int[max+1];
        for(int e: nums) freq[e] ++;

        // count no mult of x, 
        // how many no contains factor i -> count[i];
        int []count = new int[max+1];

        for(int i=1; i<=max; i++){
            for(int m=i; m<=max; m+=i){
                count[i] += freq[m];
            }
        }

        // pairs[x] containing pairs of mul of x,2x,3x,4x,.....
        long []pairs = new long[max+1];
        for(int i=1; i<=max; i++){
            pairs[i] = (1L*count[i]*(count[i]-1))/2;
        }

        // exact[x] containing pairs of mul of x Or gcd
        long []exact = new long[max+1];
        for(int i=max; i>=1; i--){

            exact[i] = pairs[i];
            for(int j=2*i; j<=max; j+=i){
                exact[i] -= exact[j];
            }
        }

        //prefSum[x] no of pairs of mul 1,2,3,.., x Or gcd 1,2,3,.,x
        long[] prefSum = new long[max+1];
        for(int i=1; i<=max; i++){
            prefSum[i] += prefSum[i-1]+exact[i];
        }

        int q = queries.length;
        int[] ans= new int[q];
        for(int i=0; i<q; i++){
            ans[i] = bs(prefSum, queries[i], max);
        }

        return ans;

    }
}

// Counting Coprime Pairs
// largets gcd of 2 no















