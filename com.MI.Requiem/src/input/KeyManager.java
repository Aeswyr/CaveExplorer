package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

	boolean[] keys = new boolean[256];
	public boolean w, a, s, d, up, left, down, right, f, c, v, rightBracket, shift, esc, space;
	private boolean w0, a0, s0, d0, up0, left0, down0, right0, f0, c0, v0, rightBracket0, shift0, esc0, space0;

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public void update() {
		w0 = w;
		a0 = a;
		s0 = s;
		d0 = d;
		up0 = up;
		down0 = down;
		left0 = left;
		right0 = right;
		f0 = f;
		c0 = c;
		v0 =  v;
		rightBracket0 = rightBracket;
		shift0 = shift;
		esc0 = esc;
		space0 = space;
		
		w = keys[KeyEvent.VK_W];
		a = keys[KeyEvent.VK_A];
		s = keys[KeyEvent.VK_S];
		d = keys[KeyEvent.VK_D];
		up = keys[KeyEvent.VK_UP];
		down = keys[KeyEvent.VK_DOWN];
		left = keys[KeyEvent.VK_LEFT];
		right = keys[KeyEvent.VK_RIGHT];
		f = keys[KeyEvent.VK_F];
		c = keys[KeyEvent.VK_C];
		v =  keys[KeyEvent.VK_V];
		rightBracket = keys[KeyEvent.VK_CLOSE_BRACKET];
		shift = keys[KeyEvent.VK_SHIFT];
		esc = keys[KeyEvent.VK_ESCAPE];
		space = keys[KeyEvent.VK_SPACE];
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
	
	public boolean getVTyped() {
		return !v0 && v;
	}
	
	public boolean getFTyped() {
		return !f0 && f;
	}
	
	public boolean getCTyped() {
		return !c0 && c;
	}
	
	public boolean getShiftTyped() {
		return !shift0 && shift;
	}
	
	public boolean getSpaceTyped() {
		return !space0 && space;
	}
	
	public boolean getRBracketTyped() {
		return !rightBracket0 && rightBracket;
	}

	public boolean getEscTyped() {
		return !esc0 && esc;
	}
	
	public boolean getWTyped() {
		return !w0 && w;
	}

	public boolean getATyped() {
		return !a0 && a;
	}
	
	public boolean getSTyped() {
		return !s0 && s;
	}
	
	public boolean getDTyped() {
		return !d0 && d;
	}
	
	public boolean getUpTyped() {
		return !up0 && up;
	}
	
	public boolean getDownTyped() {
		return !down0 && down;
	}
	
	public boolean getLeftTyped() {
		return !left0 && left;
	}
	
	public boolean getRightTyped() {
		return !right0 && right;
	}
	
}
