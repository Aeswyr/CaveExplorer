package utility;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;

/**
 * Contains a variety of useful methods
 * @author Pascal
 *
 */
public class Utility {

	/**
	 * checks if two arrays contain the same value
	 * 
	 * @param <T> - array type
	 * @param a   - list a
	 * @param b   - list b
	 * @returns true if there are matching elements in the arrays, false otherwise
	 */
	public static <T> boolean listOverlaps(T[] a, T[] b) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < b.length; j++) {
				if (a[i].equals(b[j]))
					return true;
			}
		}
		return false;
	}

	/**
	 * checks if a string of tags contains values which match
	 * 
	 * @param a - string of tags 1
	 * @param b - string of tags 2
	 * @returns true if there are matching elements in the strings, false otherwise
	 */
	public static boolean tagOverlaps(String a, String b) {
		String[] listA = a.split(" ");
		String[] listB = b.split(" ");

		for (int i = 0; i < listA.length; i++) {
			for (int j = 0; j < listB.length; j++) {
				if (listA[i].equals(listB[j]))
					return true;
			}
		}
		return false;
	}

	/**
	 * replaces a specified line within a text document
	 * @param input - new value of the line
	 * @param lineNum - line number to replace
	 * @param path - path to the text file which is edited
	 * @returns true if the change is successful, false otherwise
	 */
	public static boolean editText(String input, int lineNum, String path) {
		try {
			// input the (modified) file content to the StringBuffer "input"
			BufferedReader file = new BufferedReader(new FileReader(path));
			StringBuffer inputBuffer = new StringBuffer();
			String line;

			int count = 0;
			while ((line = file.readLine()) != null) {
				if (count == lineNum)
					line = input;
				inputBuffer.append(line);
				inputBuffer.append('\n');
				count++;
			}
			file.close();

			// write the new string with the replaced line OVER the same file
			FileOutputStream fileOut = new FileOutputStream(path);
			fileOut.write(inputBuffer.toString().getBytes());
			fileOut.close();

		} catch (Exception e) {
			System.out.println("Problem reading file.");
			return false;
		}

		return true;
	}
}
