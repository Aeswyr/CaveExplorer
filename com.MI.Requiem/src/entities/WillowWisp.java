package entities;

import core.Assets;
import entity.Hitbox;
import entity.Mob;
import gfx.DrawGraphics;
import item.Item;
import runtime.Handler;
import runtime.Light;

public class WillowWisp extends Mob {

	Light l;

	public WillowWisp(Handler handler) {
		super(handler);
		this.activeSprite = Assets.willowWisp_idle;
		this.hitbox = new Hitbox(-32, -32, 32, 32, this, handler);

		this.xOff = 32;
		this.yOff = 32;

		this.healthMax = 25;
		this.health = healthMax;

		this.speed = 1.75;

		l = new Light(32, 0xff666633, handler);
		l.light();
	}

	public WillowWisp(Handler handler, int x, int y) {
		this(handler);
		this.x = x;
		this.y = y;
	}

	@Override
	public void renderUI(DrawGraphics g) {

	}

	@Override
	public void update() {
		super.update();
		l.setPos(getCenteredX(), getCenteredY());
	}

	@Override
	public void move() {
		int xDest = handler.getPlayer().getCenteredX();
		int yDest = handler.getPlayer().getCenteredY();
		int xCent = this.getCenteredX();
		int yCent = this.getCenteredY();

		double hypo = (xDest - xCent) * (xDest - xCent) + (yDest - yCent) * (yDest - yCent);

		if (hypo > 17424) { // 132 ^ 2
			if (xDest > xCent)
				x += speed;
			else if (xDest < xCent)
				x -= speed;

			if (yDest > yCent)
				y += speed;
			else if (yDest < yCent)
				y -= speed;
		} else if (hypo < 15376) { // 124 ^ 2
			if (xDest > xCent)
				x -= speed;
			else if (xDest < xCent)
				x += speed;

			if (yDest > yCent)
				y -= speed;
			else if (yDest < yCent)
				y += speed;
		} else {
			x += speed * -(yDest - yCent) / 128;
			y += speed * (xDest - xCent) / 128;

		}
	}

	@Override
	public void equip(Item i) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean pickup(Item i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void die() {
		super.die();
		l.snuff();
	}

}
