package com.PriceChart;

import java.util.ArrayList;
import java.util.List;

public class PriceVO {
	private List<String> labels = new ArrayList<String>();
	private List<DataSets> datasets = new ArrayList<DataSets>();
	public List<String> getLabels() {
		return labels;
	}
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
	public List<DataSets> getDatasets() {
		return datasets;
	}
	public void setDatasets(List<DataSets> datasets) {
		this.datasets = datasets;
	}

}
