
public class Main {

	public static void main(String[] args) {
		
		for(int i =0; i< 20 ; i++){
			System.out.print("\t"+MutationSansClone.randomInt(0,4));
		}
		System.out.println();
		for(int i =0; i< 20 ; i++){
			System.out.print("\t"+MutationSansClone.randomIntWithException(0,4,3));
		}
	}

}
