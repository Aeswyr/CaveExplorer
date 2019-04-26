package sfx;

import javax.sound.sampled.Clip;

/**
 * An instance of executed sound
 * @author Pascal
 *
 */
public class SoundInstance implements Runnable {

	/**
	 * the clip played when this SoundInstance is executed
	 */
	public Clip clip;

	/**
	 * initializes a new SoundInstance containing a clip from the originating Sound object
	 * @param clip - the clip linked to this SoundInstance
	 */
	public SoundInstance(Clip clip) {
		this.clip = clip;
	}

	/**
	 * run method called when this executes as a thread. Plays the contained sound effect then resets its
	 * frame position to zero
	 * @Override
	 */
	public void run() {
		clip.start();
			
		clip.setFramePosition(0);
	}
	
	/**
	 * mark the contained sound effect as looping so the sound continues to play until manually stopped
	 */
	public void tagLooped() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	/**
	 * forcefully stop the contained clip, allowing the run method to end
	 */
	public void stopLoop() {
		clip.stop();
	}

}
