package mutation;

import data.*;

public class Mutation {
	
	public static double[] buyAndSell(double[] weights){
		
		double step = 0.15; //Pour l'instant
		
		int soldAsset = randomInt(0,weights.length-1);
		int boughtAsset = randomIntWithException(0,weights.length-1,soldAsset);
		weights[soldAsset] = Data.Round(weights[soldAsset] - step,4);
		weights[boughtAsset] = Data.Round(weights[boughtAsset] + step,4);
		return weights;
	}

	public static int randomInt (int deb, int fin){
		
		return (int)(Math.random()*(fin-deb+1)+deb);
		
	}
	
public static int randomIntWithException (int deb, int fin, int i){
		int random = randomInt(deb,fin);
		while(random == i){
		random = randomInt(deb,fin);
		}
		return random;
	}

}
