package crafting;

import effects.Effect;
import entities.Player;
import item.Item;
import utility.Utility;

public class Recipe {

	boolean[] stationReq;
	boolean[] researchReq;
	int[] resourceReq; // counts the number of each resource category required
	int[] idCount;
	String[] idReq;
	int hpCost;
	int spCost;

	String result;

	public Recipe() {
		stationReq = new boolean[Tag.STATION_MAX_ARRAY];
		researchReq = new boolean[Tag.RESEARCH_MAX_ARRAY];
		resourceReq = new int[Tag.RESOURCE_MAX_ARRAY];
	}

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
			} else if (req[i].charAt(0) == 'h')
				hpCost += Integer.parseInt(res[i].substring(2));
			else if (req[i].charAt(0) == 's')
				spCost += Integer.parseInt(res[i].substring(2));
		}
		result = q[2];
	}

	public boolean qualify(Player p) { // TODO finish, should return if the player can actually craft the recipe

		return true;
	}

	public Item craft(Player p) { // TODO FINISH, should consume resources then return the result item
		if (hpCost > 0)
			p.harm(hpCost, Effect.DAMAGE_TYPE_ENERGY);
		if (spCost > 0)
			p.harm(hpCost, Effect.DAMAGE_TYPE_MENTAL);
		return Utility.toItem(result);
	}

}
