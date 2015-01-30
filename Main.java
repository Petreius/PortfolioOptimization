import java.io.IOException;


public class Main {

	public static void main(String[] args) throws IOException {
		
		MeanVarianceParser mvp = new MeanVarianceParser("/Users/thomasdoutre/Desktop/port1.txt");
		mvp.donneMatriceMoyenneVariance();
		
		double[][] cov = mvp.matriceCovariance;
		double[] moy = mvp.vecteurMoyennes;
		double[] var = mvp.vecteurVariances;
		
		Recuit.solution(moy, var, cov);
		
		//Recuit.solution();

	}

}
