package com.nofatclips.crawler.storage;

import java.io.*;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import android.app.Activity;
import android.content.ContextWrapper;

import com.nofatclips.androidtesting.model.Session;
import com.nofatclips.androidtesting.model.Trace;
import com.nofatclips.androidtesting.xml.XmlGraph;
import com.nofatclips.crawler.model.Persistence;
import com.nofatclips.crawler.model.SaveStateListener;

public class DiskPersistence implements Persistence {
	
	public DiskPersistence () {
	}
	
	public DiskPersistence (Session theSession) {
		this();
		setSession(theSession);
	}

	public void setFileName(String name) {
		this.fileName = name;
	}

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

	public void addTrace(Trace t) {
		this.theSession.addTrace(t);
	}

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
		writeOnFile (this.osw, graph);
	}

	public void writeOnFile (OutputStreamWriter output, String graph) throws IOException {
		output.write(graph);
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

	public boolean delete (String fileName) {
		return w.deleteFile(fileName);
	}
	
	public boolean exists (String filename) {
		File file = w.getFileStreamPath(filename);
		return file.exists();
	}

	public void copy (String from, String to) {
		FileInputStream in;
		FileOutputStream out;
		byte[] buffer = new byte[4096];
		
		try {
			in = w.openFileInput(from);
			out = w.openFileOutput(to, ContextWrapper.MODE_PRIVATE);
			int n = 0;
			
			while ((n = in.read(buffer)) != -1) {
				out.write(buffer, 0, n);
			}

			in.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void closeFile () {
		closeFile (this.fOut, this.osw);
	}
	
	public void closeFile (FileOutputStream theFile, OutputStreamWriter theStream) {
		try {
			theStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				theStream.close();
				theFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void closeFile (OutputStream theFile, OutputStream theStream) {
		try {
			theStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				theStream.close();
				theFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void registerListener(SaveStateListener listener) { /* do nothing */ }

	FileOutputStream fOut = null; 
    OutputStreamWriter osw = null;
    ContextWrapper w = null;
	private String fileName;
	private Session theSession;
	protected int mode = ContextWrapper.MODE_PRIVATE;

}