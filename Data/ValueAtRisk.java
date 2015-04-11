package Data;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;

public class ValueAtRisk {
	private double valueAtRisk;
	
	public ValueAtRisk(double[] portfolioReturns, double alpha){
		Percentile percentile = new Percentile();
		this.valueAtRisk = percentile.evaluate(portfolioReturns, alpha);
	}
	
	public double getValueAtRisk(){
		return this.valueAtRisk;
	}
}
