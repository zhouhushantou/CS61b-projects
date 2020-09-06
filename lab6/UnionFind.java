public class UnionFind{

    // TODO - Add instance variables?
    public class vertex{
        int data=0;
        public vertex(int i){
            data=i;
        }
    }
     /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    vertex[] Value;
    int [] Parent;
    public int size=0;
    public UnionFind(int n) {
        size=n;
        Value=new vertex[n];
        Parent=new int[n];
        for(int i=0;i<n;i++){
            Parent[i]=-1;
            Value[i]=new vertex(i);
        }
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        if (vertex>=size|| vertex<0){
            throw  new IllegalArgumentException("illegal vertex!");
        }
        return;
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        validate(v1);
        return -Parent[find(v1)];
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        validate(v1);
          return Parent[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        validate(v1);
        validate(v2);
        if (find(v1)==find(v2)){
            return true;
        }
        return false;
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        validate(v1);
        validate(v2);
        if(connected(v1,v2)==true){
            return;
        }
        int root1=find(v1);
        int root2=find(v2);
        if (sizeOf(v2)>sizeOf(v1)){
            Parent[root2]+=Parent[root1];
            Parent[root1]=root2;
        }
        else {
            Parent[root1]+=Parent[root2];
            Parent[root2]=root1;
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        validate(vertex);
        if (Parent[vertex]<0){
           return vertex;
        }
        return find(Parent[vertex]);
    }
    public static void main(String[] args){
        UnionFind A=new UnionFind(16);
        A.union(1,2);
        A.union(1,3);
        A.union(5,6);
        A.union(7,8);
        A.union(3,5);
    }

}
