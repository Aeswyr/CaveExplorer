package world;

import java.util.ArrayList;
import utility.Queue;

/**
 * A thread which can load and unload chunks queued into it
 * 
 * @author Pascal
 *
 */
public class ChunkLoader implements Runnable {

	boolean running = false;
	Queue<Chunk> load;
	Queue<Chunk> unload;
	volatile ArrayList<Chunk> active;
	ArrayList<Chunk> loading;
	long time;

	/**
	 * initializes a new chunkloader
	 */
	public ChunkLoader() {
		load = new Queue<Chunk>();
		unload = new Queue<Chunk>();
		active = new ArrayList<Chunk>();
		loading = new ArrayList<Chunk>();
	}

	/**
	 * checks every 10ms if a chunk has to be loaded and if so, loads it
	 * 
	 * @Override
	 */
	public void run() {
		long lastTime = System.nanoTime();
		long currTime;
		while (running) {
			currTime = System.nanoTime();
			if (currTime - lastTime > 10000000) {
				tick();
				lastTime = System.nanoTime();
			}
		}

	}

	/**
	 * queues a chunk to be loaded into active chunks
	 * 
	 * @param k - the chunk to be loaded
	 */
	public void load(Chunk k) {
		load.enqueue(k);
	}

	/**
	 * queues a chunk to be unloaded from active chunks
	 * 
	 * @param k - the chunk to be queued
	 */
	public void unload(Chunk k) {
		unload.enqueue(k);
	}

	/**
	 * @returns all currently loaded chunks
	 */
	public ArrayList<Chunk> getActive() {
			return this.active;
	}

	/**
	 * starts the chunkloader, creating a thread and running it
	 */
	public synchronized void start() {
		this.running = true;
		new Thread(this).start();
	}

	/**
	 * stops the chunkloader
	 */
	public synchronized void stop() {
		this.running = false;
	}

	/**
	 * handles loading and unloading each chunk inserted into the load and unload
	 * queues
	 */
	@SuppressWarnings("unchecked")
	private void tick() {
		if (!load.isEmpty() || !unload.isEmpty()) {
			if (!load.isEmpty()) {
				time = System.nanoTime();
				while (!this.load.isEmpty()) {

					if (!this.loading.contains(this.load.peek())) {
						this.load.peek().load();
						this.loading.add(this.load.dequeue());
					} else
						load.dequeue();
				}

				System.out.println("time for load operation: " + (System.nanoTime() - time) / 1000000.0 + "ms");
			}

			if (!unload.isEmpty()) {
				time = System.nanoTime();
				while (!unload.isEmpty()) {
					if (loading.contains(unload.peek())) {
						unload.peek().unload();
						loading.remove(unload.dequeue());
					} else
						unload.dequeue();
				}
				System.out.println("time for unload operation: " + (System.nanoTime() - time) / 1000000.0 + "ms");
			}
			organizeChunks();
				active = (ArrayList<Chunk>) loading.clone();

		}
	}

	public void organizeChunks() {
		for (int i = 1; i < loading.size(); i++) {
			int pos = i;
			Chunk test = loading.get(i);
			loading.remove(test);
			while (test.y < loading.get(pos - 1).y) {
				pos = pos - 1;
				if (pos == 0)
					break;
			}
			loading.add(pos, test);
		}
		for (int i = 1; i < loading.size(); i++) {
			int pos = i;
			Chunk test = loading.get(i);
			loading.remove(test);
			while (test.x < loading.get(pos - 1).x && test.y == loading.get(pos - 1).y) {
				pos = pos - 1;
				if (pos == 0)
					break;
			}
			loading.add(pos, test);
		}
	}

}
