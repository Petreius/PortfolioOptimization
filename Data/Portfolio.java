package Data;

public class Portfolio {
	private double[] weights;
	private double[] expectedLogReturn;
	private double[] expectedRawReturn;

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

	public double[] getWeights(){
		return this.weights;
	}

	public double[] getExpectedRawReturn(){
		return this.expectedRawReturn;
	}
	
	public double[] getExpectedLogReturn(){
		return this.expectedLogReturn;
	}
}
