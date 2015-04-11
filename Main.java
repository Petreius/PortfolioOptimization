import java.util.ArrayList;
import java.util.Calendar;

import YahooFinance.YahooFinanceHttp;
import YahooFinance.YahooFinanceURLConstructor;
import Data.*;

public class Main {
    public static void main(String[] args){
 
        Calendar startDate = Calendar.getInstance();
        //Date de debut
        startDate.set(Calendar.YEAR, 2015);
        startDate.set(Calendar.MONTH,3); //Mois Jan = 0, Fev = 1...Dec = 11
        startDate.set(Calendar.DATE, 6);
 
        Calendar endDate = Calendar.getInstance();
        //Date de fin
        endDate.set(Calendar.YEAR, 2015);
        endDate.set(Calendar.MONTH, 3);
        endDate.set(Calendar.DATE, 8);
 
        // La tickersList contient l'ensemble des tickers que nos considérons et leur prix respectif sur la fenetre d'observation
        ArrayList<YahooFinanceHttp> tickersList = new ArrayList<YahooFinanceHttp>();
        
        // L'URL constructor permet de formuler la requete à Yahoo
        YahooFinanceURLConstructor AAPL_URL = new YahooFinanceURLConstructor(startDate, endDate, "AAPL");
        YahooFinanceURLConstructor GOOG_URL = new YahooFinanceURLConstructor(startDate, endDate, "GOOG");
        YahooFinanceURLConstructor MSFT_URL = new YahooFinanceURLConstructor(startDate, endDate, "MSFT");
        
        System.out.println("Interroge Yahoo Finance...");
        
        // YahooFinanceHttp contient une colonne de la future tickersList
        YahooFinanceHttp AAPL = new YahooFinanceHttp(AAPL_URL.constructURL()); 
        YahooFinanceHttp GOOG = new YahooFinanceHttp(GOOG_URL.constructURL());
        YahooFinanceHttp MSFT = new YahooFinanceHttp(MSFT_URL.constructURL());
        
        System.out.println("Donnees recuperees avec succes");
        
        // On construit la tickersList en concaténant les colonnes
        tickersList.add(AAPL);
        tickersList.add(GOOG);
        tickersList.add(MSFT);
        
        // Data est un objet qui contient la matrice des prix et la matrice des log returns obtenues à partir de la tickersList
        Data data = new Data(tickersList);
 
        // Portfolio est un objet qui contient les poids de chaque investissement et les rendements associés pour chaque jour de trading (histogramme...)
        Portfolio portfolio = new Portfolio(data);
        
        // Creation d'une valueAtRisk historique
        ValueAtRisk valueAtRisk = new ValueAtRisk(portfolio.getExpectedRawReturn(),5.0);
       
        // Test
    	System.out.println("Voici les prix Apple pour ces 3 jours: "+data.getMQuotes()[0][0]+" , "+data.getMQuotes()[1][0]+" , "+data.getMQuotes()[2][0]);
    	System.out.println("Voici les retours associes: "+data.getMRawReturns()[0][0]+" , "+data.getMRawReturns()[1][0]);
    	System.out.println("Voici les prix Google pour ces 3 jours: "+data.getMQuotes()[0][1]+" , "+data.getMQuotes()[1][1]+" , "+data.getMQuotes()[2][1]);
    	System.out.println("Voici les retours associes: "+data.getMRawReturns()[0][1]+" , "+data.getMRawReturns()[1][1]);
    	System.out.println("Voici les prix Microsoft pour ces 3 jours: "+data.getMQuotes()[0][2]+" , "+data.getMQuotes()[1][2]+" , "+data.getMQuotes()[2][2]);
    	System.out.println("Voici les retours associes: "+data.getMRawReturns()[0][2]+" , "+data.getMRawReturns()[1][2]);
    	System.out.println("Voici la repartition de votre portefeuille: "+portfolio.getWeights()[0]+" , "+portfolio.getWeights()[1]+" , "+portfolio.getWeights()[2]);
    	System.out.println("Les retours de votre portefeuille sont donc: "+portfolio.getExpectedRawReturn()[0]+" , "+portfolio.getExpectedRawReturn()[1]);
    	System.out.println("Les retours logarithmiques de votre portefeuille: "+portfolio.getExpectedLogReturn()[0]+" , "+portfolio.getExpectedLogReturn()[1]);
    	System.out.println("La V@R de votre portefeuille est "+valueAtRisk.getValueAtRisk());
    }
 
}
