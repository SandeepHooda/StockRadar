package dao;

import java.util.Comparator;

public class StockDateComparator implements Comparator<StockPrice> {

	@Override
	public int compare(StockPrice o1, StockPrice o2) {
		
		return  o2.getDate() - o1.getDate();
	}

}
