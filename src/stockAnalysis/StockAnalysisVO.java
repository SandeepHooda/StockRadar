package stockAnalysis;

import java.util.ArrayList;
import java.util.List;

public class StockAnalysisVO {
	private String _id, category;
	private String borderColor = "#4bc0c0";
	private boolean fill = false;
	private int maxCaptureYear ;
	private String companyName, companyCode;
	private String[] moneyControlResources = {"ratiosVI", "results/quarterly-results"};
	
	/**
	 * For a good company -
	 * 	Revenue should increase yearly bases - if revenue decreases it means company is no more growing. it is stagnent
	 *  Profit should also increase - if revenue increases but profit decreases it means that cost has increases
	 *  Profit margin - if revenue increases doubles and profit increases more than double than it means profitability of company (i.e. PBDITMargin) is increasing
	 */
	private List<Double> revenueOperationPerShare = new ArrayList<Double>();//it is Sales not income (Income = Sales - Cost)
	private List<Double> PBDITPerShare= new ArrayList<Double>();//Profit before depriciations ,intrest  & taxes
	private List<Double> PBDITMargin= new ArrayList<Double>();// =(profit/revenue) @@@@@@@@@ profitability
	
	private List<Double> revenueOperationPerShareYOY = new ArrayList<Double>();
	private List<Double> PBDITPerShareYOY= new ArrayList<Double>();
	private List<Double> PBDITMarginYOY = new ArrayList<Double>();
	
	private List<Double> quaterlyRevenueOp ;
	private List<Double> quaterlyPBDIT ;
	private List<Double> quaterlyPBDITMargin ;
	private List<Double> quaterlyRevenueOpQoQ = new ArrayList<Double>();
	private List<Double> quaterlyPBDITQoQ = new ArrayList<Double>();
	private List<Double> quaterlyPBDITMarginQoQ = new ArrayList<Double>();
	public String get_id() {
		return _id;
	}
	public void set_id(String companyName, String companyCode) {
		this._id = companyName+companyCode;
		this.companyName = companyName;
		this.companyCode = companyCode;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public List<Double> getRevenueOperationPerShare() {
		return revenueOperationPerShare;
	}
	public void setRevenueOperationPerShare(List<Double> revenueOperationPerShare) {
		this.revenueOperationPerShare = revenueOperationPerShare;
	}
	public List<Double> getPBDITPerShare() {
		return PBDITPerShare;
	}
	public void setPBDITPerShare(List<Double> pBDITPerShare) {
		PBDITPerShare = pBDITPerShare;
	}
	public List<Double> getPBDITMargin() {
		return PBDITMargin;
	}
	public void setPBDITMargin(List<Double> pBDITMargin) {
		PBDITMargin = pBDITMargin;
	}
	public int getMaxCaptureYear() {
		return maxCaptureYear;
	}
	public void setMaxCaptureYear(int maxCaptureYear) {
		this.maxCaptureYear = maxCaptureYear;
	}
	public String getCompanyName() {
		return companyName;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public String[] getMoneyControlResources() {
		return moneyControlResources;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public List<Double> getQuaterlyRevenueOp() {
		return quaterlyRevenueOp;
	}
	public void setQuaterlyRevenueOp(List<Double> quaterlyRevenueOp) {
		this.quaterlyRevenueOp = quaterlyRevenueOp;
	}
	public List<Double> getQuaterlyPBDIT() {
		return quaterlyPBDIT;
	}
	public void setQuaterlyPBDIT(List<Double> quaterlyPBDIT) {
		this.quaterlyPBDIT = quaterlyPBDIT;
	}
	public List<Double> getQuaterlyPBDITMargin() {
		return quaterlyPBDITMargin;
	}
	public void setQuaterlyPBDITMargin(List<Double> quaterlyPBDITMargin) {
		this.quaterlyPBDITMargin = quaterlyPBDITMargin;
	}
	public List<Double> getRevenueOperationPerShareYOY() {
		return revenueOperationPerShareYOY;
	}
	public void setRevenueOperationPerShareYOY(List<Double> revenueOperationPerShareYOY) {
		this.revenueOperationPerShareYOY = revenueOperationPerShareYOY;
	}
	public List<Double> getPBDITPerShareYOY() {
		return PBDITPerShareYOY;
	}
	public void setPBDITPerShareYOY(List<Double> pBDITPerShareYOY) {
		PBDITPerShareYOY = pBDITPerShareYOY;
	}
	public List<Double> getPBDITMarginYOY() {
		return PBDITMarginYOY;
	}
	public void setPBDITMarginYOY(List<Double> pBDITMarginYOY) {
		PBDITMarginYOY = pBDITMarginYOY;
	}
	public List<Double> getQuaterlyRevenueOpQoQ() {
		return quaterlyRevenueOpQoQ;
	}
	public void setQuaterlyRevenueOpQoQ(List<Double> quaterlyRevenueOpQoQ) {
		this.quaterlyRevenueOpQoQ = quaterlyRevenueOpQoQ;
	}
	public List<Double> getQuaterlyPBDITQoQ() {
		return quaterlyPBDITQoQ;
	}
	public void setQuaterlyPBDITQoQ(List<Double> quaterlyPBDITQoQ) {
		this.quaterlyPBDITQoQ = quaterlyPBDITQoQ;
	}
	public List<Double> getQuaterlyPBDITMarginQoQ() {
		return quaterlyPBDITMarginQoQ;
	}
	public void setQuaterlyPBDITMarginQoQ(List<Double> quaterlyPBDITMarginQoQ) {
		this.quaterlyPBDITMarginQoQ = quaterlyPBDITMarginQoQ;
	}
	public String getBorderColor() {
		return borderColor;
	}
	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}
	public boolean isFill() {
		return fill;
	}
	public void setFill(boolean fill) {
		this.fill = fill;
	}
	
	

}
