package com.nofatclips.crawler.automation.sensors_utils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import android.util.Log;

/**
 * Classe contenente metodi che utilizzano la reflection 
 * 
 * @author nicola
 *
 */
public class ReflectorHelper
{
	
	/**
	 * Controlla se una classe implementa l'interfaccia data
	 * 
	 * @param className CanonicalName della classe
	 * @param interfaceName CanonicalName dell'interfaccia
	 * @return esito della ricerca
	 * @throws ClassNotFoundException
	 */
	public static boolean implementsInterface(String className, String interfaceName) throws ClassNotFoundException
	{
		Class<?> myClass = (Class<?>) Class.forName(className);
		return ReflectorHelper.implementsInterface(myClass, interfaceName);
	}
	
	/**
	 * Controlla se una classe implementa l'interfaccia data
	 * 
	 * @param myClass Classe da scansionare
	 * @param interfaceName CanonicalName dell'interfaccia
	 * @return esito della ricerca
	 */
	public static boolean implementsInterface(Class<?> myClass, String interfaceName)
	{
		for (Class<?> myInterface : myClass.getInterfaces())
		{
			//Log.v("LOG", myInterface.toString());
			if (myInterface.getCanonicalName().equals(interfaceName))
				return true;
		}
		return false;
	}
	
	/**
	 * Scansiona una classe e le sue variabili alla ricerca di qualcosa che implementi
	 * un'interfaccia.
	 * 
	 * Inoltre controlla se ci sono variabili definite inline del tipo dell'interfaccia
	 * 
	 * @param className CanonicalName della classe
	 * @param interfaceName CanonicalName dell'interfaccia
	 * @return esito della scansione
	 * @throws ClassNotFoundException
	 */
	public static boolean scanClassForInterface(String className, String interfaceName) throws ClassNotFoundException
	{
		Class<?> myClass = (Class<?>) Class.forName(className);
		return ReflectorHelper.scanClassForInterface(myClass, interfaceName);
	}
	
	/* NOTA:
	 *      if (field.getType().isAssignableFrom(myType))
	 */
	
	/**
	 * Scansiona una classe e le sue variabili alla ricerca di qualcosa che implementi
	 * un'interfaccia.
	 * 
	 * Inoltre controlla se ci sono variabili definite inline del tipo dell'interfaccia
	 * 
	 * @param myClass Classe da scansionare
	 * @param interfaceName CanonicalName dell'interfaccia
	 * @return esito della scansione
	 * @return
	 */
	public static boolean scanClassForInterface(Class<?> myClass, String interfaceName)
	{
		//controllo se la classe implementa lei stessa l'interfaccia 
		if ( ReflectorHelper.implementsInterface(myClass, interfaceName))
		{
			Log.v("ReflectorHelper", "Found interface : " + interfaceName + " in " + myClass.getCanonicalName());
			return true;
		}
		//controllo se un campo implementa l'interfaccia
		for(Field field : myClass.getDeclaredFields() )
		{
			Class<?> fieldClass = field.getType();
								
			//controlla se implementa l'interfaccia
			if ( ReflectorHelper.implementsInterface(fieldClass, interfaceName))
			{
				Log.v("ReflectorHelper", "Found field implements : " + interfaceName  + " in " + fieldClass.getCanonicalName());
				return true;
			}
			
			//controlla se e' una definizione inline
			if (fieldClass.getCanonicalName().equals(interfaceName))
			{
				Log.v("ReflectorHelper", "Found field inline definition : " + interfaceName  + " in " + fieldClass.getCanonicalName());
				return true;
			}
		}
		
		Log.v("ReflectorHelper", "Not found : " + interfaceName  + " in " + myClass.getCanonicalName());
		return false;
	}
}
