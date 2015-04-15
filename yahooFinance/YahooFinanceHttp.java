package YahooFinance;

// Basé sur le code de Aris David - Parser pour récupérer dans plusieurs ArrayList l'ensemble des donnees fournies par Yahoo Finance

import java.net.*;
import java.util.ArrayList;
import java.io.*;
 
public class YahooFinanceHttp {
 
        private ArrayList<String> dateStrList = new ArrayList<String>();
        private ArrayList<Double> openList = new ArrayList<Double>();
        private ArrayList<Double> lowList = new ArrayList<Double>();
        private ArrayList<Double> highList = new ArrayList<Double>();
        private ArrayList<Double> closeList = new ArrayList<Double>();
        private ArrayList<Long> volumeList = new ArrayList<Long>();
        private ArrayList<Double> adjCloseList = new ArrayList<Double>();
 
        private URL url = null;
        private URLConnection urlConn = null;
        private InputStreamReader  inStream = null;
 
         public YahooFinanceHttp(String urlStr){
          try {
            //Open Connection the Yahoo Finance URL
            this.url  = new URL(urlStr);
            this.urlConn = url.openConnection();
 
            //Start Reading
            this.urlConn = this.url.openConnection();
            this.inStream = new InputStreamReader(this.urlConn.getInputStream());
            BufferedReader buff= new BufferedReader(this.inStream);
            String stringLine;
 
            buff.readLine(); //Read the firstLine. This is the header.
 
                while((stringLine = buff.readLine()) != null) //While not in the header
                {
 
                      String [] dohlcav = stringLine.split("\\,"); //date, ohlc, adjustedclose
 
                        String date = dohlcav[0];
                        double open = Double.parseDouble(dohlcav[1]);
                        double high = Double.parseDouble(dohlcav[2]);
                        double low = Double.parseDouble(dohlcav[3]);
                        double close = Double.parseDouble(dohlcav[4]);
                        long volume = Long.parseLong(dohlcav[5]);
                        double adjClose = Double.parseDouble(dohlcav[6]);
 
                        //Set the Data
                        dateStrList.add(date);
                        openList.add(open);
                        highList.add(high);
                        lowList.add(low);
                        closeList.add(close);
                        volumeList.add(volume);
                        adjCloseList.add(adjClose);
                }
 
            }catch (MalformedURLException e) {
                System.out.println(e.getMessage());
            }catch(IOException e){
                System.out.println(e.getMessage());
            }
            // Je trouve plus intuitif d'avoir en position "0" les plus anciennes donnees.
            // intuitivement rn = ln(rn/r(n-1)) et pas l'inverse!
          
          Collections.reverse(dateStrList);
          Collections.reverse(openList);
          Collections.reverse(highList);
          Collections.reverse(lowList);
          Collections.reverse(closeList);
          Collections.reverse(volumeList);
          Collections.reverse(adjCloseList);
       }
 
         //return the data
         public ArrayList<String> getDate(){
             return dateStrList;
         }
         public ArrayList<Double> getOpen(){
             return openList;
         }
         public ArrayList<Double> getHigh(){
             return highList;
         }
         public ArrayList<Double> getLow(){
             return lowList;
         }
         public ArrayList<Double> getClose(){
             return closeList;
         }
         public ArrayList<Double> getAdjClose(){
             return adjCloseList;
         }
         public ArrayList<Long> getVolume(){
             return volumeList;
         }
}
