package entity;

import core.Assets;
import gfx.DrawGraphics;
import runtime.Handler;
import runtime.Light;
import world.Tile;

public class Player extends Mob {

	boolean moving = false;
	double speed = Tile.tileSize / 8;

	int wounds;
	int woundMax;

	Light lamp;

	public Player(Handler handler) {
		this.x = 10 * Tile.tileSize;
		this.y = 10 * Tile.tileSize;
		this.xOff = (int) (32);
		this.yOff = (int) (32);
		this.hitbox = new Hitbox(-22, -8, 10, 10, this, handler);
		this.handler = handler;
		activeSprite = Assets.player_still;

		health = 75;
		healthMax = 100;
		woundMax = 1;
		wounds = 0;

		lamp = new Light(256, 0xffffffAA, handler);
		lamp.light();
		
		Light torch = new Light(128, 0xffff00ff, handler);
		torch.setPos(10 * Tile.tileSize, 10 * Tile.tileSize);
		torch.light();
	}

	@Override
	public void update() {
		lamp.setPos((int) x - xOff / 2, (int) y - xOff / 2);
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

}
