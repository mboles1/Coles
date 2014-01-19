package coles.app;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

public class Faculty 
{
	private String firstName;
	private String lastName;
	private String middleName;
	private String title;
	private String title2;
	private String department;
	private String office;
	private String phone;
	private String email;
	
	public Faculty()
	{
		
	}
	
	public Faculty(String f, String l, String m, String t1, String t2, String d, String o, String p, String e)
	{
		this.firstName = f;
		this.lastName = l;
		this.middleName = m;
		this.title = t1;
		this.title2 = t2;
		this.department = d; 
		this.office = o;
		this.phone = p;
		this.email = e;
	}
	
	public String getFirst()
	{
		return firstName;
	}
	
	public String getLast()
	{
		return lastName;
	}
	
	public String getMiddle()
	{
		return middleName;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getTitle2()
	{
		return title2;
	}
	
	public String getDepartment()
	{
		return department;
	}
	
	public String getOffice()
	{
		return office;
	}
	
	public String getPhone()
	{
		return phone;
	}
	
	public String getEmail()
	{
		return email;
	}

	public ArrayList<Faculty> OfflineDirectoryList(InputStream is) throws IOException
	{
		ArrayList<Faculty> facultyList = new ArrayList<Faculty>();
		String first, last, middle, title1, title2, department, office, phone, email;
		
		Scanner scanner = new Scanner(is);
		scanner.useDelimiter("[," + System.getProperty("line.separator") + "]");
		
		while(scanner.hasNextLine())
		{
			while(scanner.hasNext())
			{
				last = scanner.next();
				first = scanner.next();
				middle = scanner.next();
				title1 = scanner.next();
				title2 = scanner.next();
				department = scanner.next();
				office = scanner.next();
				phone = scanner.next();
				email = scanner.next();
				
				facultyList.add(new Faculty(first, last, middle, title1, title2, department, office, phone, email));
			}
		}
		
		scanner.close();
		return facultyList;
	}
	
	public ArrayList<Faculty> OnlineFacultyList(String address) throws Exception
	{
		ArrayList<Faculty> facultyList = new ArrayList<Faculty>();
		String first, last, middle, title1, title2, department, office, phone, email;
		
		URL url = new URL(address);
		URLConnection connection = url.openConnection();
		
		Scanner scanner = new Scanner(connection.getInputStream());
		scanner.useDelimiter("[," + System.getProperty("line.separator") + "]");
		
		while(scanner.hasNextLine())
		{
			while(scanner.hasNext())
			{
				last = scanner.next();
				first = scanner.next();
				middle = scanner.next();
				title1 = scanner.next();
				title2 = scanner.next();
				department = scanner.next();
				office = scanner.next();
				phone = scanner.next();
				email = scanner.next();
				
				facultyList.add(new Faculty(first, last, middle, title1, title2, department, office, phone, email));
			}
		}
		
		scanner.close();
		return facultyList;
	}
}
