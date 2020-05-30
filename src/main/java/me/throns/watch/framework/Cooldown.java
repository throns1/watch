package me.throns.watch.framework;

/**
 * Class to store cooldowns information
 *
 * @author throns
 * @website http://throns.me/
 */

public class Cooldown{

	/* cooldown label to identify this object */
	private String label;
	/* duration in seconds */
	private double duration;
	/* timer object to keep track of the cooldown */
	private Stopwatch stopwatch;

	/**
	 *
	 * @param label - cooldown label identification
	 * @param duration - cooldown duration in seconds
	 */
	public Cooldown(String label, double duration){
		this.label = label;
		this.duration = duration;
		this.stopwatch = new Stopwatch();
	}

	/**
	 * Get the cooldown label identifier
	 * @return label
	 */
	public String getLabel(){
		return label;
	}

	/**
	 * Get the cooldown duration in seconds
	 * @return duration
	 */
	public double getDuration(){
		return duration;
	}

	/**
	 * Get the timer object to keep track
	 * @return timer object
	 */
	public Stopwatch getStopwatch(){
		return stopwatch;
	}

	/**
	 * Get the duration left before the cooldown ends
	 * @return duration left
	 */
	public double getDurationLeft(){
		return duration - stopwatch.elapsedTime();
	}

	/**
	 * Reset the cooldown timer to zero seconds
	 */
	public void reset(){
		stopwatch.reset();
	}
}