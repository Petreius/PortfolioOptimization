
public class Recuit {

	static double temperature = 100;
	static double refroidissement = 0.001;
	static double K = 0.0001;
	
	public static double probaAcceptation(double currentEnergy, double newEnergy, double temperature)
	{
		if (newEnergy < currentEnergy) {
			return 1.0;
		}
		return Math.exp((currentEnergy - newEnergy) / (K*temperature));
	}
	
	public static Portfolio solution()
	{
		
		Portfolio currentPortfolio = new Portfolio();
		Portfolio bestPortfolio = currentPortfolio.clone();
		Portfolio newPortfolio = currentPortfolio.clone();
		
		double temperatureRecuit = temperature;
		
		while (temperatureRecuit > 1) {
			
			Mutation.buyAndSell(newPortfolio);
			
			double currentEnergy = currentPortfolio.getEnergy();
			double newEnergy = newPortfolio.getEnergy();
			
			if (probaAcceptation(currentEnergy, newEnergy, temperatureRecuit) >= Math.random()) {
				currentPortfolio = newPortfolio.clone(); 
			}

			if (currentPortfolio.getEnergy() < bestPortfolio.getEnergy()) {
				bestPortfolio = currentPortfolio.clone();
			}
			temperatureRecuit *= 1-refroidissement;
		}
		
		System.out.println("energie finale: " + bestPortfolio.getEnergy());
		System.out.println("ExpectedReturn: " + bestPortfolio.getReturn());
		System.out.println("STD: " + bestPortfolio.getVar());
		return bestPortfolio;
	}

}
