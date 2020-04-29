package entities;

import core.Assets;
import entity.Hitbox;
import entity.Mob;
import entity.Vector_KS;
import item.Item;
import items.Bone;
import runtime.Handler;
import world.World;

public class Ooze extends Mob {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8641622786017092105L;

	public Ooze() {

		this.xOff = 32;
		this.yOff = 32;

		this.healthMax = 10;
		this.health = healthMax;

		this.spiritMax = 2;
		this.spirit = spiritMax;

		this.speed = 2;
		move = speed;

		if (rng.nextDouble() < 0.3)
			inventory.add(new Bone(this));
		if (rng.nextDouble() < 0.3)
			inventory.add(new Bone(this));
		if (rng.nextDouble() < 0.3)
			inventory.add(new Bone(this));

		uiSetup();
	}

	@Override
	protected void setup() {
		this.w = 32;
		this.h = 32;
		this.hitbox = new Hitbox(0, 0, 32, 32, this);
		this.hurtbox = new Hitbox(0, 0, 32, 32, this);
		this.vector = new Vector_KS(this, 50);
		this.activeSprite = Assets.ooze_idle;
	}

	@Override
	public void move() {
		int speed = (int) (move * 6);
		int xDest = ((World) Handler.getLoadedWorld()).getPlayer().getCenteredX();
		int yDest = ((World) Handler.getLoadedWorld()).getPlayer().getCenteredY();
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
