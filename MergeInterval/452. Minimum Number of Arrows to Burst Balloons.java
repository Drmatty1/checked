class Solution {
    //sort by start
    int sol1(int[][] points) {
        Arrays.sort(points, (a,b)->Integer.compare(a[0],b[0]));
        int n = points.length;
        int count = 1;
        int prevEnd = points[0][1];
        int i=1;
        while(i<n){
            int a = points[i][0], b = points[i][1];
            if( a > prevEnd ){
                count ++;
                prevEnd = b;
            }
            else{
                prevEnd = Math.min(prevEnd,b);
            }
            i++;
        }
        return count;
    }

    //sort by end
    int sol2(int[][] points) {
        Arrays.sort(points, (a,b)->Integer.compare(a[1],b[1]));
        int n = points.length;
        int count = 1;
        int prevEnd = points[0][1];
        int i=1;

        while(i<n){
            int a = points[i][0], b = points[i][1];
            if( a > prevEnd ){
                count ++;
                prevEnd = b;
            }
            i++;
        }
        return count;
    }

    public int findMinArrowShots(int[][] points) {
        return sol2(points);
    }
}
// 1,6   2,8   7,12  10,16
