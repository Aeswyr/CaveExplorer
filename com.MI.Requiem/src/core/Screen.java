package core;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import sfx.Sound;

public class Screen extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 942448191868169090L;

	private JFrame frame;
	private Dimension d;

	public Screen(int width, int height) {

		frame = new JFrame();
		d = new Dimension();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		if (width == -1 && height == -1) {
			GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			if (gd.isFullScreenSupported()) {
				frame.setUndecorated(true);
				gd.setFullScreenWindow(frame);
				d.setSize(gd.getFullScreenWindow().getWidth(), gd.getFullScreenWindow().getHeight());
			} else {
				d.setSize(800, 600);
				frame.setSize(d);
				frame.setMinimumSize(d);
				frame.setMaximumSize(d);
			}
		} else {
			d.setSize(width, height);
			frame.setSize(d);
			frame.setMinimumSize(d);
			frame.setMaximumSize(d);
		}

		frame.addWindowListener(new WindowAdapter() // Operations to complete upon window closing
		{
			public void windowClosing(WindowEvent we) {
				try {
					Sound.shutdown();
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
				System.out.println("Game Closed");
			}
		});
		frame.setTitle("Unto the Abyss");
		frame.setName("Unto the Abyss");
		frame.setVisible(true);

		this.setPreferredSize(d);
		this.setMaximumSize(d);
		this.setMinimumSize(d);

		frame.setResizable(false);
		
		frame.add(this);
		frame.pack();
	}

	public int getWidth() {
		return (int) (d.width / Driver.scale);
	}

	public int getHeight() {
		return (int) (d.height / Driver.scale);
	}

	public void close() {
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}
}
