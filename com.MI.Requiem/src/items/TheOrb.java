package items;

import core.Assets;
import entity.Mob;
import gfx.DrawGraphics;
import item.Item;
import runtime.Handler;
import runtime.Light;

public class TheOrb extends Item {

	Light light;

	public TheOrb(Handler handler, Mob holder) {
		super(handler, holder);

		ID = "u3";
		tags = "hand";

		this.sprite = Assets.theOrb;
		this.invSprite = Assets.theOrb_inv;

		light = new Light(64, 0xff002669, handler);
		light.light();
	}

	public TheOrb(int x, int y, Handler handler) {
		super(x, y, handler);

		ID = "u3";
		tags = "hand";

		this.sprite = Assets.theOrb;
		this.invSprite = Assets.theOrb_inv;

		light = new Light(96, 0xff002669, handler);
		light.light();

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
		if (holder != null)
			light.setPos(holder.getCenteredX(), holder.getCenteredY());
		else
			light.setPos((int) x, (int) y);

	}

}
