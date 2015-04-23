package Data;

import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;

public class Portfolio{
	private double[] weights;
	private double[] portfolioLogReturn;
	private double[] portfolioRawReturn;
	private double valueAtRisk;
	
	
	// Un portefeuille c'est : un vecteur de poids (initialement, on investit tout dans un actif), des log-returns sur toute la
	// fenêtre d'observation, la value at risk associée.
	// Pour le moment, on n'utilise que les log-returns...
	public Portfolio(Data data){
		double[] weights = new double[data.getMQuotes()[0].length];

		// On part d'un investissement dans un actif unique
		for(int i = 0; i < data.getMQuotes()[0].length-1; i++){
			weights[i] = 0;
		}
		weights[(int)(Math.random()*(data.getMQuotes()[0].length))]=1;
		this.weights = weights;

		// On calcule les RawReturns du portefeuille initial
		//this.portfolioRawReturn = computeRawReturn(data, weights);
		// On calcule les LogReturns du portefeuille initial
		this.portfolioLogReturn = computeLogReturn(data, weights);
		// Calcul de la VAR avec les RawReturns
		this.valueAtRisk = computeVAR(this.portfolioLogReturn, 5.0);
	}
	
	// constructeur de portefeuille avec une allocation initiale particulière, utile pour la méthode de clonage
	public Portfolio(double[] weights, double[] portfolioLogReturn){
		this.setWeights(weights);
		//this.portfolioRawReturn = portfolioRawReturn;
		this.portfolioLogReturn = portfolioLogReturn;
		this.valueAtRisk = computeVAR(this.portfolioLogReturn, 5.0);
	}
	
	// Permet de cloner un portefeuille avec une certaine allocation initiale
	public Portfolio clone(){
		double[] clonedWeights = new double[this.getWeights().length];
		//double[] clonedPortfolioRawReturn = new double[this.portfolioRawReturn.length];
		double[] clonedPortfolioLogReturn = new double[this.portfolioLogReturn.length];
		for(int i = 0; i < this.getWeights().length; i++){
			clonedWeights[i] = this.getWeights()[i];
		}
		/*for(int i = 0; i < this.getPortfolioRawReturn().length; i++){
			clonedPortfolioRawReturn[i] = this.getPortfolioRawReturn()[i];
		}*/
		for(int i = 0; i < this.getPortfolioLogReturn().length; i++){
			clonedPortfolioLogReturn[i] = this.getPortfolioLogReturn()[i];
		}
		Portfolio clone = new Portfolio(clonedWeights, clonedPortfolioLogReturn);
		return clone;
	}
	
	// Calcul des log-returns d'un portefeuille
	// cette méthode représente environ 70% du temps de calcul... Peut-être amélioré si on n'utilise que les retours arithmétiques
	// en effet, le retour arithmétique du portefeuille est une CL des retours arithmétiques de chacun de ses actifs. En connaissant
	// les actifs que l'on a muté, le retour arithmétique du portefeuille peut être modifié en ne touchant que les actifs concernés.
	public double[] computeLogReturn(Data data, double[] weights){
		double portfolioLogReturn[] = new double[data.getMQuotes().length-1];
		double dayAValue = 0.0;
		double dayBValue = 0.0;
		for(int i = 0; i < data.getMQuotes().length-1; i++){
			for(int j = 0; j < data.getMQuotes()[0].length; j++){
				dayAValue += weights[j]*data.getMQuotes()[i][j];
				dayBValue += weights[j]*data.getMQuotes()[i+1][j];
			}
			if(dayAValue != 0.0 && dayBValue != 0.0){
			portfolioLogReturn[i] = Math.log(dayBValue/dayAValue);
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
	}
	
	// calcul des retours arithmétiques d'un portefeuille
	public double[] computeRawReturn(Data data, double[] weights){
		double portfolioRawReturn[] = new double[data.getMQuotes().length-1];
		for(int i = 0; i < data.getMQuotes().length-1; i++){
			for(int j = 0; j < data.getMQuotes()[0].length; j++){
				portfolioRawReturn[i] += weights[j]*data.getMRawReturns()[i][j];
			}
			portfolioRawReturn[i] = portfolioRawReturn[i];
		}
		return portfolioRawReturn;
	}
	
	// calcul d'un centile (VAR...) de la liste des retours du portefeuille (pour le moment, les log-returns)
	// l'appel à cette méthode représente environ 30% du temps de calcul total. Un tri efficace et un calcul du centile à la main
	// peut éventuellement améliorer cela
	public double computeVAR(double[] portfolioReturns, double alpha){
		Percentile percentile = new Percentile();
		double valueAtRisk = 0.0;
		valueAtRisk = percentile.evaluate(portfolioReturns, alpha);
		return valueAtRisk;
	}

	// calcule l'espérance du retour du portefeuille.
	public double computeExpectedReturn(double[] portfolioReturns){
		Mean mean = new Mean();
		double rendement = 0.0;
		rendement = mean.evaluate(portfolioReturns);
		return rendement;
	}

	// rafraichissement des attributs après une mutation. La mutation s'occupe de rafraichir les poids.
	public void update(Data data){
			//this.portfolioRawReturn = computeRawReturn(data, this.weights);
			this.portfolioLogReturn = computeLogReturn(data, this.weights);
			this.valueAtRisk = computeVAR(this.portfolioLogReturn, 5.0);
	}
	
	// l'energie est un compromis entre la minimisation du risque et la distance au retour que l'on cherche à obtenir.
	public double getEnergy(double targetReturn){
		double ExpectedReturn = computeExpectedReturn(this.portfolioLogReturn);
		double VAR = computeVAR(this.portfolioLogReturn, 5.0);
		double energy = 100*Math.abs(targetReturn-ExpectedReturn)-VAR;
		return energy;
	}
	
	// getters et setters
	public double[] getWeights(){
		return this.weights;
	}

	public double[] getPortfolioRawReturn(){
		return this.portfolioRawReturn;
	}
	
	public double[] getPortfolioLogReturn(){
		return this.portfolioLogReturn;
	}
	
	public double getValueAtRisk(){
		return this.valueAtRisk;
	}
	
	public void setWeights(double[] weights) {
		this.weights = weights;
	}
	
	/*public void setPortfolioRawReturn(Data data, double[] weights){
		this.portfolioRawReturn = computeRawReturn(data, weights);
	}*/
	
	public void setPortfolioLogReturn(Data data, double[] weights){
		this.portfolioLogReturn = computeLogReturn(data, weights);
	}
	
	public void setValueAtRisk(double[] portfolioReturn){
		Percentile percentile = new Percentile();
		this.valueAtRisk = percentile.evaluate(portfolioReturn, 5.0);
	}
	
	// affichage d'un portefeuille (son poids, ses retours logarithmiques, sa var, l'espérance de son rendement)
	public String toString(){
		String strWeights = "poids : [";
		for(int i=0; i < this.weights.length; i++){
			if(i != this.weights.length-1){
			strWeights += this.weights[i]+"|";
			} else{
				strWeights += this.weights[this.weights.length-1]+"]\nretours logarithmiques : [";
			}
		}
		for(int i=0; i < this.portfolioLogReturn.length; i++){
			if(i != this.portfolioLogReturn.length-1){
			strWeights += this.portfolioLogReturn[i]+"|";
			} else{
				strWeights += this.portfolioLogReturn[this.portfolioLogReturn.length-1]+"]\nV@R = "+this.getValueAtRisk()+"\nrendement = "+this.computeExpectedReturn(this.portfolioLogReturn);
			}
		}
		/*for(int i=0; i < this.portfolioRawReturn.length; i++){
			if(i != this.portfolioRawReturn.length-1){
			strWeights += this.portfolioRawReturn[i]+"|";
			} else{
				strWeights += this.portfolioRawReturn[this.portfolioRawReturn.length-1]+"]\nV@R = "+this.getValueAtRisk()+"\nrendement = "+this.computeExpectedReturn(this.portfolioRawReturn);
			}
		}*/
		return strWeights;
	}
}

