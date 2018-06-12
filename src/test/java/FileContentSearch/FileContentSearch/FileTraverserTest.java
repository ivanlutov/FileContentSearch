package FileContentSearch.FileContentSearch;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class FileTraverserTest {

	@Test(expected=FileTraverserException.class)
	public void createFileTraverserWithNullWriterMustBeThrowFileTraverserException() throws FileException, IOException, FileTraverserException {
		IWriter writer = null;
		
		FileTraverser fileTraverser = new FileTraverser(writer);
	}
	
	@Test(expected=FileException.class)
	public void invalidPathMustBeThrowFileException() throws FileException, IOException, FileTraverserException {
		IWriter writer = new StringBuilderWriter();
		FileTraverser fileTraverser = new FileTraverser(writer);
		
		String anyInvalidPath = "C://sdkajsdjkfasjkkjasfdjkjkcxz//dasdasddasdfascxzc";
		String searchWord = "word";
		
		fileTraverser.traverseDirectory(anyInvalidPath, searchWord);
	}
	
	@Test
	public void testWithCorrectPathAndCorrectResult() throws FileException, IOException, FileTraverserException {
		IWriter writer = new StringBuilderWriter();
		FileTraverser fileTraverser = new FileTraverser(writer);
		StringBuilder sb = new StringBuilder();
		sb.append("File name: firstFile.txt, size: 67 bytes").append(System.lineSeparator());
		sb.append("File name: resultFile.txt, size: 69 bytes").append(System.lineSeparator());
		sb.append("File name: nestedFileInZip.txt, size: 124 bytes").append(System.lineSeparator());
		sb.append("File name: secondFile.txt, size: 187 bytes").append(System.lineSeparator());
		sb.append("File name: resultFileInZip.txt, size: 240 bytes");
		
		//enter valid path
		String validPath = "C:\\Users\\ivanl\\Desktop\\New folder";
		//in my files search text is "searchWord";
		String searchWord = "searchWord";
		
		String result = fileTraverser.traverseDirectory(validPath, searchWord);
		
		assertEquals(sb.toString(), result);
	}

}
