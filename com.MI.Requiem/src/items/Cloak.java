package items;

import core.Assets;
import entity.Mob;
import gfx.DrawGraphics;
import item.Item;
import runtime.Handler;

public class Cloak extends Item {

	public Cloak(Handler handler, Mob holder) {
		super(handler, holder);

	}

	public Cloak(int x, int y, Handler handler) {
		super(x, y, handler);

	}

	@Override
	protected void setup() {
		ID = "u1";
		tags = "trinket body";

		useMax = 80;
		use = useMax;

		this.sprite = Assets.cloak;
		this.invSprite = Assets.cloak_inv;
	}

	@Override
	public void renderTextBox(int x, int y, DrawGraphics g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void use() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEquip() {
		this.equipped = true;
		holder.adjSpeed(0.25);

	}

	@Override
	public void onDequip() {
		this.equipped = false;
		holder.adjSpeed(-0.25);

	}

}
