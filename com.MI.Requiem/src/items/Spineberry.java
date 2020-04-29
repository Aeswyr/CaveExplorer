package items;

import core.Assets;
import effects.Effect;
import entity.Mob;
import item.Item;

public class Spineberry extends Item {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2896971184836307629L;

	public Spineberry( Mob holder) {
		super(holder);

	}

	public Spineberry(int x, int y) {
		super(x, y);

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
		Mob holder = (Mob)this.holder;
		if (!consumed && timer >= useTime) {
			holder.heal(5, Effect.DAMAGE_TYPE_MENTAL);
			this.consumed = true;
			timer = 0;
		}

	}

}
