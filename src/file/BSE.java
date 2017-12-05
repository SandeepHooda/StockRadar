package file;


import java.util.ArrayList;
import java.util.List;

import mkdt.CurrentMarketPrice;

public class BSE {

	public static void main(String[] args) {
		List<CurrentMarketPrice> nse = getBSEScripts(10);
		System.out.println(nse.get(0).getT());

	}
	
	public static List<CurrentMarketPrice> getBSEScripts(int counter){
		System.out.println(" Please fix the data for RESURGENT INDIA FUND");
		List<CurrentMarketPrice> nseStocks = new ArrayList<CurrentMarketPrice>();
		List<String> allRecords = NSE.getAllData("C:/Users/shaurya/Documents/StocksScripts//bse/bse"+counter+".csv",counter);
		allRecords.remove(0);
		for (String aRecord: allRecords){
			String[] stockData = aRecord.split(",");
			
				CurrentMarketPrice aStock = new CurrentMarketPrice();
				aStock.setE("BSE");
				aStock.setT(stockData[0]);
				nseStocks.add(aStock);
			
		}
		
		return nseStocks;
	}
	

}
