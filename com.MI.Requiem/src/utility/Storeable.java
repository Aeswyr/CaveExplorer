package utility;

import gfx.DrawGraphics;
import gfx.Sprite;

public interface Storeable {

	public void renderInventory(int x, int y, DrawGraphics g);
	
	public void renderTextBox(int x, int y, DrawGraphics g);
	
	public String getTags();
	
	public Sprite getAsset();
	
	public boolean canStack(Storeable s);
}
