package items;

import core.Assets;
import entity.Mob;
import item.Item;
import runtime.Handler;
import runtime.Light;

public class TheOrb extends Item {

	Light light;

	public TheOrb(Handler handler, Mob holder) {
		super(handler, holder);
	}

	public TheOrb(int x, int y, Handler handler) {
		super(x, y, handler);
	}
	
	@Override
	protected void setup() {
		ID = "9";
		tags = "hand";
		name = "The Orb";
		
		statPackage[ITEM_WEIGHT] = 2;

		this.sprite = Assets.theOrb;
		this.invSprite = Assets.theOrb_inv;

		light = new Light(96, 0xff002669, handler);
		light.light();
		
		stackable = false;
	}

	@Override
	public void use() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		if (holder != null)
			light.setPos(holder.getCenteredX(), holder.getCenteredY());
		else
			light.setPos((int) x, (int) y);

	}

	@Override
	public Item strip() {
		this.light.snuff();
		this.light = null;
		return this;
	}
	
}
