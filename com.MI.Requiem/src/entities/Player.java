package entities;

import java.util.ArrayList;
import core.Assets;
import crafting.Craft;
import crafting.Recipe;
import crafting.Tag;
import entity.Entity;
import entity.Hitbox;
import entity.Interactable;
import entity.Mob;
import geometry.Square;
import gfx.DrawGraphics;
import gfx.Sprite;
import gui.ClickListener;
import gui.ContainerButton;
import gui.UIObject;
import interactables.AnvilInteractable;
import interactables.ForgeInteractable;
import interactables.WorktableInteractable;
import item.Inventory;
import item.Item;
import item.ItemContainer;
import items.Bone;
import items.CrystalRod;
import items.Pickaxe;
import items.Torch;
import runtime.Handler;
import world.Tile;

public class Player extends Mob {

	boolean moving = false;

	int wounds;
	int woundMax;
	int timer;

	// Inventory

	ItemContainer<Item> lHand;
	ItemContainer<Item> rHand;

	boolean[] researchFlags;
	boolean[] stationFlags;

	public Player(Handler handler) {
		super(handler);
		this.x = 10 * Tile.tileSize;
		this.y = 10 * Tile.tileSize;
		this.xOff = 32;
		this.yOff = 32;
		this.hitbox = new Hitbox(-22, -8, 10, 10, this, handler);
		activeSprite = Assets.player_idle;

		researchFlags = new boolean[Tag.RESEARCH_MAX_ARRAY];
		researchFlags[Tag.RESEARCH_BASIC_CRAFT] = true;
		stationFlags = new boolean[Tag.STATION_MAX_ARRAY];

		speed = 2;
		health = 6;
		healthMax = 6;
		woundMax = 1;
		wounds = 0;
		spiritMax = 6;
		spirit = 6;

		str = 5;
		agi = 5;
		con = 5;
		wil = 5;
		kno = 5;

		luk = 5;

		// TODO y coordinate relies on width rather than height

		int w = handler.getWidth();
		// int h = handler.getHeight();

		lHand = new ItemContainer<Item>((int) (w / 21.8), w / 5, Assets.inventory_Empty, Assets.inventory_Mainhand,
				"hand mainhand", handler); // previously 44 and 192
		rHand = new ItemContainer<Item>((int) ((w / 21.8) + 40), w / 5, Assets.inventory_Empty,
				Assets.inventory_Offhand, "hand offhand", handler); // previously 84 and 192

		inventory = new Inventory((int) (w / 1.17), w / 60, 9, handler); // previously 824 and 16
		inventory.appendContainer(lHand);
		inventory.appendContainer(rHand);
		inventory.appendContainer(new ItemContainer<Item>((int) (w / 21.8), (int) (w / 3.25), Assets.inventory_Empty,
				Assets.inventory_Head, "head", handler)); // previously 44 and 296
		inventory.appendContainer(new ItemContainer<Item>((int) (w / 21.8) + 40, (int) (w / 3.25),
				Assets.inventory_Empty, Assets.inventory_Body, "body", handler)); // previously 84 and 296
		inventory.appendContainer(new ItemContainer<Item>(w / 40, (int) (w / 3.75), Assets.inventory_Empty,
				Assets.inventory_Trinket, "trinket", handler));// previously 24 and 256
		inventory.appendContainer(new ItemContainer<Item>(w / 40 + 40, (int) (w / 3.75), Assets.inventory_Empty,
				Assets.inventory_Trinket, "trinket", handler));// previously 64 and 256
		inventory.appendContainer(new ItemContainer<Item>(w / 40 + 80, (int) (w / 3.75), Assets.inventory_Empty,
				Assets.inventory_Trinket, "trinket", handler)); // previously 104 and 256

		inventory.add(new Torch(handler, this));
		inventory.add(new Pickaxe(handler, this));
		inventory.add(new Pickaxe(handler, this));
		inventory.add(new Bone(handler, this));
		inventory.add(new Bone(handler, this));
		inventory.add(new Bone(handler, this));
		inventory.add(new Bone(handler, this));
		inventory.add(new Bone(handler, this));
		inventory.add(new Bone(handler, this));
		inventory.add(new CrystalRod(handler, this));
	}

	@Override
	public void update() {
		inventory.update();
		super.update();
		timer++;

		if (timer % 180 == 0 && spirit < spiritMax)
			spirit++;
	}

	@Override
	public void render(DrawGraphics g) {
		super.render(g);

	}

	boolean lastFframe = false;
	boolean lastCframe = false;

	@Override
	public void move() {
		moving = false;
		if (!locked) {
			if (handler.getKeys().w || handler.getKeys().up) {
				if (!hitbox.tileYCollide(-speed))
					y -= speed;
				moving = true;
			}
			if (handler.getKeys().s || handler.getKeys().down) {
				if (!hitbox.tileYCollide(speed))
					y += speed;
				moving = true;
			}
			if (handler.getKeys().a || handler.getKeys().left) {
				if (!hitbox.tileXCollide(-speed))
					x -= speed;
				moving = true;
			}
			if (handler.getKeys().d || handler.getKeys().right) {
				if (!hitbox.tileXCollide(speed))
					x += speed;
				moving = true;
			}
			if (moving) {
				activeSprite = Assets.player_run;

			} else {
				activeSprite = Assets.player_idle;
			}

			if (!handler.getMouse().getDragging()) {
				if (handler.getMouse().getLeft() && lHand.getContained() != null)
					lHand.getContained().use();
				if (handler.getMouse().getRight() && rHand.getContained() != null)
					rHand.getContained().use();
			}
		}

		if (handler.getKeys().c) {
			ArrayList<Entity> col = hitbox.collidingAll();
			boolean interacted = false;
			for (int i = 0; i < col.size(); i++) {
				if (col.get(i) instanceof AnvilInteractable || col.get(i) instanceof ForgeInteractable
						|| col.get(i) instanceof WorktableInteractable) {
					((Interactable) col.get(i)).interact(this);
					interacted = true;
				}
			}
			if (!interacted && !lastCframe) {
				if (craftShowing)
					closeCraft();
				else
					showCraft();
			}
		} else if (handler.getKeys().f) {
			ArrayList<Entity> col = hitbox.collidingAll();
			for (int i = 0; i < col.size(); i++) {
				if (col.get(i) instanceof Interactable) {
					((Interactable) col.get(i)).interact(this);
				}
			}

		}

		lastFframe = handler.getKeys().f;
		lastCframe = handler.getKeys().c;

	}

	@Override
	public void renderUI(DrawGraphics g) {

		int w = handler.getWidth();
		int h = handler.getHeight();

		int posX = w / 60; // previously 16
		int posY = w / 40; // previously 24

		Square s = new Square(w / 6, h, 0xff202020, Sprite.TYPE_GUI_BACKGROUND_SHAPE);
		s.render(0, 0, g);
		s.render(w - w / 6, 0, g);
		int healOff = healthMax / 12;

		int spiCeil = (int) Math.ceil(spiritMax / 12.0);
		int healCeil = (int) Math.ceil(healthMax / 12.0);
		Assets.uiTop.render(posX - 6, posY - 6, g);
		for (int i = 0; i < spiCeil + healCeil + 1; i++) {
			if (i < healCeil + spiCeil)
				Assets.uiMid16.render(posX - 6, posY + i * 16, g);
			else {
				Assets.uiMid20.render(posX - 6, posY + i * 16, g);
				Assets.uiBottom.render(posX - 6, posY + i * 16 + 20, g);
			}
		}

		for (int i = 0; i < healthMax; i++)
			Assets.resContainer.render(posX + 8 * (i % 12), posY + 16 * (i / 12), g);
		for (int i = 0; i < health; i++)
			Assets.life.render(posX + 8 * (i % 12), posY + 16 * (i / 12), g);
		for (int i = 0; i < spiritMax; i++)
			Assets.resContainer.render(posX + 8 * (i % 12), posY + 18 + 16 * (i / 12) + 16 * healOff, g);
		for (int i = 0; i < spirit; i++)
			Assets.spirit.render(posX + 8 * (i % 12), posY + 18 + 16 * (i / 12) + 16 * healOff, g);
		for (int i = 0; i < woundMax; i++) {
			int pos = (int) (i * 24) + posX;
			int pos2 = posY + 16 * (healCeil + spiCeil) + 2;
			if (i >= woundMax - wounds)
				Assets.heartDead.render(pos, pos2, g);
			else
				Assets.heart.render(pos, pos2, g);

		}

		inventory.render(g);

	}

	@Override
	public void die() {
		if (spirit <= 0) super.die();
		if (health <= 0) {
			wounds++;
			if (wounds > woundMax) {
				health = 0;
				super.die();
			} else {
				health = healthMax;
			}
		}
	}

	@Override
	public void equip(Item i) {
		if (inventory.equip(i)) {
			i.setHolder(this);
		}
	}

	@Override
	public boolean pickup(Item i) {
		boolean success = inventory.add(i);
		if (success) {
			i.setHolder(this);
		}
		return success;
	}

	private boolean craftShowing = false;
	private ArrayList<ContainerButton> crafts;

	public void showCraft() {
		craftShowing = true;
		crafts = new ArrayList<ContainerButton>();
		ArrayList<Recipe> recipes = Craft.getRecipes(this);

		for (int i = 0; i < recipes.size(); i++) {
			ContainerButton b = new ContainerButton(setAction(recipes.get(i)), 120 + i * 48, 64, 32, 32,
					recipes.get(i).getResult(this, handler).strip(), handler);
			handler.getUI().addObject(b);
			crafts.add(b);
		}
		lock();
		if (recipes.size() == 0) {
			craftShowing = false;
			unlock();
		}

	}

	public void closeCraft() {
		craftShowing = false;
		unlock();
		for (int i = 0; i < crafts.size(); i++) {
			handler.getUI().removeObject(crafts.get(i));
		}
	}

	private ClickListener setAction(Recipe r) {
		Player p = this;
		return new ClickListener() {

			@Override
			public void onClick(UIObject source) {
				if (r.qualify(p)) {
					Item item = r.craft(p, handler);
					if (!p.pickup(item))
						item.drop();
					closeCraft();
					showCraft();
				}

			}

		};
	}

	public void setResearchFlag(int index, boolean value) {
		researchFlags[index] = value;
	}

	public boolean[] getResearchFlags() {
		return researchFlags;
	}

	public void setStationFlag(int index, boolean value) {
		stationFlags[index] = value;
	}

	public boolean[] getStationFlags() {
		return stationFlags;
	}

	public void resetStationFlags() {
		stationFlags = new boolean[Tag.STATION_MAX_ARRAY];
	}

	public boolean getCraftingShown() {
		return craftShowing;
	}
}
