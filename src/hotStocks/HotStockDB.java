package hotStocks;

import java.util.List;

public class HotStockDB {
	private String _id ;
	private List<HotStockVO> hotStocks;
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public List<HotStockVO> getHotStocks() {
		return hotStocks;
	}
	public void setHotStocks(List<HotStockVO> hotStocks) {
		this.hotStocks = hotStocks;
	}

}
