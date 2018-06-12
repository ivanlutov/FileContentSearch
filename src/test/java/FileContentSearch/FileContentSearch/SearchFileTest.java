package FileContentSearch.FileContentSearch;

import static org.junit.Assert.*;

import org.junit.Test;

public class SearchFileTest {

	@Test
	public void createSearchFileWithCorrectArguments() throws FileException {
		String name = "file.txt";
		long size = 70;
		
		SearchFile searchFile = new SearchFile(name, size);
		
		assertEquals(name, searchFile.getName());
		assertEquals(size, searchFile.getSize());
	}
	
	@Test(expected=FileException.class)
	public void createSearchFileWithNullForNameMustBeThrowFileException() throws FileException {
		String name = null;
		long size = 70;
		
		SearchFile searchFile = new SearchFile(name, size);
	}
	
	@Test(expected=FileException.class)
	public void createSearchFileWithEmptyStringForNameMustBeThrowFileException() throws FileException {
		String name = "";
		long size = 70;
		
		SearchFile searchFile = new SearchFile(name, size);
	}
	
	@Test(expected=FileException.class)
	public void createSearchFileWithNegativeSizeMustBeThrowException() throws FileException {
		String name = "file.txt";
		long size = -10;
		
		SearchFile searchFile = new SearchFile(name, size);
	}

}
