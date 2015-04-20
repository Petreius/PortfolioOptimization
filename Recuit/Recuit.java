package Recuit;

import Data.*;


public class Recuit {

	static double temperature = 100;
	static double refroidissement = 0.0001;
	
	public static double probaAcceptation(double currentEnergy, double newEnergy, double temperature)
	{
		if (newEnergy < currentEnergy) {
			return 1.0;
		}
		return Math.exp((currentEnergy - newEnergy) / ((temperature)));
	}
	
	public Portfolio solution(Data data, double target)
	{
		
		Portfolio currentPortfolio = new Portfolio(data);
		Portfolio bestPortfolio = currentPortfolio.clone();
		Portfolio newPortfolio = currentPortfolio.clone();
		
		double temperatureRecuit = temperature;
		while (temperatureRecuit > 1) {
			
			Mutation.buyAndSell(newPortfolio);
			newPortfolio.update(data);
			
			double currentEnergy = currentPortfolio.getEnergy(target);
			double newEnergy = newPortfolio.getEnergy(target);
			double pr = probaAcceptation(currentEnergy, newEnergy, temperatureRecuit);
			
			if (pr >= Math.random()) {
				currentPortfolio = newPortfolio.clone(); 
			}

			if (currentPortfolio.getEnergy(target) < bestPortfolio.getEnergy(target)) {
				bestPortfolio = currentPortfolio.clone();
			}
			temperatureRecuit -= refroidissement;
		}
		
		System.out.println("portefeuille final \n" + bestPortfolio.toString());
		return bestPortfolio;
	}

}

