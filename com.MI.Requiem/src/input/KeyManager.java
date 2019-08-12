package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

	boolean[] keys = new boolean[256];
	public boolean w, a, s, d, up, left, down, right, f, c, v, rightBracket, shift;
	private boolean w0, a0, s0, d0, up0, left0, down0, right0, f0, c0, v0, rightBracket0, shift0;

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
	
	public boolean getRBracketTyped() {
		return !rightBracket0 && rightBracket;
	}

}
