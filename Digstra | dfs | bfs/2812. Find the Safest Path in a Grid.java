class Solution {
    int [][]dir = {{0,1},{0,-1},{1,0},{-1,0}};

    boolean check( int[][]mat, int i, int j, int t, Set<Integer> vis ){
        int n = mat.length;

        if( mat[i][j] < t ) return false;
        if( i == n-1 && j == i ) return true;

        vis.add(i*n+j);
        boolean ans  = false;

        for(int []d : dir){
            int ni = i+d[0];
            int nj = j+d[1];
            if( ni >=0 && ni < n && nj >= 0 && nj < n &&
                vis.contains(ni*n+nj) == false 
            ){  
                boolean temp = check(mat, ni, nj, t, vis);
                ans |= temp;
            }
        }

        return ans;
    }
    boolean checkBFS(int[][] mat, int t) {
        int n = mat.length;
        if (mat[0][0] < t || mat[n - 1][n - 1] < t) return false;

        Queue<int[]> q = new ArrayDeque<>();
        boolean[][] vis = new boolean[n][n];

        q.add(new int[]{0, 0});
        vis[0][0] = true;

        while (!q.isEmpty()) {
            int[] curr = q.poll();
            int r = curr[0], c = curr[1];

            if (r == n - 1 && c == n - 1) return true;

            for (int[] d : dir) {
                int nr = r + d[0];
                int nc = c + d[1];

                if (nr >= 0 && nr < n && nc >= 0 && nc < n && !vis[nr][nc] && mat[nr][nc] >= t) {
                    vis[nr][nc] = true;
                    q.add(new int[]{nr, nc});
                }
            }
        }
        return false;
    }

    int[][] distMat(List<List<Integer>> grid){
        int n = grid.size();
        int [][]mat = new int[n][n];

        Queue<int []> q = new ArrayDeque<>();
        // Set<Integer> vis = new HashSet<>();
        boolean []vis = new boolean[n*n];

        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(grid.get(i).get(j) == 1){
                    mat[i][j] = 0;
                    q.add(new int[]{i,j}) ;
                    // vis.add(i*n+j);
                    vis[i*n+j] = true;
                }
            }
        }

        while( !q.isEmpty() ){

            int s = q.size();
            for(int t=0; t<s; t++){

                int []curr = q.poll();
                int i=curr[0], j = curr[1];
                int val = mat[i][j];

                for(int []d : dir){
                    int ni = i+d[0];
                    int nj = j+d[1];
                    if( ni >=0 && ni < n && nj >= 0 && nj < n &&
                        vis[ni*n+nj] == false ){
                        mat[ni][nj] = val+1;
                        q.add(new int[]{ni,nj});
                        vis[ni*n+nj] = true;
                    }
                }

            }

        }
        return mat;
    }

    public int maximumSafenessFactor(List<List<Integer>> grid) {

        int n = grid.size();
        int mat[][] = distMat(grid);

        /** 
        Set<Integer> vis;
        int ps = 0;
        int lb = 0, ub = n;

        while( lb <= ub ){
            int mid = (ub-lb)/2+lb;
            vis = new HashSet<>();

            if( checkBFS(mat, mid)  ){
                lb = mid+1;
                ps = mid;
            }
            else{
                ub = mid-1;
            }
        }

        return ps;
        */

        int [][]dist = mat;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[2] - a[2]);
        
        pq.add(new int[]{0, 0, dist[0][0]});
        dist[0][0] = -1; // Mark as visited to prevent re-entry

        while (!pq.isEmpty()) {
            int[] popped = pq.poll();
            int r = popped[0];
            int c = popped[1];
            int currentSafeness = popped[2];

            // Because it's a Max-PQ, the first time we reach the destination, it's the maximum possible safeness factor
            if (r == n - 1 && c == n - 1) {
                return currentSafeness;
            }

            for (int[] d : dir) {
                int nr = r + d[0];
                int nc = c + d[1];

                if (nr >= 0 && nr < n && nc >= 0 && nc < n && dist[nr][nc] != -1) {
                    // The path safeness is bounded by the current path's safety and the next cell's distance to a thief
                    int nextSafeness = Math.min(currentSafeness, dist[nr][nc]);
                    pq.add(new int[]{nr, nc, nextSafeness});
                    dist[nr][nc] = -1; // Mark as visited immediately
                }
            }
        }

        return 0;

    }
}
/*  dist matrix
3   2   1   0
2   3   2   1
1   2   3   2
0   1   2   3
*/
