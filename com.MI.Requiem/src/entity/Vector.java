package entity;

import java.io.Serializable;

import gfx.DrawGraphics;
import runtime.Handler;
import world.Tile;

/**
 * object which controls movement via x and y velocity and acceleration
 * components
 * 
 * @author Pascal
 *
 */
public class Vector implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7160300851222198335L;
	double Ax, Ay; // in tiles per second per second
	double Vx, Vy; // in tiles per second;
	transient Entity linked;
	int mass = 0;
	boolean ghost = false;

	/**
	 * initializes a vector
	 * 
	 * @param e    - entity to attach this vector to
	 * @param mass - mass for force and friction calculations. 0 mass to disable
	 *             friction
	 */
	public Vector(Entity e, int mass) {
		this.linked = e;
		this.mass = mass;
	}

	/**
	 * increments the velocity component relative to the acceleration component then
	 * updates the linked entity's position
	 */
	public void update() {

		Vx += Ax / 60;
		Vy += Ay / 60;

		if (!linked.hitbox.tileYCollide(Vy * 16 / 60) || ghost)
			linked.y += Vy * 16 / 60;
		else {
			while (linked.hitbox.tileYCollide(Vy * 16 / 60) && Math.abs(Vy) > 60 / 16)
				if (Vy > 0)
					Vy--;
				else
					Vy++;
			if (Math.abs(Vy) > 60 / 16)
				linked.y += Vy * 16 / 60;
			Ay = 0;
			Vy = 0;
		}

		if (!linked.hitbox.tileXCollide(Vx * 16 / 60) || ghost)
			linked.x += Vx * 16 / 60;
		else {
			while (linked.hitbox.tileXCollide(Vx * 16 / 60) && Math.abs(Vx) > 60 / 16)
				if (Vx > 0)
					Vx--;
				else
					Vx++;
			if (Math.abs(Vx) > 60 / 16)
				linked.x += Vx * 16 / 60;
			Ax = 0;
			Vx = 0;
		}

		if (mass > 0) {
			if (Math.abs(Vx) > 0.1)
				Vx *= 0.85;
			else
				Vx = 0;
			if (Math.abs(Vy) > 0.1)
				Vy *= 0.85;
			else
				Vy = 0;
		}

		Ax = 0;
		Ay = 0;
	}

	/**
	 * sets the x acceleration of this vector
	 * 
	 * @param i - new value for x acceleration
	 */
	public void setAccelX(double i) {
		Ax = i;
	}

	/**
	 * sets the y acceleration for this vector
	 * 
	 * @param i - new value for y acceleration
	 */
	public void setAccelY(double i) {
		Ay = i;
	}

	/**
	 * adjusts the x acceleration for this vector by summing the current component
	 * and the input value
	 * 
	 * @param i - the amount to change the current acceleration by
	 */
	public void adjAccelX(double i) {
		Ax += i;
	}

	/**
	 * adjusts the y acceleration for this vector by summing the current component
	 * and the input value
	 * 
	 * @param i - the amount to change the current acceleration by
	 */
	public void adjAccelY(double i) {
		Ay += i;
	}

	/**
	 * sets the x velocity for this vector
	 * 
	 * @param i - new value for x velocity
	 */
	public void setVelocityX(double i) {
		Vx = i;
	}

	/**
	 * sets the y velocity for this vector
	 * 
	 * @param i - new value for y velocity
	 */
	public void setVelocityY(double i) {
		Vy = i;
	}

	/**
	 * @returns x acceleration for this vector
	 */
	public double Ax() {
		return Ax;
	}

	/**
	 * @returns y acceleration for this vector
	 */
	public double Ay() {
		return Ay;
	}

	/**
	 * @returns x velocity for this vector
	 */
	public double Vx() {
		return Vx;
	}

	/**
	 * @returns y velocity for this vector
	 */
	public double Vy() {
		return Vy;
	}

	/**
	 * applies a force to this object in a variable direction
	 * 
	 * @param force - force to apply in newtons
	 * @param dir   - direction to apply the force in radians
	 */
	public void applyForce(int force, double dir) {
		Ax += Math.cos(dir) * force * 60;
		Ay += Math.sin(dir) * force * 60;
	}

	public static Handler handler;

	/**
	 * draws this vector to the screen by representing its velocity component as a
	 * blue line and its acceleration component as a red line
	 * 
	 * @param g - the DrawGraphics component associated with the renderer
	 */
	public void render(DrawGraphics g) {
		int x = linked.getCenteredX() - handler.getCamera().xOffset();
		int y = linked.getCenteredY() - handler.getCamera().yOffset();
		g.drawLine(x, y, (int) (x + Ax * Tile.tileSize), (int) (y + Ay * Tile.tileSize), 0xffff0000);
		g.drawLine(x, y, (int) (x + Vx * Tile.tileSize), (int) (y + Vy * Tile.tileSize), 0xff0000ff);
	}

	/**
	 * sets this entity as a ghost, which means it ignores tile collisions
	 * 
	 * @param b - true to enable ghost, false to disable
	 */
	public void setGhost(boolean b) {
		ghost = b;
	}

	/**
	 * called when this vector is loaded from a file, reassigns the owner entity
	 * 
	 * @param e - entity to link to this vector
	 */
	public void load(Entity e) {
		this.linked = e;
	}
}