package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private Integer[] opened;
    private WeightedQuickUnionUF parent, son;
    private int openSites, width, len;
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        width = N;
        len = N * N;
        opened = new Integer[len + 2];
        parent = new WeightedQuickUnionUF(len + 2);
        son = new WeightedQuickUnionUF(len + 2);
        for (int i = 0; i < len; i = i + 1) {
            if (i < rowColToInd(1, 0)) {
                parent.union(i, len);
                son.union(i, len);
            }
            if (i >= rowColToInd(width - 1, 0)) {
                parent.union(i, len + 1);
            }
            opened[i] = 0;
        }
        openSites = 0;
    }               // create N-by-N grid, with all sites initially blocked
    private int rowColToInd(int row, int col) {
        return row * width + col;
    }
    public void open(int row, int col) {
        int target = rowColToInd(row, col);
        if (row >= width || col >= width || row < 0 || col < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (opened[target] == 0) {
            openSites = openSites + 1;
        }
        opened[target] = 1;
        if (row > 0) {
            int top = rowColToInd(row - 1, col);
            if (opened[top] == 1) {
                parent.union(target, top);
                son.union(target, top);
            }
        }
        if (col < width - 1) {
            int right = rowColToInd(row, col + 1);
            if (opened[right] == 1) {
                parent.union(target, right);
                son.union(target, right);
            }
        }
        if (row < width - 1) {
            int bot = rowColToInd(row + 1, col);
            if (opened[bot] == 1) {
                parent.union(target, bot);
                son.union(target, bot);
            }
        }
        if (col > 0) {
            int left = rowColToInd(row, col - 1);
            if (opened[left] == 1) {
                parent.union(target, left);
                son.union(target, left);
            }
        }
    }      // open the site (row, col) if it is not open already
    public boolean isOpen(int row, int col) {
        if (row >= width || col >= width || row < 0 || col < 0) {
            throw new IndexOutOfBoundsException();
        }
        return opened[rowColToInd(row, col)] == 1;
    } // is the site (row, col) open?
    public boolean isFull(int row, int col) {
        if (row >= width || col >= width || row < 0 || col < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (isOpen(row, col)) {
            return parent.connected(len, rowColToInd(row, col))
                    && son.connected(len, rowColToInd(row, col));
        } else {
            return false;
        }
    } // is the site (row, col) full?
    public int numberOfOpenSites() {
        return openSites;
    }          // number of open sites
    public boolean percolates() {
        if (len == 1) {
            return opened[0] == 1;
        }
        return parent.connected(len + 1, len);
    }             // does the system percolate?
    // unit testing (not required)
}
