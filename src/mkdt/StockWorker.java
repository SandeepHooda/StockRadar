package mkdt;



public class StockWorker implements Runnable {
	CurrentMarketPrice request;
	int counter ;
	public StockWorker(CurrentMarketPrice request, int counter){
		this.request = request;
		this.counter = counter;
	}
	@Override
	public void run() {
		
		 GetStockQuote.getCurrentMarkerPrice( request,counter);
		//System.out.println(response.getT()+" response ="+response.getL_fix());
		
	}

}
