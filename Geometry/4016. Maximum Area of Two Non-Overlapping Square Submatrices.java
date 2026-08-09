class Solution {
    int [][]dp ;

    //  overall with sol1 O(m*n*log(min(m,n)))
    boolean valid(int [][]mat, int k){

        int r = mat.length, c= mat[0].length;

        int minR = r, minC = c;
        int maxR = 0, maxC = 0;

        for(int i=0; i<r; i++){
            for(int j=0; j<c; j++){
                if(dp[i][j] >= k){
                    minR = Math.min(minR,i);
                    minC = Math.min(minC,j);
                    maxR = Math.max(maxR,i);
                    maxC = Math.max(maxC,j);
                }
            }
        }

        return (maxR-minR >=k) || (maxC-minC >=k);
    }

    //Binary Serach
    int sol1(int[][] mat) {
        
        int r = mat.length, c= mat[0].length;
        
        dp = new int[r+1][c+1];
        for(int i=r-1; i>=0; i--){
            for(int j=c-1; j>=0; j--){
                if(mat[i][j] == 0) continue;
                int a = Math.min(dp[i+1][j],dp[i][j+1]);
                a = Math.min(a,dp[i+1][j+1]);
                dp[i][j] = a+1;
            }
        }
        
        int l = 1, u = Math.max(r,c)/2;

        int ans = 0;
        while(l<=u){
            int mid = (u-l)/2+l;
            if(valid(mat,mid)){
                ans = mid*mid;
                l = mid+1;
            }
            else{
                u = mid-1;
            }
        }
        return ans;
    }

    //Geometrical Property O(m*n)
    int sol_OP(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;

        // dp_br[i][j]: max side length of square with BOTTOM-RIGHT at (i, j)
        int[][] dp_br = new int[m][n];
        // dp_tl[i][j]: max side length of square with TOP-LEFT at (i, j)
        int[][] dp_tl = new int[m][n];

        // 1. Fill dp_br (Bottom-Right DP)
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 1) {
                    if (i == 0 || j == 0) {
                        dp_br[i][j] = 1;
                    } else {
                        dp_br[i][j] = Math.min(dp_br[i - 1][j], 
                                      Math.min(dp_br[i][j - 1], dp_br[i - 1][j - 1])) + 1;
                    }
                }
            }
        }

        // 2. Fill dp_tl (Top-Left DP)
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (mat[i][j] == 1) {
                    if (i == m - 1 || j == n - 1) {
                        dp_tl[i][j] = 1;
                    } else {
                        dp_tl[i][j] = Math.min(dp_tl[i + 1][j], 
                                      Math.min(dp_tl[i][j + 1], dp_tl[i + 1][j + 1])) + 1;
                    }
                }
            }
        }

        // 3. Prefix & Suffix max arrays for regions
        int[] topMax = new int[m];
        int[] bottomMax = new int[m];
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];

        // topMax[r]: max square in rows 0..r
        for (int i = 0; i < m; i++) {
            int rowMax = 0;
            for (int j = 0; j < n; j++) {
                rowMax = Math.max(rowMax, dp_br[i][j]);
            }
            topMax[i] = (i == 0) ? rowMax : Math.max(topMax[i - 1], rowMax);
        }

        // bottomMax[r]: max square in rows r..m-1
        for (int i = m - 1; i >= 0; i--) {
            int rowMax = 0;
            for (int j = 0; j < n; j++) {
                rowMax = Math.max(rowMax, dp_tl[i][j]);
            }
            bottomMax[i] = (i == m - 1) ? rowMax : Math.max(bottomMax[i + 1], rowMax);
        }

        // leftMax[c]: max square in cols 0..c
        for (int j = 0; j < n; j++) {
            int colMax = 0;
            for (int i = 0; i < m; i++) {
                colMax = Math.max(colMax, dp_br[i][j]);
            }
            leftMax[j] = (j == 0) ? colMax : Math.max(leftMax[j - 1], colMax);
        }

        // rightMax[c]: max square in cols c..n-1
        for (int j = n - 1; j >= 0; j--) {
            int colMax = 0;
            for (int i = 0; i < m; i++) {
                colMax = Math.max(colMax, dp_tl[i][j]);
            }
            rightMax[j] = (j == n - 1) ? colMax : Math.max(rightMax[j + 1], colMax);
        }

        // 4. Find max k across all horizontal and vertical splits
        int maxK = 0;

        // Horizontal splits (split after row r)
        for (int r = 0; r < m - 1; r++) {
            int k = Math.min(topMax[r], bottomMax[r + 1]);
            maxK = Math.max(maxK, k);
        }

        // Vertical splits (split after col c)
        for (int c = 0; c < n - 1; c++) {
            int k = Math.min(leftMax[c], rightMax[c + 1]);
            maxK = Math.max(maxK, k);
        }

        return maxK * maxK;
    }

    public int maxArea(int[][] mat) {

        return sol1(mat);
    }
}
