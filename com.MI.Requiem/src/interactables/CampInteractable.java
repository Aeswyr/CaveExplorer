package interactables;

import java.util.ArrayList;

import entities.Player;
import entity.Interactable;
import gfx.DrawGraphics;
import item.IdCountPair;
import item.Item;
import runtime.Handler;
import utility.Utility;

public class CampInteractable extends Interactable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7758338191948667609L;

	public CampInteractable(Handler handler) {
		super(handler);
		// TODO Auto-generated constructor stub
	}

	transient Player p;
	boolean interacted;

	@Override
	public void interact(Object interactor) {
		if (interactor instanceof Player) {
			if (!interacted) {
				interacted = true;
				p = (Player) interactor;

				p.lock();
				ArrayList<IdCountPair> held = p.getInventory().getRawHeld();
				ArrayList<String> food = new ArrayList<String>();
				ArrayList<String> medicine = new ArrayList<String>();
				ArrayList<String> all = new ArrayList<String>();

				for (int i = 0; i < held.size(); i++) {
					String id = held.get(i).id;
					all.add(id);
					if (Utility.tagOverlaps(Item.toItem(id, null, null).getTags(), "food"))
						food.add(id);
					if (Utility.tagOverlaps(Item.toItem(id, null, null).getTags(), "medicine"))
						medicine.add(id);
				}
			} else {
				p = null;
				interacted = false;
			}

		}

	}

	@Override
	public void render(DrawGraphics g) {
		// TODO Auto-generated method stub

	}

}
