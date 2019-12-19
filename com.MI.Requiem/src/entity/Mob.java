package entity;

import java.util.ArrayList;
import java.util.Random;

import core.Assets;
import effects.Effect;
import gfx.DrawGraphics;
import gfx.Sprite;
import interactables.Corpse;
import item.Inventory;
import item.Item;
import particle.Particle;
import particle.Particle.Behavior;
import runtime.Handler;

/**
 * represents entities which can both move and have health values
 * 
 * @author Pascal
 *
 */
public abstract class Mob extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3048223593461500698L;
	protected static Random rng = new Random();

	protected ArrayList<Effect> activeEffects;
	protected Vector vector;
	protected Hitbox hurtbox;

	public Mob(Handler handler) {
		super(handler);
		inventory = new Inventory(handler, this);
		this.deathSprite = Assets.corpse;
		activeEffects = new ArrayList<Effect>();
		setup();
	}
	
	/**
	 * a collection of tasks to preform when the mob is created or loaded from a save
	 */
	protected abstract void setup();

	/**
	 * sets up ui for non-player mobs
	 */
	protected void uiSetup() {
		if (healthMax >= 40)
			maxH = 40;
		else if (healthMax >= 24)
			maxH = 24;
		else if (healthMax >= 15)
			maxH = 15;
		else if (healthMax >= 7)
			maxH = 7;
		else if (healthMax >= 3)
			maxH = 3;
		else
			maxH = healthMax;
		if (spiritMax >= 40)
			maxS = 40;
		else if (spiritMax >= 24)
			maxS = 24;
		else if (spiritMax >= 15)
			maxS = 15;
		else if (spiritMax >= 7)
			maxS = 7;
		else if (spiritMax >= 3)
			maxS = 3;
		else
			maxS = spiritMax;
		divH = healthMax / maxH;
		divS = healthMax / maxS;
	}

	protected Sprite deathSprite;
	protected Sprite activeSprite;
	protected double speed;
	protected int health, healthMax, spirit, spiritMax;
	protected int con, wil, str, agi, kno, arm, luk;
	protected Inventory inventory;
	protected double move = speed;
	protected boolean locked = false;

	@Override
	public void render(DrawGraphics g) {
		activeSprite.render((int) x - handler.getCamera().xOffset(), (int) y - handler.getCamera().yOffset(), g);
	}

	@Override
	public void update() {
		hitbox.update();
		if (hurtbox != null) hurtbox.update();
		vector.update();
		move();

		for (int i = 0; i < activeEffects.size(); i++) {
			Effect e = activeEffects.get(i);
			e.update();
			if (e.expired())
				i--;
		}
	}

	int divH, divS;
	int maxH, maxS;
	int off = 0;

	/**
	 * draws all ui components associated with this mob
	 * 
	 * @param g - the DrawGraphics component associated with the renderer
	 */
	public void renderUI(DrawGraphics g) {

		for (int i = 0; i < maxH; i++) {
			Assets.bTick.render((int) x - handler.getCamera().xOffset() - xOff + i * 2 + off,
					(int) y - handler.getCamera().yOffset() - yOff - 7, g);
			if (i < health / divH)
				Assets.hTick.render((int) x - handler.getCamera().xOffset() - xOff + i * 2 + off,
						(int) y - handler.getCamera().yOffset() - yOff - 7, g);
		}

		for (int i = 0; i < maxS; i++) {
			Assets.bTick.render((int) x - handler.getCamera().xOffset() - xOff + i * 2 + off,
					(int) y - handler.getCamera().yOffset() - yOff - 3, g);
			if (i < spirit / divS)
				Assets.sTick.render((int) x - handler.getCamera().xOffset() - xOff + i * 2 + off,
						(int) y - handler.getCamera().yOffset() - yOff - 3, g);
		}
	}

	/**
	 * handles all mob movement and decisionmaking
	 */
	public abstract void move();

	/**
	 * deals damage to a mob
	 * 
	 * @param amount - the amount of damage
	 * @param type   - the type of damage
	 * @returns the actual amount of damage taken after mitigation
	 */
	public int harm(int amount, int type) {
		int val;
		Behavior b = new Behavior() {

			@Override
			public void initial(int x, int y, int x0, int y0, int[][] data) {
				data[0][0] = x;
				data[0][1] = y;
				data[0][2] = 30;
			}

			@Override
			public void update(int x, int y, int x0, int y0, int[] data, int index) {
				data[1] -= 1;
			}

		};
		switch (type) {
		case Effect.DAMAGE_TYPE_ENERGY:
			val = amount;
			if (health - amount < 0)
				val = health;
			health -= val;
			if (health <= 0)
				this.die();
			new Particle("" + val, 0xffAA0000, 1, getCenteredX(), getCenteredY() - 16, getCenteredX(),
					getCenteredY() + 32, handler, b).start();
			return val;
		case Effect.DAMAGE_TYPE_MENTAL:
			val = amount;
			if (spirit - amount < 0)
				val = spirit;
			spirit -= val;
			if (spirit <= 0) {
				spirit = spiritMax;
				new Effect(120 * healthMax, 120, this, 0);
			}
			new Particle("" + val, 0xff9999FF, 1, getCenteredX(), getCenteredY() - 16, getCenteredX(),
					getCenteredY() + 32, handler, b).start();
			return val;
		case Effect.DAMAGE_TYPE_PHYSICAL:
			val = amount - arm;
			if (val < 0)
				val = 0;
			if (health - val < 0)
				val = health;
			health -= val;
			if (health <= 0)
				this.die();
			new Particle("" + val, 0xffAA0000, 1, getCenteredX(), getCenteredY() - 16, getCenteredX(),
					getCenteredY() + 32, handler, b).start();
			return val;
		default:
			return 0;
		}
	}

	/**
	 * heals a mob
	 * 
	 * @param amount - the amount of healing
	 * @param type   - the type of healing
	 * @returns the actual amount of healing given after mitigation and adjustments
	 */
	public int heal(int amount, int type) {
		int val;
		Behavior b = new Behavior() {

			@Override
			public void initial(int x, int y, int x0, int y0, int[][] data) {
				data[0][0] = x;
				data[0][1] = y;
				data[0][2] = 30;
			}

			@Override
			public void update(int x, int y, int x0, int y0, int[] data, int index) {
				data[0] -= 1;
			}

		};
		switch (type) {
		case Effect.DAMAGE_TYPE_ENERGY:
			val = amount;
			if (health + amount > healthMax)
				val = healthMax - health;
			health += val;
			new Particle("" + val, 0xffFFFF00, 1, getCenteredX(), getCenteredY() - 16, getCenteredX(),
					getCenteredY() + 32, handler, b).start();
			return val;
		case Effect.DAMAGE_TYPE_MENTAL:
			val = amount;
			if (spirit + amount > spiritMax)
				val = spiritMax - spirit;
			spirit += val;
			new Particle("" + val, 0xff0000FF, 1, getCenteredX(), getCenteredY() - 16, getCenteredX(),
					getCenteredY() + 32, handler, b).start();
			return val;
		case Effect.DAMAGE_TYPE_PHYSICAL:
			val = amount - arm;
			if (val < 0)
				val = 0;
			if (health + amount - arm > healthMax)
				val = healthMax - health;
			health += val;
			new Particle("" + val, 0xffFFFF00, 1, getCenteredX(), getCenteredY() - 16, getCenteredX(),
					getCenteredY() + 32, handler, b).start();
			return val;
		default:
			return 0;
		}

	}

	/**
	 * handles equipping items
	 * 
	 * @param i - the item to equip
	 */
	public abstract void equip(Item i);

	/**
	 * handles item pickups
	 * 
	 * @param i - the item to pickup
	 * @returns true if the pickup was successful, false otherwise
	 */
	public abstract boolean pickup(Item i);

	/**
	 * gets this mob's inventory
	 * 
	 * @returns this mob's inventory
	 */
	public Inventory getInventory() {
		return inventory;
	}

	/**
	 * performs an adjustment to the speed stat, without letting move be less than 1
	 * 
	 * @param adj - the value to adjust by
	 */
	public void adjSpeed(double adj) {
		speed += adj;
		if (speed < 1)
			move = 1;
		else
			move = speed;
	}

	/**
	 * readjusts the maximum inventory capacity
	 * 
	 * @param adj - the value to adjust by
	 */
	public void adjInv(int adj) {
		inventory.resize(inventory.getSize() + adj);
	}

	/**
	 * adjusts the armor value
	 * 
	 * @param adj - the value to adjust by
	 */
	public void adjArmor(int adj) {
		this.arm += adj;
	}

	/**
	 * adjusts max hp
	 * 
	 * @param adj - the value to adjust by
	 */
	public void adjMaxhp(int adj) {
		this.healthMax += adj;
		if (health > healthMax)
			health = healthMax;
	}

	/**
	 * adjusts max sp
	 * 
	 * @param adj - the value to adjust by
	 */
	public void adjMaxsp(int adj) {
		this.spiritMax += adj;
		if (spirit > spiritMax)
			spirit = spiritMax;
	}

	/**
	 * adjusts the luck stat
	 * 
	 * @param adj - the value to adjust by
	 */
	public void adjLuck(int adj) {
		this.luk += adj;
	}

	/**
	 * @returns the current health of this mob
	 */
	public int getHealth() {
		return this.health;
	}

	/**
	 * @returns the current spirit of this mob
	 */
	public int getSpirit() {
		return this.spirit;
	}

	/**
	 * prevents this mob from moving
	 */
	public void lock() {
		this.locked = true;
	}

	/**
	 * allows this mob to move again
	 */
	public void unlock() {
		this.locked = false;
	}

	@Override
	public void die() {
		if (inventory.getRawHeld().size() > 0)
			handler.getWorld().getEntities().addEntity(new Corpse(handler, deathSprite, hitbox, inventory, x, y));
		super.die();
	}

	public void addEffect(Effect e) {
		activeEffects.add(e);
	}

	public void removeEffect(Effect e) {
		activeEffects.remove(e);
	}

	public Vector getVector() {
		return vector;
	}

	public Hitbox getHurtbox() {
		return hurtbox;
	}

	@Override
	public void load(Handler h) {
		super.load(h);
		setup();
		if (inventory != null)
			inventory.load(h, this);
	}

}
