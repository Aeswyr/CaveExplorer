package entity;

import runtime.Handler;

public abstract class Interactable extends Entity{

	public Interactable(Handler handler) {
		super(handler);
	}

	public abstract void interact(Object interactor);
	
}
