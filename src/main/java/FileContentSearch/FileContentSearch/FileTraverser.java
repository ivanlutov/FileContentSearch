package FileContentSearch.FileContentSearch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.commons.io.FilenameUtils;

public class FileTraverser {
	private static final String NO_RESULTS_FOUND = "No results found!";
	private static final String NOT_EXIST_DIRECTORY = "The directory does not exist!";
	private static final String LINE_RESULT_FORMAT = "File name: %s, size: %d bytes";
	private static final long FIRST_BYTES_FOR_COMPRESSED_FILE = 0x504B0304;

	private Set<SearchFile> resultFiles;
	private final IWriter writer;

	public FileTraverser(IWriter writer) throws FileTraverserException {
		// I will use tree set to store resulting files. If there are 2 or more files with
		// same name and size i will treat it as the same file
		this.resultFiles = new TreeSet<SearchFile>(new FileComparator());
		
		if (writer == null) {
			throw new FileTraverserException();
		}
		
		this.writer = writer;
	}

	public String traverseDirectory(String path, String searchWord) throws FileException, IOException {
		File root = new File(path);

		if (!root.exists()) {
			throw new FileException(NOT_EXIST_DIRECTORY);
		}

		// Traverse all tree with files
		dfsTraverse(root, searchWord);

		// Get result from writer class
		String result = getResult();

		// Returning the names of all files that contain the search text sorted by size
		return result;
	}

	private void dfsTraverse(File root, String searchWord) throws FileException, IOException {
		File[] currentFiles = root.listFiles();

		for (File file : currentFiles) {
			if (file.isDirectory()) {
				// If the file is directory I submit the directory name to the recursive method
				// and search word
				dfsTraverse(file, searchWord);
			} else {
				// If the file is any compressed file from extensions in compressed_extensions
				// set I traverse this compressed file and search for search word in sub files content
				if (this.checkForCompressedFile(file)) {
					traverseCompressedFile(file.getAbsolutePath(), searchWord);
				} else {
					// If the file is not any archive I check content for search word and if there
					// is a match I add the file to result set
					boolean isContainsSearchWord = checkFileContentForSearchWord(file, searchWord);
					if (isContainsSearchWord) {
						addFileToResultSet(file);
					}
				}
			}
		}
	}

	private boolean checkForCompressedFile(File file) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(file, "r");
		long n = raf.readInt();
		raf.close();
		if (n == FIRST_BYTES_FOR_COMPRESSED_FILE) {
			return true;
		}
		
		return false;
	}

	private void traverseCompressedFile(String fileName, String searchWord) throws IOException, FileException {
		File zip = new File(fileName);
		try (ZipInputStream zin = new ZipInputStream(new FileInputStream(zip));
				Scanner sc = new Scanner(zin);){
			//traverse in all files in archive and searching for "searchWord"
			for (ZipEntry zipEntry; (zipEntry = zin.getNextEntry()) != null;) {
				
				boolean isExistSearchWord = false;
				while (sc.hasNextLine()) {
					if (sc.nextLine().contains(searchWord)) {
						//If I find the search word in any line break while loop
						isExistSearchWord = true;
						break;
					}
				}
				
				//And add file in result set
				if (isExistSearchWord) {
					addFileToResultSet(zipEntry);
				}
			}
		} 
	}
	
	private boolean checkFileContentForSearchWord(File file, String searchWord) throws FileNotFoundException {
		try (Scanner sc = new Scanner(file);) {
			while (sc.hasNextLine()) {
				if (sc.nextLine().contains(searchWord)) {
					return true;
				}
			}

			return false;
		}
	}

	private void addFileToResultSet(File file) throws FileException {
		String fileAbsolutePath = file.getName();
		long fileSize = file.length();
		String fileSimpleName = this.getSimpleNameWithExtensionFromAbsolutePath(fileAbsolutePath);

		SearchFile searchFile = this.createAndGetSearchFileByNameAndSize(fileSimpleName, fileSize);

		this.resultFiles.add(searchFile);
	}

	private void addFileToResultSet(ZipEntry file) throws FileException {
		String fileAbsolutePath = file.getName();
		long fileSize = file.getSize();
		String fileSimpleName = this.getSimpleNameWithExtensionFromAbsolutePath(fileAbsolutePath);

		SearchFile searchFile = this.createAndGetSearchFileByNameAndSize(fileSimpleName, fileSize);

		this.resultFiles.add(searchFile);
	}

	private String getSimpleNameWithExtensionFromAbsolutePath(String absolutePath) {
		String baseName = FilenameUtils.getBaseName(absolutePath);
		String extension = FilenameUtils.getExtension(absolutePath);
		String fileSimpleName = baseName + "." + extension;

		return fileSimpleName;
	}

	private SearchFile createAndGetSearchFileByNameAndSize(String name, long size) throws FileException {
		return new SearchFile(name, size);
	}

	private String getResult() {
		if (this.resultFiles.size() == 0) {
			this.writer.writeLn(NO_RESULTS_FOUND);
		} else {
			for (SearchFile searchFile : this.resultFiles) {
				this.writer.writeLn(String.format(LINE_RESULT_FORMAT, searchFile.getName(), searchFile.getSize()));
			}
		}

		return this.writer.getResult();
	}
}
