package FileContentSearch.FileContentSearch;

public class FileException extends Exception{

	private static final long serialVersionUID = 7281448446775592714L;

	public FileException() {
		super();
	}

	public FileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FileException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileException(String message) {
		super(message);
	}

	public FileException(Throwable cause) {
		super(cause);
	}

}
