package utility;

import gfx.DrawGraphics;

public interface Storeable {

	public void renderInventory(int x, int y, DrawGraphics g);
	
	public void renderTextBox(int x, int y, DrawGraphics g);
	
	public String getTags();
	
	public boolean canStack(Storeable s);
}
