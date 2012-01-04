package com.nofatclips.crawler.model;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

public class SessionParams implements Serializable {

	private static final long serialVersionUID = 2346125751510656206L;
	protected Map<String,String> values;
	
	public SessionParams () {
		this.values = new Hashtable<String,String>();
	}
	
	public SessionParams (String key, String value) {
		this();
		store (key,value);
	}

	public SessionParams (String key, int number) {
		this();
		store (key, number);
	}

	public SessionParams (String key, long number) {
		this();
		store (key, number);
	}

	public String get (String key) {
		return values.get(key);
	}
	
	public int getInt (String key) {
		return Integer.parseInt(values.get(key));
	}

	public long getLong (String key) {
		return Long.parseLong(values.get(key));
	}

	public boolean has (String key) {
		return values.containsKey(key);
	}
	
	public void store (String key, String value) {
		values.put(key, value);
	}

	public void store (String key, int number) {
		values.put(key, String.valueOf(number));
	}

	public void store (String key, long number) {
		values.put(key, String.valueOf(number));
	}

	public void store (SessionParams p) {
		values.putAll(p.values);
	}

}
