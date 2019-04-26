package sfx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

import utility.Loader;

/**
 * Represents a single sound effect that can be played within the game
 * @author Pascal
 *
 */
public class Sound {

	/**
	 * path to the desired sound effect file
	 */
	String path;
	
	/**
	 * Array containing a number of usable clips to play this sound effect
	 */
	private Clip[] clips;
	private final int MAX_CHANNELS = 5;
	
	/**
	 * Initializes a new sound effect by filling the clips array with a number of clips ready to play
	 * the desired effect
	 * @param path - the path to the desired sound effect
	 */
	public Sound(String path) {	
		this.path = path;
		clips = new Clip[MAX_CHANNELS];
		
		
		for (int i = 0; i < MAX_CHANNELS; i++) {
			try {
				clips[i] = AudioSystem.getClip();
				clips[i].open(Loader.loadURLAudio(path));
			} catch (LineUnavailableException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * Creates a SoundInstance using an unused clip and adds that clip to the thread pool to play
	 */
	public void play() {
		for (Clip c : clips) {
			if (!c.isActive()) {
				Sound.add(new SoundInstance(c));
				break;
			}
		}
	}

	/**
	 * Creates a SoundInstance using an unused clip, marking it as looped and adds that clip to
	 * the thread pool to play until stopped
	 */
	public void loop() {
		
		for (Clip c : clips) {
			if (!c.isActive()) {
				SoundInstance instance = new SoundInstance(c);
				instance.tagLooped();
				loops.add(instance);
				Sound.add(instance);
				break;
			}
		}
	}

	/**
	 * a collection of all currently looped sounds
	 */
	private static ArrayList<SoundInstance> loops;
	
	/**
	 * thread pool for handling sounds
	 */
	private static ExecutorService pool;

	/**
	 * initializes the sound thread pool and loop array
	 */
	public static void InitSound() {
		pool = Executors.newFixedThreadPool(5);
		loops = new ArrayList<SoundInstance>();
	}

	/**
	 * adds a SoundInstance to the thread pool and runs it
	 * @param instance - the SoundInstance to be played
	 */
	public static void add(SoundInstance instance) {
		pool.execute(instance);
	}
	
	/**
	 * stop all currently looped sound effects, then shuts down the thread pool
	 */
	public static void shutdown() {
		for (SoundInstance instance : loops) {
			instance.stopLoop();
		}
		
		pool.shutdown();
	}



}