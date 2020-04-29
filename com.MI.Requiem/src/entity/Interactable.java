package entity;


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

	/**
	 * details what to do when this object is interacted with
	 * 
	 * @param interactor - the object which interacted with this object
	 */
	public abstract void interact(Object interactor);

}
