package com.nofatclips.crawler.planning.sensors_utils;

/*
 * NOTE:
 * latitude range -90/+90
 * longitude range -180/+180
 * altitude ?
 * TODO: bearing ?
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
	
	public static double getRandomPositiveLatitude()
	{
		double min = 0;
		double max = 90;		
				
		return Math.random() * ( max - min );
	}
	
	public static double getRandomPositiveLongitude()
	{
		double min = 0;
		double max = 180;		
				
		return Math.random() * ( max - min );		
	}
	
	public static double getRandomPositiveAltitude()
	{
		double min = 0;
		double max = 1000;
		double mid = 0; //per generare numeri negativi
				
		return Math.random() * ( max - min );
	}
	
	public static double getRandomNegativeLatitude()
	{
		return -1 * getRandomPositiveLatitude();
	}
	
	public static double getRandomNegativeLongitude()
	{
		return -1 * getRandomPositiveLongitude();		
	}
	
	public static double getRandomNegativeAltitude()
	{
		return -1 * getRandomPositiveAltitude();
	}
}
