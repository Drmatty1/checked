    boolean dfs( int i, List<List<Integer>> adj, int []visited, int []callStack){
        visited[i] = 1;
        callStack[i] = 1;
        boolean ans = false;
        if( adj.get(i).isEmpty() ){
            callStack[i] = 0;
            return false;
        }
        for( int e : adj.get(i) ){
            if( callStack[e] == 1 ) return true;
            if( visited[e] == 0 ){
                ans |= dfs(e,adj,visited,callStack);
            }
        }
        
        callStack[i] = 0;
        return ans;   
    }
    public boolean isCyclic(int V, int[][] edges) {
        // Map<Integer,List<Integer>> adj = new HashMap<>();
        // for( int[]e : edges ) 
        //     adj.computeIfAbsent(e[0], k -> new ArrayList<>()).add(e[1]);

        List<List<Integer>> adj = new ArrayList<>();
        for( int i=0; i<V; i++ ) adj.add(new ArrayList<>());
        for( int[]e : edges ) 
            adj.get(e[1]).add(e[0]);

        int []visited = new int[V];
        int []callStack = new int[V];
        boolean ans = false;
        for( int i = 0; i < V; i++ ){
            if( visited[i] == 0 ){
                ans |= dfs(i,adj,visited,callStack);
            }
            if( ans == true ) return true;
        }
        return ans;
    }
    boolean topoSort( int numCourses, int[][] prerequisites ){
        int V = numCourses;

        List<List<Integer>> adj = new ArrayList<>();
        int []indegree = new int[V];
        for( int i=0; i<V; i++ ) adj.add(new ArrayList<>());
        for( int[]e : prerequisites ){ 
            adj.get(e[1]).add(e[0]);
            indegree[e[0]]++;
        }
        Queue<Integer> q = new LinkedList<>();
        for( int i=0; i<V; i++ ) if(indegree[i]==0) q.add(i);

        int count = 0;
        while( !q.isEmpty() ){

            int curr = q.poll();
            count++;
            for( int j : adj.get(curr) ){
                indegree[j]--;
                if( indegree[j] == 0 ) q.add(j);
            }

        }
        return (count == V);
    }
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        return !isCyclic( numCourses, prerequisites );
        // return topoSort(numCourses, prerequisites);
    }
