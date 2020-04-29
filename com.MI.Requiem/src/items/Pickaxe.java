package items;

import java.util.ArrayList;
import core.Assets;
import entity.Mob;
import geometry.Rect;
import gfx.SpriteData;
import input.Controller;
import item.Item;
import particle.Particle;
import runtime.Handler;
import world.Tile;
import world.World;

public class Pickaxe extends Item {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4425233684360263139L;

	public Pickaxe(Mob holder) {
		super(holder);
	}

	public Pickaxe(int x, int y) {
		super(x, y);
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
			int holderY = holder.getCenteredY();

			int mouseX = Handler.getCamera().xOffsetAdj() + Controller.getAdjX() - Handler.getWidth() / 2;
			int mouseY = Handler.getCamera().yOffsetAdj() + Controller.getAdjY() - Handler.getHeight() / 2;

			int dx = Math.abs(mouseX - holderX);
			int dy = Math.abs(mouseY - holderY);

			int sx = holderX < mouseX ? 1 : -1;
			int sy = holderY < mouseY ? 1 : -1;

			int err = dx - dy;
			int e2;

			while (((holderX - holder.getCenteredX()) * (holderX - holder.getCenteredX())
					+ (holderY - holder.getCenteredY()) * (holderY - holder.getCenteredY())) < 4096) {

				World w = (World) Handler.getLoadedWorld();

				if (w.getTile(holderX, holderY, World.MAP_BASE).isBreakable()
						|| (w.getTile(holderX, holderY, World.MAP_OVERLAY) != null
								&& w.getTile(holderX, holderY, World.MAP_OVERLAY).isBreakable())) {

					// on tile break
					ArrayList<Item> drops = null;
					if (w.getTileID(holderX, holderY, World.MAP_OVERLAY) >= 0
							&& w.getTile(holderX, holderY, World.MAP_OVERLAY).isBreakable())
						drops = w.getTile(holderX, holderY, World.MAP_OVERLAY).tileDrop(holderX - 16, holderY - 18);
					else if (w.getTile(holderX, holderY, World.MAP_BASE).isBreakable())
						drops = w.getTile(holderX, holderY, World.MAP_BASE).tileDrop(holderX - 16, holderY - 18);
					if (w.getTileID(holderX, holderY, World.MAP_OVERLAY) == 3
							|| w.getTileID(holderX, holderY, World.MAP_OVERLAY) == 4) { // When tile
						// is forge
						if (w.getTileID(holderX, holderY, World.MAP_OVERLAY) == 3) {
							w.setTile(holderX, holderY, -1, World.MAP_OVERLAY);
							w.setTile(holderX + Tile.TILE_SIZE, holderY, -1, World.MAP_OVERLAY);

						} else {
							w.setTile(holderX, holderY, -1, World.MAP_OVERLAY);
							w.setTile(holderX - Tile.TILE_SIZE, holderY, -1, World.MAP_OVERLAY);

						}

					} else if (w.getTileID(holderX, holderY, World.MAP_OVERLAY) == 2) { // When tile is anvil
						w.setTile(holderX, holderY, -1, World.MAP_OVERLAY);

					} else if (w.getTileID(holderX, holderY, World.MAP_OVERLAY) >= 0
							&& w.getTile(holderX, holderY, World.MAP_OVERLAY).isBreakable()) {
						w.setTile(holderX, holderY, -1, World.MAP_OVERLAY);
					} else
						w.setTile(holderX, holderY, Tile.tileToFloor(w.getTileID(holderX, holderY, World.MAP_BASE)),
								World.MAP_BASE);

					for (int i = 0; i < drops.size(); i++) {
						Handler.getEntityManager().addEntity(drops.get(i));
					}
					// end tile break

					// always do
					new Particle(new Rect(1, 1, 0xffffff00, SpriteData.TYPE_ITEM_DROP).toSprite(), 8, holderX, holderY,
							holderX + 5, holderY + 5, new Particle.Behavior() {
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
