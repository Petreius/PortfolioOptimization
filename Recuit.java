import java.io.IOException;


public class Recuit
{

	
	public static double probaAcceptation(double profitCourant, double nouveauProfit, double k, double t) throws IOException 
	{

		if(profitCourant>nouveauProfit){
			return 1;
		}
		return Math.exp(profitCourant - nouveauProfit) / (k*t);
	}
	

	public static double[] solution(double[] moy, double[] var, double[][] cov){
		
		double[] pourc = initialize(moy.length);

		printVector(pourc);
		
		
		return pourc;
		
		
	}
	
	public static double[] initialize(int l){
		
		double[] random = new double[l];
		double[] perc = new double[l];
		double total = 0;
		
		for (int i=0 ; i< Math.min(7, l) ; i++){
			random[i] = Math.random();
			total = total + random[i];
		}
		
		double somme = 0;
		for (int i=0 ; i< l-1 ; i++){
			perc[i] = random[i]/total;
			somme = somme + perc[i];
		}
		
		perc[l-1] = 1 - somme ;
		
		return perc;
		
	}
	
	
	
	public static void printMatrix(double[][] twoDm) {
		System.out.println("=================================================================");
		for(double[] row : twoDm) {
			for (double i : row) {

				System.out.print(i);
				System.out.print("\t");
			}
			System.out.println();
		}
		System.out.println("=================================================================");

	}

	public static void printVector(double[] vect) {
		System.out.println("=================================================================");
		for(int i=0; i < vect.length ; i++) {

			System.out.print(vect[i]);
			System.out.println();
		}

		System.out.println("=================================================================");

	}
	
	
}

