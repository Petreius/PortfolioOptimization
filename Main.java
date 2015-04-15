import java.util.ArrayList;
import java.util.Calendar;

import YahooFinance.YahooFinanceHttp;
import YahooFinance.YahooFinanceURLConstructor;
import Data.*;
import Recuit.*;

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException{
 
        Calendar startDate = Calendar.getInstance();
        //Date de debut
        startDate.set(Calendar.YEAR, 2014);
        startDate.set(Calendar.MONTH,12); //Mois Jan = 0, Fev = 1...Dec = 11
        startDate.set(Calendar.DATE, 14);
 
        Calendar endDate = Calendar.getInstance();
        //Date de fin
        endDate.set(Calendar.YEAR, 2015);
        endDate.set(Calendar.MONTH, 3);
        endDate.set(Calendar.DATE, 14);
 
        // La tickersList contient l'ensemble des tickers que nos considérons et leur prix respectif sur la fenetre d'observation
        ArrayList<YahooFinanceHttp> tickersList = new ArrayList<YahooFinanceHttp>();
        
        // L'URL constructor permet de formuler la requete à Yahoo
        YahooFinanceURLConstructor AAPL_URL = new YahooFinanceURLConstructor(startDate, endDate, "AAPL");
        YahooFinanceURLConstructor GOOG_URL = new YahooFinanceURLConstructor(startDate, endDate, "GOOG");
        YahooFinanceURLConstructor MSFT_URL = new YahooFinanceURLConstructor(startDate, endDate, "MSFT");
        YahooFinanceURLConstructor BNPPA_URL = new YahooFinanceURLConstructor(startDate, endDate, "BNP.PA");
        YahooFinanceURLConstructor ORPA_URL = new YahooFinanceURLConstructor(startDate, endDate, "OR.PA");
        YahooFinanceURLConstructor EFRPA_URL = new YahooFinanceURLConstructor(startDate, endDate, "FR.PA");
        
        System.out.println("Interroge Yahoo Finance...");
        
        // YahooFinanceHttp contient une colonne de la future tickersList
        YahooFinanceHttp AAPL = new YahooFinanceHttp(AAPL_URL.constructURL()); 
        YahooFinanceHttp GOOG = new YahooFinanceHttp(GOOG_URL.constructURL());
        YahooFinanceHttp MSFT = new YahooFinanceHttp(MSFT_URL.constructURL());
        YahooFinanceHttp BNPPA = new YahooFinanceHttp(BNPPA_URL.constructURL());
        YahooFinanceHttp ORPA = new YahooFinanceHttp(ORPA_URL.constructURL());
        YahooFinanceHttp EFRPA = new YahooFinanceHttp(EFRPA_URL.constructURL());
        
        System.out.println("Donnees recuperees avec succes");
        
        // On construit la tickersList en concaténant les colonnes
        tickersList.add(AAPL);
        tickersList.add(GOOG);
        tickersList.add(MSFT);
        tickersList.add(BNPPA);
        tickersList.add(ORPA);
        tickersList.add(EFRPA);
        
        // Data est un objet qui contient la matrice des prix et la matrice des log returns obtenues à partir de la tickersList
        Data data = new Data(tickersList);
 
        // Portfolio est un objet qui contient les poids de chaque investissement et les rendements associés pour chaque jour de trading (histogramme...)
        Portfolio portfolio = new Portfolio(data);
        
        // Creation d'une valueAtRisk historique
        //ValueAtRisk valueAtRisk = new ValueAtRisk(portfolio.getExpectedRawReturn(),5.0);
       
        // Test
    	System.out.println("repartition initiale" + portfolio.toString());
    	Portfolio clonePortfolio = portfolio.clone();
    	
    	System.out.println("repartition initiale du clone" + clonePortfolio.toString());
    	System.out.println("La V@R initiale de votre portefeuille est "+portfolio.getValueAtRisk());
    	System.out.println("La V@R initiale de votre portefeuille clone est "+clonePortfolio.getValueAtRisk());
    	
    	double[] tableau = Mutation.buyAndSell(portfolio.getWeights());
    	portfolio.setWeights(tableau);
    	//portfolio = Mutation.buyAndSell(portfolio);
    	System.out.println("repartition finale" + portfolio.toString());
    	System.out.println("repartition finale du clone " + clonePortfolio.toString());

    	//Portfolio solution = Recuit.solution(data);
    }
 
}
