package com.nofatclips.crawler.model;

public interface SaveStateListener {
	
	public String getListenerName();
	public SessionParams onSavingState();
	public void onLoadingState(SessionParams sessionParams);

}
