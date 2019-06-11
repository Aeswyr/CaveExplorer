package particle;

import java.util.Random;

import gfx.DrawGraphics;
import gfx.Sprite;
import runtime.Handler;

public class Particle {

	public static Random rng = new Random();

	public static final int DISPERSE_BURST = 0;
	public static final int DISPERSE_TICK = 1;
	public static final int SHAPE_LINE = 2;
	public static final int SHAPE_CIRCLE = 3;
	public static final int DIRECTION_RANDOM = 4;
	public static final int DIRECTION_SWEEP = 5;
	public static final int DIRECTION_FLOAT = 8;
	public static final int LIFETIME_SET = 6;
	public static final int LIFETIME_VARIABLE = 7;

	int x, y, x0, y0;
	int max;
	int lifetime;
	int data[][]; // pos 0 is x, pos 1 is y, pos 2 is life, pos 3 x speed, pos 4 is y speed
	boolean textEnabled = false;

	private int size;
	private Sprite sprite;
	
	private Handler handler;
	boolean dead = false;

	String text;

	public Particle(Sprite s, int max, int lifetime, int x, int y, int x0, int y0, int shape, int dispersal,
			int direction, int lifetype, Handler handler) {
		setBehaviors(shape, dispersal, direction, lifetype);
		this.sprite = s;
		this.max = max;
		this.lifetime = lifetime;

		data = new int[max][5];

		this.handler = handler;
		
		if (this.dispersal == DISPERSE_BURST) {
			for (size = 0; size < max; size++) {
				switch (shape) {
				case SHAPE_LINE:
					data[size][0] = x + (int) ((x0 - x) * 1.0 * size / max);
					data[size][1] = y + (int) ((y0 - y) * 1.0 * size / max);
					switch (direction) {
					case DIRECTION_RANDOM:
						data[size][3] = rng.nextInt(5) - 2;
						data[size][4] = rng.nextInt(5) - 2;
						break;
					case DIRECTION_SWEEP:
						break;
					}
					break;
				case SHAPE_CIRCLE:
					data[size][0] = x;
					data[size][1] = y;
					switch (direction) {
					case DIRECTION_RANDOM:
						data[size][3] = rng.nextInt(5) - 2;
						data[size][4] = rng.nextInt(5) - 2;
						break;
					case DIRECTION_SWEEP:
						break;
					}
					break;
				}
				if (lifetype == LIFETIME_SET)
					data[size][2] = lifetime;
				else
					data[size][2] = 3 * lifetime / 4 + rng.nextInt(lifetime / 2);
			}
		}

	}

	public Particle(Sprite s, String st, int max, int lifetime, int x, int y, int x0, int y0, int shape, int dispersal,
			int direction, int lifetype, Handler handler) {
		this(s, max, lifetime, x, y, x0, y0, shape, dispersal, direction, lifetype, handler);
		this.text = st;
		this.textEnabled = true;

	}

	public void render(DrawGraphics g) {
		
		int xOff = handler.getCamera().xOffset();
		int yOff = handler.getCamera().yOffset();
		
		for (int i = 0; i < size; i++) {
			if (data[i][2] > 0) {
				sprite.render(data[i][0] - xOff, data[i][1] - yOff, g);
				if (this.textEnabled)
					g.write(text, data[i][0] - xOff, data[i][1] - yOff);
			}
		}
	}

	public void update() {
		dead = true;
		if (size < max && this.dispersal == DISPERSE_TICK) {
			switch (shape) {
			case SHAPE_LINE:
				data[size][0] = x + (int) ((x0 - x) * 1.0 * size / max);
				data[size][1] = y + (int) ((y0 - y) * 1.0 * size / max);
				switch (direction) {
				case DIRECTION_RANDOM:
					data[size][3] = rng.nextInt(5) - 2;
					data[size][4] = rng.nextInt(5) - 2;
					break;
				case DIRECTION_SWEEP:
					break;
				}
				break;
			case SHAPE_CIRCLE:
				data[size][0] = x;
				data[size][1] = y;
				switch (direction) {
				case DIRECTION_RANDOM:
					data[size][3] = rng.nextInt(5) - 2;
					data[size][4] = rng.nextInt(5) - 2;
					break;
				case DIRECTION_SWEEP:
					break;
				}
				break;
			}
			size++;
			if (lifetype == LIFETIME_SET)
				data[size][2] = lifetime;
			else
				data[size][2] = 3 * lifetime / 4 + rng.nextInt(lifetime / 2);
		}
		for (int i = 0; i < size; i++) {
			data[i][2]--;
			data[i][0] += data[i][3];
			data[i][1] += data[i][4];
			if (data[i][2] > 0)
				dead = false;
		}

	}

	private int shape;
	private int dispersal;
	private int direction;
	private int lifetype;

	private void setBehaviors(int shape, int dispersal, int direction, int lifetype) {
		this.shape = shape;
		this.dispersal = dispersal;
		this.direction = direction;
		this.lifetype = lifetype;
	}

	public boolean isDead() {
		return dead;
	}
	
	public void start() {
		handler.getParticles().add(this);
	}
}
