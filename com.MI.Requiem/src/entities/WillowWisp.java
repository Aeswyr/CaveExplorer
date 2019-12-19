package entities;

import core.Assets;
import effects.Attack;
import effects.Effect;
import effects.OnHit;
import entity.Entity;
import entity.Hitbox;
import entity.Mob;
import entity.Vector;
import item.Item;
import runtime.Handler;
import runtime.Light;

public class WillowWisp extends Mob {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2178124091912891623L;

	transient Light l;

	public WillowWisp(Handler handler) {
		super(handler);
		this.activeSprite = Assets.willowWisp_idle;

		this.healthMax = 3;
		this.health = healthMax;

		this.spiritMax = 5;
		this.spirit = spiritMax;

		this.speed = 1.25;

		uiSetup();
	}
	
	@Override
	protected void setup() {
		this.w = 32;
		this.h = 32;
		this.hitbox = new Hitbox(0, 0, 32, 32, this, handler);
		this.hurtbox = new Hitbox(0, 0, 32, 32, this, handler);	
		this.vector = new Vector(this, 50);
		vector.setGhost(true);
		l = new Light(32, 0xff666633, handler);
		l.light();
	}

	public WillowWisp(Handler handler, int x, int y) {
		this(handler);
		this.x = x;
		this.y = y;
	}

	@Override
	public void update() {
		super.update();
		l.setPos(getCenteredX(), getCenteredY());
	}

	int timer;

	@Override
	public void move() {
		if (!locked) {
			int speed = (int) (this.speed * 3);
			int xDest = handler.getPlayer().getCenteredX();
			int yDest = handler.getPlayer().getCenteredY();
			int xCent = this.getCenteredX();
			int yCent = this.getCenteredY();

			double hypo = (xDest - xCent) * (xDest - xCent) + (yDest - yCent) * (yDest - yCent);

			if (hypo > 17424) { // 132 ^ 2
				if (xDest > xCent)
					vector.setVelocityX(speed);
				else if (xDest < xCent)
					vector.setVelocityX(-speed);

				if (yDest > yCent)
					vector.setVelocityY(speed);
				else if (yDest < yCent)
					vector.setVelocityY(-speed);
			} else if (hypo < 15376) { // 124 ^ 2
				if (xDest > xCent)
					vector.setVelocityX(-speed);
				else if (xDest < xCent)
					vector.setVelocityX(speed);

				if (yDest > yCent)
					vector.setVelocityY(-speed);
				else if (yDest < yCent)
					vector.setVelocityY(speed);
			} else {
				vector.setVelocityX(speed * -(yDest - yCent) / 128);
				vector.setVelocityY(speed * (xDest - xCent) / 128);

			}
		}
		timer++;

		if (timer % 180 == 0) {
			new Attack(this, handler.getPlayer(), handler, Attack.TYPE_HITSCAN, new OnHit() {

				/**
				 * 
				 */
				private static final long serialVersionUID = -5251586903063992771L;

				@Override
				public void hit(Entity source, Mob target) {
					target.harm(1, Effect.DAMAGE_TYPE_MENTAL);
				}

			}).initHitscan(ATTACK_TIMER);
			lastAttack = timer;
			this.lock();
		}

		if (locked && timer - lastAttack > ATTACK_TIMER)
			unlock();
	}

	static final int ATTACK_TIMER = 45;
	int lastAttack = 0;

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

	@Override
	public void load(Handler h) {
		super.load(h);
		l.load(h);
		l.light();
	}

}
