


import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
import mkdt.CurrentMarketPrice;
import mkdt.GetStockQuote;
import mkdt.StockWorker;
import stockAnalysis.AScript;
import stockAnalysis.Scripts;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import com.PriceChart.DBPriceData;
import com.PriceChart.DataSets;
import com.PriceChart.PriceVO;
import com.PriceChart.StockPrice;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import dao.StockPriceDAO;
import dao.TickerDBData;

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
			sanityCheck();
			return ;
		}
		//https://www.nseindia.com/archives/nsccl/var/C_VAR1_30102017_1.DAT
	    //http://www.bseindia.com/corporates/List_Scrips.aspx
		for (int counter=10 ; counter<=110; counter+=10){
		//for (int counter=60 ; counter<=60; counter+=10){	
		
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
		
		
		for (CurrentMarketPrice ticker: tickers ) {
            Runnable worker = new StockWorker(ticker,counter);
            executor.execute(worker);
          }
        executor.shutdown();
       
        while (!executor.isTerminated()) {
        }
        System.out.println(" Wait for be to update the DB");
       GetStockQuote.saveXirrListToDB(counter);
        System.out.println(counter+" Saved xirr data to nse-tickers-xirr "+GetStockQuote.getNSECount());
        
        GetStockQuote.nseTickersSaveList =new ArrayList<TickerDBData>();
        
        GetStockQuote.nseCount = 0;
       
       
		}
		sanityCheck();
	}
	
	private static void sanityCheck(){
		System.out.println(" running sanity check");
		String dbDataJson = null;
		List<PriceVO> priceVOList = new ArrayList<PriceVO>();
		int maxDays = 1;
		
		Gson  json = new Gson();
		String scripts = StockPriceDAO.getADocument("script-list","script-list","myscripts",StockPriceDAO.mlabKeySonu);
		Scripts  myScripts= json.fromJson(scripts, new TypeToken<Scripts>() {}.getType());
		for (AScript ascript : myScripts.getScripts()){
			dbDataJson =  StockPriceDAO.getADocument("nse-tickers-xirr",ascript.getCollection(),ascript.getId(),StockPriceDAO.mlabKeySonu);
			if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,ascript.getId(), ascript.getColor()));
		}
		
		
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
		String  today = sdf.format(new Date());
		for (PriceVO price :priceVOList){
			if (price.getLabels().get(0).equalsIgnoreCase(today)){
				
			}else {
				System.out.println(" Fail "+price.getDatasets().get(0).getLabel()+" = "+price.getLabels().get(0));
				Scanner scanner = new Scanner(System.in);
				scanner.nextLine();
			}
		}
		System.out.println(" All done ");
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
	}
	
private static PriceVO polulatePriceVO(String dbDataJson,int maxDays, String ticker , String bordercolor){
		
		Gson  json = new Gson();
		DBPriceData  dbStockPrice= json.fromJson(dbDataJson, new TypeToken<DBPriceData>() {}.getType());	
		PriceVO priceVO = new PriceVO();
		
		List<StockPrice> stockPriceList = dbStockPrice.getStockPriceList();
		int dbStockPriceSize  = stockPriceList.size();
		if (dbStockPriceSize > maxDays){
			 
			stockPriceList = stockPriceList.subList(0,maxDays);
		}
		dbStockPriceSize = maxDays;
		
		List<DataSets> dataSets = new ArrayList<DataSets>();
		priceVO.setDatasets(dataSets);
		DataSets dateSet = new DataSets();
		dataSets.add(dateSet);
		dateSet.setLabel(ticker);
		dateSet.setBorderColor(bordercolor);
		double percentageFactor =-1;
		for (int i=0;i<maxDays && i<dbStockPriceSize;i++){
			double price = stockPriceList.get(dbStockPriceSize-i-1).getPrice();
			if (percentageFactor == -1){
				percentageFactor = price;
				
			}
			String date = ""+stockPriceList.get(dbStockPriceSize-i-1).getDate();
			priceVO.getLabels().add(date.substring(4));
			dateSet.getData().add(price);
			
		}
		return priceVO;
		
	}

}
