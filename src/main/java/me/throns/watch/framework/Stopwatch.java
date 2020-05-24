package me.throns.watch.framework;
/*
 *  created by throns on 22/05/2020
 */

public class Stopwatch{

	private long time;

	public Stopwatch(){
		this.time = System.currentTimeMillis();
	}

	public boolean reached(double seconds){
		return elapsedTime() >= seconds;
	}

	public double elapsedTime(){
		return (System.currentTimeMillis() - time) / 1000.0D;
	}

	public double elapsedTime(int precision){
		int scale = (int) Math.pow(10, precision);
		return (double) Math.round((System.currentTimeMillis() - time) * scale) / scale;
	}

	public void reset(){
		time = System.currentTimeMillis();
	}

	public void fastForward(double seconds){
		time -= (seconds * 1000.0);
	}
}
