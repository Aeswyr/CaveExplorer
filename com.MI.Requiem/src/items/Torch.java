package items;

import core.Assets;
import entity.Mob;
import item.Item;
import runtime.Handler;
import runtime.Light;

public class Torch extends Item {

	Light bright;
	Light dim;
	Light burnt;

	public Torch(Handler handler, Mob holder) {
		super(handler, holder);
	}

	public Torch(int x, int y, Handler handler) {
		super(x, y, handler);
	}
	
	@Override
	protected void setup() {
		ID = "6";
		tags = "hand";
		name = "Torch";
		

		useMax = 10800;
		use = useMax;

		this.sprite = Assets.litTorch;
		this.invSprite = Assets.litTorch_inv;

		bright = new Light(256, 0xffffffAA, handler);
		dim = new Light(128, 0xffffffAA, handler);
		burnt = new Light(90, 0xff111111, handler);
		dim.light();
		
		stackable = false;
	}

	@Override
	public void update() {
		if (equipped)
			use -= 3;
		else
			use -= 1;
		if (use == 0 || use == -1) {
			invSprite = Assets.burntTorch_inv;
			sprite = Assets.burntTorch;
			if (equipped) {
				bright.snuff();
				dim.snuff();
				burnt.light();
			} else {
				dim.snuff();
				bright.snuff();
				burnt.snuff();
			}
		}

		if (holder != null) {
			bright.setPos(holder.getCenteredX(), holder.getCenteredY());
			dim.setPos(holder.getCenteredX(), holder.getCenteredY());
			burnt.setPos(holder.getCenteredX(), holder.getCenteredY());
		} else {
			bright.setPos((int) x, (int) y);
			dim.setPos((int) x, (int) y);
			burnt.setPos((int) x, (int) y);
		}
	}

	@Override
	public void use() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEquip() {
		equipped = true;
		if (use > 0) {
			bright.light();
			dim.snuff();
		} else {
			bright.snuff();
			dim.snuff();
			burnt.light();
		}

	}

	@Override
	public void onDequip() {
		equipped = false;
		if (use > 0) {
			dim.light();
			bright.snuff();
		} else {
			dim.snuff();
			bright.snuff();
			burnt.snuff();
		}

	}

	@Override
	public Item strip() {
		this.bright.snuff();
		this.burnt.snuff();
		this.dim.snuff();
		
		this.bright = null;
		this.burnt = null;
		this.dim = null;
		return this;
	}
	
}
