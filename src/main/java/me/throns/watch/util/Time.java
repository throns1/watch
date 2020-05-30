package me.throns.watch.util;

import java.util.concurrent.TimeUnit;

/**
 * Class util to convert TimeUnits
 *
 * @author throns
 * @website http://throns.me/
 */

public class Time{

	/**
	 * Convert a TimeUnit into another one
	 * @param amount - timeunit amount. ex. TimeUnit before is Days, amount
	 *               is 1. It would equal to 24 hours, 1440 minutes, and so on.
	 * @param from - unit converted from
	 * @param to - unit converted into
	 * @return time in seconds
	 */
	public static double convertTimeUnit(double amount, TimeUnit from, TimeUnit to){
		return from.ordinal() < to.ordinal() ? amount / from.convert(1, to) : amount * to.convert(1, from);
	}
}
