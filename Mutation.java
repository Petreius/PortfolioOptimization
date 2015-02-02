
public class Mutation {
	
	public static double[] mutationElementaire(double[] parametres){
		
		
		//BESOIN DE CLONER ?
		double[] v = clone(parametres);
		double step = 0.1;
		
		int i = randomInt(0,parametres.length-1);
		int j = randomIntWithException(0,parametres.length-1,i);
		v[i] = v[i] - step;
		v[j] = v[j] + step;
		
		return v;
		
	}
	
	public static double[] clone (double[] v){
		
		double[] w = new double[v.length];

		for(int i=0; i<v.length ; i++){
			w[i]=v[i];
		}
	
		return w;
		
	}
	
	public static int randomInt (int deb, int fin){
		
		return (int) Math.random()*(fin-deb+1)+deb;
		
	}
	
public static int randomIntWithException (int deb, int fin, int i){
		
		return (int) ((i + Math.random()*(fin-deb)+deb)%(fin+1));
		
	}

}
