package coles.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.IllegalFormatConversionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class TrafficHttpClient extends TrafficClassActivity {
	
	private static int realManual = 0;
	private static int realColor=3;
	
	
	public static int getPage(){
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpGet httpGet = new HttpGet("http://130.218.51.52/ws/traffic.php");
		HttpResponse response = null;
		int status = 0;
		try {
			response = httpClient.execute(httpGet, localContext);
			status = response.getStatusLine().getStatusCode();
		}catch (ClientProtocolException e) 
		{
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static String getDate()
	{
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpGet httpGet = new HttpGet("http://130.218.51.52/ws/traffic.php");
		HttpResponse response = null;

		try 
		{
			response = httpClient.execute(httpGet, localContext);	
		} 
		catch (ClientProtocolException e) 
		{
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String result = "";
		BufferedReader reader = null;
		
		try 
		{
			reader = new BufferedReader(
				    new InputStreamReader(
				      response.getEntity().getContent()
				    )
				  );
		} 
		catch (IllegalStateException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
			String line = null;
			try 
			{
				while ((line = reader.readLine()) != null)
				{
				  result += line + "\n";
				  Log.d("Check1",result);
				  if(result.length() > 30){
					  result = "0";
					  break;
				  }
				}
				//Log.d("Check",result);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			String [] traffic_count;
			String delimiter="-";
			traffic_count = result.split(delimiter);
			String date = traffic_count[0];	
			Log.d("Check",date);
			return date;
	}
	
	public static int getCount()
	{
		HttpClient httpClient = new DefaultHttpClient();
		BasicHttpContext localContext = new BasicHttpContext();
		HttpGet httpGet = new HttpGet("http://130.218.51.52/ws/traffic.php");
		HttpResponse response = null;
		
		try 
		{
			response = httpClient.execute(httpGet, localContext);
		} 
		catch (ClientProtocolException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
			 
		String result = "";
		
		BufferedReader reader = null;
		try 
		{
			reader = new BufferedReader(
				    new InputStreamReader(
				      response.getEntity().getContent()
				    )
				  );
		} catch (Throwable t) {
			
		}
		
			String line = null;
			try {
				while ((line = reader.readLine()) != null){
				  result += line + "\n";
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String [] traffic_count;
			String delimiter="-";
			traffic_count = result.split(delimiter);
			
			String count = traffic_count[1];
			int realCount=Integer.parseInt(count);
			
			return realCount;
	}
	
	public static int getStatus()
	{
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpGet httpGet = new HttpGet("http://130.218.51.52/ws/traffic.php");
		HttpResponse response = null;

		try 
		{
			response = httpClient.execute(httpGet, localContext);
		} 
		catch (ClientProtocolException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		String result = "";
		BufferedReader reader = null;
		
		try 
		{
			reader = new BufferedReader(
				    new InputStreamReader
				    (
				      response.getEntity().getContent()
				    )
				  );
		} 
		catch (IllegalStateException e) 
		{
			e.printStackTrace();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
			 
			String line = null;
			try 
			{
				while ((line = reader.readLine()) != null)
				{
				  result += line + "\n";
				}
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			String [] traffic_count;
			String delimiter="-";
			traffic_count = result.split(delimiter);
			
			String status = traffic_count[2];
			int realStatus=Integer.parseInt(status);
			
			return realStatus;
	}
	
	public static int getManual()
	{
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpGet httpGet = new HttpGet("http://130.218.51.52/ws/traffic.php");
		HttpResponse response = null;

		try 
		{
			response = httpClient.execute(httpGet, localContext);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		String result = "";
		
		BufferedReader reader = null;
		try 
		{
			reader = new BufferedReader(
				    new InputStreamReader(
				      response.getEntity().getContent()
				    )
				  );
		} 
		catch (IllegalStateException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
			String line = null;
			try 
			{
				while ((line = reader.readLine()) != null)
				{
				  result += line + "\n";
				}
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//String value stored in an array but size is not initialized
			String [] traffic_count;
			String delimiter="-";
			traffic_count = result.split(delimiter);
			
			//Converts string value to int
			String manual = traffic_count[3];
			try{
			realManual=Integer.parseInt(manual);
			} catch (IllegalFormatConversionException e){
				realManual = 0;
			}
			
			return realManual;
	}
	public static int getColor()
	{
		//instance fields
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpGet httpGet = new HttpGet("http://130.218.51.52/ws/traffic.php");
		
		//data fields
		HttpResponse response = null;

		try 
		{
			response = httpClient.execute(httpGet, localContext);
		} 
		catch (ClientProtocolException e) 
		{

		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		String result = "";
		BufferedReader reader = null;
		
		try 
		{
			reader = new BufferedReader(
				    new InputStreamReader(
				      response.getEntity().getContent()
				    )
				  );
		} 
		catch (IllegalStateException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
			String line = "";
			try 
			{
				while ((line = reader.readLine()) != null)
				{
				  result += line + "\n";
				}
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			String [] traffic_count;
			String delimiter="-";
			traffic_count = result.split(delimiter);
			Log.d("check", "colorhttp stops here"+"\n543254325432");
			String color = traffic_count[4];
			char[] array = color.toCharArray();
			for (int i = 0; i < array.length;i++){
			Log.d("check0", Character.toString(array[i]));
			}
			
			realColor = Integer.parseInt(Character.toString(array[0]));
			
			return realColor;
	}
	
	public void getInfo2(int ppl, int m) throws Throwable
	{
		ImageView light = (ImageView) findViewById(R.id.light);
		TextView dateTV = (TextView) findViewById(R.id.date);
		dateTV.setText(getCount());

		if (m == 0) 
		{
			if (ppl <= 3) 
			{
				light.setImageDrawable(getResources().getDrawable(R.drawable.green));
			} 
			else if (ppl > 3 && ppl < 7) 
			{
				light.setImageDrawable(getResources().getDrawable(R.drawable.yellow));
			} 
			else 
			{
				light.setImageDrawable(getResources().getDrawable(R.drawable.red));
			}
		} 
		else if (m == 1) 
		{
			if (getColor() == 3) 
			{
				light.setImageDrawable(getResources().getDrawable(R.drawable.green));
			} 
			else if (getColor() == 2) 
			{
				light.setImageDrawable(getResources().getDrawable(R.drawable.yellow));
			} 
			else if (getColor() == 1) 
			{
				light.setImageDrawable(getResources().getDrawable(R.drawable.red));
			}
		}
	}
}

