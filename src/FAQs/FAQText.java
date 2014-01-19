package FAQs;

public class FAQText {
	public int id;
	public String question;
	public String answer;

	public FAQText(){
	}
	
	public FAQText(int row_id, String row_question, String row_answer) {
		id = row_id;
		question = row_question;
		answer = row_answer;
	
	}
	
	public FAQText(int row_id){
		this.id = row_id;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
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

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

}
