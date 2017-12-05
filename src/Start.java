


import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import mkdt.CurrentMarketPrice;
import mkdt.GetStockQuote;
import mkdt.StockWorker;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dao.TickerDBData;
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
		if (cal.get(Calendar.HOUR_OF_DAY) < 16){
			System.out.println(" Will run after 4 PM. Time is "+cal.get(Calendar.HOUR_OF_DAY) );
			return ;
		}
		//https://www.nseindia.com/archives/nsccl/var/C_VAR1_30102017_1.DAT
	    //http://www.bseindia.com/corporates/List_Scrips.aspx
		for (int counter=10 ; counter<=110; counter+=10){
			
		
		ExecutorService executor = Executors.newFixedThreadPool(5);
		
		List<CurrentMarketPrice> tickers =  NSE.getNSEScripts(counter);
		/*CurrentMarketPrice mp = new CurrentMarketPrice();
		mp.setE("NSE");
		mp.setT("MOTHERSUMI");
		tickers.add(mp);*/
		GetStockQuote.totalNSECount = tickers.size();
		//tickers.addAll(BSE.getNSEScripts());
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
            Runnable worker = new StockWorker(ticker,counter);
            executor.execute(worker);
          }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        GetStockQuote.saveXirrListToDB(counter);
        System.out.println(counter+" Saved xirr data to nse-tickers-xirr "+GetStockQuote.getNSECount());
        
        GetStockQuote.nseTickersSaveList =new ArrayList<TickerDBData>();
        
        GetStockQuote.nseCount = 0;
       
       
		}

	}

}
