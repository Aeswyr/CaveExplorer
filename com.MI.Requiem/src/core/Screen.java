package core;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import runtime.Handler;
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
			GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
			frame.setUndecorated(true);
			gd.setFullScreenWindow(frame);
			d.setSize(gd.getFullScreenWindow().getWidth(), gd.getFullScreenWindow().getHeight());
			this.setSize(d);
			this.setPreferredSize(d);
			this.setMaximumSize(d);
			this.setMinimumSize(d);
		} else if (width == 0 && height == 0) {
			GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			frame.setUndecorated(true);
			d.setSize(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
			frame.setSize(d);
			frame.setPreferredSize(d);
			frame.setMinimumSize(d);
			frame.setMaximumSize(d);
			this.setSize(d);
			this.setPreferredSize(d);
			this.setMaximumSize(d);
			this.setMinimumSize(d);
		} else {
			d.setSize(width, height);
			frame.setSize(d);
			frame.setPreferredSize(d);
			frame.setMinimumSize(d);
			frame.setMaximumSize(d);
			this.setSize(d);
			this.setPreferredSize(d);
			this.setMaximumSize(d);
			this.setMinimumSize(d);
		}

		frame.setTitle("Unto the Abyss");
		frame.setName("Unto the Abyss");

		frame.add(this);
		frame.pack();

		frame.setVisible(true);
		System.out.println(d.width + ", " + d.height);
	}

	public int getWidth() {
		return d.width;
	}

	public int getHeight() {
		return d.height;
	}

	public void close() {
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

	public void setClosing(Driver d, Renderer r, Handler h) {
		frame.addWindowListener(new WindowAdapter() // Operations to complete upon window closing
		{
			public void windowClosing(WindowEvent we) {
				d.stop();
				r.stop();
				try {
					Sound.shutdown();
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
				h.getWorld().unloadWorld();
				System.out.println("Game Closed");
			}
		});
	}
}
