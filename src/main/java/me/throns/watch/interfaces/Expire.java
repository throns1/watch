package me.throns.watch.interfaces;

public interface Expire{

	/**
	 * Method executed when a cooldown has reached
	 * the duration specified
	 */
	void onExpire();
}
