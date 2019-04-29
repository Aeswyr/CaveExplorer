package entity;

import java.awt.Graphics;

import core.Assets;
import runtime.Handler;
import world.Tile;

public class Player extends Mob {

	boolean moving = false;
	double speed = Tile.tileSize / 8;
	Handler handler;

	public Player(Handler handler) {
		this.hitbox = new Hitbox(10, 22, 10, 10, this, handler);
		this.handler = handler;
		activeSprite = Assets.player_still;
	}

	@Override
	public void update() {
		hitbox.update();
		move();
	}
	
	@Override
	public void move() {
		moving = false;
		if (handler.getKeys().w || handler.getKeys().up) {
			if (!hitbox.tileYCollide(-speed)) y -= speed;
			moving = true;
		}
		if (handler.getKeys().s || handler.getKeys().down) {
			if (!hitbox.tileYCollide(speed)) y += speed;
			moving = true;
		}
		if (handler.getKeys().a || handler.getKeys().left) {
			if (!hitbox.tileXCollide(-speed)) x -= speed;
			moving = true;
		}
		if (handler.getKeys().d || handler.getKeys().right) {
			if (!hitbox.tileXCollide(speed)) x += speed;
			moving = true;
		}
		if (moving) {
			activeSprite = Assets.player_run;
		} else {
			activeSprite = Assets.player_still;
		}
	}

	@Override
	public void render(Graphics g) {
		activeSprite.render((int) x - handler.getCamera().xOffset(), (int) y - handler.getCamera().yOffset(), g);

	}

	@Override
	public void renderUI(Graphics g) {
		g.fillRect(0, 0, handler.getWidth() / 6, handler.getHeight());
		g.fillRect(handler.getWidth() - handler.getWidth() / 6, 0, handler.getWidth() / 6, handler.getHeight());

	}

}
