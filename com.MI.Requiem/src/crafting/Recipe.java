package crafting;

import java.util.ArrayList;
import java.util.regex.Pattern;

import effects.Effect;
import entities.Player;
import item.IdCountPair;
import item.Item;
import runtime.Handler;

/**
 * details a single individual crafting recipe and the process of creating it
 * @author Pascal
 *
 */
public class Recipe {

	boolean[] stationReq;
	boolean[] researchReq;
	int[] resourceReq; // counts the number of each resource category required
	int[] idCount;
	String[] idReq; // tracks ids which are consumed
	String[] idNeed; // for ids which are not consumed TODO not yet implemented
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
		s = s.trim();
		s = s.substring(2, s.length() - 2);
		s = s.replaceAll(" ", "");
		String[] q = s.split(Pattern.quote("]["));
		String[] req = q[0].split(",");
		for (int i = 0; i < req.length; i++) { // start of requirements loop
			if (req[i].charAt(0) == 'r')
				researchReq[Integer.parseInt(req[i].substring(2))] = true;
			else if (req[i].charAt(0) == 'c')
				stationReq[Integer.parseInt(req[i].substring(2))] = true;
		} // end of requirements
		String[] res = q[1].split(",");
		idReq = new String[res.length];
		idCount = new int[res.length];
		idNeed = new String[res.length];
		int count = 0;
		for (int i = 0; i < res.length; i++) { // start of resources loop
			if (res[i].charAt(0) == 't')
				resourceReq[Integer.parseInt(res[i].substring(2))]++;
			else if (res[i].charAt(0) == 'i') {
				if (idReq[count] == null || idReq[count].equals(res[i].substring(2))) {
					idReq[count] = res[i].substring(2);
					idCount[count]++;
				} else {
					count++;
					idReq[count] = res[i].substring(2);
					idCount[count]++;
				}
			} else if (res[i].charAt(0) == 'n') {
				if (idNeed[count] == null || idNeed[count].equals(res[i].substring(2))) {
					idNeed[count] = res[i].substring(2);
				} else {
					count++;
					idNeed[count] = res[i].substring(2);
				}

			} else if (res[i].charAt(0) == 'h')
				hpCost += Integer.parseInt(res[i].substring(2));
			else if (res[i].charAt(0) == 's')
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

		boolean[] prFlag = p.getResearchFlags();
		boolean[] psFlag = p.getStationFlags();

		for (int i = 0; i < Tag.RESEARCH_MAX_ARRAY; i++) {
			if (researchReq[i])
				if (!prFlag[i])
					return false;
		}

		for (int i = 0; i < Tag.STATION_MAX_ARRAY; i++) {
			if (stationReq[i])
				if (!psFlag[i])
					return false;
		}

		ArrayList<IdCountPair> pair = p.getInventory().getRawHeld();
		int[] res = p.getInventory().getResourceHeld();

		for (int i = 0; i < idReq.length; i++) {
			if (idReq[i] != null) {
				boolean found = false;
				for (int j = 0; j < pair.size(); j++) {
					if (idReq[i].equals(pair.get(j).id)) {
						found = true;
						if (pair.get(j).count < idCount[i])
							return false;
					}
				}
				if (!found)
					return false;
			}
		}

		for (int i = 0; i < Tag.RESOURCE_MAX_ARRAY; i++) {
			if (res[i] < resourceReq[i])
				return false;
		}

		if (p.getHealth() <= this.hpCost)
			return false;
		if (p.getSpirit() <= this.spCost)
			return false;

		return true;
	}

	/**
	 * consumes resources from the player's inventory in order to craft the item
	 * @NOTE - this method does not check if the player can craft the item and should
	 * always be preceded by a call to the qualify method
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

		Item prod = Item.toItem(result, p, h);

		for (int i = 0; i < idReq.length; i++) {
			if (idReq[i] != null) {
				for (int j = 0; j < idCount[i]; j++)
					prod.editStatPackage(p.getInventory().removeItemID(idReq[i]).getStatPackage());
			}
		}
		for (int i = 0; i < Tag.RESOURCE_MAX_ARRAY; i++) {
			String tag = null;
			switch (i) {
			case 0:
				tag = "tissue";
				break;
			case 1:
				tag = "soil";
				break;
			case 2:
				tag = "metal";
				break;
			case 3:
				tag = "phantasm";
				break;
			case 4:
				tag = "mineral";
				break;
			case 5:
				tag = "gem";
				break;
			case 6:
				tag = "cord";
				break;
			case 7:
				tag = "carvable";
				break;
			case 8:
				tag = "cloth";
				break;
			}

			if (resourceReq[i] > 0) {
				for (int j = 0; j < resourceReq[i]; j++) {
					prod.editStatPackage(p.getInventory().removeItemTag(tag).getStatPackage());
				}
			}
		}

		prod.finalize();
		return prod;
	}

	/**
	 * Returns the item created by this recipe and binds it to the specified player
	 * @param p - the player who crafted the item
	 * @param h
	 * @returns the item created by this recipe
	 */
	public Item getResult(Player p, Handler h) {
		return Item.toItem(result, p, h);
	}

}
