package perc;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

// import java.lang.IllegalArgumentException;

public class Percolation {
  private WeightedQuickUnionUF u;
  private int n;
  private int last;
  private boolean[] sites; // true means open false means closed.
  private int numOfOpenSites;

  // creates n-by-n grid, with all sites initially blocked
  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException();
    }

    this.n = n;
    last = n*n + 1;
    int length = last + 1;
    u = new WeightedQuickUnionUF(length);
    sites = new boolean[length];

    // virtual top sites
    sites[0] = true;
    sites[last] = true;
  }

  // opens the site (row, col) if it is not open already
  public void open(int row, int col) {
    validate(row, col);

    int index = getIndex(row, col);
    sites[index] = true;
    numOfOpenSites++;

    int temp = index - 1;

    if (col != 1 && sites[temp]) {
      u.union(index, temp);
    }

    temp = index + 1;

    if (col != n && sites[temp]) {
      u.union(index, temp);
    }

    temp = index - n;

    // Cannot condense if loops because of the else statement
    if (row != 1) {
      if (sites[temp]) {
        u.union(index, temp);
      }
    } else {
      u.union(index, 0);
    }

    temp = index + n;

    if (row != n) {
      if (sites[temp]) {
        u.union(index, temp);
      }
    } else {
      u.union(index, last);
    }
  }

  // is the site (row, col) open?
  public boolean isOpen(int row, int col) {
    validate(row, col);
    
    int index = getIndex(row, col);
    if (sites[index]) {
      return true;
    }

    return false;
  }

  // is the site (row, col) full?
  @SuppressWarnings("deprecation")
  public boolean isFull(int row, int col) {
    validate(row, col);

    int index = getIndex(row, col);
    if (u.connected(0, index)) {
      return true;
    }

    return false;
  }

  // returns the number of open sites
  public int numberOfOpenSites() {
    return numOfOpenSites;
  }


  @SuppressWarnings("deprecation")
  public boolean percolates() {
    if (u.connected(0, last)) {
      return true;
    }

    return false;
  }

  // test client
  public static void main(String[] args) {
    // * * * *
    // * * * *
    // * * * *
    // * * * *

    Percolation p = new Percolation(4);

    StdOut.println(p.getIndex(3, 4)); // 12

    p.open(1, 2);
    StdOut.println(p.isOpen(1, 2)); // true
    StdOut.println(p.isOpen(2, 3));  // false
    
    p.open(2, 2);
    p.open(2, 3);
    StdOut.println(p.isFull(2, 3)); // true
    p.open(3, 1);
    StdOut.println(p.isOpen(3, 1)); // true
    StdOut.println(p.isFull(3, 1)); // false

    StdOut.println(p.numberOfOpenSites()); // 4

    p.open(3, 3);
    StdOut.println(p.percolates()); // false
    p.open(4, 3);
    StdOut.println(p.percolates()); // true
  }

  
  private int getIndex(int row, int col) {
    // int index = 1 + (row - 1) * n + col - 1; // the 1 + at the begining covers the virtual top site. This line is commented out because of the 1 + and the - 1

    return (row - 1) * n + col;
  }

  private void validate(int row, int col) {
    if (row < 1 || col < 1 || row > n || col > n) {
      throw new IllegalArgumentException();
    }
  }
}