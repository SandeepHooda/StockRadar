package com.PriceChart;

import java.util.ArrayList;
import java.util.List;

public class DataSets {
	private String label;
	private List<Double> data = new ArrayList<Double>();
	private boolean fill= false;
	private String  borderColor = "#4bc0c0";
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public List<Double> getData() {
		return data;
	}
	public void setData(List<Double> data) {
		this.data = data;
	}
	public boolean isFill() {
		return fill;
	}
	public void setFill(boolean fill) {
		this.fill = fill;
	}
	public String getBorderColor() {
		return borderColor;
	}
	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}
}
