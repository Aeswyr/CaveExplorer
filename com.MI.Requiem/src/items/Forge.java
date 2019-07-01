package items;

import core.Assets;
import entity.Mob;
import gfx.DrawGraphics;
import item.Item;
import runtime.Handler;
import world.Tile;

public class Forge extends Item {

	public Forge(int x, int y, Handler handler) {
		super(x, y, handler);

		tags = "hand";
		ID = "0:3&4";

		useTime = 30;
		timer = useTime;
		
		this.sprite = Assets.forge_inv;
		this.invSprite = Assets.forge_inv;
	}

	public Forge(Handler handler, Mob holder) {
		super(handler, holder);
		
		tags = "hand";
		ID = "0:3&4";

		useTime = 30;
		timer = useTime;
		
		this.sprite = Assets.forge_inv;
		this.invSprite = Assets.forge_inv;
	}

	@Override
	public void renderTextBox(int x, int y, DrawGraphics g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void use() {
		if (!consumed && timer >= useTime) {
			int holderX = holder.getCenteredX();
			int holderY = holder.getY() - 8;

			int mouseX = handler.getCamera().xOffsetAdj() + handler.getMouse().getAdjX() - handler.getWidth() / 2;
			int mouseY = handler.getCamera().yOffsetAdj() + handler.getMouse().getAdjY() - handler.getHeight() / 2;

			int dx = mouseX - holderX;
			int dy = mouseY - holderY;

			if (dx * dx + dy * dy < 4096 && !handler.getWorld().getTile(mouseX, mouseY).isSolid()
					&& !handler.getWorld().getTile(mouseX + Tile.tileSize, mouseY).isSolid()) {
				handler.getWorld().setOverlay(mouseX, mouseY, 3);
				handler.getWorld().setOverlay(mouseX + Tile.tileSize, mouseY, 4);
				timer = 0;
				consumed = true;
			}

		}
	}

}
