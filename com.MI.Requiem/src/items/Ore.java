package items;

import core.Assets;
import entity.Mob;
import item.Item;
import runtime.Handler;

public class Ore extends Item{

	//ID
	// 0 - iron ore
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3980248478264884341L;
	
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
		case 1:
			this.sprite = Assets.ironOre;
			this.invSprite = Assets.ironOre_inv;
			name = "Aluminum Ore";
			statPackage[ITEM_WEIGHT] = 1;
			break;
		case 2:
			this.sprite = Assets.ironOre;
			this.invSprite = Assets.ironOre_inv;
			name = "Antimony Ore";
			statPackage[ITEM_WEIGHT] = 1;
			break;
		case 3:
			this.sprite = Assets.ironOre;
			this.invSprite = Assets.ironOre_inv;
			name = "Copper Ore";
			statPackage[ITEM_WEIGHT] = 1;
			break;
		case 4:
			this.sprite = Assets.ironOre;
			this.invSprite = Assets.ironOre_inv;
			name = "Silver Ore";
			statPackage[ITEM_WEIGHT] = 1;
			break;
		case 5:
			this.sprite = Assets.ironOre;
			this.invSprite = Assets.ironOre_inv;
			name = "Lead Ore";
			statPackage[ITEM_WEIGHT] = 1;
			break;
		case 6:
			this.sprite = Assets.ironOre;
			this.invSprite = Assets.ironOre_inv;
			name = "Gold Ore";
			statPackage[ITEM_WEIGHT] = 1;
			break;
		case 7:
			this.sprite = Assets.ironOre;
			this.invSprite = Assets.ironOre_inv;
			name = "Tin Ore";
			statPackage[ITEM_WEIGHT] = 1;
			break;
		case 8:
			this.sprite = Assets.ironOre;
			this.invSprite = Assets.ironOre_inv;
			name = "Zinc Ore";
			statPackage[ITEM_WEIGHT] = 1;
			break;
		case 9:
			this.sprite = Assets.ironOre;
			this.invSprite = Assets.ironOre_inv;
			name = "Chromium";
			statPackage[ITEM_WEIGHT] = 1;
			break;
		case 10:
			this.sprite = Assets.ironOre;
			this.invSprite = Assets.ironOre_inv;
			name = "Titanium Ore";
			statPackage[ITEM_WEIGHT] = 1;
			break;
		case 11:
			this.sprite = Assets.ironOre;
			this.invSprite = Assets.ironOre_inv;
			name = "Cinnabar";
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
