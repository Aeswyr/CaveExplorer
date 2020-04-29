package items;

import core.Assets;
import entity.Mob;
import item.Item;

public class Mold extends Item{

	/**
	 * 
	 */
	private static final long serialVersionUID = 612274906360647466L;

	public Mold( Mob holder) {
		super(holder);

	}

	public Mold(int x, int y) {
		super(x, y);

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
