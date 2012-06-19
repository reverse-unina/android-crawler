package com.nofatclips.crawler.planning;

import static com.nofatclips.androidtesting.model.InteractionType.ACCELEROMETER_SENSOR_EVENT;
import static com.nofatclips.androidtesting.model.InteractionType.BACK;
import static com.nofatclips.androidtesting.model.InteractionType.CHANGE_ORIENTATION;
import static com.nofatclips.androidtesting.model.InteractionType.GPS_LOCATION_CHANGE_EVENT;
import static com.nofatclips.androidtesting.model.InteractionType.MAGNETIC_FIELD_SENSOR_EVENT;
import static com.nofatclips.androidtesting.model.InteractionType.OPEN_MENU;
import static com.nofatclips.androidtesting.model.InteractionType.ORIENTATION_SENSOR_EVENT;
import static com.nofatclips.androidtesting.model.InteractionType.SCROLL_DOWN;
import static com.nofatclips.androidtesting.model.InteractionType.TEMPERATURE_SENSOR_EVENT;
import static com.nofatclips.androidtesting.model.InteractionType.INCOMING_CALL_EVENT;
import static com.nofatclips.androidtesting.model.InteractionType.INCOMING_SMS_EVENT;
import static com.nofatclips.crawler.Resources.BACK_BUTTON_EVENT;
import static com.nofatclips.crawler.Resources.EXCLUDE_WIDGETS_INPUTS_IN_SENSORS_EVENTS;
import static com.nofatclips.crawler.Resources.MENU_EVENTS;
import static com.nofatclips.crawler.Resources.ORIENTATION_EVENTS;
import static com.nofatclips.crawler.Resources.SCROLL_DOWN_EVENT;
import static com.nofatclips.crawler.Resources.SENSOR_TYPES;
import static com.nofatclips.crawler.Resources.TAB_EVENTS_START_ONLY;
import static com.nofatclips.crawler.Resources.USE_GPS;
import static com.nofatclips.crawler.Resources.USE_SENSORS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import android.util.Log;

import com.nofatclips.androidtesting.model.ActivityState;
import com.nofatclips.androidtesting.model.Transition;
import com.nofatclips.androidtesting.model.UserEvent;
import com.nofatclips.androidtesting.model.UserInput;
import com.nofatclips.androidtesting.model.WidgetState;
import com.nofatclips.crawler.Resources;
import com.nofatclips.crawler.model.Abstractor;
import com.nofatclips.crawler.model.EventHandler;
import com.nofatclips.crawler.model.Filter;
import com.nofatclips.crawler.model.InputHandler;
import com.nofatclips.crawler.model.Plan;
import com.nofatclips.crawler.model.Planner;
import com.nofatclips.crawler.planning.sensors_utils.GpsValuesGenerator;
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
				
				//TODO: aggiungere altri valori random, +++, ---, 000, out-of-bounds
			}	
		}
		
		if (USE_GPS && a.getUsesLocationManager())
		{
			evt = null;

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
			
			//random
			String locationInputValueStr = 	GpsValuesGenerator.getRandomLatitude() + "|" +
											GpsValuesGenerator.getRandomLongitude() + "|" +
											GpsValuesGenerator.getRandomAltitude();			
			evt = getAbstractor().createEvent(null, GPS_LOCATION_CHANGE_EVENT);
			evt.setValue(locationInputValueStr);
			t = getAbstractor().createStep(a, inputs, evt);
			p.addTask(t);	
			
			//+ + +
			locationInputValueStr = GpsValuesGenerator.getRandomPositiveLatitude() + "|" +
									GpsValuesGenerator.getRandomPositiveLongitude() + "|" +
									GpsValuesGenerator.getRandomPositiveAltitude();			
			evt = getAbstractor().createEvent(null, GPS_LOCATION_CHANGE_EVENT);
			evt.setValue(locationInputValueStr);
			t = getAbstractor().createStep(a, inputs, evt);
			p.addTask(t);
			
			//- - -
			locationInputValueStr = GpsValuesGenerator.getRandomNegativeLatitude() + "|" +
									GpsValuesGenerator.getRandomNegativeLongitude() + "|" +
									GpsValuesGenerator.getRandomNegativeAltitude();			
			evt = getAbstractor().createEvent(null, GPS_LOCATION_CHANGE_EVENT);
			evt.setValue(locationInputValueStr);
			t = getAbstractor().createStep(a, inputs, evt);
			p.addTask(t);
			
			//0 0 0
			locationInputValueStr = "0|0|0";			
			evt = getAbstractor().createEvent(null, GPS_LOCATION_CHANGE_EVENT);
			evt.setValue(locationInputValueStr);
			t = getAbstractor().createStep(a, inputs, evt);
			p.addTask(t);
			
			//evento "disabilita provider"
			//evt = getAbstractor().createEvent(null, GPS_PROVIDER_DISABLE_EVENT);
			//t = getAbstractor().createStep(a, inputs, evt);
			//p.addTask(t);
		}
		
		if (Resources.SIMULATE_INCOMING_CALL)
		{
			evt = null;
			evt = getAbstractor().createEvent(null, INCOMING_CALL_EVENT);
			t = getAbstractor().createStep(a, new ArrayList<UserInput>(), evt);
			p.addTask(t);
		}
		
		if (Resources.SIMULATE_INCOMING_SMS)
		{
			evt = null;
			evt = getAbstractor().createEvent(null, INCOMING_SMS_EVENT);
			t = getAbstractor().createStep(a, new ArrayList<UserInput>(), evt);
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
