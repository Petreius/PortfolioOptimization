package Data;

import org.apache.commons.math3.stat.descriptive.rank.Percentile;

public class Portfolio{
	private double[] weights;
	private double[] expectedLogReturn;
	private double[] expectedRawReturn;
	private double valueAtRisk;
	
	public Portfolio(Data data){
		double[] weights = new double[data.getMQuotes()[0].length];
		// TODO double[] expectedLogReturn = new double[data.getMQuotes().length];

		// On procede a un investissement equireparti
		weights[data.getMQuotes()[0].length-1] = 1.0; // le dernier actif complète pour avoir une somme à 1
		for(int i = 0; i < data.getMQuotes()[0].length-1; i++){
			weights[i] = Data.Round(1.0/(data.getMQuotes()[0].length),4);
			weights[data.getMQuotes()[0].length-1] -= weights[i];
		}
		weights[data.getMQuotes()[0].length-1] = Data.Round(weights[data.getMQuotes()[0].length-1],4);
		this.weights = weights;

		// On calcule les RawReturns du portefeuille initial
		this.expectedRawReturn = computeRawReturn(data, weights);
		// On calcule les LogReturns du portefeuille initial
		this.expectedLogReturn = computeLogReturn(data, weights);
		// Calcul de la VAR avec les RawReturns
		this.valueAtRisk = new ValueAtRisk(this.expectedRawReturn, 5.0).getVARValue();
	}
	
	public Portfolio(double[] weights){
		this.setWeights(weights);
		//A MODIFIER SELON LE CALCUL DE LA VAR
	}
	
	public Portfolio clone(){
		double[] clonedWeights = new double[this.getWeights().length];
		for(int i = 0; i < this.getWeights().length; i++){
			clonedWeights[i] = this.getWeights()[i];
		}
		Portfolio clone = new Portfolio(clonedWeights);
		return clone;
	}
	
	public double[] computeLogReturn(Data data, double[] weights){
		double expectedLogReturn[] = new double[data.getMQuotes().length];
		double dayAValue = 0.0;
		double dayBValue = 0.0;
		for(int i = 0; i < data.getMQuotes().length-1; i++){
			for(int j = 0; j < data.getMQuotes()[0].length; j++){
				dayAValue += weights[j]*data.getMQuotes()[i][j];
				dayBValue += weights[j]*data.getMQuotes()[i+1][j];
			}
			if(dayAValue != 0.0 && dayBValue != 0.0){
			expectedLogReturn[i] = Data.Round(Math.log(dayBValue/dayAValue),4);
			dayAValue = 0;
			dayBValue = 0;
			}
			else{
			expectedLogReturn[i] = 0.0;
			dayAValue = 0;
			dayBValue = 0;
			}
		}
		return expectedLogReturn;
	}
	
	public double[] computeRawReturn(Data data, double[] weights){
		double expectedRawReturn[] = new double[data.getMQuotes().length];
		for(int i = 0; i < data.getMQuotes().length-1; i++){
			for(int j = 0; j < data.getMQuotes()[0].length; j++){
				expectedRawReturn[i] += weights[j]*data.getMRawReturns()[i][j];
			}
			expectedRawReturn[i] = Data.Round(expectedRawReturn[i],4);
		}
		return expectedRawReturn;
	}
	
	public double getEnergy(){
		ValueAtRisk valueAtRisk = new ValueAtRisk(this.expectedRawReturn, 5.0);
		return valueAtRisk.getVARValue();
	}
	
	public double[] getWeights(){
		return this.weights;
	}

	public double[] getExpectedRawReturn(){
		return this.expectedRawReturn;
	}
	
	public double[] getExpectedLogReturn(){
		return this.expectedLogReturn;
	}
	
	public double getValueAtRisk(){
		return this.valueAtRisk;
	}
	
	public void setWeights(double[] weights) {
		this.weights = weights;
	}
	
	public void setExpectedRawReturn(Data data, double[] weights){
		this.expectedRawReturn = computeRawReturn(data, weights);
	}
	
	public void setExpectedLogReturn(Data data, double[] weights){
		this.expectedLogReturn = computeLogReturn(data, weights);
	}
	
	public void setValueAtRisk(double[] expectedReturn){
		Percentile percentile = new Percentile();
		this.valueAtRisk = Data.Round(percentile.evaluate(expectedReturn, 5.0),4);
	}
	
	public String toString(){
		String strWeights = "[";
		for(int i=0; i < this.weights.length; i++){
			if(i != this.weights.length-1){
			strWeights += this.weights[i]+",";
			} else{
				strWeights += this.weights[this.weights.length-1]+"]";
			}
		}
		return strWeights;
	}
}
