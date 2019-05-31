package geometry;

public class Square extends Shape{

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
	}

	@Override
	public boolean contains(Shape s) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
