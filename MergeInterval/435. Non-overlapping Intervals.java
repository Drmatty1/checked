class Solution {

    //sort by end
    int sol1(int[][] intervals) {
        if (intervals == null || intervals.length == 0) return 0;

        // 1. Sort by END coordinates
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));

        int count = 0;
        int prevEnd = intervals[0][1];

        // 2. Look for overlaps
        for (int i = 1; i < intervals.length; i++) {
            // If the current interval starts BEFORE the previous one ends,
            // we have an overlap. We greedily eliminate the current interval.
            if (intervals[i][0] < prevEnd) {
                count++;
            } else {
                // No overlap! Update our boundary to the current interval's end.
                prevEnd = intervals[i][1];
            }
        }

        return count;
    }
    
    //sort by start
    int sol2(int[][] intervals) {
        // Arrays.sort(intervals, (a,b) -> {
        //     if(a[0] == b[0]) return Integer.compare(b[1],a[1]);
        //     return Integer.compare(a[0],b[0]);
        // });
        // Simpler sort: just by start time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        int i=1, n = intervals.length;
        int count =  0;

        int [][]it = intervals;
        int []prev = it[0];

        while(i<n){
            int a= it[i][0], b = it[i][1];
            if( b < prev[1] ){
                count++;
                prev = it[i];
            }
            else if( a < prev[1] ){
                count++;
                // prev remain same;
            }
            else{
                prev = it[i];
            }
            i++;
        }

        return count;

    }

    public int eraseOverlapIntervals(int[][] intervals) {
        
        return sol2(intervals);

    }
}
