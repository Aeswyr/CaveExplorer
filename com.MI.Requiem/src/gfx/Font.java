package gfx;

import utility.Loader;

public class Font {

	private Sprite font;
	private int[] offsets;
	private int[] widths;

	public Font(String path) {

		font = new Sprite(Loader.loadImage(path), Sprite.TYPE_TEXT);

		offsets = new int[59];
		widths = new int[59];

		int unicode = 0;

		int[] raw = font.getRawFrame();
		for (int i = 0; i < font.getWidth(); i++) {
			if (raw[i] == 0xff0000ff)
				offsets[unicode] = i;
			if (raw[i] == 0xff00ff00) {
				widths[unicode] = i - offsets[unicode];
				unicode++;
			}
		}
	}

	protected Sprite getSprite() {
		return font;
	}

	protected int[] getWidths() {
		return widths;
	}

	protected int[] getOffsets() {
		return offsets;
	}

}
