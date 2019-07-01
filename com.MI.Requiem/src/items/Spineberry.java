package items;

import core.Assets;
import effects.Effect;
import entity.Mob;
import gfx.DrawGraphics;
import item.Item;
import runtime.Handler;

public class Spineberry extends Item{

	public Spineberry(Handler handler, Mob holder) {
		super(handler, holder);

		ID = "4";
		tags = "hand";
		
		useTime = 30;
		timer = useTime;
		
		sprite = Assets.spineberry;
		invSprite = Assets.spineberry_inv;
		
	}
	
	public Spineberry(int x, int y, Handler handler) {
		super(x, y, handler);
	
		ID = "4";
		tags = "hand";
		
		useTime = 30;
		timer = useTime;
		
		sprite = Assets.spineberry;
		invSprite = Assets.spineberry_inv;
		
	}

	@Override
	public void renderTextBox(int x, int y, DrawGraphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void use() {
		if (!consumed && timer >= useTime) {
			holder.harm(30, Effect.DAMAGE_TYPE_ENERGY);
			holder.heal(5, Effect.DAMAGE_TYPE_HUNGER);
			holder.heal(5, Effect.DAMAGE_TYPE_MENTAL);
			this.consumed = true;
			timer = 0;
		}
		
	}

}
