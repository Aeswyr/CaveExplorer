package gfx;

import runtime.Light;

public class LightRequest {

	protected Light l;
	protected int x, y;

	public LightRequest(Light l, int x, int y) {
		this.l = l;
		this.x = x;
		this.y = y;
	}

}
