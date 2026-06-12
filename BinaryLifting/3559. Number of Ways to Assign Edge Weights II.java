class Solution {
    int mod = 1000000007;
    int [][]arr ;
    int []log;
    int []height ;
    int power1(long base, int exp) {

        long res = 1;
        base %= mod; 
        
        while (exp > 0) {
            if (exp % 2 == 1) {
                res = (res * base) % mod;
            }
            base = (base * base) % mod; 
            exp /= 2;
        }
        return (int) res;
    }
    void dfs(Map<Integer,List<Integer>> adj, int root, int prev, int d){
        
        arr[root][0] = prev;
        height[root] = d;

        int res = 0;
        for(int n : adj.get(root))
            if(n != prev) 
                dfs(adj, n, root, d+1);
    }
    int getKthAncestor(int node, int k) {
        if( node < 0 || k == 0 ) return node;
        int l = log[k];
        // int l = 31 - Integer.numberOfLeadingZeros(k);
        int k1 = 1<<l;
        return getKthAncestor(arr[node][l], k-k1);
    }
    int getKthAncestor1(int node, int k) {
        int j = 0;
        while(k > 0 && node != -1){
            if( (k&1) == 1 ){
                node = arr[node][j];
            }
            k = k>>1;
            j++;
        }
        return node;
    }
    public int[] assignEdgeWeights(int[][] edges, int[][] queries) {
        int n = 0;
        for(int []e: edges) n=Math.max(n,Math.max(e[0],e[1]));
        
        log = new int[n+1];
        for(int i=2; i<=n; i++) log[i] = log[i/2]+1;
        int levels = log[n]+1;
        arr = new int[n+1][levels];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(arr[i], -1);
        }


        Map<Integer,List<Integer>> adj = new HashMap<>();
        for(int []e : edges){
            int a = e[0], b = e[1];
            adj.computeIfAbsent(a,k->new ArrayList<>()).add(b);
            adj.computeIfAbsent(b,k->new ArrayList<>()).add(a);
        }

        height = new int[n+1];
        dfs(adj, 1, -1, 0);


        for(int j=1; j<levels; j++){
            for(int i=1; i<=n; i++){
                int a = arr[i][j-1];
                if( a != -1) arr[i][j] = arr[a][j-1];
                else arr[i][j] = a;
            }
        }

        int q = queries.length;
        int []res = new int[q];
        for(int i=0; i<q; i++){

            int a = queries[i][0], b = queries[i][1];
            // res = height(a)+height(b) - 2*Height(LCA(a,b));

            int h1 = height[a], h2 = height[b];
            if( h1 > h2){
                int temp = a;
                a = b;
                b = temp;
                h1 = height[a];
                h2 = height[b];
            }

            int l = 0, h = h1;
            int ps = -1;

            // while( l<= h){
            //     int mid = (h-l)/2+l;
            //     int d1 = mid, d2 = mid+h2-h1;
            //     int a1 = (mid==0?a:getKthAncestor(a,d1));
            //     int b1 = (d2==0?b:getKthAncestor(b,d2));
            //     if( a1 == b1 ){
            //         ps = a1;
            //         h = mid-1;
            //     }
            //     else l = mid+1;
            // }

            // alta
            int diff = h2-h1;
            int u = a, v = getKthAncestor1(b,diff);
            if( u == v ) ps = u;
            else{
                int maxJump = log[h1];
                while(maxJump >= 0 ){
                    if(arr[u][maxJump] != arr[v][maxJump]){
                        u = arr[u][maxJump];
                        v = arr[v][maxJump];
                    }
                    maxJump --;
                }
                ps = arr[u][0];
            }

            int len = height[a]+height[b] - 2*height[ps];
            res[i] = (len==0?0:power1(2, len-1));

        }

        return res;

    }
}
