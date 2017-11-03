package dao;

public class StockPrice {
	private double price;
	private int date ;
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
	}}
