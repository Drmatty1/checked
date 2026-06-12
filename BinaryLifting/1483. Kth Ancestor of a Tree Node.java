class TreeAncestor {
    int [][]arr ;
    // int []log;
    public TreeAncestor(int n, int[] parent) {
        // log = new int[n+1];
        // log[1] = 0;
        // for(int i=2; i<=n; i++) log[i] = log[i/2]+1;

        // int levels = log[n]+1;
        int levels = 31 - Integer.numberOfLeadingZeros(n)+1;

        arr = new int[n][levels];
        for(int i=0; i<n; i++) arr[i][0] = parent[i];

        for(int j=1; j<levels; j++){
            for(int i=0; i<n; i++){
                int a = arr[i][j-1];
                if( a != -1) arr[i][j] = arr[a][j-1];
                else arr[i][j] = a;
            }
        }

    }
    
    public int getKthAncestor1(int node, int k) {
        if( node < 0 || k == 0 ) return node;
        if(k == 1) return arr[node][0];
        // int l = log[k];
        int l = 31 - Integer.numberOfLeadingZeros(k);
        int k1 = 1<<l;
        return getKthAncestor(arr[node][l], k-k1);
    }
    public int getKthAncestor(int node, int k) {
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
}

/**
 * Your TreeAncestor object will be instantiated and called as such:
 * TreeAncestor obj = new TreeAncestor(n, parent);
 * int param_1 = obj.getKthAncestor(node,k);
 */
