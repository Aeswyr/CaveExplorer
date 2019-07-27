package utility;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;

public class Utility {

	public static <T> boolean listOverlaps(T[] a, T[] b) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < b.length; j++) {
				if (a[i].equals(b[j]))
					return true;
			}
		}
		return false;
	}

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
	 */
	public static boolean editText(String input, int lineNum, String path) {
		try {
	        // input the (modified) file content to the StringBuffer "input"
	        BufferedReader file = new BufferedReader(new FileReader(path));
	        StringBuffer inputBuffer = new StringBuffer();
	        String line;

	        int count = 0;
	        while ((line = file.readLine()) != null) {
	            if (count == lineNum) line = input;
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
