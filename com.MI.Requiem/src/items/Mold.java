package items;

import core.Assets;
import entity.Mob;
import item.Item;
import runtime.Handler;

public class Mold extends Item{

	public Mold(Handler handler, Mob holder) {
		super(handler, holder);

	}

	public Mold(int x, int y, Handler handler) {
		super(x, y, handler);

	}

	@Override
	protected void setup() {
		ID = "5";
		tags = "";
		name = "Mold";
		
		statPackage[ITEM_WEIGHT] = 2;
		
		useTime = 30;
		timer = useTime;

		sprite = Assets.mold;
		invSprite = Assets.mold_inv;
	}

	@Override
	public void use() {
		// TODO Auto-generated method stub
		
	}
	
}
