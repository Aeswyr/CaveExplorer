package items;

import core.Assets;
import entity.Mob;
import gfx.DrawGraphics;
import item.Item;
import runtime.Handler;

public class Ingot extends Item{

	//ID
	// 0 - iron ore
	
	int id;
	public Ingot(Handler handler, Mob holder, int id) {
		super(handler, holder);
		this.id = id;
		setup();
	}

	public Ingot(int x, int y, Handler handler, int id) {
		super(x, y, handler);
		this.id = id;
		setup();
	}
	
	@Override
	protected void setup() {
		tags = "";
		this.ID = "3:" + id;
		
		useTime = 30;
		timer = useTime;
		
		switch (id) {
		case 0:
			this.sprite = Assets.ironIngot;
			this.invSprite = Assets.ironIngot_inv;
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
