package coles.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import coles.app.TrafficClassActivity;

import android.util.Log;


public class TimeStampClient extends TrafficClassActivity
{

	public static String getStamp()
	{
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpGet httpGet = new HttpGet("http://130.218.51.52/ws/get_timestamp.php");
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
			reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
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

			}
			//Log.d("Check",result);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		result = result.trim();
		return result;
	}

}
