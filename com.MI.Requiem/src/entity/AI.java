package entity;

import runtime.Handler;

public abstract class AI {

	Entity holder;
	Handler handler;

	public AI(Entity holder, Handler handler) {
		this.holder = holder;
		this.handler = handler;
	}

	public abstract void calcNewPos();
}
