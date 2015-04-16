import java.util.ArrayList;
import java.util.Calendar;

import Recuit.Mutation;
import Recuit.Recuit;
import YahooFinance.YahooFinanceHttp;
import YahooFinance.YahooFinanceURLConstructor;
import Data.*;

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException{
 
        Calendar startDate = Calendar.getInstance();
        //Date de debut
        startDate.set(Calendar.YEAR, 2014);
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

        Data data = new Data(tickersList);

        Portfolio portfolio = new Portfolio(data);
        
        // Test
    	System.out.println("portefeuille initial \n\n" + portfolio.toString()+"\n\n");
    	Recuit.solution(data);
    }
 
}
