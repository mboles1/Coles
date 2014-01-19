package xmltosqlite;

public class Text2 {
	public int id;
	public int menu_no;
	public int item_order;
	public String heading;
	public String body;
	public String link;

	public Text2(){
	}
	
	public Text2(int row_id, int row_menu_no, int row_item_order,
			String row_heading, String row_body, String row_link) {
		id = row_id;
		menu_no = row_menu_no;
		item_order = row_item_order;
		heading = row_heading;
		body = row_body;
		link = row_link;
	}
	
	public Text2(int row_id){
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

	public int getMenu_no() {
		return menu_no;
	}

	public void setMenu_no(int menu_no) {
		this.menu_no = menu_no;
	}

	public int getItem_order() {
		return item_order;
	}

	public void setItem_order(int item_order) {
		this.item_order = item_order;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}
