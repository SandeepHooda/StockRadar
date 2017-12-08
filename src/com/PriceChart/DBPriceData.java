package com.PriceChart;

import java.util.List;

public class DBPriceData {
	private String _id;
	private double xirr5;
	private double xirr10;
	private double xirr30;
	private double xirr182;
	private double xirr365;
	private double currentMarketPrice;
	private List<StockPrice> stockPriceList;
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public double getXirr5() {
		return xirr5;
	}
	public void setXirr5(double xirr5) {
		this.xirr5 = xirr5;
	}
	public double getXirr10() {
		return xirr10;
	}
	public void setXirr10(double xirr10) {
		this.xirr10 = xirr10;
	}
	public double getXirr30() {
		return xirr30;
	}
	public void setXirr30(double xirr30) {
		this.xirr30 = xirr30;
	}
	public double getXirr182() {
		return xirr182;
	}
	public void setXirr182(double xirr182) {
		this.xirr182 = xirr182;
	}
	public double getXirr365() {
		return xirr365;
	}
	public void setXirr365(double xirr365) {
		this.xirr365 = xirr365;
	}
	public double getCurrentMarketPrice() {
		return currentMarketPrice;
	}
	public void setCurrentMarketPrice(double currentMarketPrice) {
		this.currentMarketPrice = currentMarketPrice;
	}
	public List<StockPrice> getStockPriceList() {
		return stockPriceList;
	}
	public void setStockPriceList(List<StockPrice> stockPriceList) {
		this.stockPriceList = stockPriceList;
	}

}
