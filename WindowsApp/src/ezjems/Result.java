package ezjems;

public class Result {

	private String testSteps;
	private String result;
	private String resultText;
	
	public Result(String testSteps,String resultText,String result) {
		this.testSteps = testSteps;
		this.result = result;
		this.resultText = resultText;
	}
	
	public void setTestSteps(String testSteps) {
		this.testSteps = testSteps;
	}

	public String getTestSteps() {
		return this.testSteps;
	}
	
	public void setResultText(String resultText) {
		this.resultText = resultText;
	}
	
	public String getResultText() {
		return this.resultText;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public String getResult() {
		return this.result;
	}
	
	
}

