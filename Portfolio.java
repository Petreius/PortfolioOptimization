
public class Portfolio {

	private double[] pourcentages;
	private double var;
	
	public Portfolio(double[] pourcentages){
		this.setPourcentages(pourcentages);
		this.setVar(0);
		//A MODIFIER SELON LE CALCUL DE LA VAR
	}

	public double[] getPourcentages() {
		return pourcentages;
	}

	public void setPourcentages(double[] pourcentages) {
		this.pourcentages = pourcentages;
	}

	public double getVar() {
		return var;
	}

	public void setVar(double var) {
		this.var = var;
	}
	
	
}
