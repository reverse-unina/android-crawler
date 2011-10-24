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
	
	public Session getSession() {
		return this.theSession;
	}

	public String getFileName () {
		return this.fileName;
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
	
	protected String generate () {
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
		return graph;
	}
	
	protected void save (String fileName) {
		
		// Generate text
		String graph = generate();

		// Write text to disk
		openFile(fileName);
		try {
			writeOnFile (graph);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeFile();
		}
	}
	
	public void writeOnFile (String graph) throws IOException {
		this.osw.write(graph);
	}
	
	public void openFile (String fileName) {
		try{
			this.fOut = w.openFileOutput(fileName, this.mode);
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
	protected int mode = ContextWrapper.MODE_PRIVATE;

}