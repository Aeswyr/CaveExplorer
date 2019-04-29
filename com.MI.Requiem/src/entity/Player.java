package entity;

import java.awt.Graphics;

import core.Assets;
import runtime.Handler;

public class Player extends Mob {

	boolean moving = false;
	double speed = 4;
	Handler handler;

	public Player(Handler handler) {
		this.handler = handler;
		activeSprite = Assets.player_still;
	}

	@Override
	public void update() {
		moving = false;
		if (handler.getKeys().w || handler.getKeys().up) {
			y -= speed;
			moving = true;
		}
		if (handler.getKeys().s || handler.getKeys().down) {
			y += speed;
			moving = true;
		}
		if (handler.getKeys().a || handler.getKeys().left) {
			x -= speed;
			moving = true;
		}
		if (handler.getKeys().d || handler.getKeys().right) {
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
	public void render(Graphics g) {
		activeSprite.render((int) x - handler.getCamera().xOffset(), (int) y - handler.getCamera().yOffset(), g);

	}

	@Override
	public void renderUI(Graphics g) {
		// TODO Auto-generated method stub

	}

}
