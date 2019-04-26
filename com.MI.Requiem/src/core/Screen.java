package core;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Screen extends Canvas{
	
	private JFrame frame;
	private Dimension d;
	public Screen(int width, int height) {
		
		
		d = new Dimension();
		if (width == -1 && height == -1) d = Toolkit.getDefaultToolkit().getScreenSize();
		else d.setSize(width, height);
		
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(d);
		frame.setMinimumSize(d);
		frame.setUndecorated(true);
		frame.setVisible(true);
		
		
		frame.add(this);
		frame.pack();
	}
	
	
	public int getWidth() {
		return d.width;
	}
	
	public int getHeight() {
		return d.height;
	}
}