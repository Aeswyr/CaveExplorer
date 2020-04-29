package items;

import core.Assets;
import entity.Mob;
import item.Item;

public class Ingot extends Item{

	//ID
	// 0 - iron ore
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7559663476034458062L;
	int id;
	public Ingot(Mob holder, int id) {
		super(holder);
		this.id = id;
		setup();
	}

	public Ingot(int x, int y, int id) {
		super(x, y);
		this.id = id;
		setup();
	}
	
	@Override
	protected void setup() {
		tags = "metal";
		this.ID = "3:" + id;
		
		useTime = 30;
		timer = useTime;
		
		switch (id) {
		case 0:
			this.sprite = Assets.ironIngot;
			this.invSprite = Assets.ironIngot_inv;
			name = "Iron Ingot";
			statPackage[ITEM_WEIGHT] = 1;
			statPackage[ITEM_ARMOR] = 2;
			statPackage[ITEM_DAMAGE] = 1;
			statPackage[ITEM_DURABILITY] = 10;
			break;
		case 1:
			this.sprite = Assets.ironIngot;
			this.invSprite = Assets.ironIngot_inv;
			name = "Aluminum Ingot";
			statPackage[ITEM_WEIGHT] = 1;
			statPackage[ITEM_ARMOR] = 2;
			statPackage[ITEM_DAMAGE] = 1;
			statPackage[ITEM_DURABILITY] = 10;
			break;
		case 2:
			this.sprite = Assets.ironIngot;
			this.invSprite = Assets.ironIngot_inv;
			name = "Antimony Ingot";
			statPackage[ITEM_WEIGHT] = 1;
			statPackage[ITEM_ARMOR] = 2;
			statPackage[ITEM_DAMAGE] = 1;
			statPackage[ITEM_DURABILITY] = 10;
			break;
		case 3:
			this.sprite = Assets.ironIngot;
			this.invSprite = Assets.ironIngot_inv;
			name = "Copper Ingot";
			statPackage[ITEM_WEIGHT] = 1;
			statPackage[ITEM_ARMOR] = 2;
			statPackage[ITEM_DAMAGE] = 1;
			statPackage[ITEM_DURABILITY] = 10;
			break;
		case 4:
			this.sprite = Assets.ironIngot;
			this.invSprite = Assets.ironIngot_inv;
			name = "Silver Ingot";
			statPackage[ITEM_WEIGHT] = 1;
			statPackage[ITEM_ARMOR] = 2;
			statPackage[ITEM_DAMAGE] = 1;
			statPackage[ITEM_DURABILITY] = 10;
			break;
		case 5:
			this.sprite = Assets.ironIngot;
			this.invSprite = Assets.ironIngot_inv;
			name = "Lead Ingot";
			statPackage[ITEM_WEIGHT] = 1;
			statPackage[ITEM_ARMOR] = 2;
			statPackage[ITEM_DAMAGE] = 1;
			statPackage[ITEM_DURABILITY] = 10;
			break;
		case 6:
			this.sprite = Assets.ironIngot;
			this.invSprite = Assets.ironIngot_inv;
			name = "Gold Ingot";
			statPackage[ITEM_WEIGHT] = 1;
			statPackage[ITEM_ARMOR] = 2;
			statPackage[ITEM_DAMAGE] = 1;
			statPackage[ITEM_DURABILITY] = 10;
			break;
		case 7:
			this.sprite = Assets.ironIngot;
			this.invSprite = Assets.ironIngot_inv;
			name = "Tin Ingot";
			statPackage[ITEM_WEIGHT] = 1;
			statPackage[ITEM_ARMOR] = 2;
			statPackage[ITEM_DAMAGE] = 1;
			statPackage[ITEM_DURABILITY] = 10;
			break;
		case 8:
			this.sprite = Assets.ironIngot;
			this.invSprite = Assets.ironIngot_inv;
			name = "Zinc Ingot";
			statPackage[ITEM_WEIGHT] = 1;
			statPackage[ITEM_ARMOR] = 2;
			statPackage[ITEM_DAMAGE] = 1;
			statPackage[ITEM_DURABILITY] = 10;
			break;
		case 9:
			this.sprite = Assets.ironIngot;
			this.invSprite = Assets.ironIngot_inv;
			name = "Chrome Ingot";
			statPackage[ITEM_WEIGHT] = 1;
			statPackage[ITEM_ARMOR] = 2;
			statPackage[ITEM_DAMAGE] = 1;
			statPackage[ITEM_DURABILITY] = 10;
			break;
		case 10:
			this.sprite = Assets.ironIngot;
			this.invSprite = Assets.ironIngot_inv;
			name = "Titanium Ingot";
			statPackage[ITEM_WEIGHT] = 1;
			statPackage[ITEM_ARMOR] = 2;
			statPackage[ITEM_DAMAGE] = 1;
			statPackage[ITEM_DURABILITY] = 10;
			break;
		case 11:
			this.sprite = Assets.ironIngot;
			this.invSprite = Assets.ironIngot_inv;
			name = "Mercury";
			tags = "";
			statPackage[ITEM_WEIGHT] = 1;
			statPackage[ITEM_ARMOR] = 2;
			statPackage[ITEM_DAMAGE] = 1;
			statPackage[ITEM_DURABILITY] = 10;
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
