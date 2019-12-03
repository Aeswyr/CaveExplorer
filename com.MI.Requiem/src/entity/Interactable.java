package entity;

import runtime.Handler;

/**
 * used for interactable entities like crafting stations
 * @author Pascal
 *
 */
public abstract class Interactable extends Entity{

	public Interactable(Handler handler) {
		super(handler);
	}

	/**
	 * details what to do when this object is interacted with
	 * @param interactor - the object which interacted with this object
	 */
	public abstract void interact(Object interactor);
	
}
