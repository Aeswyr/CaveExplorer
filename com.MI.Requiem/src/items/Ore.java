package items;

import core.Assets;
import entity.Mob;
import item.Item;
import runtime.Handler;

public class Ore extends Item{

	//ID
	// 0 - iron ore
	
	int id;
	public Ore(Handler handler, Mob holder, int id) {
		super(handler, holder);
		this.id = id;
		setup();
	}

	public Ore(int x, int y, Handler handler, int id) {
		super(x, y, handler);
		this.id = id;
		setup();
	}
	
	@Override
	protected void setup() {
		tags = "mineral";
		this.ID = "1:" + id;
		
		useTime = 30;
		timer = useTime;
		
		switch (id) {
		case 0:
			this.sprite = Assets.ironOre;
			this.invSprite = Assets.ironOre_inv;
			name = "Iron Ore";
			statPackage[ITEM_WEIGHT] = 1;
			break;
		default:
			break;
		}
		
	}

	@Override
	public void use() {
		// TODO Auto-generated method stub
		
	}



}
