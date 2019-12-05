package interactables;

import core.Assets;
import entity.Hitbox;
import entity.Interactable;
import gfx.DrawGraphics;
import gfx.Sprite;
import item.Inventory;
import runtime.Handler;

public class Corpse extends Interactable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2358187602203605936L;
	Sprite activeSprite;
	Inventory items;
	
	int timer;
	
	public Corpse(Handler handler, Sprite s, Hitbox h, Inventory i, double x, double y) {
		super(handler);
		this.x = x;
		this.y = y;
		this.hitbox = h;
		this.activeSprite = s;
		this.items = i;
		
		this.xOff = 32;
		this.yOff = 32;
		
	}

	boolean interacting;
	@Override
	public void interact(Object interactor) {
		interacting = true;
		
	}

	@Override
	public void update() {
		super.update();
		if (interacting) {
			timer++;
			if (timer >= 300) {
			items.dropAll();
			this.die();	
			}
		} else {
			timer = 0;
		}
		
		interacting = false;
	}

	@Override
	public void render(DrawGraphics g) {
		activeSprite.render((int) x - handler.getCamera().xOffset() - xOff,
				(int) y - handler.getCamera().yOffset() - yOff, g);
		
		if (timer != 0) {
			for (int i = 0; i < 15; i++) {
				Assets.bTick.render((int) x - handler.getCamera().xOffset() - xOff + i * 2,
						(int) y - handler.getCamera().yOffset() - yOff, g);
				if (i < timer / 20) Assets.pTick.render((int) x - handler.getCamera().xOffset() - xOff + i * 2,
						(int) y - handler.getCamera().yOffset() - yOff, g);
			}
		}
	}
	
	
	/**
	 * 
	 */@Override
	public void load(Handler h) {
		super.load(h);
		items.load(h, null);
	}

}
