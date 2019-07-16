package gui;


import entity.Hitbox;
import gfx.DrawGraphics;
import runtime.Handler;

public class Button extends UIObject{

  private ClickListener action;
  protected boolean hovered = false;
  private Hitbox hitbox;
  private Handler handler;
  
  public Button(ClickListener action, int x, int y, int width, int height, Handler handler) {
    this.action = action;
    hitbox = new Hitbox(x, y, width, height, handler);
    this.handler = handler;
    this.x = x;
    this.y = y;
  }
    
  @Override
  public void render(DrawGraphics g) {
	  
  }
  
  boolean lastMouseFrame = false;
  @Override
  public void update() {
    if (hitbox.containsMouse()) {
      this.hovered = true;
      if (lastMouseFrame == true && !handler.getMouse().getLeft()) doClick();
      lastMouseFrame = handler.getMouse().getLeft();
      
    } else {
      this.hovered = false;
      lastMouseFrame = false;
    }
  }
  
  public boolean getHovered() {
    return hovered;
  }
  
  public void doClick() {
    action.onClick(this);
  }
  
}
