package entity;

import runtime.Handler;

/**
 * used for interactable entities like crafting stations
 * 
 * @author Pascal
 *
 */
public abstract class Interactable extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7386206880171144134L;

	public Interactable(Handler handler) {
		super(handler);
	}

	@Override
	public void update() {
		hitbox.update();
	}

	/**
	 * details what to do when this object is interacted with
	 * 
	 * @param interactor - the object which interacted with this object
	 */
	public abstract void interact(Object interactor);

	@Override
	public void load(Handler h) {
		super.load(h);
	}

}
