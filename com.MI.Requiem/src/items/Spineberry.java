package items;

import core.Assets;
import effects.Effect;
import entity.Mob;
import item.Item;
import runtime.Handler;

public class Spineberry extends Item {

	public Spineberry(Handler handler, Mob holder) {
		super(handler, holder);

	}

	public Spineberry(int x, int y, Handler handler) {
		super(x, y, handler);

	}

	@Override
	protected void setup() {
		ID = "4";
		tags = "hand";
		name = "Spineberry";
		

		useTime = 30;
		timer = useTime;

		sprite = Assets.spineberry;
		invSprite = Assets.spineberry_inv;
	}

	@Override
	public void use() {
		if (!consumed && timer >= useTime) {
			holder.heal(5, Effect.DAMAGE_TYPE_MENTAL);
			this.consumed = true;
			timer = 0;
		}

	}

}
