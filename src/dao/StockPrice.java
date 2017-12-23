package dao;

public class StockPrice {
	private double price;
	private int date ;
	private double totalTradedVolume,deliveryToTradedQuantity;
	public String toString(){
		return "price: "+price+" date: "+date;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
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
	}}
