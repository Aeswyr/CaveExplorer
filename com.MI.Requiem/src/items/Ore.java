package items;

import core.Assets;
import entity.Mob;
import gfx.DrawGraphics;
import item.Item;
import runtime.Handler;

public class Ore extends Item{

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
		tags = "";
		this.ID = "1:" + id;
		
		useTime = 30;
		timer = useTime;
		
		switch (id) {
		case 0:
			this.sprite = Assets.ironOre;
			this.invSprite = Assets.ironOre_inv;
			break;
		default:
			break;
		}
		
	}

	@Override
	public void renderTextBox(int x, int y, DrawGraphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void use() {
		// TODO Auto-generated method stub
		
	}



}
