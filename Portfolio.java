
public class Portfolio {

	private double[] percentages;
	private double var;
	
	public Portfolio(double[] pourcentages){
		this.setPercentages(pourcentages);
		this.setVar(0);
		//A MODIFIER SELON LE CALCUL DE LA VAR
	}
	
	public Portfolio(){
		double[] randPercentages = new double[DataBase.getInstance().nombreDActifs];
		double sum = 0;
		for (int i=0;i<randPercentages.length-1;i++){
			randPercentages[i]= (double)((int)(1000*(1.0/(randPercentages.length))))/1000.0;
			sum = sum + ((double)((int)(1000*randPercentages[i])))/1000.0;
		}
		randPercentages[randPercentages.length-1] = (double)((int)(1000*(1-sum)))/1000.0;
		
		this.setPercentages(randPercentages);
		this.setVar(0);
		//A MODIFIER SELON LE CALCUL DE LA VAR
	}
	

	public double getEnergy() {
		//A MODIFIER SELON LE CALCUL DE L ENERGIE
		return 0;
	}
	
	public double getReturn() {
		//A MODIFIER SELON LE CALCUL DU RETOUR
		return 0;
	}

	
	public void calculateVar(){
		double var = 0;
		//A MODIFIER SELON LE CALCUL DE LA VAR
		this.setVar(var);;
	}
	
	public Portfolio clone(){
		Portfolio q = new Portfolio(this.getPercentages());
		return q;
	}

	public double[] getPercentages() {
		return percentages;
	}

	public void setPercentages(double[] pourcentages) {
		this.percentages = pourcentages;
	}

	public double getVar() {
		return var;
	}

	public void setVar(double var) {
		this.var = var;
	}
	
	
}
