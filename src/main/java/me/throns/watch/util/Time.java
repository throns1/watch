package me.throns.watch.util;
/*
 *  created by throns on 22/05/2020
 */

import java.util.concurrent.TimeUnit;

public class Time{

	public static double convertTimeUnit(double amount, TimeUnit from, TimeUnit to){
		return from.ordinal() < to.ordinal() ? amount / from.convert(1, to) : amount * to.convert(1, from);
	}
}
