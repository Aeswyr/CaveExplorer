package entities;

import core.Assets;
import entity.Hitbox;
import entity.Mob;
import entity.Vector;
import item.Item;
import items.Bone;
import runtime.Handler;

public class Ooze extends Mob {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8641622786017092105L;

	public Ooze(Handler handler) {
		super(handler);
		this.activeSprite = Assets.ooze_idle;

		this.xOff = 32;
		this.yOff = 32;

		this.healthMax = 10;
		this.health = healthMax;

		this.spiritMax = 2;
		this.spirit = spiritMax;

		this.speed = 2;

		if (rng.nextDouble() < 0.3)
			inventory.add(new Bone(handler, this));
		if (rng.nextDouble() < 0.3)
			inventory.add(new Bone(handler, this));
		if (rng.nextDouble() < 0.3)
			inventory.add(new Bone(handler, this));

		uiSetup();
	}

	@Override
	protected void setup() {
		this.w = 32;
		this.h = 32;
		this.hitbox = new Hitbox(0, 0, 32, 32, this, handler);
		this.hurtbox = new Hitbox(0, 0, 32, 32, this, handler);
		this.vector = new Vector(this, 50);
	}

	public Ooze(Handler handler, int x, int y) {
		this(handler);
		this.x = x;
		this.y = y;
	}

	@Override
	public void move() {
		int speed = (int) (this.speed * 2);
		int xDest = handler.getPlayer().getCenteredX();
		int yDest = handler.getPlayer().getCenteredY();
		int xCent = this.getCenteredX();
		int yCent = this.getCenteredY();

		if (xDest > xCent && !hitbox.tileXCollide(speed))
			vector.setVelocityX(speed);
		else if (xDest < xCent && !hitbox.tileXCollide(-speed))
			vector.setVelocityX(-speed);

		if (yDest > yCent && !hitbox.tileYCollide(speed))
			vector.setVelocityY(speed);
		else if (yDest < yCent && !hitbox.tileYCollide(-speed))
			vector.setVelocityY(-speed);

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
