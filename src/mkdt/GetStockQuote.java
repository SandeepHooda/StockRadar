package mkdt;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;


public class GetStockQuote {
	final static Pattern nse_pattern = Pattern.compile("\"lastPrice\":\"(.+?)\",");
	final static Pattern nse_patternDate = Pattern.compile("\"lastUpdateTime\":\"(.+?)\",");
	final static Pattern bse_pattern = Pattern.compile("<td.*>(.+?)</td><td><img");
	public static SimpleDateFormat stockQuoteDateTime = new SimpleDateFormat("dd-MMM-yyyy h:m:s");
	static {
		stockQuoteDateTime.setTimeZone(TimeZone.getTimeZone("IST"));
	}
	
	public static Map<String, CurrentMarketPrice> getCurrentMarkerPrice(List<CurrentMarketPrice> request){
		Map<String , CurrentMarketPrice> markerResponse = new HashMap<String , CurrentMarketPrice>();
		
		String nseURL = "https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/GetQuote.jsp?symbol=";
		String nsePostFix = "&illiquid=0&smeFlag=0&itpFlag=0";
		String bseURL = "http://www.bseindia.com/stock-share-price/SiteCache/IrBackupStockReach.aspx?scripcode=";


		 CloseableHttpClient httpclient = HttpClients.createDefault();
	
		for (CurrentMarketPrice ticker: request){
			if ("NSE".equals(ticker.getE())){
				try {
					
			       
			        HttpGet httpGet = new HttpGet(nseURL+ticker.getT()+nsePostFix);
			        CloseableHttpResponse response1 = httpclient.execute(httpGet);
			        
			        try {
		                HttpEntity entity1 = response1.getEntity();
		                
		                BufferedReader br = new BufferedReader(new InputStreamReader(entity1.getContent()));
		                String line;
		                StringBuilder respoNse = new StringBuilder();
		                while ((line = br.readLine())!= null) {
		                	respoNse.append(line);
		                }
		                // do something useful with the response body
		                // and ensure it is fully consumed
		                EntityUtils.consume(entity1);
		                
		              
			            final Matcher quote = nse_pattern.matcher(respoNse);
						Matcher quoteDate = nse_patternDate.matcher(respoNse);
						quote.find();
						quoteDate.find();
						CurrentMarketPrice nseQuote = new CurrentMarketPrice();
						nseQuote.setT(ticker.getT());
						nseQuote.setE("NSE");
						nseQuote.setLt_dts(quoteDate.group(1));
						String price = quote.group(1).replaceAll(",", "");
						nseQuote.setL_fix(Double.parseDouble(price));
						System.out.println(nseQuote.getT()+" NSE Quote : "+nseQuote.getL_fix());
						markerResponse.put(nseQuote.getT(), nseQuote);
		            } finally {
		                response1.close();
		            }
		            
					
		        } catch (Exception e) {
		        	
		        	e.printStackTrace();
		        }
			}else {
					try {
						
						HttpGet httpGet = new HttpGet(bseURL+ticker.getT());
				        CloseableHttpResponse response1 = httpclient.execute(httpGet);
				        
				        try {
			                HttpEntity entity1 = response1.getEntity();
			                
			                BufferedReader br = new BufferedReader(new InputStreamReader(entity1.getContent()));
			                String line;
			                StringBuilder respoBse = new StringBuilder();
			                while ((line = br.readLine())!= null) {
			                	respoBse.append(line);
			                }
			                // do something useful with the response body
			                // and ensure it is fully consumed
			                EntityUtils.consume(entity1);
			                
			              
			                final Matcher quote = bse_pattern.matcher(respoBse);
							quote.find();
							CurrentMarketPrice bseQuote = new CurrentMarketPrice();
							bseQuote.setT(ticker.getT());
							bseQuote.setE("BSE");
							bseQuote.setLt_dts(stockQuoteDateTime.format(new Date()));
							String price = quote.group(1).replaceAll(",", "");
							bseQuote.setL_fix(Double.parseDouble(price));
							markerResponse.put(bseQuote.getT(), bseQuote);
							System.out.println(bseQuote.getT()+" BSE Quote : "+bseQuote.getL_fix());
			            } finally {
			                response1.close();
			            }
			            
					
			       /* URL url = new URL(bseURL+ticker.getT());
		            HTTPRequest req = new HTTPRequest(url, HTTPMethod.GET, lFetchOptions);
		            HTTPResponse res = fetcher.fetch(req);
		            String respoBse =(new String(res.getContent()));
		            final Matcher quote = bse_pattern.matcher(respoBse);
					quote.find();
					CurrentMarketPrice bseQuote = new CurrentMarketPrice();
					bseQuote.setT(ticker.getT());
					bseQuote.setE("BSE");
					bseQuote.setLt_dts(stockQuoteDateTime.format(new Date()));
					String price = quote.group(1).replaceAll(",", "");
					bseQuote.setL_fix(Double.parseDouble(price));
					markerResponse.put(bseQuote.getT(), bseQuote);*/
					
		        } catch (Exception e) {
		        	
		        	e.printStackTrace();
		        }
				
				
			}
		}
	
		
		return markerResponse;
	}

}
