package entity;

import core.Assets;
import gfx.DrawGraphics;
import item.Inventory;
import item.Item;
import item.ItemContainer;
import items.Cloak;
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

		lHand = new ItemContainer<Item>(44, 192, Assets.inventory_Empty, Assets.inventory_Mainhand, "hand mainhand", handler);
		rHand = new ItemContainer<Item>(84, 192, Assets.inventory_Empty, Assets.inventory_Offhand, "hand offhand", handler);
		
		inventory = new Inventory(824, 16, 9, handler);
		inventory.appendContainer(lHand);
		inventory.appendContainer(rHand);
		inventory.appendContainer(new ItemContainer<Item>(44, 296, Assets.inventory_Empty, Assets.inventory_Head, "head", handler));
		inventory.appendContainer(new ItemContainer<Item>(84, 296, Assets.inventory_Empty, Assets.inventory_Body, "body", handler));
		inventory.appendContainer(new ItemContainer<Item>(24, 256, Assets.inventory_Empty, Assets.inventory_Trinket, "trinket", handler));
		inventory.appendContainer(new ItemContainer<Item>(64, 256, Assets.inventory_Empty, Assets.inventory_Trinket, "trinket", handler));
		inventory.appendContainer(new ItemContainer<Item>(104, 256, Assets.inventory_Empty, Assets.inventory_Trinket, "trinket", handler));

		inventory.add(new Torch(handler, this));
		inventory.add(new Cloak(handler, this));
		inventory.add(new Cloak(handler, this));
		inventory.add(new Spineberry(handler, this));
		inventory.add(new Spineberry(handler, this));
		inventory.add(new Spineberry(handler, this));
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
		
		if (handler.getMouse().getLeft() && lHand.getContained() != null) lHand.getContained().use();
		if (handler.getMouse().getRight() && rHand.getContained() != null) rHand.getContained().use();
	}

	@Override
	public void renderUI(DrawGraphics g) {

		int posX = 16;
		int posY = 24;

		int w = handler.getWidth();
		int h = handler.getHeight();

		g.fillRect(0, 0, w / 6, h, 0xff202020);
		g.fillRect(w - w / 6, 0, w / 6, h, 0xff202020);
		Assets.healthBar.render((int) (posX), (int) (posY), g);
		g.fillRect((int) ((posX + 5)), (int) ((posY + 5)), (int) (118 * health / healthMax), (int) (26), 0xff691920);

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
				this.die();
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
	public void pickup(Item i) {
		if (inventory.add(i)) {
			i.setHolder(this);
		}
	}

}
