package particle;

import java.util.ArrayList;

import gfx.DrawGraphics;

public class ParticleManager {

	private ArrayList<Particle> particles;

	public ParticleManager() {
		particles = new ArrayList<Particle>();
	}

	public void update() {
		for (int i = 0; i < particles.size(); i++) {
			if (particles.get(i).isDead()) {
				particles.remove(i);
				i--;
			} else {
				particles.get(i).update();
			}
		}
	}

	public void render(DrawGraphics g) {
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).render(g);
		}
	}
	
	public void add(Particle p) {
		particles.add(p);
	}
}
