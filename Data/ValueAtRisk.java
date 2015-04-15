package Data;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;

public class ValueAtRisk {
	private double valueAtRisk;
	
	public ValueAtRisk(double[] portfolioReturns, double alpha){
		Percentile percentile = new Percentile();
		this.valueAtRisk = Data.Round(percentile.evaluate(portfolioReturns, alpha),4);
	}
	
	public double getVARValue(){
		return this.valueAtRisk;
	}
	
	public void update(double[] portfolioReturns, double alpha){
		Percentile percentile = new Percentile();
		this.valueAtRisk = Data.Round(percentile.evaluate(portfolioReturns, alpha),4);
	}
}
