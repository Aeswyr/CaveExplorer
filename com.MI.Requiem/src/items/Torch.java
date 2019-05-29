package items;

import core.Assets;
import entity.Entity;
import gfx.DrawGraphics;
import item.Item;
import runtime.Handler;
import runtime.Light;

public class Torch extends Item {

	Light bright;
	Light dim;
	Light burnt;

	public Torch(Handler handler, Entity holder) {
		super(handler, holder);
		
		tags = "hand";
		
		useMax = 10800;
		use = useMax;
		
		this.sprite = null;
		this.invSprite = Assets.litTorch_inv;

		bright = new Light(256, 0xffffffAA, handler);
		dim = new Light(128, 0xffffffAA, handler);
		burnt = new Light(90, 0xff111111, handler);
		dim.light();
	}

	@Override
	public void renderTextBox(int x, int y, DrawGraphics g) {
		// TODO Auto-generated method stub
	}

	@Override
	public void update() {
		if (equipped)
			use -= 3;
		else
			use -= 1;
		if (use == 0 || use == -1) {
			invSprite = Assets.burntTorch_inv;
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
			bright.setPos(holder.getX() - 16, holder.getY() - 16);
			dim.setPos(holder.getX() - 16, holder.getY() - 16);
			burnt.setPos(holder.getX() - 16, holder.getY() - 16);
		} else {
			bright.setPos(x, y);
			dim.setPos(x, y);
			burnt.setPos(x, y);
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

}
