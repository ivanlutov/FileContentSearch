package FileContentSearch.FileContentSearch;

public class SearchFile {
	private static final String INVALID_FILE_NAME = "File name cannot be null or empty string!";
	private static final String INVALID_FILE_SIZE = "File size cannot be negative!";
	
	private String name;
	private long size;

	public SearchFile(String name, long size) throws FileException {
		this.setName(name);
		this.setSize(size);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws FileException {
		if (name == null || name.trim().length() <= 0) {
			throw new FileException(INVALID_FILE_NAME);
		}
		
		this.name = name;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) throws FileException {
		if (size < 0) {
			throw new FileException(INVALID_FILE_SIZE);
		}
		
		this.size = size;
	}

}
