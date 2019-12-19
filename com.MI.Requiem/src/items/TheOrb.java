package items;

import core.Assets;
import effects.Effect;
import entity.Mob;
import item.Item;
import runtime.Handler;
import runtime.Light;

public class TheOrb extends Item {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4791488089979754632L;
	Light light;
	int stage = 0;
	boolean pendingStage;
	int lightTimer = 0;

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
		
		this.useTime = 30;
		
		stackable = false;
	}

	@Override
	public void use() {
		Mob holder = (Mob)this.holder;
		if (timer >= useTime) {
			switch (stage) {
			case 0:
				holder.harm(1, Effect.DAMAGE_TYPE_MENTAL);
				stage = 1;
				light.light();
				lightTimer = 600;
				break;
			case 1:
				holder.harm(2, Effect.DAMAGE_TYPE_MENTAL);
				light.snuff();
				stage = 2;
				light = new Light(160, 0xff002669, handler);
				light.light();
				break;
			case 2:
				holder.harm(3, Effect.DAMAGE_TYPE_MENTAL);
				light.snuff();
				stage = 3;
				light = new Light(224, 0xff002669, handler);
				light.light();
				break;
			case 3:
				light.snuff();
				stage = 0;
				light = new Light(96, 0xff002669, handler);
				break;
			}
			timer = 0;
		}

	}

	@Override
	public void update() {
		super.update();
		if (lightTimer > 0) lightTimer--;
		if (holder != null && stage != 0 && lightTimer > 0) {
			light.setPos(holder.getLightX(), holder.getLightY());
		}
		if (lightTimer <= 0) {
			light.snuff();
			stage = 0;
			light = new Light(96, 0xff002669, handler);
		}
	}

	@Override
	public Item strip() {
		this.light.snuff();
		this.light = null;
		return this;
	}
}
