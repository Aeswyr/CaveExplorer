package item;

import entity.Entity;
import gfx.DrawGraphics;
import gfx.Sprite;
import runtime.Handler;
import utility.Storeable;

public abstract class Item implements Storeable {

	
	/* Tag list
	 * head - an item that can be used in the head slot
	 * body - an item that can be used in the body slot
	 * trinket - an item that can be used in any trinket slot
	 * hand - an item that can be placed in a hand slot
	 * mainhand - an item that can be placed in only the main hand
	 * offhand - an item that can be placed in only the off hand
	 */
	protected String tags;
	protected Sprite invSprite, sprite;
	protected int x, y;
	protected Handler handler;
	protected Entity holder;
	protected boolean equipped = false;
	
	
	protected int useMax = 1;
	protected int use = useMax;
	
	public Item(Handler handler, Entity holder) {
		this.handler = handler;
		this.holder = holder;
	}
	
	public void render(DrawGraphics g) {
		sprite.render(x, y, g);
	}
	
	public void renderInventory(int x, int y, DrawGraphics g) {
		invSprite.render(x, y, g);
		if (use > 0 && use != useMax) {
		g.fillRect(x + 3, y + 24, (int)(27.0 * use / useMax), 4, 0xFFFFFF00);
		}
	}
	
	public abstract void use();
	
	
	public void setHolder(Entity e) {
		
	}
	
	public abstract void update();
	
	public abstract void onEquip();
	
	public abstract void onDequip();
	
	public String getTags() {
		return tags;
	}
	
	public boolean equipped() {
		return equipped;
	}
}
