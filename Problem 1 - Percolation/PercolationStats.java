
/**
 * Created by GUNGUI on 9/2/2017.
 */
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] numOfFraction;
    private double mean,std;
    public PercolationStats(int n, int trials){
        if(n<1||trials<1){
            throw new IllegalArgumentException();
        }
        int row,col,count;
        numOfFraction = new double[trials];

        for(int i=0; i < trials; i++){
            Percolation perc = new Percolation(n);
            count = 0;
            while(!perc.percolates()){
                row = StdRandom.uniform(1,n+1);
                col = StdRandom.uniform(1,n+1);

                if(!perc.isOpen(row,col)){
                    perc.open(row,col);
                    count++;
                }
            }
            numOfFraction[i] = (double)count / (n*n);
        }
        std = StdStats.stddev(numOfFraction);
        mean = StdStats.mean(numOfFraction);
    }    // perform trials independent experiments on an n-by-n grid
    public double mean(){
        return mean;
    }                          // sample mean of percolation threshold
    public double stddev(){
        return std;
    }                        // sample standard deviation of percolation threshold
    public double confidenceLo(){
        return mean - 1.96*std/Math.sqrt(numOfFraction.length);
    }                  // low  endpoint of 95% confidence interval
    public double confidenceHi(){
        return mean + 1.96*std/Math.sqrt(numOfFraction.length);
    }                  // high endpoint of 95% confidence interval

    public static void main(String[] args){
        int sizeOfGrid,numOfTrials;
        sizeOfGrid = Integer.parseInt(args[1]);
        numOfTrials = Integer.parseInt(args[1]);
        PercolationStats pst = new PercolationStats(sizeOfGrid,numOfTrials);
        System.out.println("95/% confidence interval ="+"["+pst.confidenceLo()+" "+pst.confidenceHi()+"]");
    }        // test client (described below)
}