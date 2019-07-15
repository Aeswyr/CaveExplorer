package utility;

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
}
