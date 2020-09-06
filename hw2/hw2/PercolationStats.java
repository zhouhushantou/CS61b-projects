package hw2;
import java.util.Random;
public class PercolationStats {
    // perform T independent experiments on an N-by-N grid
    double [] stat;
    int NT;
    public PercolationStats(int N, int T, PercolationFactory pf){
        Percolation A;
        NT=T;
        Random rand = new Random();
        stat=new double[T];
        for (int i=0;i<T;i++){
            A=pf.make(N);
            while (!A.percolates()){
                A.open(rand.nextInt(N),rand.nextInt(N));
            }
            stat[i]=((double)A.numberOfOpenSites())/N/N;
        }
    }
    // sample mean of percolation threshold
    public double mean(){
        double statsum=0;
        for (int i=0;i<NT;i++){
            statsum+=stat[i];
        }
        return statsum/NT;
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        double statmean=this.mean();
        double statdevsum=0;
        for (int i=0;i<NT;i++){
            statdevsum+=(stat[i]-statmean)*(stat[i]-statmean);
        }
        return java.lang.Math.sqrt(statdevsum/NT);
    }
    // low endpoint of 95% confidence interval
    public double confidenceLow(){
        return this.mean()-2*this.stddev();
    }
    // high endpoint of 95% confidence interval
    public double confidenceHigh(){
        return this.mean()+2*this.stddev();
    }

   public static void main(String[] args) {
       PercolationFactory pf = new PercolationFactory();
       PercolationStats B = new PercolationStats(5, 10000, pf);
       System.out.println("mean of percolation threshold: " + B.mean());
       System.out.println("standard devation of percolation threshold: " + B.stddev());
       System.out.println("confidence low of percolation threshold: " + B.confidenceLow());
       System.out.println("confidence high of percolation threshold: " + B.confidenceHigh());
   }
}
