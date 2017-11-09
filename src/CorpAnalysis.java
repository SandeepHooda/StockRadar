import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
import stockAnalysis.StockAnalysisVO;


public class CorpAnalysis {
	private static final String sector_Auto = "Automobile";
	private static final String sector_Chemicals = "Chemicals";
	private static final String sector_Construction = "Construction";
	private static List<StockAnalysisVO> stocksForAnalysis = new ArrayList<StockAnalysisVO>();// {,};
	private static void addStocksForAnalysis(){
		
		StockAnalysisVO stock = new StockAnalysisVO();
		stock.set_id("DCM_Shriram_Ltd/2337");
		stock.setCategory(sector_Chemicals);
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("Maruti_Suzuki_India_Ltd/5496");
		stock.setCategory(sector_Auto);
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("Dilip_Buildcon_Ltd/36662");
		stock.setCategory(sector_Construction);
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("NCC_Ltd/2598");
		stock.setCategory(sector_Construction);
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("Ashoka_Buildcon_Ltd/25513");
		stock.setCategory(sector_Construction);
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("Prakash_Constrowell_Ltd/41136");
		stock.setCategory(sector_Construction);
		stocksForAnalysis.add(stock);
		
		
	}
	public static String [] dataArray = null;
	public static void main(String[] args) {
		dataArray = new String[12];
		addStocksForAnalysis();
		Gson  json = new Gson();
		
		String strData =new StockPriceDAO().getData("corp-analysis", "corp-analysis", StockPriceDAO.mlabKeySonu);
		List<StockAnalysisVO> analysisListFromDB = json.fromJson("["+strData+"]", new TypeToken<List<StockAnalysisVO>>() {}.getType());
		List<StockAnalysisVO> analysisList = new ArrayList<StockAnalysisVO>();
		for (StockAnalysisVO stockAnalysisVO : stocksForAnalysis){
			System.out.println("Analysing "+stockAnalysisVO.get_id());
			for (StockAnalysisVO stockAnalysisVODB : analysisListFromDB){
				if (stockAnalysisVODB.get_id().equals(stockAnalysisVO.get_id())){
					stockAnalysisVO = stockAnalysisVODB;
					break;
				}
			}
			getCurrentMarketPrice(stockAnalysisVO);
			pl(stockAnalysisVO);
			balanceSheet( stockAnalysisVO);
			/*for (int i=1;i<12;i++){
				System.out.println(dataArray[i]);
			}*/
			stockAnalysisVO.setCalculatedFields();
			analysisList.add(stockAnalysisVO);
		}
		
		
		strData = json.toJson(analysisList, new TypeToken<List<StockAnalysisVO>>() {}.getType());
		
		new StockPriceDAO().insertUpdateData("corp-analysis", "corp-analysis", strData, StockPriceDAO.mlabKeySonu, true);
	}

public static void getCurrentMarketPrice( StockAnalysisVO stockAnalysisVO){
	String url = "http://www.equityintelligence.com/CorporateInfo/Detailed-Quotes/"+stockAnalysisVO.get_id();
	final  Pattern find_pattern = Pattern.compile("<tr><td class=\"bbltxt\" align=\"right\"><b>(.+?)</b></td></tr>");
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
             final Matcher data = find_pattern.matcher(response);
             data.find();
             //System.out.println("5. CMP: "+data.group(1));
             dataArray[5] = "5. CMP: "+data.group(1);
             stockAnalysisVO.setCurrentMarketPrice(Double.parseDouble(data.group(1).replaceAll(",", "")));
		 
	    } catch (IOException e) {
       	
       }
}
	
public static void pl( StockAnalysisVO stockAnalysisVO){
	String url = "http://www.equityintelligence.com/CorporateInfo/ProfitandLoss/"+stockAnalysisVO.get_id();
	final  Pattern find_pattern = Pattern.compile("Sales Turnover  </td><td  class='GridDataR_alt bbp brp'>(.+?)</td><td");
	final  Pattern profit_pattern = Pattern.compile("Reported Net Profit</td><td  class='GridDataR_alt bbp brp'>(.+?)</td><td  class='GridDataR bbp brp'>(.+?)</td><td  class='GridDataR_alt bbp brp'>(.+?)</td><td  class='GridDataR bbp brp'>(.+?)</td><td  class='GridDataR_alt bbp brp'>(.+?)</td>");

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
             final Matcher data = find_pattern.matcher(response);
             data.find();
             final Matcher profit = profit_pattern.matcher(response);
             profit.find();
            
             //System.out.println("1. Sales turn over: "+data.group(1));
             //System.out.println("8. Net profit: "+profit.group(1)+" "+profit.group(2)+" "+profit.group(3)+" "+profit.group(4)+" "+profit.group(5));
             dataArray[1] = "1. Sales turn over: "+data.group(1);
             dataArray[8] = "8. Net profit: "+profit.group(1)+" "+profit.group(2)+" "+profit.group(3)+" "+profit.group(4)+" "+profit.group(5);
             stockAnalysisVO.setSalesTurnOver(Double.parseDouble(data.group(1).replaceAll(",", "")));
             stockAnalysisVO.setNetProfit1(Double.parseDouble(profit.group(1).replaceAll(",", "")));
             stockAnalysisVO.setNetProfit2(Double.parseDouble(profit.group(2).replaceAll(",", "")));
             stockAnalysisVO.setNetProfit3(Double.parseDouble(profit.group(3).replaceAll(",", "")));
             stockAnalysisVO.setNetProfit4(Double.parseDouble(profit.group(4).replaceAll(",", "")));
             stockAnalysisVO.setNetProfit5(Double.parseDouble(profit.group(5).replaceAll(",", "")));
		 
	    } catch (IOException e) {
       	
       }
}

public static void balanceSheet( StockAnalysisVO stockAnalysisVO){
	String url = "http://www.equityintelligence.com/CorporateInfo/BalanceSheet/"+stockAnalysisVO.get_id();
	final  Pattern find_pattern = Pattern.compile("Total Shareholders Funds</td><td class='GridDataR_alt'>(.+?)</td><td");
	final Pattern debt_pattern = Pattern.compile("<td class=\"ReptDataL\" style=\"padding-left:3px;height:22px;border-bottom:solid 1px #C6D9E6\">(.+?)</td>");
	final Pattern asset_pattern = Pattern.compile("Total Current Assets</td><td class='GridDataR_alt'>(.+?)</td>");
	final Pattern liability_pattern = Pattern.compile("Total Current Liabilities</td><td class='GridDataR_alt'>(.+?)</td>");
	final Pattern netBlock_pattern = Pattern.compile("Net Block </td><td class='GridDataR_alt'>(.+?)</td>");
	
	
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
             final Matcher data = find_pattern.matcher(response);
             final Matcher debt = debt_pattern.matcher(response);
             data.find();
             debt.find();
             String bookValue = debt.group(1);
             debt.find();
             String lowHigh = debt.group(1);
             debt.find();
             String totalDebt = debt.group(1);
             debt.find();
             String pe = debt.group(1);
             /*System.out.println("2. Total Shareholders Funds: "+data.group(1));
             System.out.println("3. Total Debt: "+totalDebt);
             System.out.println("4. BookValue: "+bookValue);
             System.out.println("6. PE & EPS: "+pe);
             System.out.println("7. 52 week  high low : "+lowHigh);*/
             dataArray[2] ="2. Total Shareholders Funds: "+data.group(1);
             dataArray[3] ="3. Total Debt: "+totalDebt;
             dataArray[4] ="4. BookValue: "+bookValue;
             dataArray[6] ="6. PE & EPS: "+pe;
             dataArray[7] ="7. 52 week  high low : "+lowHigh;
             stockAnalysisVO.setTotalStockHoldersFund(Double.parseDouble(data.group(1).replaceAll(",", "")));
             stockAnalysisVO.setTotalDebt(Double.parseDouble(totalDebt.replaceAll(",", "")));
             stockAnalysisVO.setBookValue(Double.parseDouble(bookValue.replaceAll(",", "")));
             String seperator = "&nbsp;|&nbsp;";
             stockAnalysisVO.setPe(Double.parseDouble(pe.substring(0,pe.indexOf(seperator)).replaceAll(",", "")));
             stockAnalysisVO.setEps(Double.parseDouble(pe.substring(pe.lastIndexOf(";")+1).replaceAll(",", "")));
             stockAnalysisVO.setHigh52(Double.parseDouble(lowHigh.substring(0,lowHigh.indexOf(seperator)).replaceAll(",", "")));
             stockAnalysisVO.setLow52(Double.parseDouble(lowHigh.substring(lowHigh.lastIndexOf(";")+1).replaceAll(",", "")));
             final Matcher asset = asset_pattern.matcher(response);
             asset.find();
             //System.out.println("9. Total current assets : "+asset.group(1));
             dataArray[9] ="9. Total current assets : "+asset.group(1);
             stockAnalysisVO.setCurrentAssets(Double.parseDouble(asset.group(1).replaceAll(",", "")));
             final Matcher liability = liability_pattern.matcher(response);
             liability.find();
            // System.out.println("10. Total current liabilities : "+liability.group(1));
             dataArray[10] ="10. Total current liabilities : "+liability.group(1);
             stockAnalysisVO.setCurrentLiabilities(Double.parseDouble(liability.group(1).replaceAll(",", "")));
             
             final Matcher netBlock = netBlock_pattern.matcher(response);
             netBlock.find();
             //System.out.println("11. Net Block: "+netBlock.group(1));
             dataArray[11] ="11. Net Block: "+netBlock.group(1);
             stockAnalysisVO.setNetBlock(Double.parseDouble(netBlock.group(1).replaceAll(",", "")));
             
		 
	    } catch (IOException e) {
       	
       }
}
	
}
