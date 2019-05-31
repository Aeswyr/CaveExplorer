package gfx;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import core.Driver;
import geometry.Shape;
import runtime.Light;

public class DrawGraphics {

	ArrayList<SpriteRequest> requestList;
	ArrayList<LightRequest> lightRequest;

	int width, height;
	double scale;
	BufferedImage screen;
	int[] raster;
	int[] zBuffer;
	int[] lightMap;
	int[] lightCollision;

	int ambientColor;

	int z = 0;

	public DrawGraphics(Driver d) {
		width = d.getWidth();
		height = d.getHeight();
		scale = Driver.scale;
		screen = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		raster = ((DataBufferInt) screen.getRaster().getDataBuffer()).getData();
		zBuffer = new int[raster.length];
		lightMap = new int[raster.length];
		lightCollision = new int[raster.length];

		requestList = new ArrayList<SpriteRequest>();
		lightRequest = new ArrayList<LightRequest>();
		
		ambientColor = 0xff000000;
	}

	public void render(Graphics g) {
		for (int i = 0; i < raster.length; i++) {
			raster[i] = (int) (((raster[i] >> 16) & 0xff) * (((lightMap[i] >> 16) & 0xff) / 255f)) << 16
					| (int) (((raster[i] >> 8) & 0xff) * (((lightMap[i] >> 8) & 0xff) / 255f)) << 8
					| (int) ((raster[i] & 0xff) * ((lightMap[i] & 0xff) / 255f));
		}

		g.drawImage(screen, 0, 0, (int) (width * scale), (int) (height * scale), null);
	}

	public void process() {
		
		Collections.sort(requestList, new Comparator<SpriteRequest>() {

			@Override
			public int compare(SpriteRequest i0, SpriteRequest i1) {
				if (i0.z < i1.z) return -1;
				if (i0.z > i1.z) return 1;
				return 0;
			}
			
		});
		int temp = 0;
		for (int i = 0; i < requestList.size(); i++) {
			SpriteRequest req = requestList.get(i);
			if (req.z > 0) {
				temp = z;
				break;
			}
			setZBuffer(req.z);
			draw(req.s, req.x, req.y);
		}
		for (int i = 0; i < lightRequest.size(); i++) {
			LightRequest req = lightRequest.get(i);
			drawLight(req.l, req.x, req.y);
		}
		for (int i = temp; i < requestList.size(); i++) {
			SpriteRequest req = requestList.get(i);
			setZBuffer(req.z);
			drawPost(req.s, req.x, req.y);
		}
		requestList.clear();
		lightRequest.clear();
	}

	public void setZBuffer(int z) {
		this.z = z;
	}

	public void submitRequest(SpriteRequest s) {
		requestList.add(s);
	}

	public void submitRequest(LightRequest l) {
		lightRequest.add(l);
	}
	
	public void setLightCollision(int x, int y, int value) {
		if (x < 0 || x >= width || y < 0 || y >= height)
			return;
		lightCollision[y * width + x] = value;
	}

	private void drawPixel(int x, int y, int value) {

		int alpha = (value >> 24) & 0xff;

		if ((x < 0 || x >= width || y < 0 || y >= height) || value == 0xffff00ff || alpha == 0)
			return;

		if (zBuffer[y * width + x] > z)
			return;

		if (alpha == 255)
			raster[y * width + x] = value;
		else {
			int pixel = raster[y * width + x];
			int r = ((pixel >> 16) & 0xff) - (int) ((((pixel >> 16) & 0xff) - ((value >> 16) & 0xff)) * alpha / 255f);
			int g = ((pixel >> 8) & 0xff) - (int) ((((pixel >> 8) & 0xff) - ((value >> 8) & 0xff)) * alpha / 255f);
			int b = (pixel & 0xff) - (int) (((pixel & 0xff) - (value) & 0xff) * alpha / 255f);
			raster[y * width + x] = r << 16 | g << 8 | b;
		}
	}

	private void drawLuminosity(int x, int y, int value) {
		if (x < 0 || x >= width || y < 0 || y >= height)
			return;

		int r, g, b;
		int pixel = lightMap[y * width + x];

		r = Math.max((pixel >> 16) & 0xff, (value >> 16) & 0xff);
		g = Math.max((pixel >> 8) & 0xff, (value >> 8) & 0xff);
		b = Math.max(pixel & 0xff, value & 0xff);

		lightMap[y * width + x] = r << 16 | g << 8 | b;
	}

	public void drawLight(Light l, int xOff, int yOff) {

		int rad = l.getRadius();

		for (int i = 0; i <= l.getDiameter(); i++) {
			drawLightRay(l, rad, rad, i, 0, xOff, yOff);
			drawLightRay(l, rad, rad, i, rad * 2, xOff, yOff);
			drawLightRay(l, rad, rad, 0, i, xOff, yOff);
			drawLightRay(l, rad, rad, rad * 2, i, xOff, yOff);
		}

	}

	private void drawLightRay(Light l, int x0, int y0, int x1, int y1, int xOff, int yOff) {
		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1 - y0);

		int sx = x0 < x1 ? 1 : -1;
		int sy = y0 < y1 ? 1 : -1;

		int err = dx - dy;
		int e2;

		int rad = l.getRadius();

		while (true) {

			int screenX = x0 - rad + xOff;
			int screenY = y0 - rad + yOff;

			if (screenX < 0 || screenX >= width || screenY < 0 || screenY >= height)
				return;

			int color = l.getLuminosity(x0, y0);
			if (color == 0)
				return;
			if (lightCollision[screenY * width + screenX] == Light.FULL)
				return;

			drawLuminosity(screenX, screenY, color);
			if (x0 == x1 && y0 == y1)
				break;
			e2 = 2 * err;
			if (e2 > -1 * dy) {
				err -= dy;
				x0 += sx;
			}
			if (e2 < dx) {
				err += dx;
				y0 += sy;
			}
		}
	}

	public void clear() {
		for (int i = 0; i < raster.length; i++) {
			raster[i] = 0;
			zBuffer[i] = 0;
			lightMap[i] = ambientColor;
			lightCollision[i] = 0;
		}
	}

	public void draw(Sprite s, int xOff, int yOff) {

		int xCap = 0;
		int yCap = 0;
		int widthCap = s.getWidth();
		int heightCap = s.getHeight();

		if (widthCap + xOff > width) {
			widthCap -= widthCap + xOff - width;
		}
		if (heightCap + yOff > height) {
			heightCap -= heightCap + yOff - height;
		}

		if (xCap + xOff < 0) {
			xCap -= xOff;
		}
		if (yCap + yOff < 0) {
			yCap -= yOff;
		}

		int[] r = s.getRawFrame();
		int w = s.getWidth();
		int l = s.getLightInteraction();
		for (int y = yCap; y < heightCap; y++) {
			for (int x = xCap; x < widthCap; x++) {
				drawPixel(x + xOff, y + yOff, r[y * w + x]);
				if (r[y * w + x] != 0xffff00ff)
					setLightCollision(x + xOff, y + yOff, l);
			}
		}

	}
	
	public void drawPost(Sprite s, int xOff, int yOff) {

		int xCap = 0;
		int yCap = 0;
		int widthCap = s.getWidth();
		int heightCap = s.getHeight();

		if (widthCap + xOff > width) {
			widthCap -= widthCap + xOff - width;
		}
		if (heightCap + yOff > height) {
			heightCap -= heightCap + yOff - height;
		}

		if (xCap + xOff < 0) {
			xCap -= xOff;
		}
		if (yCap + yOff < 0) {
			yCap -= yOff;
		}

		int[] r = s.getRawFrame();
		int w = s.getWidth();
		int l = s.getLightInteraction();
		for (int y = yCap; y < heightCap; y++) {
			for (int x = xCap; x < widthCap; x++) {
				drawPixel(x + xOff, y + yOff, r[y * w + x]);
				if (l == Light.IGNORE && r[y * w + x] != 0xffff00ff) drawLuminosity(x + xOff, y + yOff, 0xffffffff);					
			}
		}

	}

	public void draw(Shape s, int xOff, int yOff) {
		int xCap = 0;
		int yCap = 0;
		int widthCap = s.getWidth();
		int heightCap = s.getHeight();

		if (widthCap + xOff > width) {
			widthCap -= widthCap + xOff - width;
		}
		if (heightCap + yOff > height) {
			heightCap -= heightCap + yOff - height;
		}

		if (xCap + xOff < 0) {
			xCap -= xOff;
		}
		if (yCap + yOff < 0) {
			yCap -= yOff;
		}

		int[] r = s.getRaster();
		int w = s.getWidth();
		for (int y = yCap; y < heightCap; y++) {
			for (int x = xCap; x < widthCap; x++) {
				drawPixel(x + xOff, y + yOff, r[y * w + x]);
			}
		}
	}

	// TODO shape objects and drawshape method
	/*
	 * public void drawCircle(int xOff, int yOff, int radius, int color) { for
	 * (double i = 0; i < 2 * Math.PI; i += Math.PI / (radius * radius)) {
	 * this.drawPixel((int)(xOff + Math.cos(i) * radius), (int) (yOff + Math.sin(i)
	 * * radius), color); this.drawLuminosity((int)(xOff + Math.cos(i) * radius),
	 * (int) (yOff + Math.sin(i) * radius), 0xff666666); } }
	 */
	public void fillRect(int xOff, int yOff, int width, int height, int color) {
		setZBuffer(10);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				this.drawPixel(xOff + x, yOff + y, color);
				this.drawLuminosity(xOff + x, yOff + y, 0xffffffff);
			}
		}
	}

}
