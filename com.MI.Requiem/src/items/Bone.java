package items;

import core.Assets;
import entity.Mob;
import item.Item;

public class Bone extends Item{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2900728830259426097L;

	public Bone( Mob holder) {
		super(holder);

	}

	public Bone(int x, int y) {
		super(x, y);

	}

	@Override
	protected void setup() {
		ID = "10";
		tags = "carvable";
		name = "Bone";
		
		statPackage[ITEM_WEIGHT] = 1;
		statPackage[ITEM_DURABILITY] = -5;
		
		useTime = 30;
		timer = useTime;

		sprite = Assets.bone;
		invSprite = Assets.bone_inv;
	}

	@Override
	public void use() {
		// TODO Auto-generated method stub
		
	}
	
}
