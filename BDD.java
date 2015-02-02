
public class BDD {

	private static int nombreDActifs;
	public static int nombreDeJours;
	public static int nombreDeRealisations;
	private static double[][] matriceDesLogReturns;
	private static double[][] matriceDesScenarios;
	
	public BDD(int nombreDActifs, int nombreDeJours, int nombreDeRealisations){
		
		BDD.setNombreDActifs(nombreDActifs);
		
		double [][] matLR = new double[nombreDeJours][nombreDActifs];
		double [][] matS = new double[nombreDeRealisations][nombreDActifs];
		
		for (int i=0; i<nombreDeJours; i++){
			for (int j=0 ; j<nombreDActifs ; j++){
				
				//A REMPLIR A L AIDE DU EXCEL MANAGER
				
			}
		}
		
		for (int i=0; i<nombreDeRealisations; i++){
			for (int j=0 ; j<nombreDActifs ; j++){
				
				//A REMPLIR A L AIDE DU EXCEL MANAGER
				
			}
		}
		
		
		BDD.setMatriceDesLogReturns(matLR);
		BDD.setMatriceDesScenarios(matS);
		
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

	public static int getNombreDActifs() {
		return nombreDActifs;
	}

	public static void setNombreDActifs(int nombreDActifs) {
		BDD.nombreDActifs = nombreDActifs;
	}
	
	
}
