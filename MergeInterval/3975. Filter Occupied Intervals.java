class Solution {
    List<List<Integer>> sol(int[][] occupiedIntervals, int freeStart, int freeEnd) {
        
        int[][] arr = occupiedIntervals;
        Arrays.sort( arr,  (a,b) -> Integer.compare(a[1],b[1]) )  ;

        List<List<Integer>> res =  new ArrayList<>();

        Stack<int[]> s = new Stack<>();

        for(int i=0; i<arr.length; i++){
            int []curr = arr[i];

            while( !s.isEmpty() && curr[0] <= s.peek()[1] + 1 ){
                int prev[] = s.pop();
                curr = new int[]{ Math.min(prev[0],curr[0]), curr[1]};
            }

            s.add(curr);

        }

        while( !s.isEmpty() ) {

            int []prev = s.pop();
            int a = prev[0], b = prev[1];

            if( freeStart > b || freeEnd < a ){
                res.add(List.of(a, b));
            }
            else{
                // Order is rev as Reversing at last too -> neutral
                if( freeEnd < b ) res.add(List.of( freeEnd+1, b));
                if( freeStart > a ) res.add(List.of(a, freeStart-1));
            }
            
        }

        Collections.reverse(res);
        return res;
    }

    List<List<Integer>> sol1(int[][] occupiedIntervals, int freeStart, int freeEnd) {
        
        int[][] arr = occupiedIntervals;
        Arrays.sort( arr,  (a,b) -> Integer.compare(a[0],b[0]) )  ;

        List<List<Integer>> res =  new ArrayList<>();

        List<int[]> merged = new ArrayList<>();

        for(int i=0; i<arr.length; i++){
            int []interval = arr[i];

            if( merged.isEmpty() ){
                merged.add(interval);
            }
            else{
                int []last = merged.get(merged.size()-1);
                if( last[1] + 1 >= interval[0]  ){
                    last[1] = Math.max(interval[1],last[1]);
                }
                else{
                    merged.add(interval);
                }
            }

        }

        for(int []prev : merged) {

            int a = prev[0], b = prev[1];

            if( freeStart > b || freeEnd < a ){
                res.add(List.of(a, b));
            }
            else{
                if( freeStart > a ) res.add(List.of(a, freeStart-1));
                if( freeEnd < b ) res.add(List.of( freeEnd+1, b));
            }
            
        }

        return res;
    }


    public List<List<Integer>> filterOccupiedIntervals(int[][] occupiedIntervals, int freeStart, int freeEnd) {
        
       return sol1(occupiedIntervals, freeStart, freeEnd );
    }
}
