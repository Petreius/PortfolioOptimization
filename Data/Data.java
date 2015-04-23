import YahooFinance.*;

public class Data {

	private double[][] quoteMatrix;
	private double[][] logReturnsMatrix;
	private double[][] rawReturnsMatrix;
	
	// Data contient toutes les informations "utiles" (prix, log-return, retours arithmetiques) sous forme de tableaux 2D rectangulaires
	// si on n'a pas le même nombre d'informations pour chaque jour (ex Apple remonte jusqu'à 1980, Microsoft jusqu'à 1987) alors on coupe
	// à la plus récente date tous les actifs (Apple depuis 1987, Microsoft depuis 1987) -> autoscaling. La matrice des prix est donc rectangulaire.
	public Data(ArrayList<YahooFinanceHttp> tickersList, int step){
		tickersList = AutoScaling(tickersList);
		System.out.println("nouvelle de date de début : "+tickersList.get(0).getDate().get(0));
		double[][] quoteMatrix = new double[tickersList.get(0).getAdjClose().size()][tickersList.size()];
		for(int i = 0; i < tickersList.size(); i++){
			for(int j = 0; j < tickersList.get(i).getAdjClose().size(); j++){
				quoteMatrix[j][i] = tickersList.get(i).getAdjClose().get(j);
			}
		}
		this.quoteMatrix = quoteMatrix;
		this.logReturnsMatrix = computeLogReturns(quoteMatrix, step);
		this.rawReturnsMatrix = computeRawReturns(quoteMatrix, step);
	}

	// Pour eviter tout comportement indetermine, AutoScaling adapte la fenetre d observation (voir explication constructeur)
	public ArrayList<YahooFinanceHttp> AutoScaling(ArrayList<YahooFinanceHttp> tickersList){
		ArrayList<Integer> timeLength = new ArrayList<Integer>();
		int minLength = 0;
		for(int i = 0; i < tickersList.size(); i++){
			timeLength.add(tickersList.get(i).getDate().size());
		}
		minLength = (int) Collections.min(timeLength);
		for(int i = 0; i < tickersList.size(); i++){
			int length = tickersList.get(i).getDate().size();
			for(int j = 0; j < length-minLength; j++){
				tickersList.get(i).getDate().remove(0);
				tickersList.get(i).getAdjClose().remove(0);
				tickersList.get(i).getVolume().remove(0);
				tickersList.get(i).getOpen().remove(0);
				tickersList.get(i).getClose().remove(0);
				tickersList.get(i).getHigh().remove(0);
				tickersList.get(i).getLow().remove(0);
			}
		}
		return tickersList;
	}

	// calcule les log-returns sur la fenêtre d'observation pour chacun des actifs.
	public double[][] computeLogReturns(double[][] quoteMatrix, int step){
		double[][] logReturnsMatrix = new double[quoteMatrix.length-step][quoteMatrix[0].length];
		int warningNullPrices = -1;
		for(int j = 0; j < quoteMatrix[0].length; j++){
			for(int i = 0; i < quoteMatrix.length-step; i++){
				if(quoteMatrix[i+step][j] != 0.0 && quoteMatrix[i][j] != 0.0){
					logReturnsMatrix[i][j] = Math.log(quoteMatrix[i+step][j]/quoteMatrix[i][j]);
				}
				else{ 
					if(warningNullPrices != j){
						System.out.println("ATTENTION certaines cotations sont nulles sur cet intervalle de temps pour le ticker numero "+j);
						warningNullPrices = j;
					}
					logReturnsMatrix[i][j] = 0.0;
				}
			}
		}
		return logReturnsMatrix;
	}

	// calcule les retours arithmétiques sur la fenêtre d'observation pour chaque actif
	private double[][] computeRawReturns(double[][] quoteMatrix, int step){
		double[][] rawReturnsMatrix = new double[quoteMatrix.length-step][quoteMatrix[0].length];
		for(int j = 0; j < quoteMatrix[0].length; j++){
			for(int i = 0; i < quoteMatrix.length-step; i++){
				if(quoteMatrix[i+step][j] != 0.0 && quoteMatrix[i][j] != 0.0){
					rawReturnsMatrix[i][j] = Round((quoteMatrix[i+step][j]-quoteMatrix[i][j])/(quoteMatrix[i][j]),4);
				}
				else{ 
					rawReturnsMatrix[i][j] = 0.0;
				}
			}
		}
		return rawReturnsMatrix;
	}

	// Méthode d'arrondi - à améliorer... temps de calcul prohibitif si utilisé répétitivement
	public static double Round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	// Affichage des Log-Returns d'un actif
	public String toStringAssetReturn(int index){
		String strAssetReturn = "[";
		for(int i=0; i < this.getMLogReturns().length; i++){
			if(i != this.getMLogReturns().length-1){
				strAssetReturn += this.getMLogReturns()[i][index]+"|";
			} else{
				strAssetReturn += this.getMLogReturns()[i][index]+"]";
			}
		}
		return strAssetReturn;
	}
	
	// Setters, Getters
	public double[][] getMQuotes(){
		return quoteMatrix;
	}

	public double[][] getMLogReturns(){
		return logReturnsMatrix;
	}

	public double[][] getMRawReturns(){
		return rawReturnsMatrix;
	}
}
