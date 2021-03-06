package items;

import core.Assets;
import effects.Effect;
import entity.Hitbox;
import entity.Mob;
import geometry.Rect;
import gfx.SpriteData;
import input.Controller;
import item.Item;
import particle.Particle;
import runtime.Handler;

public class CrystalRod extends Item {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5542786568095131743L;

	public CrystalRod( Mob holder) {
		super( holder);
	}

	public CrystalRod(int x, int y) {
		super(x, y);
	}

	@Override
	protected void setup() {
		ID = "11";
		tags = "hand";
		name = "Crystal Rod";

		statPackage[ITEM_WEIGHT] = 5;
		statPackage[ITEM_DAMAGE] = 2;

		useMax = 128;
		use = useMax;
		useTime = 40;
		timer = useTime;

		sprite = Assets.crystalRod;
		invSprite = Assets.crystalRod_inv;

		stackable = false;
	}

	@Override
	public void use() {
		if (timer == useTime) {
			Mob holder = (Mob) this.holder;

			holder.heal(1, Effect.DAMAGE_TYPE_MENTAL);
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

			Hitbox h = new Hitbox(holderX, holderY, 3, 3);

			while (((holderX - holder.getCenteredX()) * (holderX - holder.getCenteredX())
					+ (holderY - holder.getCenteredY()) * (holderY - holder.getCenteredY())) < 16384) {
				h.updatePos(holderX, holderY);

				Mob m = h.collidingWithMob();
				if (m != null && m != holder) {
					m.harm(statPackage[ITEM_DAMAGE], Effect.DAMAGE_TYPE_PHYSICAL);
					break;
				}
				if (h.tileXCollide(0) || h.tileYCollide(0))
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
			new Particle(new Rect(2, 2, 0xAA4444ff, SpriteData.TYPE_ITEM_DROP).toSprite(), 40, holder.getCenteredX(),
					holder.getCenteredY(), holderX, holderY, new Particle.Behavior() {

						@Override
						public void update(int x, int y, int x0, int y0, int[] data, int index) {
							data[0] += move[index][0];
							data[1] += move[index][1];
						}

						int[][] move;

						@Override
						public void initial(int x, int y, int x0, int y0, int[][] data) {
							int dx = x0 - x;
							int dy = y0 - y;
							move = new int[data.length][2];
							for (int i = 0; i < data.length; i++) {
								data[i][0] = x + (int) (dx * 1.0 * i / data.length);
								data[i][1] = y + (int) (dy * 1.0 * i / data.length);
								data[i][2] = (int) (8 - Math.random() * 5);

								move[i][0] = (int) (3 - Math.random() * 6);
								move[i][1] = (int) (3 - Math.random() * 6);
							}
						}
					}).start();
			timer = 0;
		}
	}

	@Override
	public void update() {
		if (timer < useTime)
			timer++;
	}

}
