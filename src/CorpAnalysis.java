import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class CorpAnalysis {

	public static void main(String[] args) {
		String stock = "Maruti_Suzuki_India_Ltd/5496";
		getCurrentMarketPrice(stock);
		pl(stock);
		balanceSheet(stock);
		
	}

public static void getCurrentMarketPrice(String stock){
	String url = "http://www.equityintelligence.com/CorporateInfo/Detailed-Quotes/"+stock;
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
             System.out.println("CMP: "+data.group(1));
		 
	    } catch (IOException e) {
       	
       }
}
	
public static void pl(String stock){
	String url = "http://www.equityintelligence.com/CorporateInfo/ProfitandLoss/"+stock;
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
            
             System.out.println("Sales turn over: "+data.group(1));
             System.out.println("Net profit: "+profit.group(1)+" "+profit.group(2)+" "+profit.group(3)+" "+profit.group(4)+" "+profit.group(5));
            
		 
	    } catch (IOException e) {
       	
       }
}

public static void balanceSheet(String stock){
	String url = "http://www.equityintelligence.com/CorporateInfo/BalanceSheet/"+stock;
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
             System.out.println("Total Shareholders Funds: "+data.group(1));
             System.out.println("Total Debt: "+totalDebt);
             System.out.println("BookValue: "+bookValue);
             System.out.println("PE & EPS: "+pe);
             System.out.println("52 week  high low : "+lowHigh);
             
             final Matcher asset = asset_pattern.matcher(response);
             asset.find();
             System.out.println("Total current assets : "+asset.group(1));
             final Matcher liability = liability_pattern.matcher(response);
             liability.find();
             System.out.println("Total current liabilities : "+liability.group(1));
             
             final Matcher netBlock = netBlock_pattern.matcher(response);
             netBlock.find();
             System.out.println("Net Block: "+netBlock.group(1));
             
             
		 
	    } catch (IOException e) {
       	
       }
}
	
}
