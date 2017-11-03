package file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import mkdt.CurrentMarketPrice;

public class NSE {

	public static void main(String[] args) {
		
		List<CurrentMarketPrice> nse = getNSEScripts();
		System.out.println(nse.get(0).getT());
	}
	
	public static List<CurrentMarketPrice> getNSEScripts(){
		List<CurrentMarketPrice> nseStocks = new ArrayList<CurrentMarketPrice>();
		List<String> allRecords = getAllData(null);
		for (String aRecord: allRecords){
			String[] stockData = aRecord.split(",");
			if ("EQ".equalsIgnoreCase(stockData[2])){
				CurrentMarketPrice aStock = new CurrentMarketPrice();
				aStock.setE("NSE");
				aStock.setT(stockData[1]);
				nseStocks.add(aStock);
			}
		}
		
		return nseStocks;
	}
	public static List<String> getAllData(String fileName){
		if (null == fileName){
			fileName = "C:/Users/shaurya/Documents/StocksScripts/nse.csv";
		}
		
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(fileName));
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		return lines;
	}

}
