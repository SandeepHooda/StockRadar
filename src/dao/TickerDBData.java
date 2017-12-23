package dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TickerDBData {
	private String _id ="1";
	private double xirr5;
	private double xirr10;
	private double xirr30;
	private double xirr182;
	private double xirr365;
	private double currentMarketPrice;
	private double highestPrice;
	private List<StockPrice> stockPriceList = new ArrayList<StockPrice>();
	private List<XirrValue> xirrEveryForthNight ;
	private double median3,median6, median1Y, median2Y, median3Y, median5Y;
	private double totalTradedVolume,deliveryToTradedQuantity;
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
	public List<StockPrice> getStockPriceList() {
		Collections.sort(stockPriceList, new StockDateComparator());
		return stockPriceList;
	}
	public void setStockPriceList(List<StockPrice> stockPriceList) {
		this.stockPriceList = stockPriceList;
	}
	public double getCurrentMarketPrice() {
		return currentMarketPrice;
	}
	public void setCurrentMarketPrice(double currentMarketPrice) {
		this.currentMarketPrice = currentMarketPrice;
	}
	public double getHighestPrice() {
		return highestPrice;
	}
	public void setHighestPrice(double highestPrice) {
		this.highestPrice = highestPrice;
	}
	public List<XirrValue> getXirrEveryForthNight() {
		if (null == xirrEveryForthNight){
			xirrEveryForthNight = new ArrayList<XirrValue>();
		}
		return xirrEveryForthNight;
	}
	public void setXirrEveryForthNight(List<XirrValue> xirrEveryForthNight) {
		this.xirrEveryForthNight = xirrEveryForthNight;
	}
	public double getMedian3() {
		return median3;
	}
	public void setMedian3(double median3) {
		this.median3 = median3;
	}
	public double getMedian6() {
		return median6;
	}
	public void setMedian6(double median6) {
		this.median6 = median6;
	}
	public double getMedian1Y() {
		return median1Y;
	}
	public void setMedian1Y(double median1y) {
		median1Y = median1y;
	}
	public double getMedian2Y() {
		return median2Y;
	}
	public void setMedian2Y(double median2y) {
		median2Y = median2y;
	}
	public double getMedian3Y() {
		return median3Y;
	}
	public void setMedian3Y(double median3y) {
		median3Y = median3y;
	}
	public double getMedian5Y() {
		return median5Y;
	}
	public void setMedian5Y(double median5y) {
		median5Y = median5y;
	}
	public double getTotalTradedVolume() {
		return totalTradedVolume;
	}
	public void setTotalTradedVolume(double totalTradedVolume) {
		this.totalTradedVolume = totalTradedVolume;
	}
	public double getDeliveryToTradedQuantity() {
		return deliveryToTradedQuantity;
	}
	public void setDeliveryToTradedQuantity(double deliveryToTradedQuantity) {
		this.deliveryToTradedQuantity = deliveryToTradedQuantity;
	}
	
	

}
