package com.nofatclips.crawler.planning.sensors_utils;

/*
 * NOTE:
 * latitude range -90/+90
 * longiutude range -180/+180
 */
public class GpsValuesGenerator {

	public static double getRandomLatitude()
	{
		double min = 0;
		double max = 180;
		double mid = 90; //per generare numeri negativi
				
		return Math.random() * ( max - min ) - mid;
	}
	
	public static double getRandomLongitude()
	{
		double min = 0;
		double max = 360;
		double mid = 180; //per generare numeri negativi
				
		return Math.random() * ( max - min ) - mid;		
	}
	
	public static double getRandomAltitude()
	{
		double min = 0;
		double max = 1000;
		double mid = 0; //per generare numeri negativi
				
		return Math.random() * ( max - min ) - mid;
	}
}
