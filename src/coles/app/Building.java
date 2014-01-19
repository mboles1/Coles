package coles.app;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

public class Building 
{
	String abbrev;
	String name;
	String bNum;
	String lat; 
	String longitude;
	
	public Building()
	{
		
	}
	
	public Building(String a, String n, String bNum, String lat, String longitude)
	{
		this.abbrev = a;
		this.name = n;
		this.bNum = bNum;
		this.lat = lat;
		this.longitude = longitude;
	}
	
	public String getAbbrev()
	{
		return abbrev;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getNumber()
	{
		return bNum;
	}
	
	public String getLat()
	{
		return lat;
	}
	
	public String getLongitude()
	{
		return longitude;
	}
	
	public ArrayList<Building> OfflineBuildingList(InputStream is) throws IOException
	{
		ArrayList<Building> buildingList = new ArrayList<Building>();
		String abbrev, name, number, lat, longitude;
		
		Scanner scanner = new Scanner(is);
		scanner.useDelimiter("[," + System.getProperty("line.separator") + "]");
		
		while(scanner.hasNextLine())
		{
			while(scanner.hasNext())
			{
				abbrev = scanner.next();
				name = scanner.next();
				number = scanner.next();
				lat = scanner.next();
				longitude = scanner.next();
				
				buildingList.add(new Building(abbrev, name, number, lat, longitude));
			}
		}
		
		scanner.close();
		return buildingList;
	}
	
	public ArrayList<Building> OnlineBuildingList(String address) throws Exception
	{
		ArrayList<Building> buildingList = new ArrayList<Building>();
		String abbrev, name, number, lat, longitude;
		
		URL url = new URL(address);
		URLConnection connection = url.openConnection();
		
		Scanner scanner = new Scanner(connection.getInputStream());
		scanner.useDelimiter("[," + System.getProperty("line.separator") + "]");
		
		while(scanner.hasNextLine())
		{
			while(scanner.hasNext())
			{
				abbrev = scanner.next();
				name = scanner.next();
				number = scanner.next();
				lat = scanner.next();
				longitude = scanner.next();
				
				buildingList.add(new Building(abbrev, name, number, lat, longitude));
			}
		}
		
		scanner.close();
		return buildingList;
	}
}
