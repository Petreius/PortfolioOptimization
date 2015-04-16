package Recuit;

import Data.*;


public class Recuit {

	static double temperature = 100;
	static double refroidissement = 0.001;
	
	public static double probaAcceptation(double currentEnergy, double newEnergy, double temperature)
	{
		if (newEnergy < currentEnergy) {
			return 1.0;
		}
		//return 0;
		return Math.exp((currentEnergy - newEnergy) / (10000000*temperature));
	}
	
	public static void solution(Data data)
	{
		
		Portfolio currentPortfolio = new Portfolio(data);
		Portfolio bestPortfolio = currentPortfolio.clone();
		Portfolio newPortfolio = currentPortfolio.clone();
		
		double temperatureRecuit = temperature;
		
		while (temperatureRecuit > 1) {
			
			Mutation.buyAndSell(newPortfolio);
			newPortfolio.update(data);
			
			double currentEnergy = currentPortfolio.getEnergy();
			double newEnergy = newPortfolio.getEnergy();
			
			if (probaAcceptation(currentEnergy, newEnergy, temperatureRecuit) >= Math.random()) {
				currentPortfolio = newPortfolio.clone(); 
			}

			if (currentPortfolio.getEnergy() < bestPortfolio.getEnergy()) {
				bestPortfolio = currentPortfolio.clone();
			}
			temperatureRecuit -= refroidissement;
		}
		
		System.out.println("portefeuille final \n" + bestPortfolio.toString());
	}

}
