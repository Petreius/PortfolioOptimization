import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/* Récupère le vecteur des moyennes, le vecteur des variances et la matrice de covariance des actifs des benchmarks
 * de l'adresse http://people.brunel.ac.uk/~mastjjb/jeb/orlib/files/   ex : "port1.txt"
 */



public class MeanVarianceParser {

	public String fichier;
	public int nombreAssets;
	public double[] vecteurMoyennes;
	public double[] vecteurVariances;
	public double[][] matriceCovariance;
	private BufferedReader br;

	public MeanVarianceParser(String fichier){

		this.fichier = fichier;

	}

	public void donneMatriceMoyenneVariance() throws IOException{

		Scanner scanner;
		br = new BufferedReader(new InputStreamReader(new FileInputStream(fichier)));
		String thisLine;
		int compteur = 0;

		thisLine = br.readLine();
		this.nombreAssets = Integer.decode(thisLine.replace(" ", ""));
		
		vecteurMoyennes = new double[this.nombreAssets];
		vecteurVariances = new double[this.nombreAssets];
		matriceCovariance = new double[this.nombreAssets][this.nombreAssets];

		while ((thisLine = br.readLine()) != null){

			if(compteur<this.nombreAssets){

				scanner = new Scanner(thisLine.replace(".", "0,"));

				if (scanner.hasNextDouble()) {

					vecteurMoyennes[compteur] = scanner.nextDouble();
					vecteurVariances[compteur] = scanner.nextDouble();
					
				}

				scanner.close();
			}

			else{
				
				scanner = new Scanner(thisLine.replace("1.", "1,").replace(".", "0,"));

				if (scanner.hasNextDouble()) {

					int x = (int)scanner.nextDouble();
					int y = (int)scanner.nextDouble();
					double z = scanner.nextDouble();
					
					this.matriceCovariance[x-1][y-1]=z;
					this.matriceCovariance[y-1][x-1]=z;
					
				}

				scanner.close();
				
			}

			compteur ++;
		}

		br.close();

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
