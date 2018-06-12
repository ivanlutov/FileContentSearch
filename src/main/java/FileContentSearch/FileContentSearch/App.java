package FileContentSearch.FileContentSearch;

import java.util.Scanner;

public class App {
	private static final String EMPTY_DIR_NAME_MESSAGE = "Directory cannot be empty. Please enter again:";
	private static final String EMPTY_SEARCH_TEXT_MESSAGE = "Search text cannot be empty. Please enter again:";
	private static final String ENTER_DIR_MESSAGE = "Please enter directory:";
	private static final String ENTER_SEARCH_TEXT_MESSAGE = "Please enter search text:";

	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)){
			System.out.println(ENTER_DIR_MESSAGE);
			String rootDirectory = sc.nextLine().trim();
			while(rootDirectory.isEmpty()) {
				System.out.println(EMPTY_DIR_NAME_MESSAGE);
				rootDirectory = sc.nextLine();
			}

			System.out.println(ENTER_SEARCH_TEXT_MESSAGE);
			String searchText = sc.nextLine().trim();
			while(searchText.isEmpty()) {
				System.out.println(EMPTY_SEARCH_TEXT_MESSAGE);
				searchText = sc.nextLine();
			}
			
			IWriter writer = new StringBuilderWriter();
			FileTraverser fileTraverser = new FileTraverser(writer);
			
			String result = fileTraverser.traverseDirectory(rootDirectory, searchText);
			System.out.println(result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
