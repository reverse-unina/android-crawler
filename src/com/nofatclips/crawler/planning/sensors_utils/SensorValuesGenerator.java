package com.nofatclips.crawler.planning.sensors_utils;

/**
 * NOTA: range presi dal Samsung Galaxy S i9003
 *
 */
public class SensorValuesGenerator
{
	public static float[] generateSensorValues(int SENSOR_TYPE)
	{
		switch (SENSOR_TYPE)
		{
			case android.hardware.Sensor.TYPE_ACCELEROMETER:
				return generateAccelerometerValues();
				
			case android.hardware.Sensor.TYPE_ORIENTATION:
				return generateOrientationValues();
				
			case android.hardware.Sensor.TYPE_MAGNETIC_FIELD:
				return generateMagneticFieldValues();
				
			case android.hardware.Sensor.TYPE_TEMPERATURE:
				return generateTemperatureValues();
				
			default:
				float[] f = {0f, 0f , 0f};
				return f;
		}
	}
	
	/*
	 * Orientation e' un insieme di MagneticField + Accelerometer 
	 */
	public static float[] generateOrientationValues()
	{
		float min = 0;
		float max = 360;
		float mid = 0; //per generare numeri negativi
		
		float[] f = new float[3];
		f[0] = ( (float)Math.random() * ( max - min ) ) - mid;
		
		min = 0; max = 20; mid = 10;
		f[1] = ( (float)Math.random() * ( max - min ) ) - mid;
		f[2] = ( (float)Math.random() * ( max - min ) ) - mid;
		
		return f;
	}
	
	public static float[] generateAccelerometerValues()
	{
		float min = 0;
		float max = (float)19.6133;
		float mid = 0; //per generare numeri negativi
		
		float[] f = new float[3];
		f[0] = ( (float)Math.random() * ( max - min ) ) - mid;
		f[1] = ( (float)Math.random() * ( max - min ) ) - mid;
		f[2] = ( (float)Math.random() * ( max - min ) ) - mid;
		
		return f;
	}
	
	public static float[] generateMagneticFieldValues()
	{
		float min = 0;
		float max = 300;
		float mid = 0; //per generare numeri negativi
		
		float[] f = new float[3];
		f[0] = ( (float)Math.random() * ( max - min ) ) - mid;
		f[1] = ( (float)Math.random() * ( max - min ) ) - mid;
		f[2] = ( (float)Math.random() * ( max - min ) ) - mid;
		
		return f;
	}
	
	//GRADI CELSIUS
	public static float[] generateTemperatureValues()
	{
		return generateAmbientTemperatureValues();
	}
	
	public static float[] generateAmbientTemperatureValues()
	{
		float min = 0;
		float max = 300;
		float mid = 150; //per generare numeri negativi
		
		float[] f = new float[3];
		f[0] = ( (float)Math.random() * ( max - min ) ) - mid;
		
		//ignorati
		f[1] = 0f;//( (float)Math.random() * ( max - min ) ) - mid;
		f[2] = 0f;//( (float)Math.random() * ( max - min ) ) - mid;
		
		return f;	
	}
}
