package registration;

public class RegistrationText {
	public int id;
	public String title;
	public String description;
	public String link;

	public RegistrationText(){
	}
	
	public RegistrationText(int row_id, String row_title, String row_description, String row_link) {
		id = row_id;
		title = row_title;
		description = row_description;
		link = row_link;
	
	}
	
	public RegistrationText(int row_id){
		this.id = row_id;
	}

	@Override
	public String toString() {
		return "Text toString()";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}
