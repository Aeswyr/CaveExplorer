package entities;

import core.Assets;
import entity.Hitbox;
import entity.Mob;
import gfx.DrawGraphics;
import item.Item;
import runtime.Handler;

public class Ooze extends Mob {

	public Ooze(Handler handler) {
		super(handler);
		this.activeSprite = Assets.ooze_idle;
		this.hitbox = new Hitbox(-32, -32, 32, 32, this, handler);

		this.xOff = 32;
		this.yOff = 32;

		this.healthMax = 200;
		this.health = healthMax;

		this.speed = 1;
	}

	public Ooze(Handler handler, int x, int y) {
		this(handler);
		this.x = x;
		this.y = y;
	}

	@Override
	public void renderUI(DrawGraphics g) {
		hitbox.render(g);
	}

	@Override
	public void move() {
		int xDest = handler.getPlayer().getCenteredX();
		int yDest = handler.getPlayer().getCenteredY();
		int xCent = this.getCenteredX();
		int yCent = this.getCenteredY();

		if (xDest > xCent && !hitbox.tileXCollide(speed))
			x += speed;
		else if (xDest < xCent && !hitbox.tileXCollide(-speed))
			x -= speed;

		if (yDest > yCent && !hitbox.tileYCollide(speed))
			y += speed;
		else if (yDest < yCent && !hitbox.tileYCollide(-speed))
			y -= speed;

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

}
