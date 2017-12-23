import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
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
import stockAnalysis.StockAnalysisVO;


public class CorpAnalysis {
	private static final String sector_Auto = "Automobile";
	private static final String sector_Finance = "Finance";
	private static final String sector_HouseConstruction = "HouseConstruction";
	private static final String sector_Infra = "Infra";
	private static final String sector_Oil = "Oil";
	private static final String sector_Multi = "Multi";
	private static final String sector_FMCG = "FMCG";
	private static final String sector_Agro = "Agro";
	private static final String sector_Misc = "Misc";
	private static final String sector_Chemicals = "Chemicals";
	private static final String sector_Textile = "Textiles";
	private static final String sector_Electricals = "Electricals";
	private static final String sector_ConsumerElectronics = "ConsumerElectronic";
	private static final String sector_Fashion = "Fashion";
	private static final String sector_Plastic = "Plastic";
	private static List<StockAnalysisVO> stocksForAnalysis = new ArrayList<StockAnalysisVO>();// {,};
	private static void addStocksForAnalysis(){
		
		StockAnalysisVO stock = new StockAnalysisVO();
		stock.set_id("marutisuzukiindia/","/MS24#MS24");
		stock.setCategory(sector_Auto);
		stock.setBorderColor("#000080");
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("muthootfinance/","/MF10#MF10");
		stock.setCategory(sector_Finance);
		stock.setBorderColor("#cbc0c0");
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("cerasanitaryware/","/CS18#CS18");
		stock.setCategory(sector_HouseConstruction);
		stock.setBorderColor("#cb5040");
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("asiangranitoindia/","/AGI04#AGI04");
		stock.setCategory(sector_HouseConstruction);
		stock.setBorderColor("#7b5040");
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("actionconstructionequipment/","/ACE3#ACE3");
		stock.setCategory(sector_Infra);
		stock.setBorderColor("#800080");
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("alphageoindia/","/AI10#AI10");
		stock.setCategory(sector_Oil);
		stock.setBorderColor("#FF00FF");
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("ashokleyland/","/AL#AL");
		stock.setCategory(sector_Auto);
		stock.setBorderColor("#00FF00");
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("bharatforge/","/BF03#BF03");
		stock.setCategory(sector_Multi);
		stock.setBorderColor("#008080");
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("britanniaindustries/","/BI#BI");
		stock.setCategory(sector_FMCG);
		stock.setBorderColor("#00FFFF");
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("aplapollotubes/","/BT09#BT09I");
		stock.setCategory(sector_HouseConstruction);
		stock.setBorderColor("#0000FF");
		stocksForAnalysis.add(stock);
		
		
		stock = new StockAnalysisVO();
		stock.set_id("avantifeeds/","/AF21#AF21");
		stock.setCategory(sector_Agro);
		stock.setBorderColor("#008000");
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("controlprint/","/CP11#CP11");
		stock.setCategory(sector_Misc);
		stock.setBorderColor("#808000");
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("dilipbuildcon/","/DB04#DB04");
		stock.setCategory(sector_Infra);
		stock.setBorderColor("#FFFF00");
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("dcmshriram/","/DCM02#DCM02");
		stock.setCategory(sector_Chemicals);
		stock.setBorderColor("#FFA500");
		stocksForAnalysis.add(stock);
		
		
		stock = new StockAnalysisVO();
		stock.set_id("filatexindia/","/FI06#FI06");
		stock.setCategory(sector_Textile);
		stock.setBorderColor("#FF0000");
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("finolexcables/","/FC01#FC01");
		stock.setCategory(sector_Electricals);
		stock.setBorderColor("#DEB887");
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("gayatriprojects/","/GP10#GP10");
		stock.setCategory(sector_Infra);
		stock.setBorderColor("#5F9EA0");
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("gilletteindia/","/GI22#GI22");
		stock.setCategory(sector_FMCG);
		stock.setBorderColor("#D2691E");
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("godrejindustries/","/GI23#GI23");
		stock.setCategory(sector_FMCG);
		stock.setBorderColor("#FF7F50");
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("havellsindia/","/HI01#HI01");
		stock.setCategory(sector_Electricals);
		stock.setBorderColor("#B8860B");
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("heromotocorp/","/HHM#HHM");
		stock.setCategory(sector_Auto);
		stock.setBorderColor("#006400");
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("tvsmotorcompany/","/TVS#TVS");
		stock.setCategory(sector_Auto);
		stock.setBorderColor("#9932CC");
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("ifbindustries/","/IFB02#IFB02");
		stock.setCategory(sector_ConsumerElectronics);
		stock.setBorderColor("#FFD700");
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("itdcementationindia/","/ITD03#ITD03");
		stock.setCategory(sector_HouseConstruction);
		stock.setBorderColor("#DAA520");
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("kansainerolacpaints/","/KNP#KNP");
		stock.setCategory(sector_HouseConstruction);
		stock.setBorderColor("#CD5C5C");
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("lumaxindustries/","/LI05#LI05");
		stock.setCategory(sector_Auto);
		stock.setBorderColor("#");
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("meghmaniorganics/","/MO04#MO04");
		stock.setCategory(sector_Agro);
		stock.setBorderColor("#F0E68C");
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("mothersonsumisystems/","/MSS01#MSS01");
		stock.setCategory(sector_Auto);
		stock.setBorderColor("#E6E6FA");
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("proctergamblehygienehealthcare/","/PGH#PGH");
		stock.setCategory(sector_FMCG);
		stock.setBorderColor("#FFF0F5");
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("somanyceramics/","/SC49#SC49");
		stock.setCategory(sector_HouseConstruction);
		stock.setBorderColor("#FFFACD");
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("titancompany/","/TI01#TI01");
		stock.setCategory(sector_Fashion);
		stock.setBorderColor("#6B8E23");
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("vguardindustries/","/VI02#VI02");
		stock.setCategory(sector_Electricals);
		stock.setBorderColor("#CD853F");
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("vipindustries/","/VIP#VIP");
		stock.setCategory(sector_Plastic);
		stock.setBorderColor("#FA8072");
		stocksForAnalysis.add(stock);
		
		stock = new StockAnalysisVO();
		stock.set_id("whirlpoolindia/","/WI#WI");
		stock.setCategory(sector_ConsumerElectronics);
		stock.setBorderColor("#F5DEB3");
		stocksForAnalysis.add(stock);
		
		
		
	}
	public static String [] dataArray = null;
	public static void main(String[] args) {
		
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
			
			if( (new Date().getTime() - stockAnalysisVO.getLastUpdateTime())> (60000*60*24*10)){// 10 days
				stockAnalysisVO.setLastUpdateTime(new Date().getTime());
				ratios(stockAnalysisVO);
				QuaterResults(stockAnalysisVO);
			}
			
			analysisList.add(stockAnalysisVO);
		}
		
		strData = json.toJson(analysisList, new TypeToken<List<StockAnalysisVO>>() {}.getType());
		new StockPriceDAO().insertUpdateData("corp-analysis", "corp-analysis", strData, StockPriceDAO.mlabKeySonu, true);
	}


	
public static void QuaterResults( StockAnalysisVO stockAnalysisVODB){
	String url = "http://www.moneycontrol.com/financials/"+
          stockAnalysisVODB.getCompanyName()+stockAnalysisVODB.getMoneyControlResources()[1]+stockAnalysisVODB.getCompanyCode();
	final  Pattern find_pattern = Pattern.compile("<td align=\"right\" class=\"det\">(.+?)</td>");
	

	try {
		StringBuilder response = new StringBuilder();
		 CloseableHttpClient client = HttpClients.createDefault();
		 
		
		 
			 HttpGet httpGet = new HttpGet(url);
			 CloseableHttpResponse response1 = client.execute(httpGet);
			 HttpEntity entity1 = response1.getEntity();
             BufferedReader br = new BufferedReader(new InputStreamReader(entity1.getContent()));
             String line = "";
            List<Double> revenueList = new ArrayList<Double>();
            List<Double> PBDITList = new ArrayList<Double>();
 
             boolean captureRevenue = false;
             boolean capturePBDIT = false;
         
             
             while ((line = br.readLine())!= null) {
             	response.append(line);
             	
             	//Flags turn on to caputures figures
             	if (line.indexOf("</tr>")>= 0){
             		captureRevenue = false;
             		capturePBDIT = false;
             	
             	}
             	
             	//Actual data capture
             	
             	if (captureRevenue){
             		if (line != null && line.indexOf("<td align=\"right\" class=\"det\"") >=0){
             			final Matcher data = find_pattern.matcher(line);
                        data.find();
                        System.out.println(data.group(1));
                        revenueList.add( Double.parseDouble( data.group(1).replaceAll(",", "")));
                      
             		}
             		
             	}else
             	if (capturePBDIT){
             		if (line != null && line.indexOf("<td align=\"right\" class=\"det\"") >=0){
             			final Matcher data = find_pattern.matcher(line);
                        data.find();
                        System.out.println(data.group(1));
                        PBDITList.add( Double.parseDouble( data.group(1).replaceAll(",", "")));
                      
             		}
             		
             	}
             	
             	
             	//Flags turn on to caputures figures
             	if (line.indexOf("Total Income From Operations")>= 0){
             		captureRevenue = true;
             	}else
             	if (line.indexOf("P/L Before Other Inc. , Int., Excpt. Items & Tax")>= 0){
             		capturePBDIT = true;
             	}
             }
             EntityUtils.consume(entity1);
             
             
             populateQuaterlyResults(stockAnalysisVODB,  revenueList,PBDITList);  
             System.out.println(" Done ");
		 
	    } catch (IOException e) {
       	
       }
}

private static void populateQuaterlyResults(StockAnalysisVO stockAnalysisVODB, List<Double> revenueList, List<Double> PBDITList){
	Collections.reverse(revenueList);
	Collections.reverse(PBDITList);
	stockAnalysisVODB.setQuaterlyRevenueOp(revenueList);
	stockAnalysisVODB.setQuaterlyPBDIT(PBDITList);
	stockAnalysisVODB.setQuaterlyPBDITMargin(new ArrayList<Double>());
	for (int i=0;i<revenueList.size();i++){
		stockAnalysisVODB.getQuaterlyPBDITMargin().add(stockAnalysisVODB.getQuaterlyPBDIT().get(i)/stockAnalysisVODB.getQuaterlyRevenueOp().get(i)*100);
	}
	
	//Now set QoQ percentage chart data;
			for (Double val: stockAnalysisVODB.getQuaterlyRevenueOp()){
				Double baseVal = stockAnalysisVODB.getQuaterlyRevenueOp().get(0);
				stockAnalysisVODB.getQuaterlyRevenueOpQoQ().add((val-baseVal)/baseVal*100);
			}
			for (Double val: stockAnalysisVODB.getQuaterlyPBDIT()){
				Double baseVal = stockAnalysisVODB.getQuaterlyPBDIT().get(0);
				stockAnalysisVODB.getQuaterlyPBDITQoQ().add((val-baseVal)/baseVal*100);
			}
			for (Double val: stockAnalysisVODB.getQuaterlyPBDITMargin()){
				Double baseVal = stockAnalysisVODB.getQuaterlyPBDITMargin().get(0);
				stockAnalysisVODB.getQuaterlyPBDITMarginQoQ().add((val-baseVal)/baseVal*100);
			}
	
}

public static void ratios( StockAnalysisVO stockAnalysisVODB){
	String url = "http://www.moneycontrol.com/financials/"+
          stockAnalysisVODB.getCompanyName()+stockAnalysisVODB.getMoneyControlResources()[0]+stockAnalysisVODB.getCompanyCode();
	final  Pattern find_pattern = Pattern.compile("<td align=\"right\" class=\"det\">(.+?)</td>");
	final  Pattern find_year_pattern = Pattern.compile("<td align=\"right\" class=\"detb\">(.+?)</td>");
	final String findYearStr = "<td colspan=\"1\" class=\"detb\" width=\"40%\"></td>";
	
	;
	try {
		StringBuilder response = new StringBuilder();
		 CloseableHttpClient client = HttpClients.createDefault();
		 
		
		 
			 HttpGet httpGet = new HttpGet(url);
			 CloseableHttpResponse response1 = client.execute(httpGet);
			 HttpEntity entity1 = response1.getEntity();
             BufferedReader br = new BufferedReader(new InputStreamReader(entity1.getContent()));
             String line = "";
            List<Double> revenueList = new ArrayList<Double>();
            List<Double> PBDITList = new ArrayList<Double>();
            List<Double> PBDITMarginList = new ArrayList<Double>();
             boolean captureRevenue = false;
             boolean capturePBDIT = false;
             boolean capturePBDITMargin = false;
             boolean find_year = false;
             int capturedYearMax = 0;int capturedYearMin=100;
             while ((line = br.readLine())!= null) {
             	response.append(line);
             	
             	//Flags turn on to caputures figures
             	if (line.indexOf("</tr>")>= 0){
             		captureRevenue = false;
             		capturePBDIT = false;
             		capturePBDITMargin = false;
             		find_year = false;
             	}
             	
             	//Actual data capture
             	if (find_year){
             		if (line != null && line.indexOf("<td align=\"right\" class=\"detb\"") >=0){
             			final Matcher data = find_year_pattern.matcher(line);
                        data.find();
                        int year = Integer.parseInt(data.group(1).replaceAll("[^\\d.]", ""));
                        System.out.println(year);
                        if (year > capturedYearMax){
                        	capturedYearMax = year;
                        }
                        if (year < capturedYearMin){
                        	capturedYearMin = year;
                        }
                        //revenueList.add( Double.parseDouble( data.group(1).replaceAll(",", "")));
                      
             		}
             		
             	}else
             	if (captureRevenue){
             		if (line != null && line.indexOf("<td align=\"right\" class=\"det\"") >=0){
             			final Matcher data = find_pattern.matcher(line);
                        data.find();
                        System.out.println(data.group(1));
                        revenueList.add( Double.parseDouble( data.group(1).replaceAll(",", "")));
                      
             		}
             		
             	}else
             	if (capturePBDIT){
             		if (line != null && line.indexOf("<td align=\"right\" class=\"det\"") >=0){
             			final Matcher data = find_pattern.matcher(line);
                        data.find();
                        System.out.println(data.group(1));
                        PBDITList.add( Double.parseDouble( data.group(1).replaceAll(",", "")));
                      
             		}
             		
             	}else
             	if (capturePBDITMargin){
             		if (line != null && line.indexOf("<td align=\"right\" class=\"det\"") >=0){
             			final Matcher data = find_pattern.matcher(line);
                        data.find();
                        System.out.println(data.group(1));
                        PBDITMarginList.add( Double.parseDouble( data.group(1).replaceAll(",", "")));
                      
             		}
             		
             	}
             	
             	
             	//Flags turn on to caputures figures
             	if (line.indexOf("Revenue from Operations/Share (Rs.)")>= 0){
             		captureRevenue = true;
             	}else
             	if (line.indexOf("PBDIT/Share (Rs.)")>= 0){
             		capturePBDIT = true;
             	}else
             	if (line.indexOf("PBDIT Margin")>= 0){
             		capturePBDITMargin = true;
             	}else
             	if (line.indexOf(findYearStr)>= 0){
             		find_year = true;
             	}
             }
             EntityUtils.consume(entity1);
             
             
             appendCapturedData(stockAnalysisVODB, capturedYearMax,  capturedYearMin,  revenueList,PBDITList, PBDITMarginList);  
             System.out.println(" Done ");
		 
	    } catch (IOException e) {
       	
       }
}

private static void appendCapturedData(StockAnalysisVO stockAnalysisVODB, int capturedYearMax, int capturedYearMin, List<Double> revenueList, List<Double> PBDITList, List<Double> PBDITMarginList){
	if (stockAnalysisVODB.getMaxCaptureYear() <capturedYearMax){//we have captured smt new
		int countOfNewRecords = capturedYearMax - stockAnalysisVODB.getMaxCaptureYear();
		if (countOfNewRecords > revenueList.size()){
			countOfNewRecords = revenueList.size();
		}
		for (int i= countOfNewRecords-1;i>=0;i-- ){
			stockAnalysisVODB.getRevenueOperationPerShare().add(revenueList.get(i));
			stockAnalysisVODB.getPBDITPerShare().add(PBDITList.get(i));
			stockAnalysisVODB.getPBDITMargin().add(PBDITMarginList.get(i));
		}
		
		
		//Now set YOY percentage chart data;
		for (Double val: stockAnalysisVODB.getRevenueOperationPerShare()){
			Double baseVal = stockAnalysisVODB.getRevenueOperationPerShare().get(0);
			stockAnalysisVODB.getRevenueOperationPerShareYOY().add((val-baseVal)/baseVal*100);
		}
		for (Double val: stockAnalysisVODB.getPBDITPerShare()){
			Double baseVal = stockAnalysisVODB.getPBDITPerShare().get(0);
			stockAnalysisVODB.getPBDITPerShareYOY().add((val-baseVal)/baseVal*100);
		}
		for (Double val: stockAnalysisVODB.getPBDITMargin()){
			Double baseVal = stockAnalysisVODB.getPBDITMargin().get(0);
			stockAnalysisVODB.getPBDITMarginYOY().add((val-baseVal)/baseVal*100);
		}
		stockAnalysisVODB.setMaxCaptureYear(capturedYearMax);
	}
	
	
}
	
}
