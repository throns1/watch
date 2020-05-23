package me.throns.watch.framework;
/*
 *  created by throns on 22/05/2020
 */

public class Cooldown{

	private String label;
	private double duration;
	private Stopwatch stopwatch;

	public Cooldown(String label, double duration){
		this.label = label;
		this.duration = duration;
		this.stopwatch = new Stopwatch();
	}

	public String getLabel(){
		return label;
	}

	public void setLabel(String label){
		this.label = label;
	}

	public double getDuration(){
		return duration;
	}

	public void setDuration(double duration){
		this.duration = duration;
	}

	public Stopwatch getStopwatch(){
		return stopwatch;
	}

	public double getDurationLeft(){
		return duration - stopwatch.elapsedTime();
	}
}
