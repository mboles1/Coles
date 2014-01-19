package coles.app;

public class Major {
	public int id;
	public String major_name;
	public String major_description;
	
	public Major (int row_id, String row_major_name, String row_major_description) {
		id = row_id;
		major_name = row_major_name;
		major_description = row_major_description.replace("\n", "");
	}
}
