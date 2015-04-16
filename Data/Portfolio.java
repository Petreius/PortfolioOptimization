package Data;

import org.apache.commons.math3.stat.descriptive.rank.Percentile;

public class Portfolio{
	private double[] weights;
	//private double[] portfolioLogReturn;
	private double[] portfolioRawReturn;
	private double valueAtRisk;
	
	public Portfolio(Data data){
		double[] weights = new double[data.getMQuotes()[0].length];
		// TODO double[] portfolioLogReturn = new double[data.getMQuotes().length];

		// On procede a un investissement equireparti
		weights[data.getMQuotes()[0].length-1] = 1.0; // le dernier actif complète pour avoir une somme à 1
		for(int i = 0; i < data.getMQuotes()[0].length-1; i++){
			weights[i] = Data.Round(1.0/(data.getMQuotes()[0].length),4);
			weights[data.getMQuotes()[0].length-1] -= weights[i];
		}
		weights[data.getMQuotes()[0].length-1] = Data.Round(weights[data.getMQuotes()[0].length-1],4);
		this.weights = weights;

		// On calcule les RawReturns du portefeuille initial
		this.portfolioRawReturn = computeRawReturn(data, weights);
		// On calcule les LogReturns du portefeuille initial
		//this.portfolioLogReturn = computeLogReturn(data, weights);
		// Calcul de la VAR avec les RawReturns
		this.valueAtRisk = computeVAR(this.portfolioRawReturn, 5.0);
	}
	
	public Portfolio(double[] weights, double[] portfolioRawReturn){
		this.setWeights(weights);
		this.portfolioRawReturn = portfolioRawReturn;
		//this.portfolioLogReturn = portfolioLogReturn;
		this.valueAtRisk = computeVAR(this.portfolioRawReturn, 5.0);
	}
	
	public Portfolio clone(){
		double[] clonedWeights = new double[this.getWeights().length];
		double[] clonedPortfolioRawReturn = new double[this.portfolioRawReturn.length];
		//double[] clonedPortfolioLogReturn = new double[this.portfolioLogReturn.length];
		for(int i = 0; i < this.getWeights().length; i++){
			clonedWeights[i] = this.getWeights()[i];
		}
		for(int i = 0; i < this.getPortfolioRawReturn().length; i++){
			clonedPortfolioRawReturn[i] = this.getPortfolioRawReturn()[i];
		}
		/*for(int i = 0; i < this.getPortfolioLogReturn().length; i++){
			clonedPortfolioLogReturn[i] = this.getPortfolioLogReturn()[i];
		}*/
		Portfolio clone = new Portfolio(clonedWeights, clonedPortfolioRawReturn);
		return clone;
	}
	
	/*public double[] computeLogReturn(Data data, double[] weights){
		double portfolioLogReturn[] = new double[data.getMQuotes().length-1];
		double dayAValue = 0.0;
		double dayBValue = 0.0;
		for(int i = 0; i < data.getMQuotes().length-1; i++){
			for(int j = 0; j < data.getMQuotes()[0].length; j++){
				dayAValue += Data.Round(weights[j]*data.getMQuotes()[i][j],10);
				dayBValue += Data.Round(weights[j]*data.getMQuotes()[i+1][j],10);
			}
			if(dayAValue != 0.0 && dayBValue != 0.0){
			portfolioLogReturn[i] = Data.Round(Math.log(dayBValue/dayAValue),4);
			dayAValue = 0;
			dayBValue = 0;
			}
			else{
			portfolioLogReturn[i] = 0.0;
			dayAValue = 0;
			dayBValue = 0;
			}
		}
		return portfolioLogReturn;
	}*/
	
	public double[] computeRawReturn(Data data, double[] weights){
		double portfolioRawReturn[] = new double[data.getMQuotes().length-1];
		for(int i = 0; i < data.getMQuotes().length-1; i++){
			for(int j = 0; j < data.getMQuotes()[0].length; j++){
				portfolioRawReturn[i] += weights[j]*data.getMRawReturns()[i][j];
			}
			portfolioRawReturn[i] = Data.Round(portfolioRawReturn[i],4);
		}
		return portfolioRawReturn;
	}
	
	public double computeVAR(double[] portfolioReturns, double alpha){
		Percentile percentile = new Percentile();
		double valueAtRisk = 0.0;
		valueAtRisk = Data.Round(percentile.evaluate(portfolioReturns, alpha),4);
		return valueAtRisk;
	}
	
	public double computeExpectedReturn(double[] portfolioReturns){
		Percentile percentile = new Percentile();
		double rendement = 0.0;
		rendement = Data.Round(percentile.evaluate(portfolioReturns, 50),4);
		return rendement;
	}
	
	public void update(Data data){
			this.portfolioRawReturn = computeRawReturn(data, this.weights);
			//this.portfolioLogReturn = computeLogReturn(data, this.weights);
			this.valueAtRisk = computeVAR(this.portfolioRawReturn, 5.0);
	}
	
	public double getEnergy(){
		double ExpectedReturn = computeExpectedReturn(this.portfolioRawReturn);
		double VAR = computeVAR(this.portfolioRawReturn, 5.0);
		double energy = - 10*VAR;
		return energy;
	}
	
	public double[] getWeights(){
		return this.weights;
	}

	public double[] getPortfolioRawReturn(){
		return this.portfolioRawReturn;
	}
	
	/*public double[] getPortfolioLogReturn(){
		return this.portfolioLogReturn;
	}*/
	
	public double getValueAtRisk(){
		return this.valueAtRisk;
	}
	
	public void setWeights(double[] weights) {
		this.weights = weights;
	}
	
	public void setPortfolioRawReturn(Data data, double[] weights){
		this.portfolioRawReturn = computeRawReturn(data, weights);
	}
	
	public void setPortfolioLogReturn(Data data, double[] weights){
		//this.portfolioLogReturn = computeLogReturn(data, weights);
	}
	
	public void setValueAtRisk(double[] portfolioReturn){
		Percentile percentile = new Percentile();
		this.valueAtRisk = Data.Round(percentile.evaluate(portfolioReturn, 5.0),4);
	}
	
	public String toString(){
		String strWeights = "poids : [";
		for(int i=0; i < this.weights.length; i++){
			if(i != this.weights.length-1){
			strWeights += this.weights[i]+"|";
			} else{
				strWeights += this.weights[this.weights.length-1]+"]\nretours arithmetiques : [";
			}
		}
		/*for(int i=0; i < this.portfolioLogReturn.length; i++){
			if(i != this.portfolioLogReturn.length-1){
			strWeights += this.portfolioLogReturn[i]+"|";
			} else{
				strWeights += this.portfolioLogReturn[this.portfolioLogReturn.length-1]+"]\nretours arithmetiques : [";
			}
		}*/
		for(int i=0; i < this.portfolioRawReturn.length; i++){
			if(i != this.portfolioRawReturn.length-1){
			strWeights += this.portfolioRawReturn[i]+"|";
			} else{
				strWeights += this.portfolioRawReturn[this.portfolioRawReturn.length-1]+"]\nV@R = "+this.getValueAtRisk()+"\nrendement = "+this.computeExpectedReturn(this.portfolioRawReturn);
			}
		}
		return strWeights;
	}
}

