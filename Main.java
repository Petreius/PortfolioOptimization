import java.util.ArrayList;
import java.util.Calendar;

import org.jfree.ui.RefineryUtilities;

import Recuit.Recuit;
import YahooFinance.YahooFinanceHttp;
import YahooFinance.YahooFinanceURLConstructor;
import Data.*;
import GUI.*;

public class Main {
	public static void main(String[] args) throws CloneNotSupportedException{

		Calendar startDate = Calendar.getInstance();
		//Date de debut
		startDate.set(Calendar.YEAR, 2013);
		startDate.set(Calendar.MONTH,0); //Mois Jan = 0, Fev = 1...Dec = 11
		startDate.set(Calendar.DATE, 01);

		Calendar endDate = Calendar.getInstance();
		//Date de fin
		endDate.set(Calendar.YEAR, 2015);
		endDate.set(Calendar.MONTH, 0);
		endDate.set(Calendar.DATE, 01);

		// La tickersList contient l'ensemble des tickers que nos considérons et leur prix respectif sur la fenetre d'observation
		ArrayList<YahooFinanceHttp> tickersList = new ArrayList<YahooFinanceHttp>();

		// L'URL constructor permet de formuler la requete à Yahoo
		YahooFinanceURLConstructor ORAPA_URL = new YahooFinanceURLConstructor(startDate, endDate, "ORA.PA");
		YahooFinanceURLConstructor VIEPA_URL = new YahooFinanceURLConstructor(startDate, endDate, "VIE.PA");
		YahooFinanceURLConstructor CAPPA_URL = new YahooFinanceURLConstructor(startDate, endDate, "CAP.PA");
		YahooFinanceURLConstructor TECPA_URL = new YahooFinanceURLConstructor(startDate, endDate, "TEC.PA");
		YahooFinanceURLConstructor AIRPA_URL = new YahooFinanceURLConstructor(startDate, endDate, "AIR.PA");
		YahooFinanceURLConstructor GLEPA_URL = new YahooFinanceURLConstructor(startDate, endDate, "GLE.PA");

		System.out.println("Interroge Yahoo Finance...");

		// YahooFinanceHttp contient une colonne de la future tickersList
		YahooFinanceHttp ORAPA = new YahooFinanceHttp(ORAPA_URL.constructURL()); 
		YahooFinanceHttp VIEPA = new YahooFinanceHttp(VIEPA_URL.constructURL());
		YahooFinanceHttp CAPPA = new YahooFinanceHttp(CAPPA_URL.constructURL());
		YahooFinanceHttp TECPA = new YahooFinanceHttp(TECPA_URL.constructURL());
		YahooFinanceHttp AIRPA = new YahooFinanceHttp(AIRPA_URL.constructURL());
		YahooFinanceHttp GLEPA = new YahooFinanceHttp(GLEPA_URL.constructURL());

		System.out.println("Donnees recuperees avec succes");

		// On construit la tickersList en concaténant les colonnes
		tickersList.add(ORAPA);
		tickersList.add(VIEPA);
		tickersList.add(CAPPA);
		tickersList.add(TECPA);
		tickersList.add(AIRPA);
		tickersList.add(GLEPA);
		
		// On crée la tickerList (arrayList qui contient des infos sur tous les tickers (AdjClose, Date, Open, Close...) 
		Data data = new Data(tickersList,1);
		// On crée un portefeuille initiale (100% investi dans un actif aléatoire + données associées)
		Portfolio portfolio = new Portfolio(data);
		
		Recuit c= new Recuit();
		Extrapolation e = new Extrapolation();
		// extrapolation de la value at risk sur "days" jours
		// Le coefficient d'extrapolation correspond à la somme pondérée des coefficients de Hurst de chaque actif
		int days = 10;
		
		// Test
		System.out.println("portefeuille initial \n\n" + portfolio.toString()+"\n\n");
		Portfolio bestPortfolio = c.solution(data,0.0012);
		double[] portfolioLogReturns = bestPortfolio.getPortfolioLogReturn();
		System.out.println("portfolio VaR 1 day = "+bestPortfolio.computeVAR(portfolioLogReturns,5));
		System.out.println("portfolio VaR "+days+" days ="+e.computeAvgHurstCoefficient(days, data, 5, bestPortfolio.getWeights())*bestPortfolio.computeVAR(portfolioLogReturns, 5));
		
		final returnGUI demo = new returnGUI("Best portfolio returns for Value at Risk 1 day", bestPortfolio.getPortfolioLogReturn(), 50);
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);
	}

}
