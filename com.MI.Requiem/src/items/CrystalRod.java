package items;

import core.Assets;
import effects.Effect;
import entity.Hitbox;
import entity.Mob;
import geometry.Shape;
import geometry.Square;
import gfx.Sprite;
import item.Item;
import particle.Particle;
import runtime.Handler;

public class CrystalRod extends Item {

	public CrystalRod(Handler handler, Mob holder) {
		super(handler, holder);
	}

	public CrystalRod(int x, int y, Handler handler) {
		super(x, y, handler);
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

	private Shape spark = new Square(2, 2, 0xffAAAAff, Sprite.TYPE_ITEM_DROP);
	
	@Override
	public void use() {
		if (equipped && timer >= useTime && holder.getSpirit() >= 1) {
			holder.harm(1, Effect.DAMAGE_TYPE_MENTAL);
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

			Hitbox h = new Hitbox(holderX, holderY, 3, 3, handler);

			while (((holderX - holder.getCenteredX()) * (holderX - holder.getCenteredX())
					+ (holderY - holder.getCenteredY()) * (holderY - holder.getCenteredY())) < 16384) {
				h.updatePos(holderX, holderY);
				
				Mob m = h.collidingWithMob();
				if (m != null && m != holder) {
					m.harm(statPackage[ITEM_DAMAGE], Effect.DAMAGE_TYPE_PHYSICAL);
					break;
				}
				if (h.tileXCollide(0) || h.tileYCollide(0)) break;
				
				
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
			new Particle(spark.toSprite(), 40, 5, holder.getCenteredX(), holder.getY() - 8, holderX, holderY,
					Particle.SHAPE_LINE, Particle.DISPERSE_BURST, Particle.DIRECTION_RANDOM,
					Particle.LIFETIME_VARIABLE, handler).start();
			timer = 0;
		}
	}

	

	@Override
	public void update() {
		if (timer < useTime)
			timer++;
	}

}
