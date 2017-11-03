

import java.util.ArrayList;
import java.util.List;

import mkdt.CurrentMarketPrice;
import mkdt.GetStockQuote;
import mkdt.StockWorker;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import file.BSE;
import file.NSE;
public class Start {
	
	public static void main(String[] args) {
		System.out.println(" Scanning all the stocks now....");
		//https://www.nseindia.com/archives/nsccl/var/C_VAR1_30102017_1.DAT
	    //http://www.bseindia.com/corporates/List_Scrips.aspx
		ExecutorService executor = Executors.newFixedThreadPool(10);
		
		List<CurrentMarketPrice> tickers =  NSE.getNSEScripts();
		tickers.addAll(BSE.getNSEScripts());
		
		
		
		/* List<CurrentMarketPrice> tickers  = new ArrayList<CurrentMarketPrice>();
		 CurrentMarketPrice t = new CurrentMarketPrice();
		t.setE("NSE");
		t.setT("20MICRONS");
		tickers.add(t);*/
		for (CurrentMarketPrice ticker: tickers ) {
            Runnable worker = new StockWorker(ticker);
            executor.execute(worker);
          }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        
        System.out.println("NSE scripts updated "+GetStockQuote.getNSECount());
        System.out.println("BSE scripts updated "+GetStockQuote.getBSECount());
		

	}

}
