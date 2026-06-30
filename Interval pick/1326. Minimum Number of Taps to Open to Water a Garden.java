class Solution {
    int videoStitching(int[][] clips, int time) {

        Arrays.sort(clips, (a,b)->Integer.compare(a[0],b[0]));
        
        int n = clips.length;

        Queue<Integer> q = new ArrayDeque<>();
        boolean []vis = new boolean[n];
        int lvl = 1;

        for(int i=0; i<n; i++){
            int []t = clips[i];
            if(t[0] == 0){ 
                q.add(i);
                vis[i] = true;
            }
            else break;
        }

        while( !q.isEmpty() ){

            int s = q.size();

            for(int i=0; i<s; i++){

                int idx = q.poll();
                int a = clips[idx][0], b = clips[idx][1];
                if( b >= time ) return lvl;

                for(int j = idx+1; j<n; j++){

                    if( vis[j] == false && clips[j][0] >= a && clips[j][0] <= b ){
                        q.add(j);
                        vis[j] = true;
                    }

                }

            }

            lvl ++;

        }

        return -1;
    }
    int videoStitching2(int[][] clips, int time){

        Arrays.sort(clips, (a,b)->Integer.compare(a[0],b[0]));
        
        int n = clips.length;
        int currentEnd = 0;
        int i = 0;
        int count = 0;

        while( currentEnd < time ){

            int nextEnd = currentEnd;

            while( i<n && clips[i][0] <= currentEnd ){
                nextEnd = Math.max(clips[i][1], nextEnd);
                i++;
            }

            if( nextEnd == currentEnd ) return -1;

            count++;
            currentEnd = nextEnd;
        }

        return count;

    }

    int solOP(int n, int[] ranges) {
        // maxRight[i] stores the furthest right point we can reach 
        // using a tap that covers the starting point i
        int[] maxRight = new int[n + 1];

        // Step 1: Populate the maxRight array
        for (int i = 0; i <= n; i++) {
            int left = Math.max(0, i - ranges[i]);
            int right = i + ranges[i];
            maxRight[left] = Math.max(maxRight[left], right);
        }

        int reach = 0;      // The current boundary watered by opened taps
        int nextReach = 0;  // The furthest boundary we can reach by opening one more tap
        int count = 0;      // Number of taps opened

        // Step 2: Greedily traverse the garden
        for (int i = 0; i < n; i++) {
            // Update the best potential tap seen so far
            nextReach = Math.max(nextReach, maxRight[i]);

            // When we reach the end of our currently watered zone, we must open a tap
            if (reach == i) {
                // If the best tap available can't move us forward, there's a gap
                if (nextReach == reach) {
                    return -1;
                }
                
                reach = nextReach;
                count++;

                // Early Exit: If we've covered the whole garden, no need to keep looping
                if (reach >= n) {
                    return count;
                }
            }
        }

        return count;
    }
    
    public int minTaps(int n, int[] ranges) {

        // int [][]interval = new int[n+1][2];
        // for(int i=0; i<=n; i++){
        //     int a = Math.max(0,i-ranges[i]);
        //     int b = i+ranges[i];
        //     interval[i] = new int[]{a,b};
        // }
        
        //m-1
        // return videoStitching( interval, n );

        // m-2
        // return videoStitching2( interval, n );

        // m3 Best - Jump game2 Analogy
        return solOP(n, ranges);
    }
}
