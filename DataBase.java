import java.io.IOException;


public class DataBase {

	/* Classe qui reprend les différents paramètres du problème, dans le but d'un 
	 * programme facilement adaptable, les methodes des autres classes appelleront
	 * ces paramètres si besoin.
	 * On spécifie les 3 premiers entiers (dimensions des vecteurs/matrices) et on construit
	 * les matrices dans le constructeur de classe. La matrice matricesDesScénarios (matS) est la
	 * matrice de Bootstrap.
	 * On choisit un design pattern de type Singleton.
	 */


	private DataBase(){}
	private static DataBase uniqueInstance = new DataBase();
	public static DataBase getInstance()
	{	return uniqueInstance;
	}


	public int nombreDActifs;
	public int nombreDeJours;
	public int nombreDeRealisations;
	private double[][] matriceDesLogReturns;
	private double[][] matriceDesScenarios;


	public DataBase(int nombreDActifs, int nombreDeJours, int nombreDeRealisations) throws IOException{

		double [][] matLR = new double[nombreDeJours][nombreDActifs];
		double [][] matS = new double[nombreDeRealisations][nombreDActifs];

		//ExcelManager exM = new ExcelManager("");
		// Modifier avec le filePath du fichier Excel.

		for (int i=0; i<nombreDeJours; i++){
			for (int j=0 ; j<nombreDActifs ; j++){

				//matLR[i][j] = exM.lireDoubleCellule(i, j);
				//A MODIFIER A L AIDE DU EXCEL MANAGER

			}
		}

		for (int i=0; i<nombreDeRealisations; i++){

			int rand = randomInt(0,uniqueInstance.nombreDeJours-1);

			for (int j=0 ; j<nombreDActifs ; j++){

				matS[i][j] = matLR[rand][j];

			}
		}


		uniqueInstance.nombreDActifs = nombreDActifs;
		uniqueInstance.setMatriceDesLogReturns(matLR);
		uniqueInstance.setMatriceDesScenarios(matS);

	}

	public static int randomInt (int deb, int fin){

		return (int)(Math.random()*(fin-deb+1)+deb);

	}

	public double[][] getMatriceDesLogReturns() {
		return matriceDesLogReturns;
	}

	public void setMatriceDesLogReturns(double[][] matriceDesLogReturns) {
		this.matriceDesLogReturns = matriceDesLogReturns;
	}

	public double[][] getMatriceDesScenarios() {
		return matriceDesScenarios;
	}

	public void setMatriceDesScenarios(double[][] matriceDesScenarios) {
		this.matriceDesScenarios = matriceDesScenarios;
	}

}
