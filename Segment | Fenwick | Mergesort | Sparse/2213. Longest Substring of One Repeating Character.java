class Solution {
    class Node{
        int ans;
        int p_len, s_len;
        char p_char, s_char;
        int size;
        Node(int a, int b, int c, char d, char e, int f){
            ans = a;
            p_len = b;
            s_len = c;
            p_char = d;
            s_char = e;
            size = f;
        }
    }
    class Seg{
        Node[] tree ;
        Seg(int n){
            tree = new Node[4*n];
        }
        Node merge(Node a, Node b){

            int ans, p, s, size;

            char l = a.p_char, r = b.s_char;
            
            size = a.size+b.size;
            if(a.p_len == a.size && a.s_char == b.p_char) 
                p = a.size+b.p_len;
            else p = a.p_len;
            if(b.s_len == b.size && a.s_char == b.p_char) 
                s = b.size+a.s_len;
            else
                s = b.s_len;
            
            ans = Math.max(a.ans,b.ans);
            if(a.s_char == b.p_char) ans = Math.max(ans,a.s_len+b.p_len);

            return new Node(ans,p,s,l,r,size);

        }

        void build(int node, String s, int i, int j){
            if(i==j){
                tree[node] = new Node(1,1,1,s.charAt(i),s.charAt(i),1);
                return ;
            }
            int mid = (i+j)/2;
            build(2*node,s,i,mid);
            build(2*node+1,s,mid+1,j);
            
            tree[node] = merge(tree[2*node],tree[2*node+1]);
        }
        void update(int node, int i, int j, int idx, char val ){
            if(i==j){
                tree[node] = new Node(1,1,1,val,val,1);
                return ;
            }
            int mid = (i+j)/2;
            if(idx<=mid)
                update(2*node,i,mid,idx,val);
            else
                update(2*node+1,mid+1,j,idx,val);
            
            tree[node] = merge(tree[2*node],tree[2*node+1]);
        }

    }
    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {
        int n = s.length();
        int l = queryIndices.length;
        int []ans = new int[l];
        Seg tree = new Seg(n);
        tree.build(1,s,0,n-1);
        for(int i=0; i<l; i++){
            tree.update(1,0,n-1,queryIndices[i],queryCharacters.charAt(i));
            ans[i] = tree.tree[1].ans;
        }
        return ans;
    }
}












