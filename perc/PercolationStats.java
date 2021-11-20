package perc;

import perc.*;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

// import java.lang.IllegalArgumentException;
// import java.lang.Math;

public class PercolationStats {
  private double x_mean;
  private double std_dev;
  private double[] x_i;
  private int T;

  // perform independent trials on an n-by-n grid
  public PercolationStats(int n, int trials) {
    T = trials;
    if (n <= 0 || trials <= 0) {
      throw new IllegalArgumentException();
    }

    x_i = new double[trials];

    double sum = 0;

    for (int i=0; i < trials; i++) {
      Percolation p = new Percolation(n);
      while (!p.percolates()) {
        int row = StdRandom.uniform(n) + 1; // so now the range is [1, n+1) for integers
        int col = StdRandom.uniform(n) + 1;
        p.open(row, col);
      }
      double x = (double)p.numberOfOpenSites() / (n*n);
      x_i[i] = x;

      sum += x;
    }

    x_mean = sum / (double)trials;

    std_dev = StdStats.stddev(x_i);
  }

  // sample mean of percolation threshold
  public double mean() {
    return x_mean;
  }

  // sample standard deviation of percolation threshold
  public double stddev() {
    return std_dev;
  }

  // low endpoint of 95% confidence interval
  public double confidenceLo() {
    return x_mean - 1.96*std_dev/Math.sqrt(T);
  }

  // high endpoint of 95% confidence interval
  public double confidenceHi() {
    return x_mean + 1.96*std_dev/Math.sqrt(T);
  }

  // test client (see below)
  public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);
    int T = Integer.parseInt(args[1]);

    PercolationStats ps = new PercolationStats(n, T);

    StdOut.println("mean                    = " + ps.mean());
    StdOut.println("stddev                  = " + ps.stddev());
    StdOut.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
  }
}