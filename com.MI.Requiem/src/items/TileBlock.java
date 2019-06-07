package items;

import core.Assets;
import entity.Mob;
import gfx.DrawGraphics;
import item.Item;
import runtime.Handler;

public class TileBlock extends Item {

	int id;

	public TileBlock(int x, int y, Handler handler, int id) {
		super(x, y, handler);
		this.id = id;

		tags = "hand";
		ID = "0:" + id;

		useTime = 30;
		timer = useTime;

		switch (id) {
		case 1:
			this.sprite = Assets.dirt;
			this.invSprite = Assets.dirt_inv;
			break;
		default:
			break;
		}
	}

	public TileBlock(Handler handler, Mob holder, int id) {
		super(handler, holder);
		this.id = id;
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

			if (dx * dx + dy * dy < 4096 && !handler.getWorld().getTile(mouseX, mouseY).isSolid()) {
				handler.getWorld().setTile(mouseX, mouseY, id);
				timer = 0;
				consumed = true;
			}
		}
	}

}
