package geometry;

import gfx.Sprite;

public class Square extends Shape {

	public Square(int width, int height, int color, int type) {
		this.width = width;
		this.height = height;
		this.type = type;

		this.raster = new int[width * height];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				raster[y * width + x] = color;
			}
		}

		this.sprite = new Sprite(width, height, raster, type);
	}

	public Square(int width, int height, int color, int type, boolean hollow) {
		this.width = width;
		this.height = height;
		this.type = type;

		this.raster = new int[width * height];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (x == 0 || y == 0 || x == height - 1 || y == height - 1)
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

}
