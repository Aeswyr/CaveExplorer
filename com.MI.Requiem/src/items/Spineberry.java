package items;

import core.Assets;
import entity.Mob;
import gfx.DrawGraphics;
import item.Item;
import runtime.Handler;

public class Spineberry extends Item{

	public Spineberry(Handler handler, Mob holder) {
		super(handler, holder);

		ID = "1";
		tags = "hand";
		
		useTime = 30;
		timer = useTime;
		
		sprite = Assets.spineberry;
		invSprite = Assets.spineberry_inv;
		
	}
	
	public Spineberry(int x, int y, Handler handler) {
		super(x, y, handler);
	
		ID = "0";
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
			holder.harm(30);
			holder.healHNG(15);
			holder.healSPI(15);
			this.consumed = true;
			timer = 0;
		}
		
	}

}
