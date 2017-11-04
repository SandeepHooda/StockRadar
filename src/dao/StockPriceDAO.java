package dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;





public class StockPriceDAO {
	public static final String mlabKeySonu = "soblgT7uxiAE6RsBOGwI9ZuLmcCgcvh_";
	public static final String noCollection = "";
	public static String getHistoricalPrice(String exchange, String ticker,  String apiKey ){
		exchange = exchange.toLowerCase();
		String httpsURL = "https://api.mlab.com/api/1/databases/"+exchange+"/collections/"+ticker+"?apiKey="+apiKey;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		StringBuilder response = new StringBuilder();
		 try {
			
			 HttpGet httpGet = new HttpGet(httpsURL);
		        CloseableHttpResponse response1 = httpclient.execute(httpGet);
		        
		        try {
	                HttpEntity entity1 = response1.getEntity();
	                BufferedReader br = new BufferedReader(new InputStreamReader(entity1.getContent()));
	                String line = "";
	                
	                while ((line = br.readLine())!= null) {
	                	response.append(line);
	                }
	                EntityUtils.consume(entity1);
	                
	             
		           
	            } finally {
	                response1.close();
	            }
	            
	           
	            
	        } catch (IOException e) {
	        	//respo = e.getMessage();
	        }
		String responseStr = response.toString();
		responseStr = responseStr.replaceFirst("\\[", "").trim();
		 if (responseStr.indexOf("]") >= 0){
			
			 responseStr = responseStr.substring(0, responseStr.length()-1);
		 }
		
		 return responseStr;
		
		
		
		
	}
	
	
	
	public static void insertUpdateData( String db, String collection, String data, String key,  boolean isUpdate){
		db = db.toLowerCase();
		String httpsURL = "https://api.mlab.com/api/1/databases/"+db+"/collections/"+collection+"?apiKey="+key;
		
		 try {
			 CloseableHttpClient client = HttpClients.createDefault();
			 StringEntity requestEntity = new StringEntity(	 data,	    ContentType.APPLICATION_JSON);
			 if (isUpdate){
				 HttpPut put = new HttpPut(httpsURL);
				 put.setEntity(requestEntity);
				 client.execute(put);
			 }else {
				 HttpPost post = new HttpPost(httpsURL);
				 post.setEntity(requestEntity);
				 client.execute(post);
			 }
			 
		    } catch (IOException e) {
	        	
	        }
	}

}
