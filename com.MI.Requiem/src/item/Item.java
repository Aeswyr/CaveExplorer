package item;

import entity.Mob;
import gfx.DrawGraphics;
import gfx.Sprite;
import runtime.Handler;
import utility.Storeable;

public abstract class Item implements Storeable {

	/*
	 * Tag list 
	 * head - an item that can be used in the head slot 
	 * body - an item that
	 * can be used in the body slot 
	 * trinket - an item that can be used in any trinket slot 
	 * hand - an item that can be placed in a hand slot 
	 * mainhand - an item that can be placed in only the main hand 
	 * offhand - an item that can be placed in only the off hand
	 */
	protected String tags, ID;
	protected Sprite invSprite, sprite;
	protected int x, y;
	protected Handler handler;
	protected Mob holder;
	protected boolean equipped = false;
	protected boolean consumed = false;

	protected int useMax = 1;
	protected int use = useMax;

	public Item(Handler handler, Mob holder) {
		this.handler = handler;
		this.holder = holder;
	}

	public void render(DrawGraphics g) {
		sprite.render(x, y, g);
	}

	public void renderInventory(int x, int y, DrawGraphics g) {
		invSprite.render(x, y, g);
		if (use > 0 && use != useMax) {
			g.fillRect(x + 3, y + 24, (int) (27.0 * use / useMax), 4, 0xFFFFFF00);
		}
	}

	public abstract void use();

	public void setHolder(Mob m) {
		this.holder = m;
	}

	public abstract void update();

	public void onEquip() {
		this.equipped = true;
	}

	public void onDequip() {
		this.equipped = false;
	}

	public String getTags() {
		return tags;
	}

	public boolean equipped() {
		return equipped;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Item) {
		if (((Item)obj).ID.equals(this.ID) && this.ID.charAt(0) != 'u') return true;
		return false;
		} else return super.equals(obj);
		
	}
	
	public void setConsumed(boolean b) {
		this.consumed = b;
	}
}
