import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.StockPriceDAO;
import hotStocks.HotStockVO;


public class BulkDeals {

	private static List<String> topInvestors = new ArrayList<>();
	private static List<HotStockVO> hotStocks = new ArrayList<HotStockVO>();
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	public static void main(String[] args) throws IOException {
		
		topInvestors = Files.readAllLines(Paths.get("C:/Users/shaurya/Documents/StocksScripts/TopInvestors.txt"));
		getBSEUpperCircuit();
		getNSEBulkDeals();
		getBSEBulkDeals();
		for (HotStockVO stock: hotStocks){
			if (topInvestors.contains(stock.getInvestorName())){
				stock.setHighProfile(true);
			}
		}
		StockPriceDAO.insertUpdateData("hotstocks", ""+sdf.format(new Date()), dataStr(hotStocks), StockPriceDAO.mlabKeySonu, false);
	}

	
	public static void getBSEUpperCircuit(){
		String url = "http://www.bseindia.com/markets/equity/EQReports/mtw_uclc.aspx";
		
		Pattern bse_bulkPatternText = Pattern.compile("class='tablebluelink'>(.+?)</a></td>");
		Pattern bse_bulkPatternNumbers = Pattern.compile("<td class='TTRow_right'>(.+?)</td>");
		
		
		
		
		try {
			StringBuilder response = new StringBuilder();
			 CloseableHttpClient client = HttpClients.createDefault();
			 
			
			 
				 HttpGet httpGet = new HttpGet(url);
				 CloseableHttpResponse response1 = client.execute(httpGet);
				 HttpEntity entity1 = response1.getEntity();
	             BufferedReader br = new BufferedReader(new InputStreamReader(entity1.getContent()));
	             String line = "";
	             
	             while ((line = br.readLine())!= null) {
	             	response.append(line);
	             }
	             EntityUtils.consume(entity1);
	             final Matcher dataColText = bse_bulkPatternText.matcher(response);
	             final Matcher dataColNumbers = bse_bulkPatternNumbers.matcher(response);
	             
	             HotStockVO hotStockVO = null;
	             while (dataColText.find()){
	            	 hotStockVO = new HotStockVO();
            		 hotStockVO.setExchange("BSE");
            		 hotStockVO.setReason(HotStockVO.reason_UpperCkt);
            		 dataColText.find();
            		 hotStockVO.setScript(dataColText.group(1));
            		 
            		 dataColNumbers.find();
            		 hotStockVO.setCmp(dataColNumbers.group(1));
            		 dataColNumbers.find();dataColNumbers.find();dataColNumbers.find();
            		 dataColNumbers.find();dataColNumbers.find();dataColNumbers.find();
            		 hotStockVO.setCmpPercentChange(dataColNumbers.group(1));
            		 
            		
            		 hotStocks.add(hotStockVO);
	             }
	             
	            
	           System.out.println(" Done ");
			 
		    } catch (IOException e) {
	       	
	       }
	}
	public static void getNSEBulkDeals(){
		String url = "https://www.nseindia.com/products/dynaContent/equities/equities/bulkdeals.jsp?symbol=&segmentLink=13&symbolCount=&dateRange=day&fromDate=&toDate=&dataType=DEALS";
		
		Pattern nse_bulkPatternText = Pattern.compile("<td class=\"normalText\">(.+?)</td>");
		Pattern nse_bulkPatternNumbers = Pattern.compile("<td class=\"number\">(.+?)</td>");
		
		
		
		
		try {
			StringBuilder response = new StringBuilder();
			 CloseableHttpClient client = HttpClients.createDefault();
			 
			
			 
				 HttpGet httpGet = new HttpGet(url);
				 CloseableHttpResponse response1 = client.execute(httpGet);
				 HttpEntity entity1 = response1.getEntity();
	             BufferedReader br = new BufferedReader(new InputStreamReader(entity1.getContent()));
	             String line = "";
	             
	             while ((line = br.readLine())!= null) {
	             	response.append(line);
	             }
	             EntityUtils.consume(entity1);
	             final Matcher dataColText = nse_bulkPatternText.matcher(response);
	             final Matcher dataColNumbers = nse_bulkPatternNumbers.matcher(response);
	             int i=0;
	             HotStockVO hotStockVO = null;
	             while (dataColText.find()){
	            	 hotStockVO = new HotStockVO();
            		 hotStockVO.setExchange("NSE");
            		 hotStockVO.setReason(HotStockVO.reason_BulkDeal);
            		 hotStockVO.setScript(dataColText.group(1));
            		 
            		 dataColText.find();//SDecurity name
            		 dataColText.find();
            		 hotStockVO.setInvestorName(dataColText.group(1));
            		 
            		 dataColNumbers.find();//Buy /sell
            		 hotStockVO.setTradeType(dataColNumbers.group(1));
            		 dataColNumbers.find();
            		 hotStockVO.setQtyTraded(dataColNumbers.group(1));
            		 dataColNumbers.find();
            		 hotStockVO.setCmp(dataColNumbers.group(1));
            		 dataColText.find();//Remarks
            		 hotStocks.add(hotStockVO);
	             }
	             
	            
	           System.out.println(" Done ");
			 
		    } catch (IOException e) {
	       	
	       }
	}
	public static void getBSEBulkDeals(){
		String url = "http://www.bseindia.com/markets/equity/EQReports/bulk_deals.aspx";
		Pattern bse_bulkPattern = Pattern.compile("<td class=\"TTRow_left\">(.+?)</td>");
		Pattern bse_bulkPatternRight = Pattern.compile("<td class=\"TTRow_right\">(.+?)</td>");
		Pattern bse_DealType = Pattern.compile("<td class=\"TTRow\">(.+?)</td>");
		
		List<String> extractScript = new ArrayList<String>();
		List<String> extractNumbers = new ArrayList<String>();
		List<String> dealtypes = new ArrayList<String>();
		
		
		try {
			StringBuilder response = new StringBuilder();
			 CloseableHttpClient client = HttpClients.createDefault();
			 
			
			 
				 HttpGet httpGet = new HttpGet(url);
				 CloseableHttpResponse response1 = client.execute(httpGet);
				 HttpEntity entity1 = response1.getEntity();
	             BufferedReader br = new BufferedReader(new InputStreamReader(entity1.getContent()));
	             String line = "";
	             
	             while ((line = br.readLine())!= null) {
	             	response.append(line);
	             }
	             EntityUtils.consume(entity1);
	             final Matcher dataColScript = bse_bulkPattern.matcher(response);
	             final Matcher dataColNumbers = bse_bulkPatternRight.matcher(response);
	             final Matcher dealType = bse_DealType.matcher(response);
	             int i=0;
	             
	             while (dataColScript.find()){
	            	 String dataExtracted = dataColScript.group(1);
	            	 extractScript.add(dataExtracted);
	             }
	             
	             while (dataColNumbers.find()){
	            	 String dataExtracted = dataColNumbers.group(1);
	            	 extractNumbers.add(dataExtracted);
	             }
	             
	             while (dealType.find()){
	            	 
	            	 String dataExtracted = dealType.group(1);
	            	 
	            		 if ("B".equals(dataExtracted)){
	            			 dealtypes.add("BUY");
	            		 }else
	            		 if ("S".equals(dataExtracted)){
	            			 dealtypes.add("SELL");
	            		 }
	            		 
	            	 
	            	 
	             }
	             HotStockVO hotStockVO = null;
	             for ( i=0;i<extractScript.size();i++){
	            	 if (i%2 == 0){
	            		 hotStockVO = new HotStockVO();
	            		 hotStockVO.setExchange("BSE");
	            		 hotStockVO.setReason(HotStockVO.reason_BulkDeal);
	            		 hotStockVO.setScript(extractScript.get(i));
	            		 hotStockVO.setQtyTraded(extractNumbers.get(i));
	            		 hotStockVO.setTradeType(dealtypes.get(i/2));
	            	 }else {
	            		 hotStockVO.setInvestorName(extractScript.get(i));
	            		 hotStockVO.setCmp(extractNumbers.get(i));
	            		 hotStocks.add(hotStockVO);
	            	 }
	             }
	           System.out.println(" Done ");
			 
		    } catch (IOException e) {
	       	
	       }
	}
	
	private static String dataStr(List<HotStockVO> hotStocks){
		Gson  json = new Gson();
		return  json.toJson(hotStocks, new TypeToken<List<HotStockVO>>() {}.getType());
	}
	
}