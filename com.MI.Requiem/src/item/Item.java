package item;

import java.util.ArrayList;
import java.util.Random;

import effects.Effect;
import entities.Player;
import entity.Entity;
import entity.Hitbox;
import entity.Interactable;
import entity.Mob;
import geometry.Rect;
import gfx.DrawGraphics;
import gfx.Sprite;
import gfx.SpriteData;
import items.Anvil;
import items.Bone;
import items.Cloak;
import items.Crate;
import items.CrystalRod;
import items.Forge;
import items.Gem;
import items.Ingot;
import items.Mold;
import items.Ore;
import items.Pickaxe;
import items.Spineberry;
import items.TheOrb;
import items.TileBlock;
import items.Torch;
import items.Worktable;
import runtime.Handler;
import utility.Storeable;

/**
 * base for all items in the game
 * 
 * @author Pascal
 *
 */
public abstract class Item extends Interactable implements Storeable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5707511923118175296L;
	/*
	 * Tag list head - an item that can be used in the head slot body - an item that
	 * can be used in the body slot trinket - an item that can be used in any
	 * trinket slot hand - an item that can be placed in a hand slot mainhand - an
	 * item that can be placed in only the main hand offhand - an item that can be
	 * placed in only the off hand
	 */
	protected String tags, ID;
	protected Sprite invSprite, sprite;
	transient protected Entity holder;
	protected boolean equipped = false;
	protected boolean consumed = false;
	protected int useTime = 0;
	protected int timer;
	protected String name;

	protected boolean stackable = true;

	protected int useMax = 1;
	protected int use = useMax;

	protected int[] statPackage = new int[9];
	public static final int ITEM_WEIGHT = 0;
	public static final int ITEM_DURABILITY = 1;
	public static final int ITEM_ARMOR = 2;
	public static final int ITEM_DAMAGE = 3;
	public static final int ITEM_CAPACITY = 4;
	public static final int ITEM_SPEED = 5;
	public static final int ITEM_HEALTH = 6;
	public static final int ITEM_SPIRIT = 7;
	public static final int ITEM_LUCK = 8;
	protected static Random rng = new Random();

	/**
	 * initializes an item within an inventory
	 * 
	 * @param handler
	 * @param holder  - the mob who's inventory holds the item
	 */
	public Item( Mob holder) {
		this.holder = holder;
		setup();
	}

	/**
	 * initializes an item within the world
	 * 
	 * @param x       - x position of the item
	 * @param y       - y position of the item
	 * @param handler
	 */
	public Item(int x, int y) {
		this.x = x;
		this.y = y;
		this.holder = null;
		this.hitbox = new Hitbox(6, 6, 20, 20, this);
		hitbox.updatePos(x, y);
		setup();
	}

	/**
	 * defines the unique functions of the item which must be set up no mater where
	 * it's initialized
	 */
	protected abstract void setup();

	@Override
	public void render(DrawGraphics g) {
		sprite.render((int) x - Handler.getCamera().xOffset(), (int) y - Handler.getCamera().yOffset(), g);
//		if (hitbox != null) hitbox.render(g);
	}

	@Override
	public void renderInventory(int x, int y, DrawGraphics g) {
		invSprite.render(x, y, g);
		if (use > 0 && use != useMax) {
			g.drawRect(x + 3, y + 24, (int) (27.0 * use / useMax), 4, SpriteData.TYPE_GUI_ITEM_SHAPE, 0xFFFFFF00);
		}
	}

	@Override
	public void renderTextBox(int x, int y, DrawGraphics g) {
		g.write(name, x - 36, y, 0xffffffff);
		int count = 1;
		String str = null;
		for (int i = 0; i < statPackage.length; i++) {
			if (statPackage[i] != 0) {
				switch (i) {
				case ITEM_WEIGHT:
					str = statPackage[i] + " lbs";
					break;
				case ITEM_DURABILITY:
					str = "";
					count--;
					break;
				case ITEM_ARMOR:
					str = "Armor: " + statPackage[i];
					break;
				case ITEM_DAMAGE:
					str = "Damage: " + statPackage[i];
					break;
				case ITEM_CAPACITY:
					str = "Storage: " + statPackage[i];
					break;
				case ITEM_SPEED:
					str = "Speed: " + statPackage[i];
					break;
				case ITEM_HEALTH:
					str = "Health: " + statPackage[i];
					break;
				case ITEM_SPIRIT:
					str = "Spirit: " + statPackage[i];
					break;
				case ITEM_LUCK:
					str = "Luck: " + statPackage[i];
					break;
				}
				g.write(str, x - 36, y + count * 12);
				count++;
			}
		}

	}

	@Override
	public void interact(Object interactor) {
		if (interactor instanceof Player) {
			if (((Player) interactor).pickup(this))
				die();
		}
	}

	/**
	 * contains functions of the item when it is used from a hand slot
	 */
	public abstract void use();

	/**
	 * sets which mob is holding this item
	 * 
	 * @param m - the mob who will hold the item
	 */
	public void setHolder(Entity e) {
		this.holder = e;
		this.hitbox = null;
	}

	/**
	 * applies item stats to the holder mob and sets the item as equipped
	 */
	public void onEquip() {
		this.equipped = true;
		if (holder != null)
			applyStats();

	}

	/**
	 * removes stats from the holder mob and sets the item as not equipped
	 */
	public void onDequip() {
		this.equipped = false;
		if (holder != null)
			removeStats();
	}

	@Override
	public String getTags() {
		return tags;
	}

	/**
	 * @returns true if this item is equipped, false otherwise
	 */
	public boolean equipped() {
		return equipped;
	}

	@Override
	public boolean canStack(Storeable s) {
		if (s instanceof Item && ((Item) s).ID.equals(this.ID) && this.stackable)
			return true;
		return false;
	}

	/**
	 * sets whether or not this item has been consumed
	 * 
	 * @param b - true if this item has been consumed, false otherwise
	 */
	public void setConsumed(boolean b) {
		this.consumed = b;
	}

	/**
	 * adds this item to the world at the feet of the mob who was holding it
	 */
	public void drop() {
		this.hitbox = new Hitbox(6, 6, 20, 20, this);
		if (holder != null) {
			x = (int) (holder.getCenteredX() + Math.random() * 32 - 16);
			y = (int) (holder.getCenteredY() + Math.random() * 32 - 16);
			holder = null;
			Handler.getEntityManager().addEntity(this);
		}
		hitbox.updatePos((int) x, (int) y);
	}

	@Override
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
	 * 
	 * @param id      - the id of the desired item
	 * @param holder  - the mob who's inventory will contain the new item
	 * @param handler
	 * 
	 * @returns the new item based on the input id
	 */
	public static Item toItem(String id, Mob holder) {
		String[] sec = id.split(":");
		switch (Integer.parseInt(sec[0])) {
		case 0:
			switch (Integer.parseInt(sec[1])) {
			case 2:
				return new Anvil( holder);
			case 3:
				return new Forge(holder);
			case 10:
				return new Worktable(holder);
			case 24:
				return new Crate(holder);
			default:
				return new TileBlock(holder, Integer.parseInt(sec[1]));
			}
		case 1:
			return new Ore( holder, Integer.parseInt(sec[1]));
		case 2:
			return new Gem(holder, Integer.parseInt(sec[1]));
		case 3:
			return new Ingot( holder, Integer.parseInt(sec[1])); // Ingot
		case 4:
			return new Spineberry( holder);
		case 5:
			return new Mold( holder);
		case 6:
			return new Torch(holder);
		case 7:
			return new Cloak( holder);
		case 8:
			return new Pickaxe( holder);
		case 9:
			return new TheOrb( holder);
		case 10:
			return new Bone( holder);
		case 11:
			return new CrystalRod(holder);
		default:
			return new TileBlock(holder, 1); // default returns dirt block
		}
	}

	/**
	 * @returns the stat adjustments that this item grants
	 */
	public int[] getStatPackage() {
		return statPackage;
	}

	/**
	 * edits this item's package of stats
	 * 
	 * @param edit - an array containing the adjustment values to be added to this
	 *             item's stat package
	 */
	public void editStatPackage(int[] edit) {
		if (holder != null)
			removeStats();
		for (int i = 0; i < statPackage.length; i++) {
			statPackage[i] += edit[i];
		}
		if (holder != null)
			applyStats();

	}

	/**
	 * applies the stats of all items used to craft this one. Also can do things like change the item's name.
	 * @param items - the list of items to pull data from
	 */
	public void craft(ArrayList<Item> items) {
		for (Item i : items) {
			editStatPackage(i.statPackage);
		}
	}
	
	/**
	 * cleanup operations for stat application when an item is first generated
	 */
	public void finalize() {
		this.use += statPackage[ITEM_DURABILITY];
		this.useMax += statPackage[ITEM_DURABILITY];
	}

	/**
	 * applies stat adjustments from the item to the carrying mob
	 */
	protected void applyStats() {
		if (this.holder instanceof Mob) {
			Mob holder = (Mob) this.holder;
			holder.adjArmor((int) statPackage[ITEM_ARMOR]);
			holder.adjInv((int) statPackage[ITEM_CAPACITY]);
			holder.adjMaxhp((int) statPackage[ITEM_HEALTH]);
			holder.adjMaxsp((int) statPackage[ITEM_SPIRIT]);
			holder.adjSpeed((int) statPackage[ITEM_SPEED] / 4.0);
			holder.adjLuck((int) statPackage[ITEM_LUCK]);
		}
	}

	/**
	 * removes stat adjustments of the item from the carrying mob
	 */
	protected void removeStats() {
		if (this.holder instanceof Mob) {
			Mob holder = (Mob) this.holder;
			holder.adjArmor(-(int) statPackage[ITEM_ARMOR]);
			holder.adjInv(-(int) statPackage[ITEM_CAPACITY]);
			holder.adjMaxhp(-(int) statPackage[ITEM_HEALTH]);
			holder.adjMaxsp(-(int) statPackage[ITEM_SPIRIT]);
			holder.adjSpeed(-(int) statPackage[ITEM_SPEED] / 4.0);
			holder.adjLuck(-(int) statPackage[ITEM_LUCK]);
		}
	}

	/**
	 * @returns whether or not this item can stack with other items
	 */
	public boolean getStackable() {
		return stackable;
	}

	@Override
	public Sprite getAsset() {
		return this.invSprite;
	}

	/**
	 * removes all non-essential parts of the item resulting in a skeleton
	 */
	public Item strip() {
		return this;
	}

	/**
	 * initializes this item if it is held
	 * 
	 * @param m - the new holder of this item
	 */
	public void load( Entity e) {
		this.setHolder(e);
		this.setup();
		if (equipped && e instanceof Mob) {
			removeStats();
			this.onEquip();
			((Mob)e).heal(statPackage[ITEM_HEALTH], Effect.DAMAGE_TYPE_ENERGY);
		}
	}
}
