package crafting;

import effects.Effect;
import entities.Player;
import item.Item;
import runtime.Handler;

public class Recipe {

	boolean[] stationReq;
	boolean[] researchReq;
	int[] resourceReq; // counts the number of each resource category required
	int[] idCount;
	String[] idReq; // tracks ids which are consumed
	String[] idNeed; // for ids which are not consumed
	int hpCost;
	int spCost;

	String result;

	public Recipe() {
		stationReq = new boolean[Tag.STATION_MAX_ARRAY];
		researchReq = new boolean[Tag.RESEARCH_MAX_ARRAY];
		resourceReq = new int[Tag.RESOURCE_MAX_ARRAY];
	}

	/**
	 * takes in the whole recipe string and splits it into its component parts, then
	 * assigns the relevant values to variables in the recipe
	 * 
	 * @param s - the recipe string
	 */
	protected void setVal(String s) {
		String[] q = s.trim().substring(2, s.length() - 2).replaceAll(" ", "").split("][");
		String[] req = q[0].split(",");
		for (int i = 0; i < req.length; i++) {
			if (req[i].charAt(0) == 'r')
				researchReq[Integer.parseInt(req[i].substring(2))] = true;
			else if (req[i].charAt(0) == 'c')
				stationReq[Integer.parseInt(req[i].substring(2))] = true;
		}
		String[] res = q[1].split(",");
		idReq = new String[res.length];
		idCount = new int[res.length];
		idNeed = new String[res.length];
		int count = 0;
		for (int i = 0; i < res.length; i++) {
			if (req[i].charAt(0) == 't')
				resourceReq[Integer.parseInt(res[i].substring(2))]++;
			else if (req[i].charAt(0) == 'i') {
				if (idReq[count] == null || idReq[count].equals(res[i].substring(2))) {
					idReq[count] = res[i].substring(2);
					idCount[count]++;
				} else {
					count++;
					idReq[count] = res[i].substring(2);
					idCount[count]++;
				}
			} else if (req[i].charAt(0) == 'n') {
				if (idNeed[count] == null || idNeed[count].equals(res[i].substring(2))) {
					idNeed[count] = res[i].substring(2);
				} else {
					count++;
					idNeed[count] = res[i].substring(2);
				}

			} else if (req[i].charAt(0) == 'h')
				hpCost += Integer.parseInt(res[i].substring(2));
			else if (req[i].charAt(0) == 's')
				spCost += Integer.parseInt(res[i].substring(2));
		}
		result = q[2];
	}

	/**
	 * scans the player's inventory and flags and checks if they have the required
	 * resources and flags active to craft the item
	 * 
	 * @param p - the player to scan
	 * @returns true if the recipe is craftable, false otherwise
	 */
	public boolean qualify(Player p) { // TODO finish, should return if the player can actually craft the recipe

		return true;
	}

	/**
	 * consumes resources from the player's inventory in order to craft the item
	 * NOTE - this method does not check if the player can craft the item and should
	 * always be preceeded by a call to the qualify method
	 * 
	 * @param p - the player to consume items from
	 * @param h - the game handler
	 * @returns the finished item product
	 */
	public Item craft(Player p, Handler h) { // TODO FINISH, should consume resources then return the result item
		if (hpCost > 0)
			p.harm(hpCost, Effect.DAMAGE_TYPE_ENERGY);
		if (spCost > 0)
			p.harm(hpCost, Effect.DAMAGE_TYPE_MENTAL);
		return Item.toItem(result, p, h);
	}

}
