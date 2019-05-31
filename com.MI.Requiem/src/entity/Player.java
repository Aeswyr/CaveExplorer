package entity;

import java.util.ArrayList;

import core.Assets;
import geometry.Square;
import gfx.DrawGraphics;
import gfx.Sprite;
import item.Inventory;
import item.Item;
import item.ItemContainer;
import items.Cloak;
import items.Pickaxe;
import items.Spineberry;
import items.Torch;
import runtime.Handler;
import world.Tile;

public class Player extends Mob {

	boolean moving = false;

	int wounds;
	int woundMax;

	// Inventory
	Inventory inventory;
	ItemContainer<Item> lHand;
	ItemContainer<Item> rHand;

	public Player(Handler handler) {
		this.x = 10 * Tile.tileSize;
		this.y = 10 * Tile.tileSize;
		this.xOff = 32;
		this.yOff = 32;
		this.hitbox = new Hitbox(-22, -8, 10, 10, this, handler);
		this.handler = handler;
		activeSprite = Assets.player_still;

		speed = 2;
		health = 75;
		healthMax = 100;
		woundMax = 1;
		wounds = 0;

		int w = handler.getWidth();
		int h = handler.getHeight();

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
		inventory.add(new Cloak(handler, this));
		inventory.add(new Cloak(handler, this));
		inventory.add(new Spineberry(handler, this));
		inventory.add(new Spineberry(handler, this));
		inventory.add(new Spineberry(handler, this));
		inventory.add(new Pickaxe(handler, this));
	}

	@Override
	public void update() {
		inventory.update();
		super.update();
		if (health < healthMax)
			heal(healthMax / (5 * 60 * 60));
	}

	@Override
	public void render(DrawGraphics g) {
		super.render(g);

	}

	@Override
	public void move() {
		moving = false;
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
			activeSprite = Assets.player_still;
		}

		if (handler.getMouse().getLeft() && lHand.getContained() != null)
			lHand.getContained().use();
		if (handler.getMouse().getRight() && rHand.getContained() != null)
			rHand.getContained().use();
		if (handler.getKeys().f) {
			ArrayList<Entity> col = hitbox.collidingAll();
			for (int i = 0; i < col.size(); i++) {
				if (col.get(i) instanceof Interactable) {
					((Interactable)col.get(i)).interact(this);
				}
			}
		}
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

		Square hp = new Square((int) (118 * health / healthMax), (int) (26), 0xff691920,
				Sprite.TYPE_GUI_FOREGROUND_SHAPE);
		Assets.healthBar.render((int) (posX), (int) (posY), g);
		hp.render(posX + 5, posY + 5, g);

		for (int i = 0; i < woundMax; i++) {
			int pos = (int) (i * 24) + posX;
			int pos2 = 35 + posY;
			if (i == 0)
				Assets.heartContainer_Left.render((int) ((10 + pos)), (int) (pos2), g);
			else
				Assets.heartContainer_Mid.render((int) ((10 + pos)), (int) (pos2), g);
			Assets.heartContainer_Mid.render((int) ((10 + pos + 8)), (int) (pos2), g);
			if (i == woundMax - 1)
				Assets.heartContainer_Right.render((int) ((10 + pos + 16)), (int) (pos2), g);
			else
				Assets.heartContainer_Mid.render((int) ((10 + pos + 16)), (int) (pos2), g);

			if (i >= woundMax - wounds)
				Assets.heartDead.render((int) ((12 + pos)), (int) ((pos2 + 3)), g);
			else
				Assets.heart.render((int) ((12 + pos)), (int) ((pos2 + 3)), g);

		}

		inventory.render(g);

	}

	@Override
	public void harm(double amount) {
		health -= amount;
		if (health <= 0) {
			wounds++;
			if (wounds > woundMax) {
				health = 0;
				this.die();
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

}
