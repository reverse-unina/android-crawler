package com.nofatclips.crawler.storage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import android.app.Activity;
import android.content.ContextWrapper;

import com.nofatclips.androidtesting.model.Session;
import com.nofatclips.androidtesting.model.Trace;
import com.nofatclips.androidtesting.xml.XmlGraph;
import com.nofatclips.crawler.model.Persistence;

public class DiskPersistence implements Persistence {
	
	public DiskPersistence () {
	}
	
	public DiskPersistence (Session theSession) {
		this();
		setSession(theSession);
	}

	@Override
	public void setFileName(String name) {
		this.fileName = name;
	}

	@Override
	public void setSession(Session s) {
		this.theSession = s;
	}
	
	public void setContext(Activity a) {
		this.w = new ContextWrapper(a);
	}

	@Override
	public void addTrace(Trace t) {
		this.theSession.addTrace(t);
	}

	@Override
	public void save() {
		save (this.fileName);
	}
	
	private void save (String fileName) {
		
		// Generate text
		String graph = "";
		try {
			if (this.theSession instanceof XmlGraph) {
				graph = ((XmlGraph)this.theSession).toXml();
			} else {
				graph = this.theSession.toString();
			}
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Write text to disk
		createFile();
		try {
			this.osw.write(graph);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeFile();
		}
	}
	
	public void createFile () {
		try{
			this.fOut = w.openFileOutput(this.fileName, ContextWrapper.MODE_PRIVATE);
			this.osw = new OutputStreamWriter(fOut);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void closeFile () {
		try {
			this.osw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				this.osw.close();
				this.fOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	FileOutputStream fOut = null; 
    OutputStreamWriter osw = null;
    ContextWrapper w = null;
	private String fileName;
	private Session theSession;

}