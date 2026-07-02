class Solution {
    int [][]dir = {{0,1},{0,-1},{1,0},{-1,0}};

    boolean check( int[][]mat, int i, int j, int t, Set<Integer> vis ){
        int n = mat.length, m =  mat[0].length;

        if( i == n-1 && j == m-1 ) return true;

        vis.add(i*m+j);
        boolean ans  = false;

        for(int []d : dir){
            int ni = i+d[0];
            int nj = j+d[1];
            
            if( ni >=0 && ni < n && nj >= 0 && nj < m &&
                vis.contains(ni*m+nj) == false 
            ){  
                if( Math.abs(mat[ni][nj]-mat[i][j]) <= t ){
                    boolean temp = check(mat, ni, nj, t, vis);
                    ans |= temp;
                }
            }
        }

        return ans;
    }
    int solBinarySearch(int[][] heights) {
        int n = heights.length;

        Set<Integer> vis;
        int ps = 0;
        int lb = 0, ub = 0;
        for(int []a: heights) for(int b:a) ub = Math.max(ub,b);

        while( lb <= ub ){
            int mid = (ub-lb)/2+lb;
            vis = new HashSet<>();
            if( check(heights, 0, 0, mid, vis)  ){
                ub = mid-1;
                ps = mid;
            }
            else{
                lb = mid+1;
            }
        }

        return ps;
    }
    public int minimumEffortPath(int[][] heights) {
        int n = heights.length, m =  heights[0].length;

        PriorityQueue<int[]> pq = new PriorityQueue<>(
            (a, b) -> a[2] - b[2]
        );
        
        int [][]dist = heights;
        pq.add(new int[]{0, 0, 0 });

        int [][]res = new int[n][m];
        for(int i=0; i<n; i++) Arrays.fill(res[i], Integer.MAX_VALUE);

        while (!pq.isEmpty()) {
            int[] popped = pq.poll();
            int r = popped[0];
            int c = popped[1];
            int currentEffort = popped[2];
           
            if (r == n - 1 && c == m - 1) {
                return currentEffort;
            }

            for (int[] d : dir) {
                int nr = r + d[0];
                int nc = c + d[1];

                if (nr >= 0 && nr < n && nc >= 0 && nc < m ) {
                    
                    int nextEffort = Math.abs(dist[r][c]-dist[nr][nc]);

                    int totalEffort = Math.max(currentEffort, nextEffort);
                    
                    if( totalEffort < res[nr][nc] ){
                        res[nr][nc] = totalEffort;
                        pq.add(new int[]{nr, nc, totalEffort});
                    }
                }
            }
        }

        return 0;
        
    }
}
