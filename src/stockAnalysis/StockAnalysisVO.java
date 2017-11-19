package stockAnalysis;

public class StockAnalysisVO {
	private String _id, category;
	
	private double currentMarketPrice;
	private double marketCap;
	private double salesTurnOver;
	private double netProfit1, netProfit2, netProfit3, netProfit4, netProfit5;
	private double totalStockHoldersFund;
	private double totalDebt;
	private double bookValue;
	private double pe;
	private double eps;
	private double high52;
	private double low52;
	private double currentAssets;
	private double currentLiabilities;
	private double netBlock;
	
	//calculated
	private double debt_eq;
	private double p_e;
	private double p_b;
	private double low52DifferencePercent;
	private double high52DifferencePercent;
	private double growthRate1, growthRate2,growthRate3, growthRate4;
	private double p_e_g;
	private double profit_Equity;
	private double price_profit;
	private double asset_Liabilities;
	private double profit_sales;
	
	private double promotoresShare;
	private int preference;
	
	public void setCalculatedFields(){
		debt_eq = totalDebt/totalStockHoldersFund;
		p_e = currentMarketPrice/eps;
		p_b = currentMarketPrice/bookValue;
		low52DifferencePercent = (currentMarketPrice- low52)/low52*100;
		high52DifferencePercent = (high52- currentMarketPrice)/currentMarketPrice*100;
		growthRate1 = (netProfit1-netProfit2)/netProfit2*100;
		growthRate2 = (netProfit2-netProfit3)/netProfit3*100;
		growthRate3 = (netProfit3-netProfit4)/netProfit4*100;
		growthRate4 = (netProfit4-netProfit5)/netProfit5*100;
		p_e_g = p_e/growthRate1*100;
		profit_Equity = netProfit1/totalStockHoldersFund *100;
		price_profit = currentMarketPrice/netProfit1*100;
		asset_Liabilities = currentAssets/currentLiabilities*100;
		profit_sales = netProfit1/salesTurnOver*100;
	}
	public double getCurrentMarketPrice() {
		return currentMarketPrice;
	}
	public void setCurrentMarketPrice(double currentMarketPrice) {
		this.currentMarketPrice = currentMarketPrice;
	}
	public double getSalesTurnOver() {
		return salesTurnOver;
	}
	public void setSalesTurnOver(double salesTurnOver) {
		this.salesTurnOver = salesTurnOver;
	}
	
	public double getTotalStockHoldersFund() {
		return totalStockHoldersFund;
	}
	public void setTotalStockHoldersFund(double totalStockHoldersFund) {
		this.totalStockHoldersFund = totalStockHoldersFund;
	}
	public double getTotalDebt() {
		return totalDebt;
	}
	public void setTotalDebt(double totalDebt) {
		this.totalDebt = totalDebt;
	}
	public double getBookValue() {
		return bookValue;
	}
	public void setBookValue(double bookValue) {
		this.bookValue = bookValue;
	}
	public double getPe() {
		return pe;
	}
	public void setPe(double pe) {
		this.pe = pe;
	}
	public double getEps() {
		return eps;
	}
	public void setEps(double eps) {
		this.eps = eps;
	}
	public double getHigh52() {
		return high52;
	}
	public void setHigh52(double high52) {
		this.high52 = high52;
	}
	public double getLow52() {
		return low52;
	}
	public void setLow52(double low52) {
		this.low52 = low52;
	}
	public double getCurrentAssets() {
		return currentAssets;
	}
	public void setCurrentAssets(double currentAssets) {
		this.currentAssets = currentAssets;
	}
	public double getCurrentLiabilities() {
		return currentLiabilities;
	}
	public void setCurrentLiabilities(double currentLiabilities) {
		this.currentLiabilities = currentLiabilities;
	}
	public double getNetBlock() {
		return netBlock;
	}
	public void setNetBlock(double netBlock) {
		this.netBlock = netBlock;
	}
	public double getNetProfit1() {
		return netProfit1;
	}
	public void setNetProfit1(double netProfit1) {
		this.netProfit1 = netProfit1;
	}
	public double getNetProfit2() {
		return netProfit2;
	}
	public void setNetProfit2(double netProfit2) {
		this.netProfit2 = netProfit2;
	}
	public double getNetProfit3() {
		return netProfit3;
	}
	public void setNetProfit3(double netProfit3) {
		this.netProfit3 = netProfit3;
	}
	public double getNetProfit4() {
		return netProfit4;
	}
	public void setNetProfit4(double netProfit4) {
		this.netProfit4 = netProfit4;
	}
	public double getNetProfit5() {
		return netProfit5;
	}
	public void setNetProfit5(double netProfit5) {
		this.netProfit5 = netProfit5;
	}
	public double getDebt_eq() {
		return debt_eq;
	}
	public void setDebt_eq(double debt_eq) {
		this.debt_eq = debt_eq;
	}
	public double getP_e() {
		return p_e;
	}
	public void setP_e(double p_e) {
		this.p_e = p_e;
	}
	public double getP_b() {
		return p_b;
	}
	public void setP_b(double p_b) {
		this.p_b = p_b;
	}
	public double getLow52DifferencePercent() {
		return low52DifferencePercent;
	}
	public void setLow52DifferencePercent(double low52DifferencePercent) {
		this.low52DifferencePercent = low52DifferencePercent;
	}
	public double getHigh52DifferencePercent() {
		return high52DifferencePercent;
	}
	public void setHigh52DifferencePercent(double high52DifferencePercent) {
		this.high52DifferencePercent = high52DifferencePercent;
	}
	public double getGrowthRate1() {
		return growthRate1;
	}
	public void setGrowthRate1(double growthRate1) {
		this.growthRate1 = growthRate1;
	}
	public double getGrowthRate2() {
		return growthRate2;
	}
	public void setGrowthRate2(double growthRate2) {
		this.growthRate2 = growthRate2;
	}
	public double getGrowthRate3() {
		return growthRate3;
	}
	public void setGrowthRate3(double growthRate3) {
		this.growthRate3 = growthRate3;
	}
	public double getGrowthRate4() {
		return growthRate4;
	}
	public void setGrowthRate4(double growthRate4) {
		this.growthRate4 = growthRate4;
	}
	public double getP_e_g() {
		return p_e_g;
	}
	public void setP_e_g(double p_e_g) {
		this.p_e_g = p_e_g;
	}
	public double getProfit_Equity() {
		return profit_Equity;
	}
	public void setProfit_Equity(double profit_Equity) {
		this.profit_Equity = profit_Equity;
	}
	public double getPrice_profit() {
		return price_profit;
	}
	public void setPrice_profit(double price_profit) {
		this.price_profit = price_profit;
	}
	public double getAsset_Liabilities() {
		return asset_Liabilities;
	}
	public void setAsset_Liabilities(double asset_Liabilities) {
		this.asset_Liabilities = asset_Liabilities;
	}
	public double getProfit_sales() {
		return profit_sales;
	}
	public void setProfit_sales(double profit_sales) {
		this.profit_sales = profit_sales;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public double getPromotoresShare() {
		return promotoresShare;
	}
	public void setPromotoresShare(double promotoresShare) {
		this.promotoresShare = promotoresShare;
	}
	public int getPreference() {
		return preference;
	}
	public void setPreference(int preference) {
		this.preference = preference;
	}
	public double getMarketCap() {
		return marketCap;
	}
	public void setMarketCap(double marketCap) {
		this.marketCap = marketCap;
	}
	

}
