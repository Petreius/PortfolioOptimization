import java.io.IOException;
import org.jblas.DoubleMatrix;

public class DataBase {

	/* Classe qui reprend les diffÃ©rents paramÃ¨tres du problÃ¨me, dans le but d'un 
	 * programme facilement adaptable, les methodes des autres classes appelleront
	 * ces paramÃ¨tres si besoin.
	 * On spÃ©cifie les 3 premiers entiers (dimensions des vecteurs/matrices) et on construit
	 * les matrices dans le constructeur de classe. La matrice matricesDesScÃ©narios (matS) est la
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

	private DoubleMatrix logReturnsMatrix= new DoubleMatrix();
	private DoubleMatrix scenariiMatrix= new DoubleMatrix();
	
	public DataBase(int nombreDActifs, int nombreDeJours, int nombreDeRealisations) throws IOException{

		uniqueInstance.nombreDActifs = nombreDActifs;
		uniqueInstance.nombreDeJours = nombreDeJours;
		uniqueInstance.nombreDeRealisations = nombreDeRealisations;
		
		double [][] matLR = new double[nombreDeJours][nombreDActifs];
		double [][] matS = new double[nombreDeRealisations][nombreDActifs];
		
		ExcelManager exM = new ExcelManager("C:/Users/Belaube/Desktop/BenchmarkVAR.xls");
		// Modifier avec le filePath du fichier Excel.

		for (int i=0; i<nombreDeJours; i++){
			for (int j=0 ; j<nombreDActifs ; j++){

				matLR[i][j] = exM.lireDoubleCellule(i+2, j);

			}
		}
		
		for (int i=0; i<nombreDeRealisations; i++){
			int rand = randomInt(0,uniqueInstance.nombreDeJours-1);
			for (int j=0 ; j<nombreDActifs ; j++){

				matS[i][j] = matLR[rand][j];

			}
		}

		DoubleMatrix LRMatrix = new DoubleMatrix(matLR);
		DoubleMatrix SMatrix = new DoubleMatrix(matS);
		
		uniqueInstance.setMatriceDesLogReturns(LRMatrix);
		uniqueInstance.setMatriceDesScenarios(SMatrix);

		//printDoubleMatrix(uniqueInstance.matriceDesLogReturns);
		System.out.print(uniqueInstance.logReturnsMatrix);
		
	}

	public static int randomInt (int deb, int fin){

		return (int)(Math.random()*(fin-deb+1)+deb);

	}

	public DoubleMatrix getMatriceDesLogReturns() {
		return logReturnsMatrix;
	}

	public void setMatriceDesLogReturns(DoubleMatrix logReturnsMatrix) {
		this.logReturnsMatrix = logReturnsMatrix;
	}

	public DoubleMatrix getMatriceDesScenarios() {
		return scenariiMatrix;
	}

	public void setMatriceDesScenarios(DoubleMatrix scenariiMatrix) {
		this.scenariiMatrix = scenariiMatrix;
	}
	
	// On conserve quand même. Dommage que l'affichage des objets DoubleMatrix soit pas très élégant...
	public static void printDoubleMatrix(double[][] twoDm) {
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

}
