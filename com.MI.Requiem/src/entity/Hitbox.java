package entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import core.Driver;
import entity.Entity;
import entity.EntityManager;
import entity.Mob;
import runtime.Handler;


public class Hitbox {
  
  private int xOff, yOff;
  private Entity e;
  private int x, y, width, height;
  Rectangle r;
  private Handler handler;
  
  public Hitbox(int xOffset, int yOffset, int width,  int height, Entity e, Handler handler) {
    this.e = e;
    this.xOff = (int) (xOffset * Driver.scale);
    this.yOff = (int) (yOffset * Driver.scale);
    this.handler = handler;
    this.width = (int) (width * Driver.scale);
    this.height = (int) (height * Driver.scale);
  }
  
  public Hitbox(int xOffset, int yOffset, int size, Entity e, Handler handler) {
    this.e = e;
    this.xOff = (int) (Driver.scale);
    this.yOff = (int) (Driver.scale);
    this.handler = handler;
    this.width = (int) (Driver.scale);
    this.height = (int) (Driver.scale);
  }
  
  public Hitbox(int x, int y, int width, int height, Handler handler) {
    this.x = x;
    this.y = y;
    this.width = (int) (Driver.scale);
    this.height = (int) (Driver.scale);
    this.xOff = 0;
    this.yOff = 0;
    this.handler = handler;
  }
  
  public void update() {
    x = e.getX();
    y = e.getY();
  }
  
  public boolean contains(Hitbox h) {
    int[] thisBound = this.getBounds();
    int[] newBound = h.getBounds();
    
    if ((newBound[0] >= thisBound[0] && newBound[0] <= thisBound[1]) || 
        (newBound[1] >= thisBound[0] && newBound[1] <= thisBound[1]) ||
        (thisBound[0] >= newBound[0] && thisBound[0] <= newBound[1]) ||
        (thisBound[1] >= newBound[0] && thisBound[1] <= newBound[1])){
      if((newBound[2] >= thisBound[2] && newBound[2] <= thisBound[3])|| 
        (newBound[3] >= thisBound[2] && newBound[3] <= thisBound[3]) ||
        (thisBound[2] >= newBound[2] && thisBound[2] <= newBound[3]) || 
        (thisBound[3] >= newBound[2] && thisBound[3] <= newBound[3]))
        return true;
    }
    
    return false;
  }
  
  public boolean tileXCollide(double xMove) {
    int[] bound = this.getBounds();
      if ((handler.getWorld().getTile((int) (bound[0] + xMove) , bound[2]).isSolid()) || 
          (handler.getWorld().getTile((int) (bound[0] + xMove), bound[3] - 2).isSolid()) ||
          (handler.getWorld().getTile((int) (bound[1] + xMove), bound[2]).isSolid()) || 
          (handler.getWorld().getTile((int) (bound[1] + xMove), bound[3] - 2).isSolid())) {
        return true;
      }
    return false;
  }

  public boolean tileYCollide(double yMove) {
    int[] bound = this.getBounds();
      if (handler.getWorld().getTile(bound[0], (int) (bound[2] + yMove)).isSolid() || 
          handler.getWorld().getTile(bound[1] - 2, (int) (bound[2] + yMove)).isSolid() ||
          handler.getWorld().getTile(bound[0], (int) (bound[3] + yMove)).isSolid() || 
          handler.getWorld().getTile(bound[1] - 2, (int) (bound[3] + yMove)).isSolid()) {
        return true;
      }
    return false;
}
  
  public Entity collidingWith() {
    EntityManager em = handler.getWorld().getEntities();
    for (int i = 0; i < em.totalEntities(); i++) {
      if (em.getEntity(i).getHitbox() != null && (this.contains(em.getEntity(i).getHitbox())) || 
          em.getEntity(i).getHitbox().contains(this)) return em.getEntity(i);
    }
    return null;
  }
  
  public Entity collidingWithMob() {
    EntityManager em = handler.getWorld().getEntities();
    for (int i = 0; i < em.totalEntities(); i++) {
      if (em.getEntity(i).getHitbox() != null && (this.contains(em.getEntity(i).getHitbox())) || 
          em.getEntity(i).getHitbox().contains(this) && em.getEntity(i) instanceof Mob) return em.getEntity(i);
    }
    return null;
  }
  
  public ArrayList<Entity> collidingAll() {
    EntityManager em = handler.getWorld().getEntities();
    ArrayList<Entity> found = new ArrayList<Entity>();
    for (int i = 0; i < em.totalEntities(); i++) {
      if (em.getEntity(i).getHitbox() != null && (this.contains(em.getEntity(i).getHitbox())) || 
          em.getEntity(i).getHitbox().contains(this)) found.add(em.getEntity(i));
    }
    return found;
  }
  
  public boolean containsMouse() {
    int mouseX = handler.getMouse().getX();
    int mouseY = handler.getMouse().getY();
    int[] bound = this.getBounds();
    
    if (mouseX > bound[0] && mouseX < bound[1] &&
        mouseY > bound[2] && mouseY < bound[3]) return true;
    
    return false;
  }
  
  //Getters and Setters

  private int[] getBounds() {
    int[] bounds = {
        x + xOff,
        x + xOff + width,
        y + yOff,
        y + yOff + height
    };
    return bounds;
  }
  
  public void updatePos(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  public void render(Graphics g) {
	  if (handler.devMode) g.drawRect(x + xOff - handler.getCamera().xOffset(), y  + yOff - handler.getCamera().yOffset(), width, height);
  }
  
  public void renderStill(Graphics g) {
	  if (handler.devMode) g.drawRect(x + xOff, y  + yOff, width, height);
  }
  
}
