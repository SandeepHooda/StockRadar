package mkdt;



public class StockWorker implements Runnable {
	CurrentMarketPrice request;
	public StockWorker(CurrentMarketPrice request){
		this.request = request;
	}
	@Override
	public void run() {
		
		 GetStockQuote.getCurrentMarkerPrice( request);
		//System.out.println(response.getT()+" response ="+response.getL_fix());
		
	}

}
