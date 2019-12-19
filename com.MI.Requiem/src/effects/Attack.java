package effects;

import entity.Entity;
import entity.Hitbox;
import entity.Mob;
import gfx.DrawGraphics;
import gfx.Sprite;
import runtime.Handler;

/**
 * Object representing an attack in the form of a projectile or hitscan weapon
 * 
 * @author Pascal
 *
 */
public class Attack extends Entity {

	public static final int TYPE_HITSCAN = 0;
	public static final int TYPE_HITSCAN_PREDICT = 1;
	public static final int TYPE_PROJ = 2;
	public static final int TYPE_PROJ_PREDICT = 3;

	/**
	 * 
	 */
	private static final long serialVersionUID = -66885725667448864L;

	int x0, y0;
	Entity source;
	int type;
	OnHit onHit;

	/**
	 * Initializes an attack and adds it to the world
	 * 
	 * @param source  - entity generating this effect
	 * @param target  - intended target of this effect
	 * @param handler
	 * @param type    - type determining what style of attack to make
	 * @param onHit   - affect to apply when contacting a valid target
	 */
	public Attack(Entity source, Mob target, Handler handler, int type, OnHit onHit) {
		super(handler);

		this.source = source;
		this.onHit = onHit;
		this.type = type;

		switch (type) {
		case TYPE_HITSCAN:
			timer = 30;
			x = source.getCenteredX();
			y = source.getCenteredY();
			x0 = target.getCenteredX();
			y0 = target.getCenteredY();

			int holderX = (int) x;
			int holderY = (int) y;

			int dx = Math.abs(x0 - holderX);
			int dy = Math.abs(y0 - holderY);

			int sx = holderX < x0 ? 1 : -1;
			int sy = holderY < y0 ? 1 : -1;

			int err = dx - dy;
			int e2;

			Hitbox h = new Hitbox(holderX, holderY, 1, 1, handler);

			while (((holderX - x) * (holderX - x) + (holderY - y) * (holderY - y)) < 262144) {
				h.updatePos(holderX, holderY);

				if (h.tileXCollide(0) || h.tileYCollide(0))
					break;

				e2 = 2 * err;
				if (e2 > -1 * dy) {
					err -= dy;
					holderX += sx;
				}
				if (e2 < dx) {
					err += dx;
					holderY += sy;
				}
			}

			x0 = holderX;
			y0 = holderY;
			break;

		case TYPE_HITSCAN_PREDICT:
			timer = 30;
			break;
		case TYPE_PROJ:
			break;
		case TYPE_PROJ_PREDICT:
			break;
		}
		handler.getWorld().getEntities().addEntity(this);
	}

	/**
	 * initializes a hitscan attack with the timer between attacking and firing
	 * @param timer - value of frames between starting the attack and firing
	 */
	public void initHitscan(int timer) {
		this.timer = timer;
	}

	Sprite activeSprite;

	/**
	 * initializes a projectile attack with the sprite to render
	 * @param s - sprite to render
	 * @param speed - speed of the projectile
	 */
	public void initProjectile(Sprite s, int speed) {
		this.activeSprite = s;
	}

	int timer;

	@Override
	public void update() {
		switch (type) {
		case TYPE_HITSCAN:
			timer--;
			if (timer == 3) {
				int holderX = (int) x;
				int holderY = (int) y;

				int dx = Math.abs(x0 - holderX);
				int dy = Math.abs(y0 - holderY);

				int sx = holderX < x0 ? 1 : -1;
				int sy = holderY < y0 ? 1 : -1;

				int err = dx - dy;
				int e2;

				Hitbox h = new Hitbox(holderX, holderY, 1, 1, handler);

				while (((holderX - x) * (holderX - x) + (holderY - y) * (holderY - y)) < 262144) {
					h.updatePos(holderX, holderY);

					Mob m = h.collidingWithMob();
					if (m != null && m != source) {
						onHit.hit(source, m);
						this.x0 = holderX;
						this.y0 = holderY;
						break;
					}
					if (h.tileXCollide(0) || h.tileYCollide(0))
						break;

					e2 = 2 * err;
					if (e2 > -1 * dy) {
						err -= dy;
						holderX += sx;
					}
					if (e2 < dx) {
						err += dx;
						holderY += sy;
					}
				}

			}
			break;
		case TYPE_HITSCAN_PREDICT:
			timer--;
			if (timer == 3) {

			}
			break;
		case TYPE_PROJ:
			break;
		case TYPE_PROJ_PREDICT:
			break;
		}
		if (timer <= 0)
			handler.getWorld().getEntities().removeEntity(this);
	}

	@Override
	public void render(DrawGraphics g) {
		switch (type) {
		case TYPE_HITSCAN:
			timer--;
			if (timer <= 3)
				g.drawLine((int) x - handler.getCamera().xOffset(), (int) y - handler.getCamera().yOffset(),
						x0 - handler.getCamera().xOffset(), y0 - handler.getCamera().yOffset(), 0xFFFFFFFF);
			else
				g.drawLine((int) x - handler.getCamera().xOffset(), (int) y - handler.getCamera().yOffset(),
						x0 - handler.getCamera().xOffset(), y0 - handler.getCamera().yOffset(), 0x44CCAA22);
			break;
		case TYPE_HITSCAN_PREDICT:
			timer--;
			if (timer <= 3)
				g.drawLine((int) x - handler.getCamera().xOffset(), (int) y - handler.getCamera().yOffset(),
						x0 - handler.getCamera().xOffset(), y0 - handler.getCamera().yOffset(), 0xFFFFFFFF);
			else
				g.drawLine((int) x - handler.getCamera().xOffset(), (int) y - handler.getCamera().yOffset(),
						x0 - handler.getCamera().xOffset(), y0 - handler.getCamera().yOffset(), 0x44CCAA22);
			break;
		case TYPE_PROJ:
			break;
		case TYPE_PROJ_PREDICT:
			break;
		}

	}

}
