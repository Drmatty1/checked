class Solution {

    int incSubSeq(int []arr, int prev, int idx, int k, int [][][]dp, boolean [][][]vis){
        if( k == -1 )return 1;
        if( idx == arr.length ) return 0;

        if( vis[prev+1][idx][k] == true )
            return dp[prev+1][idx][k];

        //take
        int a = 0;
        if( prev == -1 || arr[idx] > arr[prev] ){
            a = incSubSeq(arr, idx, idx+1, k-1, dp, vis);
        }

        //skip
        a += incSubSeq(arr, prev, idx+1, k, dp, vis);

        vis[prev+1][idx][k] = true;
        return  dp[prev+1][idx][k] = a;
    }
    int decSubSeq(int []arr, int prev, int idx, int k, int[][][]dp, boolean [][][]vis){
        if( k == -1 ) return 1;
        if( idx == arr.length ) return 0;

        if( vis[prev+1][idx][k] == true )
            return dp[prev+1][idx][k];

        //take
        int a = 0;
        if(  prev == -1 || arr[idx] < arr[prev]  ){
            a = decSubSeq(arr, idx, idx+1, k-1, dp, vis);
        }

        //skip
        a += decSubSeq(arr, prev, idx+1, k, dp, vis);

        vis[prev+1][idx][k] = true;
        return  dp[prev+1][idx][k] = a;
    }
    int sol(int[] rating) {
        
        int n = rating.length;

        int [][][]dp = new int[n+1][n][3];
        boolean [][][]vis = new boolean[n+1][n][3];
        int a = incSubSeq(rating,-1,0,2,dp,vis);
        
        // dp = new int[n+1][n][4];
        vis = new boolean[n+1][n][3];
        int b = decSubSeq(rating,-1,0,2,dp,vis);

        return a+b;
    } 

    class Fenwick{
        int []fenwick;
        int n ;
        Fenwick(int s){
            n = s+1;
            fenwick = new int[n];
        }

        void update(int idx, int val){
            idx = idx+1;
            while(idx < n){
                fenwick[idx] += val;
                idx += idx & (-idx);
            }
        }
        int find( int r ){
            r = r+1;
            int sum = 0;
            while( r > 0 ){
                sum += fenwick[r];
                r -= r & (-r);
            }
            return sum;
        }
    }

    public int numTeams(int[] rating) {
        
        int n = rating.length;
        
        int len = 100001;

        Fenwick f1 = new Fenwick(len);
        Fenwick f2 = new Fenwick(len);

        for(int i=0; i<n; i++){
            int idx = rating[i];
            f2.update(idx, 1);
        }

        int ans = 0;

        for(int i=0; i<n; i++){
            int idx = rating[i];
            f2.update(idx, -1);

            int leftSmall = f1.find(idx-1);
            int leftLarge = i - f1.find(idx);

            int rightsmall = f2.find(idx-1);
            int rightlarge = (n - 1 - i) - f2.find(idx);

            ans += leftSmall*rightlarge + leftLarge*rightsmall;

            f1.update(idx,1);

        }

        return ans;

    } 
}
