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
        startDate.set(Calendar.DATE, 1);
 
        Calendar endDate = Calendar.getInstance();
        //Date de fin
        endDate.set(Calendar.YEAR, 2015);
        endDate.set(Calendar.MONTH, 3);
        endDate.set(Calendar.DATE, 9);
 
        // La tickersList contient l'ensemble des tickers que nos considérons et leur prix respectif sur la fenetre d'observation
        ArrayList<YahooFinanceHttp> tickersList = new ArrayList<YahooFinanceHttp>();
        
        // L'URL constructor permet de formuler la requete à Yahoo
        YahooFinanceURLConstructor AAPL_URL = new YahooFinanceURLConstructor(startDate, endDate, "AAPL");
        YahooFinanceURLConstructor GOOG_URL = new YahooFinanceURLConstructor(startDate, endDate, "GOOG");
        
        System.out.println("Interroge Yahoo Finance...");
        
        // YahooFinanceHttp contient une colonne de la future tickersList
        YahooFinanceHttp AAPL = new YahooFinanceHttp(AAPL_URL.constructURL()); 
        YahooFinanceHttp GOOG = new YahooFinanceHttp(GOOG_URL.constructURL());
        
        System.out.println("Donnees recuperees avec succes");
        
        // On construit la tickersList en concaténant les colonnes
        tickersList.add(AAPL);
        tickersList.add(GOOG);
        
        // Data est un objet qui contient la matrice des prix et la matrice des log returns obtenues à partir de la tickersList
        Data data = new Data(tickersList);
 
        // Test
        System.out.println("prix Apple le 1er avril: "+(data.getMQuotes())[0][0]);
        System.out.println("prix Google le 1er avril: "+(data.getMQuotes())[0][1]);
        System.out.println("prix Apple le 2 avril: "+(data.getMQuotes())[1][0]);
        System.out.println("prix Google le 2 avril: "+(data.getMQuotes())[1][1]);
        System.out.println("1er log return Apple= ln("+(data.getMQuotes())[1][0]+"/"+(data.getMQuotes())[0][0]+") = "+Math.log((data.getMQuotes())[1][0]/(data.getMQuotes())[0][0]));
        System.out.println("1er log return Apple= ln("+(data.getMQuotes())[1][1]+"/"+(data.getMQuotes())[0][1]+") = "+Math.log((data.getMQuotes())[1][1]/(data.getMQuotes())[0][1]));
        System.out.println("1er element de la matrice des log returns :"+(data.getMReturns())[0][0]);
        System.out.println("2nd element de la matrice des log returns :"+(data.getMReturns())[0][1]);
    }
 
}
