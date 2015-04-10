package Data;

public class Portfolio {
	private double[] weights;
	// TODO private double[] expectedLogReturn;
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
}
