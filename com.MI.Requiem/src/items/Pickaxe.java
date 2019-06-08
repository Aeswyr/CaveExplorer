package items;

import java.util.ArrayList;

import core.Assets;
import entity.Mob;
import gfx.DrawGraphics;
import item.Item;
import runtime.Handler;

public class Pickaxe extends Item {

	public Pickaxe(Handler handler, Mob holder) {
		super(handler, holder);

		ID = "u2";
		tags = "mainhand";

		useMax = 128;
		use = useMax;
		useTime = 40;
		timer = useTime;

		sprite = Assets.pickaxe;
		invSprite = Assets.pickaxe_inv;
	}
	
	public Pickaxe(int x, int y, Handler handler) {
		super(x, y, handler);
	
		ID = "u2";
		tags = "mainhand";

		useMax = 128;
		use = useMax;
		useTime = 40;
		timer = useTime;

		sprite = Assets.pickaxe;
		invSprite = Assets.pickaxe_inv;
		
	}

	@Override
	public void renderTextBox(int x, int y, DrawGraphics g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void use() {
		if (equipped && timer >= useTime) {

			int holderX = holder.getCenteredX();
			int holderY = holder.getY() - 8;

			int mouseX = handler.getCamera().xOffsetAdj() + handler.getMouse().getAdjX() - handler.getWidth() / 2;
			int mouseY = handler.getCamera().yOffsetAdj() + handler.getMouse().getAdjY() - handler.getHeight() / 2;

			int dx = Math.abs(mouseX - holderX);
			int dy = Math.abs(mouseY - holderY);

			int sx = holderX < mouseX ? 1 : -1;
			int sy = holderY < mouseY ? 1 : -1;

			int err = dx - dy;
			int e2;

			while (((holderX - holder.getCenteredX()) * (holderX - holder.getCenteredX())
					+ (holderY - holder.getCenteredY()) * (holderY - holder.getCenteredY())) < 4096) {

				if (handler.getWorld().getTile(holderX, holderY).isBreakable()) {
					
					// on tile break
					ArrayList<Item> drops = handler.getWorld().getTile(holderX, holderY).tileDrop(holderX - 16, holderY - 18, handler);
					handler.getWorld().setTile(holderX, holderY, handler.getWorld().getTileID(holderX, holderY) - 1);
					for (int i = 0; i < drops.size(); i++) {
						handler.getWorld().getEntities().addEntity(drops.get(i));
					}
					// end tile break
					
					// always do
					Assets.pickaxe1.play();
					use--;
					timer = 0;
					break;
					// end
				}

				if (holderX == mouseX && holderY == mouseY)
					break;
				e2 = 2 * err;
				if (e2 > -1 * dy) {
					err -= dy;
					holderX += sx;
				}
				if (e2 < dx) {
					err += dx;
					holderY += sy;
				}

			}


		}

	}

	@Override
	public void update() {
		if (timer < useTime)
			timer++;
		if (use <= 0)
			this.consumed = true;

	}

}
