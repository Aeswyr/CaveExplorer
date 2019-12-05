package items;

import core.Assets;
import entity.Mob;
import item.Item;
import runtime.Handler;

public class Gem extends Item{

	//ID
	// 0 - Corundum
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6885793369745861688L;
	int id;
	
	public Gem(Handler handler, Mob holder, int id) {
		super(handler, holder);
		this.id = id;
		setup();
	}
	
	public Gem(int x, int y, Handler handler, int id) {
		super(x, y, handler);
		this.id = id;
		setup();
	}
	
	@Override
	protected void setup() {
		tags = "gem";
		this.ID = "2:" + id;
		
		useTime = 30;
		timer = useTime;
		
		switch (id) {
		case 0:
			this.sprite = Assets.corundum;
			this.invSprite = Assets.corundum_inv;
			name = "Corundum";
			break;
		case 1:
			this.sprite = Assets.corundum;
			this.invSprite = Assets.corundum_inv;
			name = "Ruby";
			break;
		case 2:
			this.sprite = Assets.corundum;
			this.invSprite = Assets.corundum_inv;
			name = "Sapphire";
			break;
		case 3:
			this.sprite = Assets.corundum;
			this.invSprite = Assets.corundum_inv;
			name = "Garnet";
			break;
		case 4:
			this.sprite = Assets.corundum;
			this.invSprite = Assets.corundum_inv;
			name = "Quartz";
			break;
		case 5:
			this.sprite = Assets.corundum;
			this.invSprite = Assets.corundum_inv;
			name = "Amethyst";
			break;
		case 6:
			this.sprite = Assets.corundum;
			this.invSprite = Assets.corundum_inv;
			name = "Citrine";
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
