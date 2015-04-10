package Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import YahooFinance.*;

public class Data {

	private double[][] quoteMatrix;
	private double[][] logReturnsMatrix;
	private double[][] rawReturnsMatrix;

	public Data(ArrayList<YahooFinanceHttp> tickersList){
		double[][] quoteMatrix = new double[tickersList.get(0).getAdjClose().size()][tickersList.size()];
		for(int i = 0; i < tickersList.size(); i++){
			for(int j = 0; j < tickersList.get(i).getAdjClose().size(); j++){
				quoteMatrix[j][i] = tickersList.get(i).getAdjClose().get(j);
			}
		}
		this.quoteMatrix = quoteMatrix;
		this.logReturnsMatrix = computeLogReturns(quoteMatrix);
		this.rawReturnsMatrix = computeRawReturns(quoteMatrix);
	}

	private double[][] computeLogReturns(double[][] quoteMatrix){
		double[][] logReturnsMatrix = new double[quoteMatrix.length-1][quoteMatrix[0].length];
		for(int j = 0; j < quoteMatrix[0].length; j++){
			for(int i = 0; i < quoteMatrix.length-1; i++){
				if(quoteMatrix[i+1][j] != 0.0 && quoteMatrix[i][j] != 0.0){
					logReturnsMatrix[i][j] = Round(Math.log(quoteMatrix[i+1][j]/quoteMatrix[i][j]),4);
				}
				else{ 
					logReturnsMatrix[i][j] = 0.0;
				}
			}
		}
		return logReturnsMatrix;
	}

	private double[][] computeRawReturns(double[][] quoteMatrix){
		double[][] rawReturnsMatrix = new double[quoteMatrix.length-1][quoteMatrix[0].length];
		for(int j = 0; j < quoteMatrix[0].length; j++){
			for(int i = 0; i < quoteMatrix.length-1; i++){
				if(quoteMatrix[i+1][j] != 0.0 && quoteMatrix[i][j] != 0.0){
					rawReturnsMatrix[i][j] = Round((quoteMatrix[i+1][j]-quoteMatrix[i][j])/(quoteMatrix[i][j]),4);
				}
				else{ 
					rawReturnsMatrix[i][j] = 0.0;
				}
			}
		}
		return rawReturnsMatrix;
	}

	public static double Round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

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
