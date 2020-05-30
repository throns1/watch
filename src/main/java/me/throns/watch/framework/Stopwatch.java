package me.throns.watch.framework;

/**
 * Timer util class to keep track of time,
 * manage elapsed time restarting, forwarding
 * and checking if x amount of time passed
 * since the class creation or reset method
 *
 * @author throns
 * @website http://throns.me/
 */

public class Stopwatch{

	/* time field to store start */
	private long time;

	/**
	 * Initialize the time field to match
	 * current system time and calculate elapsed time
	 * from this point forward
	 */
	public Stopwatch(){
		this.time = System.currentTimeMillis();
	}

	/**
	 * Check if the timer has reached x seconds
	 * @param seconds - input time
	 * @return true if the input seconds are the same or
	 * more than the elapsed time
	 */
	public boolean reached(double seconds){
		return elapsedTime() >= seconds;
	}

	/**
	 * General usage which returns elapsed time in seconds
	 * with one decimal place.
	 * @return seconds in #.# format with one decimal place
	 */
	public double elapsedTime(){
		return (System.currentTimeMillis() - time) / 1000.0D;
	}

	/**
	 * Return elapsed time in seconds with the specified
	 * decimal places
	 * @param precision - decimal places
	 * @return seconds with specific decimal places
	 */
	public double elapsedTime(int precision){
		int scale = (int) Math.pow(10, precision);
		return (double) Math.round((System.currentTimeMillis() - time) * scale) / scale;
	}

	/**
	 * Reset the timer back to zero seconds
	 */
	public void reset(){
		time = System.currentTimeMillis();
	}

	/**
	 * Fast forward the timer in seconds
	 * @param seconds - input seconds
	 */
	public void fastForward(double seconds){
		time -= (seconds * 1000.0);
	}
}
