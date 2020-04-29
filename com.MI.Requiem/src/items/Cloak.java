package items;

import core.Assets;
import entity.Mob;
import item.Item;

public class Cloak extends Item {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4076010768148886682L;

	public Cloak( Mob holder) {
		super( holder);

	}

	public Cloak(int x, int y) {
		super(x, y);

	}

	@Override
	protected void setup() {
		ID = "7";
		tags = "trinket body";
		name = "Cloak";
		
		statPackage[ITEM_WEIGHT] = 1;
		statPackage[ITEM_SPEED] = 1;

		useMax = 80;
		use = useMax;

		this.sprite = Assets.cloak;
		this.invSprite = Assets.cloak_inv;
		
		stackable = false;
	}



	@Override
	public void use() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

}
