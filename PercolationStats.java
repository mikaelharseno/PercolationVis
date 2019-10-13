package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int times, width;
    private Percolation current;
    private double[] fillCount;
    private int trialRow, trialCol;
    private double curX;
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        times = T;
        width = N;
        fillCount = new double[T];
        for (int i = 0; i < T; i = i + 1) {
            current = new Percolation(N);
            while (!current.percolates()) {
                trialRow = StdRandom.uniform(N);
                trialCol = StdRandom.uniform(N);
                current.open(trialRow, trialCol);
                curX = current.numberOfOpenSites() * 1.0;
            }
            fillCount[i] = curX / (width * width);
        }

    }  // perform T independent experiments on an N-by-N grid
    public double mean() {
        double total = 0;
        for (double count : fillCount) {
            total = total + count;
        }
        return total / times;
    }                   // sample mean of percolation threshold
    public double stddev() {
        return StdStats.stddev(fillCount);
    }                 // sample standard deviation of percolation threshold
    public double confidenceLow() {
        return mean() - (stddev() * 1.96 / (Math.sqrt(times)));
    }           // low  endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + (stddev() * 1.96 / (Math.sqrt(times)));
    }         // high endpoint of 95% confidence interval
}
