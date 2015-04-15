import java.io.IOException;


public class Main {

	public static void main(String[] args) throws IOException {
		
		DataBase db = new DataBase(10,200,500);
		Portfolio p = new Portfolio();
		printVector(p.getPercentages());
		
		Recuit.solution();
		
	}
	
	public static void printVector(double[] v) {
		System.out.println("=================================================================");
			for (int i = 0; i<v.length ; i++) {
				System.out.print(v[i]+"   ");
			}
			System.out.println();
		System.out.println("=================================================================");

	}


}
