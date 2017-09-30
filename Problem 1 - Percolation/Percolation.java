
/**
 * Created by GUNGUI on 9/2/2017.
 */
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int count = 0;
    private final int size;

    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF buf;
    public Percolation(int n){                     // create n-by-n grid, with all sites blocked
        if(n < 1){
            throw new IllegalArgumentException();
        }
        size = n;
        grid = new boolean[n][n];
        uf = new WeightedQuickUnionUF(n*n + 2); // create union data type with @param 2 top and bottom note addition
        buf = new WeightedQuickUnionUF(n*n+1);
        for (boolean[] booleans : grid) {
            for (boolean aBoolean : booleans) {
                aBoolean = false;
            }
        }

    }
    public void open(int row, int col) {      // open site (row, col) if it is not open already

        if(row > size || row < 1 || col > size || col < 1) {
            throw new IllegalArgumentException();
        }
        if(!isOpen(row,col)) {
            grid[row - 1][col - 1] = true;
            count++;
            if (row == 1) {
                uf.union(trans(row, col), 0);
                buf.union(trans(row, col), 0);
            }
            if (row == size) {
                uf.union(trans(row, col), size * size + 1);
            }

            if (col > 1 && isOpen(row, col - 1)) {
                uf.union(trans(row, col), trans(row, col - 1));
                buf.union(trans(row, col), trans(row, col - 1));
            }
            if (col < size && isOpen(row, col + 1)) {
                uf.union(trans(row, col), trans(row, col + 1));
                buf.union(trans(row, col), trans(row, col + 1));
            }
            if (row > 1 && isOpen(row - 1, col)) {
                uf.union(trans(row, col), trans(row - 1, col));
                buf.union(trans(row, col), trans(row - 1, col));
            }
            if (row < size && isOpen(row + 1, col)) {
                uf.union(trans(row, col), trans(row + 1, col));
                buf.union(trans(row, col), trans(row + 1, col));
            }
        }
    }
    public boolean isOpen(int row, int col) {  // is site (row, col) open?
        if(row <1||row>size||col<1||col>size){
            throw new IllegalArgumentException();
        }
        return grid[row - 1][col - 1] == true;
    }
    public boolean isFull(int row, int col)
    {   // is site (row, col) full?
        if(row <1||row>size||col<1||col>size){
            throw new IllegalArgumentException();
        }
        return buf.connected(trans(row,col),0);
    }
    public int numberOfOpenSites(){             // number of open sites
        return count;
    };
    public boolean percolates(){                // does the system percolate?
        return uf.connected(0,size*size+1);
    };
    private int trans(int row,int col) {           //trans coordinate to uf array address
        return (col-1)*size+row;
    }
}