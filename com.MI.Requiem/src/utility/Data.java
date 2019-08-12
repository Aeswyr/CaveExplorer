package utility;

public class Data {

	private static String[] loadTips = { "Here's a tip: try not to die",
			"Remember, if your health hits zero, you wont die unless you can't sustain any more wounds.",
			"Remember, even though spirit regenerates quickly, if you run out, you'll die instantly.!",
			//"HEY YOU! DISLIKE WAITING FOR THE COOLDOWNS ON YOUR ITEMS? WELL, NOW YOU DON’T HAVE TO! GET "
			//+ "YOUR GRUBBY MITTS ON TWO COPIES OF THE SAME ITEM AND YOU CAN DUAL WIELD! TWO CRYSTAL RODS "
			//+ "TO DRAIN YOUR SPIRIT TWICE AS QUICKLY AND KILL YOU THAT MUCH FASTER!",
			"Caution: licking doorknobs on other planets is illegal"
	};

	public static String getLoadTip() {
		return loadTips[(int) (Math.random() * loadTips.length)];
	}

}
