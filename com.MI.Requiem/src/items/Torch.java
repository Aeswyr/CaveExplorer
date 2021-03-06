package items;

import core.Assets;
import entity.Mob;
import item.Item;
import runtime.Light;
import utility.Utils;

public class Torch extends Item {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4616465095259641145L;
	Light bright;
	Light dim;
	Light burnt;

	public Torch(Mob holder) {
		super(holder);
	}

	public Torch(int x, int y) {
		super(x, y);
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

		bright = new Light(256, 0xffffffAA);
		dim = new Light(128, 0xffffffAA);
		burnt = new Light(90, 0xff111111);
		dim.light();

		stackable = false;
	}

	@Override
	public void update() {
		if (equipped)
			use -= 3;
		else
			use -= 1;
		if (use >= -3 && use <= 0 && !Utils.tagOverlaps(tags, "carvable")) {
			invSprite = Assets.burntTorch_inv;
			sprite = Assets.burntTorch;
			tags += " carvable";
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
			bright.setPos(holder.getLightX(), holder.getLightY());
			dim.setPos(holder.getLightX(), holder.getLightY());
			burnt.setPos(holder.getLightX(), holder.getLightY());
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
		super.onEquip();
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
		super.onDequip();
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
