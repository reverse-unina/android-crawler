package com.nofatclips.crawler.helpers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;

/**
 * Classe contenente metodi che utilizzano la reflection 
 * 
 * @author nicola
 *
 */
public class ReflectionHelper
{
	public static final String TAG = "ReflectionHelper";
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
		return ReflectionHelper.implementsInterface(myClass, interfaceName);
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
			//Log.v(TAG, myInterface.toString());
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
		return ReflectionHelper.scanClassForInterface(myClass, interfaceName);
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
		if ( ReflectionHelper.implementsInterface(myClass, interfaceName))
		{
			Log.v(TAG, "Found interface : " + interfaceName + " in " + myClass.getCanonicalName());
			return true;
		}
		//controllo se un campo implementa l'interfaccia
		for(Field field : myClass.getDeclaredFields() )
		{
			Class<?> fieldClass = field.getType();
								
			//controlla se implementa l'interfaccia
			if ( ReflectionHelper.implementsInterface(fieldClass, interfaceName))
			{
				Log.v(TAG, "Found field implements : " + interfaceName  + " in " + fieldClass.getCanonicalName());
				return true;
			}
			
			//controlla se e' una definizione inline
			if (fieldClass.getCanonicalName().equals(interfaceName))
			{
				Log.v(TAG, "Found field inline definition : " + interfaceName  + " in " + fieldClass.getCanonicalName());
				return true;
			}
		}
		
		Log.v(TAG, "Not found : " + interfaceName  + " in " + myClass.getCanonicalName());
		return false;
	}
		
	/**
	 * Utilizzando la Reflection ottiene i listener associati ad un oggetto View
	 * 
	 * @param view View da esaminare
	 * @return 	risultato sotto forma di HashMap<String, Boolean>, che associa il nome del
	 * 			listener alla sua effettiva esistenza
	 */
	public static HashMap<String, Boolean> reflectViewListeners(android.view.View view)
    {
		HashMap<String, Boolean> ret = new HashMap<String, Boolean>();
		
		ret.put( "OnFocusChangeListener", checkIfFieldIsSet(view, "mOnFocusChangeListener") );
		ret.put( "OnClickListener", checkIfFieldIsSet(view, "mOnClickListener") );
		ret.put( "OnLongClickListener", checkIfFieldIsSet(view, "mOnLongClickListener") );
		ret.put( "OnCreateContextMenuListener", checkIfFieldIsSet(view, "mOnCreateContextMenuListener") );
		ret.put( "OnKeyListener", checkIfFieldIsSet(view, "mOnKeyListener") );
		ret.put( "OnTouchListener", checkIfFieldIsSet(view, "mOnTouchListener") );
		
    	return ret;
    }
	
	/**
	 * Utilizzando la Reflection ottiene i listener associati ad un oggetto EditText
	 * 
	 * @param editText EditText da esaminare
	 * @return 	risultato sotto forma di HashMap<String, Boolean>, che associa il nome del
	 * 			listener alla sua effettiva esistenza
	 */
	public static HashMap<String, Boolean> reflectEditTextListeners(android.widget.EditText editText)
	{
		HashMap<String, Boolean> ret = new HashMap<String, Boolean>();
		
		//EditText e' una view
		ret.putAll( reflectViewListeners(editText) );

		//addTextChangedListener
		ret.put( "OnTextChangedListener", checkIfArrayListFieldIsSet(editText, "mListeners") );
		
		return ret;
	}
	
	public static boolean checkIfFieldIsSet(Object o, String fieldName)
	{
		java.lang.reflect.Field field;
		
		try
    	{
			//TODO: cache
			Class<?> viewObj = Class.forName("android.view.View");
			field = viewObj.getDeclaredField(fieldName);
			field.setAccessible(true);
			
			boolean ret = (field.get(o) != null);
			Log.v(TAG, o.getClass().getCanonicalName() + " > " + fieldName + " FOUND | " + ((ret)?"ACTIVE":"NOT ACTIVE") );
			return ret;
			
			/* NOTA:
			 * Senza log
			 * 		return (field.get(o) != null);
			 */
    	}
		catch (Exception e)
		{
			Log.e(TAG, e.toString());
		}
		
		Log.v(TAG, o.getClass().getCanonicalName() + " > " + fieldName + " NOT FOUND");
		
		return false;
	}
	
	public static boolean checkIfArrayListFieldIsSet(Object o, String fieldName)
	{
		java.lang.reflect.Field field;
		
		try
    	{
			//TODO: cache
			Class<?> viewObj = Class.forName("android.view.View");
			field = viewObj.getDeclaredField(fieldName);
			field.setAccessible(true);
			
			ArrayList arrayListField = (ArrayList) field.get(o);
			
			if (arrayListField != null)
			{
				if (arrayListField.size() > 0)
				{
					Log.v(TAG, o.getClass().getCanonicalName() + " > " + fieldName + " FOUND | ACTIVE" );
					return true;
				}
				else
				{
					Log.v(TAG, o.getClass().getCanonicalName() + " > " + fieldName + " FOUND | NOT ACTIVE" );
				}
				
				/* NOTA:
				 * Senza log
				 * 		return (arrayListField.size() > 0);
				 */
			}			
    	}
		catch (Exception e)
		{
			Log.e(TAG, e.toString());
		}
		
		Log.v(TAG, o.getClass().getCanonicalName() + " > " + fieldName + " NOT FOUND");
		
		return false;
	}
	
	/*
	private static void enableAllViewClassListenerFields()
	{
		Field field = null;
		Class<?> viewObj = null;
		
		try {
			viewObj = Class.forName("android.view.View");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Are you on Android?");
		}
		
		try
		{
			field = viewObj.getDeclaredField("mOnFocusChangeListener");
			field.setAccessible(true);
		}
		catch (Exception ex) {}
		
		try
		{
			field = viewObj.getDeclaredField("mOnClickListener");
			field.setAccessible(true);
		}
		catch (Exception ex) {}
		
		try
		{
			field = viewObj.getDeclaredField("mOnLongClickListener");
			field.setAccessible(true);
		}
		catch (Exception ex) {}
		
		try
		{
			field = viewObj.getDeclaredField("mOnCreateContextMenuListener");
			field.setAccessible(true);
		}
		catch (Exception ex) {}
		
		try
		{
			field = viewObj.getDeclaredField("mOnKeyListener");
			field.setAccessible(true);
		}
		catch (Exception ex) {}
		
		try
		{
			field = viewObj.getDeclaredField("mOnTouchListener");
			field.setAccessible(true);
		}
		catch (Exception ex) {}
	}
	*/	
}
