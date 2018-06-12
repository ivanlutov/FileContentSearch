package FileContentSearch.FileContentSearch;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringBuilderWriterTest {

	@Test
	public void testForCorrectWritedText() {
		IWriter sbWriter = new StringBuilderWriter();
		String testText = "any text";
		
		sbWriter.writeLn(testText);
		
		assertEquals(testText, sbWriter.getResult());
	}
	
	@Test
	public void testForIncorrectWritedText() {
		IWriter sbWriter = new StringBuilderWriter();
		String testText = "any text";
		String otherText = "other text";
		
		sbWriter.writeLn(testText);
		
		assertNotEquals(otherText, sbWriter.getResult());
	}

}
