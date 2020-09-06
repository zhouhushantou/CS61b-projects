package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    int[][] grid;
    private int size;
    private int openSize;
    private int nRow;
    WeightedQuickUnionUF set;
    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        //0:blocked
        //1:open
        //2:full
        if (N <= 0) {
            throw new IllegalArgumentException("N should not be smaller than 1!");
        }
        grid = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = 0;
            }
        }
        size = N * N;
        nRow = N;
        openSize=0;
        set = new WeightedQuickUnionUF(size+2);
        for(int i=0;i<N;i++){
            set.union(size,twoToOne(0,i));
            set.union(size+1,twoToOne(N-1,i));
        }
    }
    //is valid row and col?
    private boolean isValid(int row, int col){
        if (row < 0 || row >= nRow) {
            return false;
        }
        if (col < 0 || col >= nRow) {
            return false;
        }
        return true;
    }
    //change two dimension array row and col to one dimension index
    private int twoToOne(int row, int col) {
        isValid(row,col);
        return row * nRow + col;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isValid(row, col)) {
            return;
        }
        if (grid[row][col] == 0) {
            grid[row][col] = 1;
            openSize++;
            //up
            if (isValid(row - 1, col)) {
                if (isOpen(row - 1, col)) {
                    set.union(twoToOne(row, col), twoToOne(row - 1, col));
                }
            }
            //down
            if (isValid(row + 1, col)) {
                if (isOpen(row + 1, col)) {
                    set.union(twoToOne(row, col), twoToOne(row + 1, col));
                }
            }
            //right
            if (isValid(row, col + 1)) {
                if (isOpen(row, col + 1)) {
                    set.union(twoToOne(row, col), twoToOne(row, col + 1));
                }
            }
            //left
            if (isValid(row, col - 1)) {
                if (isOpen(row, col - 1)) {
                    set.union(twoToOne(row, col), twoToOne(row, col - 1));
                }
            }
        }
        return;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!isValid(row,col)){
            return false;
        }
        if (grid[row][col]>0) {
            return true;
        }
        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isValid(row,col)){
            return false;
        }
       return set.connected(twoToOne(row,col),size);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSize;
    }

    // does the system percolate?
    public boolean percolates() {
        if (set.connected(size, size + 1)) {
            return true;
        }
        return false;
    }

    // use for unit testing (not required, but keep this here for the autograder)
    public static void main(String[] args) {
        Percolation A = new Percolation(4);
        A.open(1, 0);
        A.open(2, 0);
        A.open(3, 0);
        System.out.println(A.percolates());
        A.open(0, 0);
        System.out.println(A.percolates());
    }
}
