import java.util.Random;


public class Mutation {

	public void mutationElementaire(double[] param,double[] moy,double[] var,double[][] cov){
		
		ActionsMutation actionsAModifier = actionsAModifier(param.length);
		int i = actionsAModifier.getI();
		int j = actionsAModifier.getJ();
		int k = actionsAModifier.getK();
		
		double d = Math.random();
		double sign = 1;
		if(d<0.5){
			sign = -1;
		}
		
		double Ri = moy[i];
		double Rj = moy[j];
		double Rk = moy[k];
		
		double radius = 0.1; // floor and ceiling constraints...
		
		double step = sign*(radius*(Rj-Rk))/(Math.sqrt((Rj-Rk)*(Rj-Rk) + (Ri-Rk)*(Ri-Rk) + (Rj-Ri)*(Rj-Ri)));
		
		param[i] = param[i] - step;
		param[j] = param[j] + step*((Ri - Rk)/(Rj - Rk));
		param[k] = param[k] + step*((Rj - Ri)/(Rj - Rk));
		
	}
	
	public ActionsMutation actionsAModifier(int longueur){
		
		int x = randInt(0,longueur-1);
		int y = randInt(0,longueur-2);
		int z = randInt(0,longueur-3);
		
		int i = x;
		int j = (x+y)%(longueur-1);
		int k = (y+z)%(longueur-1);
		
		while(k==i || k==j){
			k--;	
		}
		
		return new ActionsMutation(i,j,k);
	}
	
	public static int randInt(int min, int max) {

	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
}
