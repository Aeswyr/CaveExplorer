package geometry;

import gfx.Sprite;

public class Square extends Shape {

	boolean hollow = false;

	/**
	 * generates a solid color rectangle
	 * 
	 * @param width
	 * @param height
	 * @param color
	 * @param type
	 */
	public Square(int width, int height, int color, int type) {
		this.width = width;
		this.height = height;
		this.type = type;
		this.color = color;
		this.raster = new int[width * height];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				raster[y * width + x] = color;
			}
		}

		this.sprite = new Sprite(width, height, raster, type);
	}

	/**
	 * generates a hollow rectangle
	 * 
	 * @param width
	 * @param height
	 * @param color
	 * @param type
	 * @param hollow
	 */
	public Square(int width, int height, int color, int type, boolean hollow) {
		this.width = width;
		this.height = height;
		this.type = type;
		this.color = color;
		this.raster = new int[width * height];
		this.hollow = hollow;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (x == 0 || y == 0 || x == width - 1 || y == height - 1)
					raster[y * width + x] = color;
			}
		}

		this.sprite = new Sprite(width, height, raster, type);
	}

	/**
	 * generates a filled rectangle with an outline
	 * 
	 * @param width
	 * @param height
	 * @param color1
	 * @param color2
	 * @param type
	 * @param hollow
	 */
	public Square(int width, int height, int color1, int color2, int type) {
		this.width = width;
		this.height = height;
		this.type = type;
		this.color = color1;
		this.color2 = color2;
		this.raster = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (x == 0 || y == 0 || x == width - 1 || y == height - 1)
					raster[y * width + x] = color2;
				else
					raster[y * width + x] = color;
			}
		}

		this.sprite = new Sprite(width, height, raster, type);
	}

	@Override
	public boolean contains(Shape s) {
		// TODO Auto-generated method stub
		return false;
	}

	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
		this.raster = new int[width * height];
		if (hollow) {
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (x == 0 || y == 0 || x == height - 1 || y == height - 1)
						raster[y * width + x] = color;
				}
			}
		} else if (color2 != -1) {
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (x == 0 || y == 0 || x == width - 1 || y == height - 1)
						raster[y * width + x] = color2;
					else
						raster[y * width + x] = color;
				}
			}
		} else {
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					raster[y * width + x] = color;
				}
			}
		}
		this.sprite = new Sprite(width, height, raster, type);
	}

}
