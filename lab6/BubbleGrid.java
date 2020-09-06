public class BubbleGrid {
    int [][] grid;
    UnionFind B;
    public BubbleGrid(int [][] GridIn){
        grid=GridIn;
        B=new UnionFind(grid.length*grid[0].length);
        for (int i=1;i< grid.length;i++){
            for (int j=0;j<grid[0].length;j++){
                if (isone(i,j) && isone(i-1,j-1)){
                    B.union(twoToone(i,j),twoToone(i-1,j-1));
                }
                if (isone(i,j) && isone(i-1,j+1)){
                    B.union(twoToone(i,j),twoToone(i-1,j+1));
                }
            }
        }
    }
    private int twoToone(int i,int j){
        return i*grid[0].length+j;
    }
    private boolean isone(int i,int j){
        if (i<0 || i>=grid.length) {
            return false;
        }
        if(j<0||j>=grid[0].length){
            return false;
        }
        if (grid[i][j]==0) {
            return false;
        }
        return true;
    }
    public int[] popBubbles(int[][] darts){
        int T=0;
        int N=-1;
        int [] results=new int[darts.length];
        for (int i=0;i<darts.length;i++){
            N=-1;
            T=twoToone(darts[i][0],darts[i][1]);
            for(int j=0;j<B.size;j++){
                if (B.connected(T,j)){
                    N++;
                }
            }
            results[i]=N;
        }
        return results;
    }
    public static void main(String[] args) {
        int [][] grid={{1,1,0},{1,0,0},{1,1,0},{1,1,1}};
        BubbleGrid A=new BubbleGrid(grid);
        int[][] darts={{1,1},{1,0}};
        int [] results=A.popBubbles(darts);
    }
}
