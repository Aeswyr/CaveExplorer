package entity;

import gfx.DrawGraphics;
import gfx.Sprite;
import item.Item;
import runtime.Handler;

public abstract class Mob extends Entity {

	public Mob(Handler handler) {
		super(handler);
	}

	protected Sprite activeSprite;
	protected double health, healthMax, speed, spirit, spiritMax, hunger, hungerMax;
	protected int con, wil, str, agi, cha, kno, arm, luk, srv;
	protected boolean starving;

	@Override
	public void render(DrawGraphics g) {
		activeSprite.render((int) x - handler.getCamera().xOffset() - xOff,
				(int) y - handler.getCamera().yOffset() - yOff, g);
	}

	@Override
	public void update() {
		hitbox.update();
		move();
	}

	public abstract void renderUI(DrawGraphics g);

	public abstract void move();

	public void harm(double amount) {
		health -= (amount - arm);
		if (health <= 0) this.die();
	}

	public void heal(double amount) {
		health += amount;
		if (health > healthMax)
			health = healthMax;
	}
	
	public double drain(double amount) {
		harm(amount);
		return amount - arm;
	}
	
	public void harmSPI(double amount) {
		spirit -= amount;
		if (spirit <= 0) this.die();
	}
	public void healSPI(double amount) {
		spirit += amount;
		if (spirit > spiritMax)
			spirit = spiritMax;
	}
	public double drainSPI(double amount) {
		harmSPI(amount);
		return amount;
	}
	
	public void harmHNG(double amount) {
		hunger -= amount;
		if (hunger <= 0) {
			starving = true;
			hunger = 0;
		}
	}
	public void healHNG(double amount) {
		hunger += amount;
		if (hunger > hungerMax)
			hunger = hungerMax;
	}
	public double drainHNG(double amount) {
		harmHNG(amount);
		return amount;
	}
	
	public abstract void equip(Item i);
	
	public abstract boolean pickup(Item i);
	
	public void adjSpeed(double adj) {
		speed += adj;
	}
}
