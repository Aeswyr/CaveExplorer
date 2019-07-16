package items;

import core.Assets;
import entity.Mob;
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
		ID = "7";
		tags = "trinket body";
		name = "Cloak";
		

		useMax = 80;
		use = useMax;

		this.sprite = Assets.cloak;
		this.invSprite = Assets.cloak_inv;
		
		stackable = false;
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
