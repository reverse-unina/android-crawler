package com.nofatclips.crawler.planning;

import static com.nofatclips.androidtesting.model.InteractionType.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import android.util.Log;

import com.nofatclips.androidtesting.model.ActivityState;
import com.nofatclips.androidtesting.model.InteractionType;
import com.nofatclips.androidtesting.model.SupportedEvent;
import com.nofatclips.androidtesting.model.Transition;
import com.nofatclips.androidtesting.model.UserEvent;
import com.nofatclips.androidtesting.model.UserInput;
import com.nofatclips.androidtesting.model.WidgetState;
import com.nofatclips.crawler.Resources;
import com.nofatclips.crawler.model.EventHandler;
import com.nofatclips.crawler.model.Plan;

public class SimpleReflectionPlanner extends SimplePlanner {

	public static final String TAG = "SimpleReflectionPlanner";
	
	public SimpleReflectionPlanner() {
		super();
	}

	private Collection<UserEvent> getEventsToHandle(ActivityState a, WidgetState w)
	{
		ArrayList<String> eventTypes = new ArrayList<String>();
		for ( SupportedEvent evt : a.getSupportedEventsByWidgetUniqueId( w.getUniqueId() ) )
		{
			if (evt.getEventType().startsWith("_") == false)
				eventTypes.add(evt.getEventType());
		}
		return this.getUserForWidgetAndEvents(w.getSimpleType(), eventTypes).handleEvent(w);
	}
		
	@Override
	public Plan getPlanForActivity (ActivityState a, boolean allowSwapTabs, boolean allowGoBack) {
		Plan p = new Plan();
		Log.i("nofatclips", "Planning for new Activity " + a.getName());
		
		for (WidgetState w: getEventFilter()) {
			//Collection<UserEvent> events = getUser().handleEvent(w);			
			for ( UserEvent evt: getEventsToHandle(a, w) ) {
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
		
		boolean BACK_BUTTON_EVENT = Resources.BACK_BUTTON_EVENT;
		boolean MENU_EVENTS = Resources.MENU_EVENTS;
		boolean SCROLL_DOWN_EVENT = Resources.SCROLL_DOWN_EVENT;
		boolean ORIENTATION_EVENTS = Resources.ORIENTATION_EVENTS;
		boolean SUPPORTS_KEY_EVENTS = (Resources.KEY_EVENTS.length > 0);
		
		//TODO: da provare
		if (Resources.REFLECT_ACTIVITY_LISTENERS)
		{
			MENU_EVENTS = false;
			SUPPORTS_KEY_EVENTS = false;
		}
		
		for (SupportedEvent se : a.getSupportedEventsByWidgetUniqueId( SupportedEvent.GENERIC_ACTIVITY_UID ) )
		{
			if (se.getEventType().equals(InteractionType.OPEN_MENU))
				MENU_EVENTS = true;
			
			if (se.getEventType().equals(InteractionType.PRESS_KEY))
				SUPPORTS_KEY_EVENTS = true;			
		}
		
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
		
		if (SUPPORTS_KEY_EVENTS && Resources.KEY_EVENTS.length>0) {
			for (int keyCode: Resources.KEY_EVENTS) {
				evt = getAbstractor().createEvent(null, PRESS_KEY);
				evt.setValue(String.valueOf(keyCode));
				t = getAbstractor().createStep(a, new HashSet<UserInput>(), evt);
				Log.i("nofatclips", "Created trace to perform key press (key code: " + keyCode + ")");
				p.addTask(t);
			}
		}

		//NOTA: w0 significa nessun id, quindi scateno gli eventi che non
		//		interessano nessun widget, per esempio, che interessano
		//		l'activity
		for ( SupportedEvent se : a.getSupportedEventsByWidgetUniqueId(SupportedEvent.GENERIC_ACTIVITY_UID) )
		{
			if (se.getEventType().equals(InteractionType.ACCELEROMETER_SENSOR_EVENT))
			{
				addStepsForSensor(
						android.hardware.Sensor.TYPE_ACCELEROMETER,
						InteractionType.ACCELEROMETER_SENSOR_EVENT,
						a, p);
			}
			
			else if (se.getEventType().equals(InteractionType.ORIENTATION_SENSOR_EVENT))
			{
				addStepsForSensor(
						android.hardware.Sensor.TYPE_ORIENTATION,
						InteractionType.ORIENTATION_SENSOR_EVENT,
						a, p);
			}			
			
			else if (se.getEventType().equals(InteractionType.MAGNETIC_FIELD_SENSOR_EVENT))
			{
				addStepsForSensor(
						android.hardware.Sensor.TYPE_MAGNETIC_FIELD,
						InteractionType.MAGNETIC_FIELD_SENSOR_EVENT,
						a, p);
			}
			
			//NOTA: TYPE_TEMPERATURE e' deprecato nelle api 14: diventa TYPE_AMBIENT_TEMPERATURE
			else if (se.getEventType().equals(InteractionType.TEMPERATURE_SENSOR_EVENT))
			{
				addStepsForSensor(
						android.hardware.Sensor.TYPE_TEMPERATURE,
						InteractionType.TEMPERATURE_SENSOR_EVENT,
						a, p);
			}
			
			else if (se.getEventType().equals(InteractionType.GPS_LOCATION_CHANGE_EVENT))				
			{
				addStepsForGPS(a, p);
			}
			
			else if (se.getEventType().equals(InteractionType.GPS_PROVIDER_DISABLE_EVENT))				
			{
				//TODO
				//evento "disabilita provider"
				//evt = getAbstractor().createEvent(null, GPS_PROVIDER_DISABLE_EVENT);
				//t = getAbstractor().createStep(a, inputs, evt);
				//p.addTask(t);
			}
			
			else if (se.getEventType().equals(InteractionType.INCOMING_CALL_EVENT))
			{
				evt = null;
				evt = getAbstractor().createEvent(null, INCOMING_CALL_EVENT);
				t = getAbstractor().createStep(a, new ArrayList<UserInput>(), evt);
				p.addTask(t);
			}
			
			else if (se.getEventType().equals(InteractionType.INCOMING_SMS_EVENT))
			{
				evt = null;
				evt = getAbstractor().createEvent(null, INCOMING_SMS_EVENT);
				t = getAbstractor().createStep(a, new ArrayList<UserInput>(), evt);
				p.addTask(t);
			}
		}		
		
		return p;
	}
	
	public EventHandler getUserForWidgetAndEvents(String widgetType, ArrayList<String> eventTypes) {
		return UserFactory.getUserForEvents(getAbstractor(), widgetType, eventTypes);
	}
}
