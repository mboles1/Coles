package FAQs;

public class FAQ {
	public int id;
	public String question;
	public String answer;

	public FAQ(){
	}
	
	public FAQ(int id, String question, String answer) {
		this.id=id;
		this.question=question;
		this.answer=answer;
	}

	@Override
	public String toString() {
		return "FAQ toString()";
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

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	
}
