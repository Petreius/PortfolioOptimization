
public class MutationSansClone {
	
	/* La première mutation codée est très simple :
	 * On enlève un pourcentage investi dans une action et on l'ajoute à une autre
	 * Ce pourcentage est pour l'instant constant.
	 */
	
	
	public static double[] mutationElementaire(double[] v){
		
		
		double step = 0.1;
		
		int i = randomInt(0,v.length-1);
		int j = randomIntWithException(0,v.length-1,i);
		v[i] = v[i] - step;
		v[j] = v[j] + step;
		
		return v;
		
	}

	
	public static int randomInt (int deb, int fin){
		
		return (int)(Math.random()*(fin-deb+1)+deb);
		
	}
	
public static int randomIntWithException (int deb, int fin, int i){
		int z = randomInt(deb,fin-1);
		return (i+z)%(fin+1);
		
	}

}
