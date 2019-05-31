package gfx;

import geometry.Shape;

public class SpriteRequest {

	protected Sprite s;
	protected int x, y, z;

	public SpriteRequest(Sprite s, int z, int x, int y) {
		this.s = s;
		this.y = y;
		this.x = x;
		this.z = z;
	}

	public SpriteRequest(Shape s, int x, int y) {
		this.s = s.toSprite();
		this.y = y;
		this.x = x;
		this.z = this.s.priority;
	}

}
