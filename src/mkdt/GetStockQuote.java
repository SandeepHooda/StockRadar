package mkdt;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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


public class GetStockQuote {
	private static List<TickerDBData> nseTickersList = new ArrayList<TickerDBData>();
	private static float nseCount = 0;
	private static float bseCount = 0;
	public static float totalNSECount = 0;
	public static float totalBSECount = 0;
	public static long nseStartTime;
	public static long bseStartTime;
	final static Pattern nse_pattern = Pattern.compile("\"lastPrice\":\"(.+?)\",");
	final static Pattern nse_patternDate = Pattern.compile("\"lastUpdateTime\":\"(.+?)\",");
	final static Pattern bse_pattern = Pattern.compile("<td.*>(.+?)</td><td><img");
	public static SimpleDateFormat stockQuoteDateTime = new SimpleDateFormat("dd-MMM-yyyy h:m:s");
	private static SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyyMMdd");
	static {
		stockQuoteDateTime.setTimeZone(TimeZone.getTimeZone("IST"));
	}
	
	private static void savePriceInDB(String exchange, String ticker, double price){
		TickerDBData tickerDBData = null;
		String key = StockPriceDAO.mlabKeySonu;
		String historicalPrice = StockPriceDAO.getHistoricalPrice(exchange, ticker, key);
		if (StockPriceDAO.noCollection.equals(historicalPrice)){
			tickerDBData = new TickerDBData();
			tickerDBData.set_id(ticker);
			StockPrice stockPrice = new StockPrice();
			stockPrice.setPrice(price);
			stockPrice.setDate(Integer.parseInt(yyyymmdd.format(new Date())));
			tickerDBData.getStockPriceList().add(stockPrice);
			calculateXirr(tickerDBData);
			StockPriceDAO.insertUpdateData(exchange, ticker, dataStr(tickerDBData), key, false);
			
		}else {
			tickerDBData = toTickerData( historicalPrice);
			tickerDBData.set_id(ticker);
			List<StockPrice> stockPrices = tickerDBData.getStockPriceList();
			
			if (stockPrices.get(0).getDate() < Integer.parseInt(yyyymmdd.format(new Date()))){
				StockPrice stockPrice = new StockPrice();
				stockPrice.setPrice(price);
				stockPrice.setDate(Integer.parseInt(yyyymmdd.format(new Date())));
				stockPrices.add(0,stockPrice);
				calculateXirr(tickerDBData);
				StockPriceDAO.insertUpdateData(exchange, ticker, dataStr(tickerDBData), key, true);
			}
			
		}
		
		if ("NSE".equals(exchange)){
			synchronized (nseTickersList) {
				nseTickersList.add(tickerDBData);
				/*if (nseTickersList.size() == 10){
					String jsonData = dataStr(nseTickersList);
					StockPriceDAO.insertUpdateData("nse-tickers-xirr", "nse-tickers-xirr", jsonData, StockPriceDAO.mlabKeySonu, true);
				}*/
			}
			addToNSECount();
		}else {
			addToBSECount();
		}
		
		System.out.printf("Progress NSE: %.2f BSE pregress %.2f \r",(nseCount/ totalNSECount *100),(bseCount/ totalBSECount *100));
		//System.out.printf(" \r Progress NSE: %.2f "+nseCount+" BSE pregress %.2f "+ totalNSECount );
	}
	
	private static void calculateXirr(TickerDBData tickerDBData ){
		
		try {
			double [] payments = new double[2];
			Date [] dates = new Date[2];
			List<StockPrice> stockPrices = tickerDBData.getStockPriceList();
			payments[0] = stockPrices.get(0).getPrice() *-1;
			dates[0] = yyyymmdd.parse(""+ stockPrices.get(0).getDate());
			
			int xiirDay = 5;
			if (stockPrices.size() >=xiirDay){
				payments[1] = stockPrices.get(xiirDay-1).getPrice() ;
				dates[1] = yyyymmdd.parse(""+ stockPrices.get(xiirDay-1).getDate());
				tickerDBData.setXirr5(XirrCalculatorService.Newtons_method(0.1, payments, dates));
			}
			xiirDay = 10;
			if (stockPrices.size() >=xiirDay){
				payments[1] = stockPrices.get(xiirDay-1).getPrice() ;
				dates[1] = yyyymmdd.parse(""+ stockPrices.get(xiirDay-1).getDate());
				tickerDBData.setXirr10(XirrCalculatorService.Newtons_method(0.1, payments, dates));
			}
			xiirDay = 30;
			if (stockPrices.size() >=xiirDay){
				payments[1] = stockPrices.get(xiirDay-1).getPrice() ;
				dates[1] = yyyymmdd.parse(""+ stockPrices.get(xiirDay-1).getDate());
				tickerDBData.setXirr30(XirrCalculatorService.Newtons_method(0.1, payments, dates));
			}
			xiirDay = 182;
			if (stockPrices.size() >=xiirDay){
				payments[1] = stockPrices.get(xiirDay-1).getPrice() ;
				dates[1] = yyyymmdd.parse(""+ stockPrices.get(xiirDay-1).getDate());
				tickerDBData.setXirr182(XirrCalculatorService.Newtons_method(0.1, payments, dates));
			}
			xiirDay = 365;
			if (stockPrices.size() >=xiirDay){
				payments[1] = stockPrices.get(xiirDay-1).getPrice() ;
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
	public static CurrentMarketPrice getCurrentMarkerPrice(CurrentMarketPrice ticker){
		CurrentMarketPrice markerResponse = new  CurrentMarketPrice();
		String historicalPrice = StockPriceDAO.getHistoricalPrice((ticker.getE().toLowerCase()), ticker.getT(), StockPriceDAO.mlabKeySonu);
		TickerDBData tickerDBData = toTickerData( historicalPrice);
		List<StockPrice> stockPrices = null;
		if (tickerDBData != null){
			stockPrices = tickerDBData.getStockPriceList();
		}
		boolean priceAvailableInDb = true;
		if (null == stockPrices || ( stockPrices.get(0).getDate() < Integer.parseInt(yyyymmdd.format(new Date()))) ){
			priceAvailableInDb = false;	
		}else {
			markerResponse.setL_fix(stockPrices.get(0).getPrice());
			markerResponse.setE(ticker.getE().toLowerCase());
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
						StockPriceDAO.insertUpdateData("nse-tickers-xirr", "nse-tickers-xirr", dataStr(nseTickersList), StockPriceDAO.mlabKeySonu, true);
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
				savePriceInDB(markerResponse.getE(), markerResponse.getT(), markerResponse.getL_fix());
			}
			
		
		return markerResponse;
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
