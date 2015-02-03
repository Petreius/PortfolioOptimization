
public class Mutation {
	
	/* La première mutation codée est très simple :
	 * On enlève un pourcentage investi dans une action et on l'ajoute à une autre
	 * Ce pourcentage est pour l'instant constant.
	 */
	
	
	
	
	public static void buyAndSell(Portfolio p){
		
		double[] v = p.getPercentages();
		double step = 0.01; //Pour l'instant
		
		int soldAsset = randomInt(0,v.length-1);
		int boughtAsset = randomIntWithException(0,v.length-1,soldAsset);
		v[soldAsset] = v[soldAsset] - step;
		v[boughtAsset] = v[boughtAsset] + step;
		p.setPercentages(v);
		p.calculateVar();
		
	}

	public static int randomInt (int deb, int fin){
		
		return (int)(Math.random()*(fin-deb+1)+deb);
		
	}
	
public static int randomIntWithException (int deb, int fin, int i){
		int z = randomInt(deb,fin-1);
		return (i+z)%(fin+1);
		
	}

}
