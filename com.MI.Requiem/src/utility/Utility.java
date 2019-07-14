package utility;

import item.Item;

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
	 * converts an id to an item
	 */
	public static Item toItem(String id) {
		return null;
	}

	/**
	 * returns the floor tile associated with the tile id
	 * @param id of the wall tile.
	 * @returns the id of the floor tile, dirt floor if  wall id is not valid
	 */
	public static int tileToFloor(int id) {
		switch (id) {
		case 1:
			return 0;
		case 6:
			return 5;
		default:
			return 0;
		}
	}
}
