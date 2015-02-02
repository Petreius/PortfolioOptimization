import java.io.IOException;


public class BDD {

	/* Classe qui reprend les différents paramètres du problème, dans le but d'un 
	 * programme facilement adaptable, les methodes des autres classes appelleront
	 * ces paramètres si besoin.
	 * On spécifie les 3 premiers entiers (dimensions des vecteurs/matrices) et on construit
	 * les matrices dans le constructeur de classe. La matrice matricesDesScénarios (matS) est la
	 * matrice de Bootstrap.
	 */

	
	public static int nombreDActifs;
	public static int nombreDeJours;
	public static int nombreDeRealisations;
	private static double[][] matriceDesLogReturns;
	private static double[][] matriceDesScenarios;

	
	public BDD(int nombreDActifs, int nombreDeJours, int nombreDeRealisations) throws IOException{

		BDD.nombreDActifs = nombreDActifs;

		double [][] matLR = new double[nombreDeJours][nombreDActifs];
		double [][] matS = new double[nombreDeRealisations][nombreDActifs];

		ExcelManager exM = new ExcelManager("");
		// Modifier avec le filePath du fichier Excel.

		for (int i=0; i<nombreDeJours; i++){
			for (int j=0 ; j<nombreDActifs ; j++){

				matLR[i][j] = exM.lireDoubleCellule(i, j);
				//A MODIFIER A L AIDE DU EXCEL MANAGER

			}
		}

		for (int i=0; i<nombreDeRealisations; i++){
			
			int rand = randomInt(i,matLR.length-1);
			
			for (int j=0 ; j<nombreDActifs ; j++){

				matS[i][j] = matLR[rand][j];

			}
		}


		BDD.setMatriceDesLogReturns(matLR);
		BDD.setMatriceDesScenarios(matS);

	}
	
	public static int randomInt (int deb, int fin){
		
		return (int)(Math.random()*(fin-deb+1)+deb);
		
	}

	public static double[][] getMatriceDesLogReturns() {
		return matriceDesLogReturns;
	}

	public static void setMatriceDesLogReturns(double[][] matriceDesLogReturns) {
		BDD.matriceDesLogReturns = matriceDesLogReturns;
	}

	public static double[][] getMatriceDesScenarios() {
		return matriceDesScenarios;
	}

	public static void setMatriceDesScenarios(double[][] matriceDesScenarios) {
		BDD.matriceDesScenarios = matriceDesScenarios;
	}

}
