package items;

import java.util.ArrayList;
import core.Assets;
import entity.Mob;
import geometry.Rect;
import gfx.Sprite;
import item.Item;
import particle.Particle;
import runtime.Handler;
import world.Tile;

public class Pickaxe extends Item {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4425233684360263139L;

	public Pickaxe(Handler handler, Mob holder) {
		super(handler, holder);
	}

	public Pickaxe(int x, int y, Handler handler) {
		super(x, y, handler);
	}

	@Override
	protected void setup() {
		ID = "8";
		tags = "mainhand";
		name = "Pickaxe";

		statPackage[ITEM_WEIGHT] = 4;

		useMax = 128;
		use = useMax;
		useTime = 40;
		timer = useTime;

		sprite = Assets.pickaxe;
		invSprite = Assets.pickaxe_inv;

		stackable = false;
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

				if (handler.getWorld().getTile(holderX, holderY).isBreakable()
						|| (handler.getWorld().getOverlay(holderX, holderY) != null
								&& handler.getWorld().getOverlay(holderX, holderY).isBreakable())) {

					// on tile break
					ArrayList<Item> drops = null;
					if (handler.getWorld().getOverlayID(holderX, holderY) >= 0
							&& handler.getWorld().getOverlay(holderX, holderY).isBreakable())
						drops = handler.getWorld().getOverlay(holderX, holderY).tileDrop(holderX - 16, holderY - 18,
								handler);
					else if (handler.getWorld().getTile(holderX, holderY).isBreakable())
						drops = handler.getWorld().getTile(holderX, holderY).tileDrop(holderX - 16, holderY - 18,
								handler);
					if (handler.getWorld().getOverlayID(holderX, holderY) == 3
							|| handler.getWorld().getOverlayID(holderX, holderY) == 4) { // When tile is forge
						if (handler.getWorld().getOverlayID(holderX, holderY) == 3) {
							handler.getWorld().setOverlay(holderX, holderY, -1);
							handler.getWorld().setOverlay(holderX + Tile.tileSize, holderY, -1);

						} else {
							handler.getWorld().setOverlay(holderX, holderY, -1);
							handler.getWorld().setOverlay(holderX - Tile.tileSize, holderY, -1);

						}

					} else if (handler.getWorld().getOverlayID(holderX, holderY) == 2) { // When tile is anvil
						handler.getWorld().setOverlay(holderX, holderY, -1);

					} else if (handler.getWorld().getOverlayID(holderX, holderY) >= 0
							&& handler.getWorld().getOverlay(holderX, holderY).isBreakable()) {
						handler.getWorld().setOverlay(holderX, holderY, -1);
					} else
						handler.getWorld().setTile(holderX, holderY,
								Tile.tileToFloor(handler.getWorld().getTileID(holderX, holderY)));

					for (int i = 0; i < drops.size(); i++) {
						handler.getWorld().getEntities().addEntity(drops.get(i));
					}
					// end tile break

					// always do
					new Particle(new Rect(1, 1, 0xffffff00, Sprite.TYPE_ITEM_DROP).toSprite(), 8, holderX, holderY, holderX + 5, holderY + 5, handler,
							new Particle.Behavior() {
								int[][] move;

								@Override
								public void initial(int x, int y, int x0, int y0, int[][] data) {
									move = new int[data.length][2];
									for (int i = 0; i < data.length; i++) {
										data[i][0] = x;
										data[i][1] = y;
										data[i][2] = (int) (8 - Math.random() * 5);

										move[i][0] = (int) (3 - Math.random() * 6);
										move[i][1] = (int) (3 - Math.random() * 6);

									}
								}

								@Override
								public void update(int x, int y, int x0, int y0, int[] data, int index) {
									data[0] += move[index][0];
									data[1] += move[index][1];
								}

							}).start();
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
