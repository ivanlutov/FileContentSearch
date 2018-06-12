package FileContentSearch.FileContentSearch;

public class StringBuilderWriter implements IWriter{
	private StringBuilder sb;
	
	public StringBuilderWriter() {
		this.sb = new StringBuilder();
	}
	
	public void writeLn(String text) {
		sb.append(text).append(System.lineSeparator());
	}

	public String getResult() {
		return sb.toString().trim();
	}
}
