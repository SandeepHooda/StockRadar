package hotStocks;

import java.util.Date;

public class HotStockVO {
	private String _id;
	private String script;
	private String exchange;
	private Date date = new Date();
	private String investorName;
	private String reason;
	private String qtyTraded;
	private String cmp;
	private String cmpPercentChange; 
	private String tradeType ;//B/S
	private boolean isHighProfile;
	
	public static final String reason_UpperCkt = "reason_UpperCkt";
	public static final String reason_BulkDeal = "reason_BulkDeal";
	public String getScript() {
		return script;
	}
	public void setScript(String script) {
		this.script = script;
		this._id = script;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getInvestorName() {
		return investorName;
	}
	public void setInvestorName(String investorName) {
		this.investorName = investorName;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public static String getReasonUpperckt() {
		return reason_UpperCkt;
	}
	public static String getReasonBulkdeal() {
		return reason_BulkDeal;
	}
	public String getExchange() {
		return exchange;
	}
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}
	public String getQtyTraded() {
		return qtyTraded;
	}
	public void setQtyTraded(String qtyTraded) {
		this.qtyTraded = qtyTraded;
	}
	public String getCmp() {
		return cmp;
	}
	public void setCmp(String cmp) {
		this.cmp = cmp;
	}
	public String getCmpPercentChange() {
		return cmpPercentChange;
	}
	public void setCmpPercentChange(String cmpPercentChange) {
		this.cmpPercentChange = cmpPercentChange;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public boolean isHighProfile() {
		return isHighProfile;
	}
	public void setHighProfile(boolean isHighProfile) {
		this.isHighProfile = isHighProfile;
	}

}
