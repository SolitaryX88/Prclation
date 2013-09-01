import java.lang.IllegalArgumentException;
public class PercolationStats {
    private int gridSize, nExpr;
    
    private double[] results;
    private double mean = 0;
    private int t ; 
    public PercolationStats(int N, int T) throws IllegalArgumentException {
        if (T <= 0 || N <= 0)
            throw new IllegalArgumentException();
        t = T;
        gridSize = N;
        nExpr = T;
        results = new double[T];
        int p = 0;
        int q = 0;
        double counter = 0;
        for (int i = 0; i < T; i++) {
            counter = 0;
            Percolation obj = new Percolation(gridSize);
            while (!obj.percolates()) {
                p = 1 + (int) (Math.random() * ((gridSize - 1) + 1));
                q = 1 + (int) (Math.random() * ((gridSize - 1) + 1));
                if (!obj.isOpen(p, q)) {
                    obj.open(p, q);
                    counter++;
                }
            }
            results[i] = counter / (gridSize * gridSize);
        }
    }

    public double mean() {
        double r = 0;
        for (int i = 0; i < results.length; i++) {
            r += results[i];
        }
        mean = r / nExpr;
        return mean;
    }

    public double stddev() {
        if (nExpr == 1)
            return Double.NaN;
        else {
            double q = 0;
            for (int i = 0; i < results.length; i++) {
                q += (results[i] - mean) * (results[i] - mean);
            }
            return Math.sqrt(q / (nExpr - 1));
        }
    }
    
    public double confidenceHi(){
    	return  this.mean() + 1.96 * this.stddev() / Math.sqrt(this.t);
    } 
    public double confidenceLo(){
    	return  this.mean() - 1.96 * this.stddev() / Math.sqrt(this.t);
    }
    
    
    public static void main(String[] args) {
    	int T = Integer.parseInt(args[1]);
        PercolationStats o = new PercolationStats(Integer.parseInt(args[0]), T);
        System.out.println("mean = " + o.mean());
        System.out.println("stddev = " + o.stddev());
        System.out.println("95% confidence interval = "
                + o.confidenceLo() + ", "
                + o.confidenceHi());
    }
}