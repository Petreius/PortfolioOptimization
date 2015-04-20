package Recuit;

import Data.*;

public class Mutation {

	// la mutation consiste à investir un certain pourcentage de l'allocation dans un actif i et à revendre cette même allocation d'un actif j
	public static void buyAndSell(Portfolio portfolio){

		double[] weights = portfolio.getWeights();
		double step = 0.01; //Pour l'instant

		int soldAsset = randomInt(0,weights.length-1);
		int boughtAsset = randomIntWithException(0,weights.length-1,soldAsset);
		if(weights[boughtAsset]+step <= 1 && weights[soldAsset]-step >= 0){
			weights[soldAsset] = Data.Round(weights[soldAsset] - step,4);
			weights[boughtAsset] = Data.Round(weights[boughtAsset] + step,4);}

		portfolio.setWeights(weights);
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
