package item;

import entities.Player;
import entity.Hitbox;
import entity.Interactable;
import entity.Mob;
import geometry.Square;
import gfx.DrawGraphics;
import gfx.Sprite;
import items.Gem;
import items.Ingot;
import items.Ore;
import items.Spineberry;
import items.TileBlock;
import runtime.Handler;
import utility.Storeable;

public abstract class Item extends Interactable implements Storeable, Cloneable {

	/*
	 * Tag list head - an item that can be used in the head slot body - an item that
	 * can be used in the body slot trinket - an item that can be used in any
	 * trinket slot hand - an item that can be placed in a hand slot mainhand - an
	 * item that can be placed in only the main hand offhand - an item that can be
	 * placed in only the off hand
	 */
	protected String tags, ID;
	protected Sprite invSprite, sprite;
	protected Mob holder;
	protected boolean equipped = false;
	protected boolean consumed = false;
	protected int useTime = 0;
	protected int timer;

	protected int useMax = 1;
	protected int use = useMax;
	
	protected double[] statPackage = new double[9];
	public static final int ITEM_WEIGHT = 0;
	public static final int ITEM_DURABILITY = 1;
	public static final int ITEM_ARMOR = 2;
	public static final int ITEM_DAMAGE = 3;
	public static final int ITEM_CAPACITY = 4;
	public static final int ITEM_SPEED = 5;
	public static final int ITEM_HEALTH = 6;
	public static final int ITEM_SPIRIT = 7;
	public static final int ITEM_LUCK = 8;

	public Item(Handler handler, Mob holder) {
		super(handler);
		this.holder = holder;
		setup();
	}

	public Item(int x, int y, Handler handler) {
		super(handler);
		this.x = x;
		this.y = y;
		this.holder = null;
		this.hitbox = new Hitbox(6, 6, 20, this, handler);
		hitbox.updatePos(x, y);
		setup();
	}

	protected abstract void setup();

	public void render(DrawGraphics g) {
		sprite.render((int) x - handler.getCamera().xOffset(), (int) y - handler.getCamera().yOffset(), g);
//		if (hitbox != null) hitbox.render(g);
	}

	public void renderInventory(int x, int y, DrawGraphics g) {
		invSprite.render(x, y, g);
		if (use > 0 && use != useMax) {
			Square s = new Square((int) (27.0 * use / useMax), 4, 0xFFFFFF00, Sprite.TYPE_GUI_ITEM_SHAPE);
			s.render(x + 3, y + 24, g);
		}
	}

	@Override
	public void interact(Object interactor) {
		if (interactor instanceof Player) {
			if (((Player) interactor).pickup(this))
				die();
		}
	}

	public abstract void use();

	public void setHolder(Mob m) {
		this.holder = m;
		this.hitbox = null;
	}

	public void onEquip() {
		this.equipped = true;
		applyStats();
		
	}

	public void onDequip() {
		this.equipped = false;
		removeStats();
	}

	public String getTags() {
		return tags;
	}

	public boolean equipped() {
		return equipped;
	}

	public boolean canStack(Storeable s) {
		if (s instanceof Item && ((Item) s).ID.equals(this.ID) && this.ID.charAt(0) != 'u')
			return true;
		return false;
	}

	public void setConsumed(boolean b) {
		this.consumed = b;
	}

	public void drop() {
		this.hitbox = new Hitbox(6, 6, 20, this, handler);
		if (holder != null) {
			x = holder.getCenteredX() + Math.random() * 32 - 16;
			y = holder.getCenteredY() + Math.random() * 32 - 16;
			holder = null;
			handler.getWorld().getEntities().addEntity(this);
		}
		hitbox.updatePos((int) x, (int) y);
	}

	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public void update() {
		if (timer < useTime)
			timer++;
	}

	/**
	 * converts an id to an item
	 */
	public static Item toItem(String id, Mob holder, Handler handler) {
		String[] sec = id.split(":");
		switch (sec[0]) {
		case "0":
			return new TileBlock(handler, holder, Integer.parseInt(sec[1]));
		case "1":
			return new Ore(handler, holder, Integer.parseInt(sec[1]));
		case "2":
			return new Gem(handler, holder, Integer.parseInt(sec[1]));
		case "3":
			return new Ingot(handler, holder, Integer.parseInt(sec[1])); // Ingot
		case "4":
			return new Spineberry(handler, holder);
		default:
			return new TileBlock(handler, holder, 1); // default returns dirt block
		}
	}
	
	
	public double[] getStatPackage() {
		return statPackage;
	}
	
	public void editStatPackage(double[] edit) {
		if (holder != null) removeStats();
		for (int i = 0; i < statPackage.length; i++) {
			statPackage[i] += edit[i];
		}
		if (holder != null) applyStats();
	}
	
	protected void applyStats() { //TODO actually write methods to apply stats to mobs
		
	}
	
	protected void removeStats() { //TODO actually write methods to apply stats to mobs
		
	}
}
