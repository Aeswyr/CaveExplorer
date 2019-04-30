package entity;

import java.awt.Color;
import java.awt.Graphics;

import core.Assets;
import core.Driver;
import runtime.Handler;
import world.Tile;

public class Player extends Mob {

	boolean moving = false;
	double speed = Tile.tileSize / 8;

	int wounds;
	int woundMax;

	public Player(Handler handler) {
		this.x = 10 * Tile.tileSize;
		this.y = 10 * Tile.tileSize;
		this.xOff = (int) (32 * Driver.scale);
		this.yOff = (int) (32 * Driver.scale);
		this.hitbox = new Hitbox(-22, -8, 10, 10, this, handler);
		this.handler = handler;
		activeSprite = Assets.player_still;

		health = 75;
		healthMax = 100;
		woundMax = 1;
		wounds = 0;
	}

	@Override
	public void update() {
		super.update();
		if (health < healthMax)
			heal(healthMax / (5 * 60 * 60));
	}

	@Override
	public void render(Graphics g) {
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
	public void renderUI(Graphics g) {

		int posX = 16;
		int posY = 24;

		g.fillRect(0, 0, handler.getWidth() / 6, handler.getHeight());
		g.fillRect(handler.getWidth() - handler.getWidth() / 6, 0, handler.getWidth() / 6, handler.getHeight());
		Assets.healthBar.render((int) (posX * Driver.scale), (int) (posY * Driver.scale), g);
		g.setColor(new Color(105, 25, 32));
		g.fillRect((int) ((posX + 5) * Driver.scale), (int) ((posY + 5) * Driver.scale),
				(int) (118 * Driver.scale * health / healthMax), (int) (26 * Driver.scale));
		g.setColor(Color.BLACK);

		for (int i = 0; i < woundMax; i++) {
			int pos = (int) (i * 24) + posX;
			int pos2 = 35 + posY;
			if (i == 0)
				Assets.heartContainer_Left.render((int) ((10 + pos) * Driver.scale), (int) (pos2 * Driver.scale), g);
			else
				Assets.heartContainer_Mid.render((int) ((10 + pos) * Driver.scale), (int) (pos2 * Driver.scale), g);
			Assets.heartContainer_Mid.render((int) ((10 + pos + 8) * Driver.scale), (int) (pos2 * Driver.scale), g);
			if (i == woundMax - 1)
				Assets.heartContainer_Right.render((int) ((10 + pos + 16) * Driver.scale), (int) (pos2 * Driver.scale),
						g);
			else
				Assets.heartContainer_Mid.render((int) ((10 + pos + 16) * Driver.scale), (int) (pos2 * Driver.scale),
						g);

			if (i >= woundMax - wounds)
				Assets.heartDead.render((int) ((12 + pos) * Driver.scale), (int) ((pos2 + 3) * Driver.scale), g);
			else
				Assets.heart.render((int) ((12 + pos) * Driver.scale), (int) ((pos2 + 3) * Driver.scale), g);

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
