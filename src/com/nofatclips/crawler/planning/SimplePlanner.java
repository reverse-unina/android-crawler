package com.nofatclips.crawler.planning;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import android.util.Log;

import com.nofatclips.androidtesting.model.*;
import com.nofatclips.crawler.model.*;

import static com.nofatclips.crawler.Resources.*;
import static com.nofatclips.androidtesting.model.InteractionType.*;
//import static com.nofatclips.androidtesting.model.SimpleType.*;

public class SimplePlanner implements Planner {

	public final static boolean ALLOW_SWAP_TAB = true;
	public final static boolean NO_SWAP_TAB = false;
	public final static boolean ALLOW_GO_BACK = true;
	public final static boolean NO_GO_BACK = false;
	
	public Plan getPlanForActivity (ActivityState a) {
		return getPlanForActivity(a, !TAB_EVENTS_START_ONLY, ALLOW_GO_BACK);
	}

	public Plan getPlanForBaseActivity (ActivityState a) {
		return getPlanForActivity(a, ALLOW_SWAP_TAB, NO_GO_BACK);
	}

	public Plan getPlanForActivity (ActivityState a, boolean allowSwapTabs, boolean allowGoBack) {
		Plan p = new Plan();
		Log.i("nofatclips", "Planning for new Activity " + a.getName());
		for (WidgetState w: getEventFilter()) {
			Collection<UserEvent> events = getUser().handleEvent(w);
			for (UserEvent evt: events) {
				if (evt == null) continue;
				Collection<UserInput> inputs = new ArrayList<UserInput>();
				for (WidgetState formWidget: getInputFilter()) {
					List<UserInput> alternatives = getFormFiller().handleInput(formWidget); 
					UserInput inp = ((alternatives.size()>0)?alternatives.get(alternatives.size()-1):null);
					if (inp != null) {
						inputs.add(inp);
					}
				}
				Transition t = getAbstractor().createStep(a, inputs, evt);
				p.addTask(t);
			}
		}

		UserEvent evt;
		Transition t;
		
		// Special handling for pressing back button
		if (BACK_BUTTON_EVENT && allowGoBack) {
			evt = getAbstractor().createEvent(null, BACK);
			t = getAbstractor().createStep(a, new HashSet<UserInput>(), evt);
			Log.i("nofatclips", "Created trace to press the back button");
			p.addTask(t);
		}
		
		if (MENU_EVENTS) {
			evt = getAbstractor().createEvent(null, OPEN_MENU);
			t = getAbstractor().createStep(a, new HashSet<UserInput>(), evt);
			Log.i("nofatclips", "Created trace to press the menu button");
			p.addTask(t);
		}

		// Special handling for scrolling down
		if (SCROLL_DOWN_EVENT) {
			evt = getAbstractor().createEvent(null, SCROLL_DOWN);
			t = getAbstractor().createStep(a, new HashSet<UserInput>(), evt);
			Log.i("nofatclips", "Created trace to perform scrolling down");
			p.addTask(t);
		}

		if (ORIENTATION_EVENTS) {
			evt = getAbstractor().createEvent(null, CHANGE_ORIENTATION);
			t = getAbstractor().createStep(a, new HashSet<UserInput>(), evt);
			Log.i("nofatclips", "Created trace to change orientation");
			p.addTask(t);
		}

		/** @author nicola amatucci */
		if (USE_SENSORS && a.getUsesSensorsManager())
		{
			evt = null;
			for (Integer type : SENSOR_TYPES)
			{	
				float[] sensorInputValues = null;					
				String sensorInteractionType = null;
				
				switch(type)
				{
					case android.hardware.Sensor.TYPE_ACCELEROMETER: {
						Log.i("nofatclips", "Creating trace for accelerometer");
						sensorInteractionType = ACCELEROMETER_SENSOR_EVENT;
						sensorInputValues = com.nofatclips.crawler.planning.sensors_utils.SensorValuesGenerator.generateAccelerometerValues();							
						break;
					}
					
					case android.hardware.Sensor.TYPE_ORIENTATION: {
						Log.i("nofatclips", "Creating trace for orientation");
						sensorInteractionType = ORIENTATION_SENSOR_EVENT;
						sensorInputValues = com.nofatclips.crawler.planning.sensors_utils.SensorValuesGenerator.generateOrientationValues();
						break;
					}
					
					case android.hardware.Sensor.TYPE_MAGNETIC_FIELD: {
						Log.i("nofatclips", "Creating trace for magnetic field");
						sensorInteractionType = MAGNETIC_FIELD_SENSOR_EVENT;
						sensorInputValues = com.nofatclips.crawler.planning.sensors_utils.SensorValuesGenerator.generateMagneticFieldValues();
						break;
					}
					
					case android.hardware.Sensor.TYPE_TEMPERATURE: {
						Log.i("nofatclips", "Creating trace for temperature");
						sensorInteractionType = TEMPERATURE_SENSOR_EVENT;
						sensorInputValues = com.nofatclips.crawler.planning.sensors_utils.SensorValuesGenerator.generateTemperatureValues();
						break;
					}						

					/* TYPE_TEMPERATURE e' deprecato nelle api 14
					
					case android.hardware.Sensor.TYPE_AMBIENT_TEMPERATURE: {
						Log.i("nofatclips", "Creating trace for temperature");
						sensorInteractionType = AMBIENT_TEMPERATURE_SENSOR_EVENT;
						sensorInputValues = it.unina.android.utils.SensorValuesGenerator.generateAmbientTemperatureValues();
						break;
					}
					
					*/
				}
				
				//caso in cui il sensore non e' supportato
				if (sensorInteractionType == null) continue;
				
				//definisco gli input
				Collection<UserInput> inputs = new ArrayList<UserInput>();
				
				if (EXCLUDE_WIDGETS_INPUTS_IN_SENSORS_EVENTS == false)
				{
					for (WidgetState formWidget: getInputFilter()) {
						List<UserInput> alternatives = getFormFiller().handleInput(formWidget); 
						UserInput inp = ((alternatives.size()>0)?alternatives.get(alternatives.size()-1):null);
						if (inp != null) {
							inputs.add(inp);
						}
					}
				}

				//creo l'evento relativo al sensore
				String sensorInputValueStr = sensorInputValues[0] + "|" + sensorInputValues[1] + "|" + sensorInputValues[2];
				evt = getAbstractor().createEvent(null, sensorInteractionType);
				evt.setValue(sensorInputValueStr);
				t = getAbstractor().createStep(a, inputs, evt);
				p.addTask(t);
			}	
		}
		
		if (USE_GPS && a.getUsesLocationManager())
		{
			evt = null;
			
			//genero i valori di test
			float[] location = new float[3];
			location[0] = 20.0f; //latitude
			location[1] = 20.0f; //longitude
			location[2] = 10.0f; //altitude

			//definisco gli input
			Collection<UserInput> inputs = new ArrayList<UserInput>();
			
			if (EXCLUDE_WIDGETS_INPUTS_IN_SENSORS_EVENTS == false)
			{
				for (WidgetState formWidget: getInputFilter()) {
					List<UserInput> alternatives = getFormFiller().handleInput(formWidget); 
					UserInput inp = ((alternatives.size()>0)?alternatives.get(alternatives.size()-1):null);
					if (inp != null) {
						inputs.add(inp);
					}
				}
			}
			
			String locationInputValueStr = location[0] + "|" + location[1] + "|" + location[2];
			evt = getAbstractor().createEvent(null, LOCATION_CHANGE_EVENT);
			evt.setValue(locationInputValueStr);
			t = getAbstractor().createStep(a, inputs, evt);
			p.addTask(t);		
		}
		/** @author nicola amatucci */
		
		return p;
	}
	
	public Filter getEventFilter() {
		return this.eventFilter;
	}

	public void setEventFilter(Filter eventFilter) {
		this.eventFilter = eventFilter;
	}
	
	public Filter getInputFilter() {
		return this.inputFilter;
	}
	
	public void setInputFilter(Filter inputFilter) {
		this.inputFilter = inputFilter;
	}

	public EventHandler getUser() {
		return this.user;
	}

	public void setUser(EventHandler user) {
		this.user = user;
	}

	public InputHandler getFormFiller() {
		return this.formFiller;
	}

	public void setFormFiller(InputHandler formFiller) {
		this.formFiller = formFiller;
	}

	public Abstractor getAbstractor() {
		return this.abstractor;
	}

	public void setAbstractor(Abstractor abstractor) {
		this.abstractor = abstractor;
	}

	private Filter eventFilter;
	private Filter inputFilter;
	private EventHandler user;
	private InputHandler formFiller;
	private Abstractor abstractor;

}
