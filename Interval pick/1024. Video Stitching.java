class Solution {
    int solOP(int[][] clips, int time){

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

    int sol2(int[][] clips, int time) {

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

    public int videoStitching(int[][] clips, int time) {
        
        return solOP(clips, time);
       
    }
}
