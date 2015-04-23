package Data;

import java.util.ArrayList;

import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.apache.commons.math3.stat.regression.SimpleRegression;

public class Extrapolation {
	
	public double[] computeExtrapolation(int days, Data data, int alpha){
		double[][] MQuotes = data.getMQuotes();
		int[] samplingArray = {1,2,5,10,15,20};
		ArrayList<double[][]> extrapolatedData = new ArrayList<double[][]>();
		double[] hurstCoefficient = new double[MQuotes[0].length];
		double[] yaxis = new double[samplingArray.length];
		double[] xaxis = new double[samplingArray.length];
		
		// On forme une arrayList conséquente qui contient tous les log returns (1,2,4,8,16,32 jours) pour tous les actifs
		for(int i = 0; i < samplingArray.length; i++){
		double[][] extrapolatedReturns = data.computeLogReturns(MQuotes, samplingArray[i]);
		extrapolatedData.add(extrapolatedReturns);
		}
		
		for(int j = 0; j < MQuotes[0].length; j++){
			SimpleRegression regression = new SimpleRegression();
			Percentile percentile = new Percentile();
			regression.addData(0,0);
			for(int i = 1; i < extrapolatedData.size(); i++){
				xaxis[i] = Math.log(Math.abs(percentile.evaluate(getColumn(extrapolatedData.get(i),j),alpha)))-Math.log(Math.abs(percentile.evaluate(getColumn(extrapolatedData.get(0),j),alpha)));
				yaxis[i] = Math.log(samplingArray[i]);
				regression.addData(xaxis[i], yaxis[i]);
			}
			if(regression.getRSquare()<0.98){
				System.out.println("questionable accuracy for Hurst coefficient number "+j+" r² = "+regression.getRSquare());
			}
			hurstCoefficient[j] = regression.getSlope();
			System.out.println(1/hurstCoefficient[j]);
		}
		return hurstCoefficient;
	}
	
	private double[] getColumn(double[][] matrix, int index){
		   double[] vector = new double[matrix.length];
			for(int i = 0; i < matrix.length; ++i) {
		      vector[i] = matrix[i][index];
		   }
			return vector;
	}
	
	public double computeAvgHurstCoefficient(int days, Data data, int alpha, double[] weights){
		double[] hurstCoefficient = computeExtrapolation(days, data, alpha);
		double avgHurstCoefficient = 0;
		for(int i = 0; i < hurstCoefficient.length; i++){
			avgHurstCoefficient += weights[i]*(1/hurstCoefficient[i]);
		}
		System.out.println(avgHurstCoefficient);
		return Math.pow(days, avgHurstCoefficient);
	}
}
