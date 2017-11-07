package mkdt;

public class CurrentMarketPrice {
	private String t;
	private String e;
	private String lt_dts;
	private double l_fix;
	public String toString(){
		return t;
	}
	public String getT() {
		return t;
	}
	public void setT(String t) {
		this.t = t;
	}
	public String getE() {
		return e;
	}
	public void setE(String e) {
		this.e = e;
	}
	public String getLt_dts() {
		return lt_dts;
	}
	public void setLt_dts(String lt_dts) {
		this.lt_dts = lt_dts;
	}
	public double getL_fix() {
		return l_fix;
	}
	public void setL_fix(double l_fix) {
		this.l_fix = l_fix;
	}
	

}
