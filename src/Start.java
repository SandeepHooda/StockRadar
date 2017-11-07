


import java.net.InetAddress;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
		
		Calendar cal = new GregorianCalendar();
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
			System.out.println(" Today is exchange Holiday ");
			return ;
		}
		//https://www.nseindia.com/archives/nsccl/var/C_VAR1_30102017_1.DAT
	    //http://www.bseindia.com/corporates/List_Scrips.aspx
		
		ExecutorService executor = Executors.newFixedThreadPool(5);
		
		List<CurrentMarketPrice> tickers =  NSE.getNSEScripts();
		GetStockQuote.totalNSECount = tickers.size();
		tickers.addAll(BSE.getNSEScripts());
		GetStockQuote.totalBSECount = tickers.size() - GetStockQuote.totalNSECount;
		boolean internetAvailable = false;
		while (!internetAvailable){
			
			try {
				Thread.sleep(1000);
				InetAddress Address = InetAddress.getByName("www.nseindia.com");
				internetAvailable = Address.isReachable(1000);
				internetAvailable = true;
			} catch (Exception e) {
				System.err.println(" Not able to connect to internet!!!");
				
			} 
		}
		
		
		
		/*List<CurrentMarketPrice> tickers  = new ArrayList<CurrentMarketPrice>();
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
        GetStockQuote.saveXirrListToDB();
        System.out.println("Saved xirr data to nse-tickers-xirr "+GetStockQuote.getNSECount());
        System.out.println(" BSE finished in Minutes "+((System.currentTimeMillis()- GetStockQuote.bseStartTime )/60000));
        System.out.println("NSE scripts updated "+GetStockQuote.getNSECount());
        System.out.println("BSE scripts updated "+GetStockQuote.getBSECount());
		

	}

}
