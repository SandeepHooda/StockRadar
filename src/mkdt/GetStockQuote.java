package mkdt;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import com.XirrCalculatorService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import dao.StockPrice;
import dao.StockPriceDAO;
import dao.TickerDBData;
import dao.XirrValue;


public class GetStockQuote {
	private static List<TickerDBData> nseTickersList = new ArrayList<TickerDBData>();
	public static List<TickerDBData> nseTickersSaveList = new ArrayList<TickerDBData>(); 
	private static float percentComplete =0;
	public static float nseCount = 0;
	private static float bseCount = 0;
	public static float totalNSECount = 0;
	public static float totalBSECount = 0;
	public static long nseStartTime;
	public static long bseStartTime;
	final static Pattern nse_pattern_totalTradedVolume = Pattern.compile("\"totalTradedVolume\":\"(.+?)\"}");
	final static Pattern nse_pattern_deliveryToTradedQuantity = Pattern.compile("\"deliveryToTradedQuantity\":\"(.+?)\",");
	final static Pattern nse_pattern = Pattern.compile("\"lastPrice\":\"(.+?)\",");
	final static Pattern nse_patternDate = Pattern.compile("\"lastUpdateTime\":\"(.+?)\",");
	final static Pattern bse_pattern = Pattern.compile("<td.*>(.+?)</td><td><img");
	public static SimpleDateFormat stockQuoteDateTime = new SimpleDateFormat("dd-MMM-yyyy h:m:s");
	private static SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat dd = new SimpleDateFormat("dd");
	private static int todayDate ; 
	private static int todayDay;
	private static int xirrForthNightStartDate, xirrForthNightEndDate;
	static {
		stockQuoteDateTime.setTimeZone(TimeZone.getTimeZone("IST"));
		todayDate = Integer.parseInt(yyyymmdd.format(new Date()));
		todayDay = Integer.parseInt(dd.format(new Date()));
		Calendar cal = new GregorianCalendar();
		if (todayDay >= 15){ // 1 to 15 of this month
			cal.set(Calendar.DATE, 15);
			xirrForthNightEndDate = Integer.parseInt(yyyymmdd.format(cal.getTime()));
			cal.set(Calendar.DATE, 1);
			xirrForthNightStartDate = Integer.parseInt(yyyymmdd.format(cal.getTime()));
		}else {
			//15 to last date of that month
			cal.add(Calendar.MONTH, -1);
		    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		    xirrForthNightEndDate = Integer.parseInt(yyyymmdd.format(cal.getTime()));
		    cal.set(Calendar.DATE, 15);
		    xirrForthNightStartDate = Integer.parseInt(yyyymmdd.format(cal.getTime()));
		}
	}
	
	private static void savePriceInDB(CurrentMarketPrice markerResponse,  TickerDBData tickerDBData, boolean rowExistInDB, int counter){
		
		String key = StockPriceDAO.mlabKeySonu;
	
			tickerDBData.set_id(markerResponse.getT());
			List<StockPrice> stockPrices = tickerDBData.getStockPriceList();
			
			
				StockPrice stockPrice = new StockPrice();
				stockPrice.setPrice(markerResponse.getL_fix());
				stockPrice.setDate(todayDate);
				stockPrice.setTotalTradedVolume(markerResponse.getTotalTradedVolume());
				stockPrice.setDeliveryToTradedQuantity(markerResponse.getDeliveryToTradedQuantity());
				stockPrices.add(0,stockPrice);
				
				calculateXirr(tickerDBData);
				StockPriceDAO.insertUpdateData(markerResponse.getE().toLowerCase()+counter, markerResponse.getT(), dataStr(tickerDBData), key, rowExistInDB);
			
	
	}
	
	private static void updateProgress(String exchange, TickerDBData tickerDBData, int counter){
		
		if ("NSE".equalsIgnoreCase(exchange)){
			synchronized (nseTickersList) {
				nseTickersList.add(tickerDBData);
				nseTickersSaveList.add(tickerDBData);
				percentComplete = (nseCount/ totalNSECount *100);
				
				
			}
			addToNSECount();
		}else {
			addToBSECount();
		}
		
		System.out.printf(counter+" Progress NSE: %.2f \r",percentComplete);
	}
	
	//xirr from 1 to 15 and then 15 to last day all days
	//This will be used to calculate median for last  3 months, 6 months, 1 year , 2 year, 3 year and 5 years
	private static void setEveryForthNight(TickerDBData tickerDBData) throws ParseException{
		
		//xirr15StartDate, xirr15EndDate
		
		if (tickerDBData.getXirrEveryForthNight().size() > 0){
			XirrValue xirrValue = tickerDBData.getXirrEveryForthNight().get(0);
			if (xirrValue.getEndDate()<xirrForthNightEndDate){//Check if already have latest forth night xirr or not
				xirrValue = calXirrForLatestForthNight(tickerDBData);
				if (null != xirrValue){
					tickerDBData.getXirrEveryForthNight().add(0,xirrValue);
				}
				
			}
		}else {
			XirrValue xirrValue = calXirrForLatestForthNight(tickerDBData);
			if (null != xirrValue){
				tickerDBData.getXirrEveryForthNight().add(calXirrForLatestForthNight(tickerDBData));
			}
			
		}
		
		if (tickerDBData.getXirrEveryForthNight().size() >=6){//calculate last 3 months median xirr
			tickerDBData.setMedian3(getMedian(tickerDBData.getXirrEveryForthNight().subList(0, 6)));
		}
		if (tickerDBData.getXirrEveryForthNight().size() >=12){//calculate last 6 months median xirr
			tickerDBData.setMedian6(getMedian(tickerDBData.getXirrEveryForthNight().subList(0, 12)));
		}
		if (tickerDBData.getXirrEveryForthNight().size() >=24){//calculate last 1 year median xirr
			tickerDBData.setMedian1Y(getMedian(tickerDBData.getXirrEveryForthNight().subList(0, 24)));
		}
		if (tickerDBData.getXirrEveryForthNight().size() >=48){//calculate last 2 year median xirr
			tickerDBData.setMedian2Y(getMedian(tickerDBData.getXirrEveryForthNight().subList(0, 48)));
		}
		if (tickerDBData.getXirrEveryForthNight().size() >=72){//calculate last 3 year median xirr
			tickerDBData.setMedian3Y(getMedian(tickerDBData.getXirrEveryForthNight().subList(0, 72)));
		}
		if (tickerDBData.getXirrEveryForthNight().size() >=120){//calculate last 5 year median xirr
			tickerDBData.setMedian5Y(getMedian(tickerDBData.getXirrEveryForthNight().subList(0, 120)));
		}
	}
	private static double getMedian(List<XirrValue> xirrList){
		double [] numArray = new double[xirrList.size()];
		int i=0;
		for (XirrValue xirr : xirrList){
			numArray[i] = xirr.getXirrValue();
			i++;
		}
		Arrays.sort(numArray);
		double median;
		if (numArray.length % 2 == 0)
		    median = ((double)numArray[numArray.length/2] + (double)numArray[numArray.length/2 - 1])/2;
		else
		    median = (double) numArray[numArray.length/2];
		return median;
	}
	private static XirrValue calXirrForLatestForthNight(TickerDBData tickerDBData) throws ParseException{
		List<StockPrice> stockPrices = tickerDBData.getStockPriceList();
		double [] payments = new double[2];
		Date [] dates = new Date[2];
		boolean payment1Set = false, payment2Set = false;
		int i = 0;
		for (StockPrice price: stockPrices){
			i++;
			if (!payment1Set && price.getDate() <= xirrForthNightEndDate){//End of forth night not set
				payment1Set = true;
				payments[0] = price.getPrice();
				dates [0] =  yyyymmdd.parse(""+ price.getDate());
			}
			if (payment1Set && (price.getDate() <= xirrForthNightStartDate  || i== stockPrices.size())){
				payment2Set = true;
				payments[1] = price.getPrice() *-1;
				dates [1] =  yyyymmdd.parse(""+ price.getDate());
			}
			if (payment2Set){//Both paymemnt set now exit for loop and calculate xirr 
				XirrValue xirrValue = new XirrValue();
				xirrValue.setXirrValue(XirrCalculatorService.Newtons_method(0.1, payments, dates));
				xirrValue.setEndDate(xirrForthNightEndDate);
				xirrValue.setStartDate(xirrForthNightStartDate);
				return xirrValue;
				
				
			}
		}
		return null;
	}
	private static void calculateXirr(TickerDBData tickerDBData ){
		
		try {
			double [] payments = new double[2];
			Date [] dates = new Date[2];
			List<StockPrice> stockPrices = tickerDBData.getStockPriceList();
			payments[0] = stockPrices.get(0).getPrice() ;
			try{
				dates[0] = yyyymmdd.parse(""+ stockPrices.get(0).getDate());
			}catch(Exception e){
				dates[0] = new Date();
				System.out.println(" could not parse date #"+ stockPrices.get(0).getDate()+"#");
				System.out.println(" Ticket  "+tickerDBData.get_id()+"  price "+payments[0]);
			}
			
			tickerDBData.setCurrentMarketPrice(stockPrices.get(0).getPrice());
			if (stockPrices.size() >=14){
				try{
					setEveryForthNight( tickerDBData);
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
			 
			int xiirDay = 5;
			if (stockPrices.size() >=xiirDay){
				payments[1] = stockPrices.get(xiirDay-1).getPrice() ;
				payments[1] *=  -1;
				dates[1] = yyyymmdd.parse(""+ stockPrices.get(xiirDay-1).getDate());
				tickerDBData.setXirr5(XirrCalculatorService.Newtons_method(0.1, payments, dates));
			}
			xiirDay = 10;
			if (stockPrices.size() >=xiirDay){
				payments[1] = stockPrices.get(xiirDay-1).getPrice() ;
				payments[1] *=  -1;
				dates[1] = yyyymmdd.parse(""+ stockPrices.get(xiirDay-1).getDate());
				tickerDBData.setXirr10(XirrCalculatorService.Newtons_method(0.1, payments, dates));
			}
			xiirDay = 30;
			if (stockPrices.size() >=xiirDay){
				payments[1] = stockPrices.get(xiirDay-1).getPrice() ;
				payments[1] *=  -1;
				dates[1] = yyyymmdd.parse(""+ stockPrices.get(xiirDay-1).getDate());
				tickerDBData.setXirr30(XirrCalculatorService.Newtons_method(0.1, payments, dates));
			}
			xiirDay = 182;
			if (stockPrices.size() >=xiirDay){
				payments[1] = stockPrices.get(xiirDay-1).getPrice() ;
				payments[1] *=  -1;
				dates[1] = yyyymmdd.parse(""+ stockPrices.get(xiirDay-1).getDate());
				tickerDBData.setXirr182(XirrCalculatorService.Newtons_method(0.1, payments, dates));
			}
			xiirDay = 365;
			if (stockPrices.size() >=xiirDay){
				payments[1] = stockPrices.get(xiirDay-1).getPrice() ;
				payments[1] *=  -1;
				dates[1] = yyyymmdd.parse(""+ stockPrices.get(xiirDay-1).getDate());
				tickerDBData.setXirr365(XirrCalculatorService.Newtons_method(0.1, payments, dates));
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
	}
	
	private static String dataStr(TickerDBData tickerDBData){
		Gson  json = new Gson();
		return  json.toJson(tickerDBData, new TypeToken<TickerDBData>() {}.getType());
	}
	private static String dataStr(List<TickerDBData> tickerDBData){
		Gson  json = new Gson();
		return  json.toJson(tickerDBData, new TypeToken<List<TickerDBData>>() {}.getType());
	}
	private static TickerDBData toTickerData(String jsonStr){
		Gson  json = new Gson();
		return  (TickerDBData)json.fromJson(jsonStr, new TypeToken<TickerDBData>() {}.getType());
	}
	public static void getCurrentMarkerPrice(CurrentMarketPrice ticker, int counter){
		CurrentMarketPrice markerResponse =null;
		String historicalPrice = StockPriceDAO.getData(ticker.getE().toLowerCase()+counter, ticker.getT(), StockPriceDAO.mlabKeySonu);
		TickerDBData tickerDBData  = null;
		boolean priceAvailableInDb = true;
		boolean rowExistInDB = true;

		if (StockPriceDAO.noCollection.equals(historicalPrice)){
			tickerDBData = new TickerDBData();
			tickerDBData.set_id(ticker.getT());
			priceAvailableInDb = false;
			rowExistInDB = false;
			
		}else {
			tickerDBData = toTickerData( historicalPrice);
		}
		
		if (!rowExistInDB || ( tickerDBData.getStockPriceList().get(0).getDate() < Integer.parseInt(yyyymmdd.format(new Date()))) ){
			priceAvailableInDb = false;	
		}
		
		
		String nseURL = "https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/GetQuote.jsp?symbol=";
		String nsePostFix = "&illiquid=0&smeFlag=0&itpFlag=0";
		String bseURL = "http://www.bseindia.com/stock-share-price/SiteCache/IrBackupStockReach.aspx?scripcode=";
		StringBuilder respoBse = null;

		 CloseableHttpClient httpclient = HttpClients.createDefault();
	     if (!priceAvailableInDb){
	    	 if ("NSE".equals(ticker.getE())){
					if (nseStartTime == 0){
						nseStartTime = System.currentTimeMillis();
					}
					
					
					
					StringBuilder respoNse = new StringBuilder();
					try {
						
				       
				        HttpGet httpGet = new HttpGet(nseURL+ticker.getT()+nsePostFix);
				        CloseableHttpResponse response1 = httpclient.execute(httpGet);
				        
				        try {
			                HttpEntity entity1 = response1.getEntity();
			                
			                BufferedReader br = new BufferedReader(new InputStreamReader(entity1.getContent()));
			                String line = "";
			                
			                while ((line = br.readLine())!= null) {
			                	respoNse.append(line);
			                }
			                // do something useful with the response body
			                // and ensure it is fully consumed
			                EntityUtils.consume(entity1);
			                
			                //System.out.println(respoNse);
				            final Matcher quote = nse_pattern.matcher(respoNse);
							Matcher quoteDate = nse_patternDate.matcher(respoNse);
							if (quote.find() && quoteDate.find()){
								CurrentMarketPrice nseQuote = new CurrentMarketPrice();
								nseQuote.setT(ticker.getT());
								nseQuote.setE("NSE");
								nseQuote.setLt_dts(quoteDate.group(1));
								String price = quote.group(1).replaceAll(",", "");
								nseQuote.setL_fix(Double.parseDouble(price));
								
								try{
									//nse_pattern_totalTradedVolume
									Matcher nse_pattern_totalTradedVolumeMatcher = nse_pattern_totalTradedVolume.matcher(respoNse);
									nse_pattern_totalTradedVolumeMatcher.find();
									price = nse_pattern_totalTradedVolumeMatcher.group(1).replaceAll(",", "");
									nseQuote.setTotalTradedVolume(Double.parseDouble(price));
									
									Matcher nse_pattern_deliveryToTradedQuantityMatcher = nse_pattern_deliveryToTradedQuantity.matcher(respoNse);
									nse_pattern_deliveryToTradedQuantityMatcher.find();
									price = nse_pattern_deliveryToTradedQuantityMatcher.group(1).replaceAll(",", "");
									nseQuote.setDeliveryToTradedQuantity(Double.parseDouble(price));
									
								}catch(Exception e){
									
								}
								
								
								markerResponse = nseQuote;
							}else {
								System.out.println(" No info for "+ticker.getT());
								//System.out.println(respoNse);
							}
							
							
			            } finally {
			                response1.close();
			            }
			            
						
			        } catch (Exception e) {
			        	System.out.println(respoNse);
			        	System.out.println(" Error for : "+ticker.getT());
			        	e.printStackTrace();
			        }
				}else {
					
					if (bseStartTime == 0){
						bseStartTime = System.currentTimeMillis();
						
						System.out.println(" NSE finished in Minutes "+((bseStartTime - nseStartTime)/60000));
						
					}
						try {
							
							HttpGet httpGet = new HttpGet(bseURL+ticker.getT());
					        CloseableHttpResponse response1 = httpclient.execute(httpGet);
					        
					        try {
				                HttpEntity entity1 = response1.getEntity();
				                
				                BufferedReader br = new BufferedReader(new InputStreamReader(entity1.getContent()));
				                String line = "";
				                 respoBse = new StringBuilder();
				                while ((line = br.readLine())!= null) {
				                	respoBse.append(line);
				                }
				                // do something useful with the response body
				                // and ensure it is fully consumed
				                EntityUtils.consume(entity1);
				                
				                if (null != respoBse &&  respoBse.indexOf("nochange.gif") <0 && respoBse.indexOf("Sorry") <0 ){
				                	 final Matcher quote = bse_pattern.matcher(respoBse);
										quote.find();
										CurrentMarketPrice bseQuote = new CurrentMarketPrice();
										bseQuote.setT(ticker.getT());
										bseQuote.setE("BSE");
										bseQuote.setLt_dts(stockQuoteDateTime.format(new Date()));
										String price = quote.group(1).replaceAll(",", "");
										bseQuote.setL_fix(Double.parseDouble(price));
										markerResponse =  bseQuote;
				                }
				              
				               
								//System.out.println(" response from bse "+markerResponse.getT()+ " : "+ markerResponse.getL_fix());
				            } finally {
				                response1.close();
				            }
				            
						
				     
						
			        } catch (Exception e) {
			        	
			        	if (null != respoBse){
			        		System.out.println(respoBse.toString());
			        	}else {
			        		e.printStackTrace();
			        	}
			        	
			        	if (null != ticker){
			        		System.out.println(bseURL+ticker.getT());
			        	}
			        	
			        	//System.out.println(" Error for : "+ticker.getT());
			        	//e.printStackTrace();
			        }
					
					
				}
	     }
		
			
	
			if (null!= markerResponse && markerResponse.getL_fix() > 0){
				if (tickerDBData.getHighestPrice()< markerResponse.getL_fix()){
					tickerDBData.setHighestPrice(markerResponse.getL_fix());
				}
				savePriceInDB(markerResponse,tickerDBData, rowExistInDB, counter);
			}
			updateProgress(ticker.getE().toUpperCase(), tickerDBData, counter);
			
		
		
	}

	public static void saveXirrListToDB(int counter){
		StockPriceDAO.insertUpdateData("nse-tickers-xirr", "nse-tickers-xirr"+counter, dataStr(nseTickersSaveList), StockPriceDAO.mlabKeySonu, true);
	}
	public static synchronized void   addToNSECount(){
		nseCount++;
	}
	public static synchronized void   addToBSECount(){
		bseCount++;
	}
	
	public static float   getNSECount(){
		return nseCount;
	}
	public static float  getBSECount(){
		return bseCount;
	}

}
