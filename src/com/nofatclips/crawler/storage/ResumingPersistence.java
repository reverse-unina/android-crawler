package com.nofatclips.crawler.storage;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import android.content.ContextWrapper;
import android.util.Log;

import com.nofatclips.androidtesting.guitree.FinalActivity;
import com.nofatclips.androidtesting.model.*;
import com.nofatclips.androidtesting.xml.ElementWrapper;
import com.nofatclips.crawler.model.*;

public class ResumingPersistence extends StepDiskPersistence implements DispatchListener, StateDiscoveryListener, TerminationListener {

	private List<Trace> taskList;
	private String activityFile;
	private String taskListFile;
	private String parametersFile;
	private FileOutputStream taskFile;
	private OutputStreamWriter taskStream;
	private FileOutputStream stateFile;
	private OutputStreamWriter stateStream;
	private Map<String, SessionParams> parameters = new Hashtable<String, SessionParams>();
	private Hashtable<String,SaveStateListener> theListeners = new Hashtable<String,SaveStateListener>();
	
	public ResumingPersistence () {
		super(1);
	}
	
	public ResumingPersistence (Session theSession) {
		super(theSession, 1);
	}

	@Override 
	public void addTrace (Trace t) {
		t.setFailed(false);
		super.addTrace(t);
	}
	
	public void onNewTaskAdded (Trace t) { /* do nothing */ }
	
	public void onTaskDispatched(Trace t) {
		t.setFailed(true);
		saveTaskList();
		saveParameters();
	}
	
	public void saveTaskList() {
		// No tasks to save
		if (noTasks()) {
			Log.d("nofatclips", "Task list is empty. Deleting file from disk.");
			delete (getTaskListFileName());
			return;
		}
		
		// Creating a copy of the backup file
		if (exists(getTaskListFileName())) {
			Log.d("nofatclips", "Performing backup of the old task list on disk");
			backupFile (getTaskListFileName());
		}

		// Saving tasks in XML format - the old content of tasklist.xml is deleted
		try {
			Log.d("nofatclips", "Saving task list on disk");
			openTaskFile();
			for (Trace task: this.taskList) {
				Log.v("nofatclips", "Backing up trace #" + task.getId() + " to disk.");
				String xml = ((ElementWrapper)task).toXml() + System.getProperty("line.separator");
				writeOnTaskFile(xml);
			}
			closeTaskFile();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Deleting backup copies
		if (exists(backup(this.taskListFile))) {
			Log.d("nofatclips", "Deleting backup of the old task list from disk");
			delete (backup(this.taskListFile));
		}
		if (exists(backup(this.activityFile))) {
			Log.d("nofatclips", "Deleting backup of the old activity list from disk");
			delete (backup(this.activityFile));
		}

	}
	
	public boolean canHasResume () {
		if (!exists(getFileName())) {
			Log.d("nofatclips", "No session to resume: GUI Tree not found. Will start from scratch.");
			return false; // GUI Tree not found
		}
		if (!exists(getActivityFileName())) throw new Error("Cannot resume previous session: state list not found.");
		if (exists(backup(getTaskListFileName()))) {
			Log.d("nofatclips", "Restoring backup of the task list");
			restoreFile(getTaskListFileName());
			if (exists(backup(getActivityFileName()))) {
				Log.d("nofatclips", "Restoring backup of the activity list");
				restoreFile(getActivityFileName());
			}
		}
		if (!exists(getTaskListFileName())) {
			Log.d("nofatclips", "No session to resume: task list not found. Will start from scratch.");
			return false;
		}
		return true;
	}
	
	public void backupFile (String fileName) {
		copy(fileName,backup(fileName));
	}
	
	public void restoreFile (String fileName) {
		copy(backup(fileName),fileName);
	}	
	
	public String backup (String original) {
		return original + ".bak";
	}
	
	public void saveParameters() {
		parameters.clear();
		FileOutputStream theFile = null;
		ObjectOutputStream theStream = null;
		
		for (Entry<String, SaveStateListener> listener: this.theListeners.entrySet()) {
			parameters.put(listener.getKey(), listener.getValue().onSavingState());
		}
		
		try {
			theFile = w.openFileOutput(getParametersFileName(), ContextWrapper.MODE_PRIVATE);
			theStream = new ObjectOutputStream(theFile);
			theStream.writeObject(this.parameters);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (theFile!=null && theStream!=null) {
				closeFile(theFile, theStream);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void loadParameters() {
		FileInputStream theFile = null;
		ObjectInputStream theStream = null;
		
		try {
			theFile = w.openFileInput(getParametersFileName());
			theStream = new ObjectInputStream(theFile);
			this.parameters = (Map<String, SessionParams>) theStream.readObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException ignore) {}
		
		for (Entry<String, SaveStateListener> listener: this.theListeners.entrySet()) {
			listener.getValue().onLoadingState(this.parameters.get(listener.getKey()));
		}
	}
	
	public void onNewState(ActivityState newState) {

		// Creating a copy of the state file
		if (exists(getActivityFileName())) {
			Log.d("nofatclips", "Performing backup of the old activity list on disk");
			backupFile (getActivityFileName());
		}
		
		// Updating the file
		try {
			Log.d("nofatclips", "Saving new found state '" + newState.getId() + "' on disk");
			openStateFile(newState instanceof FinalActivity); // append if final activity - write from scratch if start activity
			String xml = ((ElementWrapper)newState).toXml() + System.getProperty("line.separator");
			writeOnStateFile(xml);
			closeStateFile();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void save() {
		super.save();
		saveParameters();
		saveTaskList();
		if (noTasks()) { // Deletes file when crawling is over
			Log.d("nofatclips", "Task list is empty: no resume needed. Deleting parameters and activity list from disk.");
//			delete (getActivityFileName());
			delete (backup(getActivityFileName()));
			delete (getParametersFileName());
			delete (backup(getParametersFileName()));
			delete (getTaskListFileName());
			delete (backup(getTaskListFileName()));
			
			// Creazione del file closed (AKA pezzottone)
			
			FileOutputStream fOut;
			try {
				fOut = w.openFileOutput("closed.txt", ContextWrapper.MODE_WORLD_READABLE);
				OutputStreamWriter osw = new OutputStreamWriter(fOut); 
				osw.write("the end");
				osw.flush();
				osw.close();
				fOut.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Fine Pezzottone
			
			return;
		}
	}

	@Override
	public boolean isLast() {
		return ( (super.isLast()) && noTasks() );
	}
	
	public void onTerminate() {
		this.taskList.clear();
	}

	
	public List<String> readTaskFile () {
		FileInputStream theFile;
		BufferedReader theStream = null;
		String line;
		List<String> output = new ArrayList<String>();
		Log.i("nofatclips", "Reading task file");
		try{
			theFile = w.openFileInput (getTaskListFileName());
			theStream = new BufferedReader (new FileReader (theFile.getFD()));
			while ( (line = theStream.readLine()) != null) {
				output.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}
	
	public List<String> readStateFile () {
		FileInputStream theFile;
		BufferedReader theStream = null;
		String line;
		List<String> output = new ArrayList<String>();
		Log.i("nofatclips", "Reading state file");
		try{
			theFile = w.openFileInput (getActivityFileName());
			theStream = new BufferedReader (new FileReader (theFile.getFD()));
			while ( (line = theStream.readLine()) != null) {
				output.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}

	public void openTaskFile () {
		try{
			this.taskFile = w.openFileOutput(getTaskListFileName(), ContextWrapper.MODE_PRIVATE);
			this.taskStream = new OutputStreamWriter(this.taskFile);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void openStateFile () {
		openStateFile (true);
	}

	public void openStateFile (boolean append) {
		try{
			Log.v("nofatclips", "Opening state file in " + ((append)?"append":"overwrite") + " mode.");
			this.stateFile = w.openFileOutput(getActivityFileName(), (append)?ContextWrapper.MODE_APPEND:ContextWrapper.MODE_PRIVATE);
			this.stateStream = new OutputStreamWriter(this.stateFile);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writeOnTaskFile (String graph) throws IOException {
		writeOnFile (this.taskStream, graph);
	}

	public void writeOnStateFile (String graph) throws IOException {
		writeOnFile (this.stateStream, graph);
	}

	public void closeTaskFile () {
		closeFile(this.taskFile, this.taskStream);
	}

	public void closeStateFile () {
		closeFile(this.stateFile, this.stateStream);
	}

	public boolean noTasks() {
		return (this.taskList.size()==0);
	}
	
	public void setTaskList(List<Trace> taskList) {
		this.taskList = taskList;
	}

	public String getActivityFileName() {
		return activityFile;
	}

	public void setActivityFile(String activityFile) {
		this.activityFile = activityFile;
	}

	public String getParametersFileName() {
		return parametersFile;
	}

	public void setParametersFile(String name) {
		this.parametersFile = name;
	}

	public String getTaskListFileName() {
		return taskListFile;
	}

	public void setTaskListFile(String taskListFile) {
		this.taskListFile = taskListFile;
	}
	
	public void registerListener (SaveStateListener listener) {
		theListeners.put(listener.getListenerName(), listener);
	}

}
