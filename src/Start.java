import java.util.ArrayList;
import java.util.List;

import mkdt.CurrentMarketPrice;
import mkdt.GetStockQuote;

public class Start {

	public static void main(String[] args) {
		System.out.println(" Scanning all the stocks now....");
		List<CurrentMarketPrice> request = new ArrayList<CurrentMarketPrice>();
		CurrentMarketPrice req = new CurrentMarketPrice();
		req.setE("NSE");
		req.setT("MARUTI");
		request.add(req);
		GetStockQuote.getCurrentMarkerPrice( request);

	}

}
