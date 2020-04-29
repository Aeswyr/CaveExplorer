package effects;

import java.io.Serializable;
import core.Assets;
import entity.Mob;
import gfx.DrawGraphics;
import gfx.Sprite;

/**
 * Contains useful integer tags and methods for combat effects
 * 
 * @author Pascal
 *
 */
public class Effect implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 470011636390513937L;
	public static final int DAMAGE_TYPE_PHYSICAL = 0;
	public static final int DAMAGE_TYPE_MENTAL = 1;
	public static final int DAMAGE_TYPE_ENERGY = 2;

	int time;
	int tick;
	Mob target;
	Sprite symbol;
	Action a;

	public Effect(int time, int tick, Mob target, int id) {
		this.time = time;
		this.tick = tick;
		this.target = target;
		switch (id) {
		case 0:
			symbol = Assets.spirit;
			a = new Action() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 470785142676620025L;

				@Override
				public void tick(Mob m) {
					m.harm(1, Effect.DAMAGE_TYPE_ENERGY);
				}

				@Override
				public void init(Mob m) {
					// TODO Auto-generated method stub

				}

				@Override
				public void end(Mob m) {
					// TODO Auto-generated method stub

				}

			};
			break;
		default:
			symbol = Assets.inventory_Empty;
			a = new Action() {

				/**
				 * 
				 */
				private static final long serialVersionUID = -6444157057898884106L;

				@Override
				public void tick(Mob m) {
					m.heal(0, Effect.DAMAGE_TYPE_ENERGY);
				}

				@Override
				public void init(Mob m) {
					// TODO Auto-generated method stub

				}

				@Override
				public void end(Mob m) {
					// TODO Auto-generated method stub

				}

			};
			break;
		}
		target.addEffect(this);
		a.init(target);
	}

	/**
	 * counts down effect timer and applies effect at set intervals
	 */
	public void update() {
		if (time > 0) {
			time--;
			if (time % tick == 0)
				a.tick(target);
		} else if (time == 0) {
			a.tick(target);
			a.end(target);
			target.removeEffect(this);
		}
	}

	public void render(int x, int y, DrawGraphics g) {
		symbol.render(x, y, g);
		String s = time / 3600 + ":";
		int sec = ((time / 60) % 60);
		if (sec < 10) s += "0";
		s += sec;
		g.write(s, x, y + 24);
	}

	public boolean expired() {
		return time <= 0;
	}

}
