class Solution {
    int []spf ;
    class DSU {
        int []par ;
        DSU(int n){
            par =  new int[n+1];
            for(int i=0; i<n;i ++){
                par[i] = i;
            }
        }
        int find(int i){
            if(par[i] == i) return i;
            return par[i] = find(par[i]);
        }
        void union(int a, int b){
            int x = find(a), y = find(b);
            if(x != y){
                par[x] = y;
            }
        }
        void print(){
            for(int e: par) System.out.print(e+" ");
        }
    }
    void spf(int []arr, int max){
        int n = arr.length;

        for(int i=2; i<=max; i++){
            for(int j=i; j<=max; j+=i){
                if(spf[j] == -1){
                    spf[j] = i;
                }
            }
        }
    }
    public boolean gcdSort(int[] nums) {

        int n = nums.length;

        int max = 2;
        for(int e: nums) if(e>max) max=e;
        spf = new int[max+1];
        Arrays.fill(spf,-1);

        DSU dsu = new DSU(max);

        spf(nums,max);
        

        for(int e: nums){
            int a = e;
            while(spf[a] > 1){
                dsu.union(e,spf[a]);
                a/= spf[a];
            }
        }

        // dsu.print();

        int []sorted_arr = nums.clone();
        Arrays.sort(sorted_arr);

        for(int i=0; i<n; i++){
            if(dsu.find(sorted_arr[i]) != dsu.find(nums[i])) return false;
        }

        return true;

    }
}
