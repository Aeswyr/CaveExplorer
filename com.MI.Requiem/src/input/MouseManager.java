package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import core.Driver;
import item.Item;
import item.ItemContainer;

public class MouseManager implements MouseListener, MouseMotionListener {

	private boolean left, right, middle, dragging;
	private int x, y, x0, y0; // x0 and y0 are the last positions of the mouse. x and y are current
	ItemContainer<Item> startHovered, endHovered;

	@Override
	public void mouseDragged(MouseEvent e) {
		x0 = x;
		y0 = y;
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		x0 = x;
		y0 = y;
		x = e.getX();
		y = e.getY();

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch (e.getButton()) {
		case 0:
			break;
		case 1:
			left = true;
			break;
		case 2:
			middle = true;
			break;
		case 3:
			right = true;
			break;
		default:
			break;
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch (e.getButton()) {
		case 0:
			break;
		case 1:
			left = false;
			break;
		case 2:
			middle = false;
			break;
		case 3:
			right = false;
			break;
		default:
			break;
		}

		if (startHovered != null && endHovered != null && endHovered.containsMouse()) {
			if (startHovered.equals(endHovered) && startHovered.getContained() != null) {
				startHovered.getContained().use();
			} else {
				switch (e.getButton()) {
				case MouseEvent.BUTTON1:
					if (endHovered.store(startHovered.getContained(), startHovered.getAmount())) startHovered.remove(startHovered.getAmount());
					break;
				case MouseEvent.BUTTON2:
					if (endHovered.store(startHovered.getContained())) startHovered.remove();
					break;
				case MouseEvent.BUTTON3:
					if (endHovered.store(startHovered.getContained(), (int) Math.ceil(startHovered.getAmount() / 2.0))) startHovered.remove((int) Math.ceil(startHovered.getAmount() / 2.0));
					break;
					default:
						break;
				}
				
			}
				
		} else if (startHovered != null && startHovered.getContained() != null) {
			startHovered.drop();
		}
		startHovered = null;
		endHovered = null;
		this.dragging = false;
	}

	// Getters and Setter

	public boolean getLeft() {
		return left;
	}

	public boolean getRight() {
		return right;
	}
	
	public boolean getMiddle() {
		return middle;
	}

	/**
	 * @returns the mouse x position in pixels on the frame
	 */
	public int getX() {
		return x;
	}

	/**
	 * @returns the mouse y position in pixels on the frame
	 */
	public int getY() {
		return y;
	}

	/**
	 * @returns the mouse x position in pixels relative to the DrawGraphics, not the
	 *          frame
	 */
	public int getAdjX() {
		return (int) (x / Driver.scale);
	}

	/**
	 * @returns the mouse y position in pixels relative to the DrawGraphics, not the
	 *          frame
	 */
	public int getAdjY() {
		return (int) (y / Driver.scale);
	}

	public int getDeltaX() {
		return x - x0;
	}

	public int getDeltaY() {
		return y - y0;
	}

	public void setStartHovered(ItemContainer<Item> c) {
		startHovered = c;
		this.dragging = true;
	}

	public void setEndHovered(ItemContainer<Item> c) {
		endHovered = c;
	}

	public ItemContainer<Item> getStartHovered() {
		return startHovered;
	}

	public boolean getDragging() {
		return dragging;
	}
	
}
