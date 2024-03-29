package it.unina.androidripper.planning;

import it.unina.androidripper.Prefs;
import it.unina.androidripper.model.ResourceFile;
import it.unina.androidripper.planning.adapters.InteractorAdapter;
import it.unina.androidripper.planning.interactors.*;

import java.util.ArrayList;
import java.util.Arrays;

import static com.nofatclips.androidtesting.model.InteractionType.*;
import static com.nofatclips.androidtesting.model.SimpleType.*;
import static android.view.KeyEvent.*;

@SuppressWarnings("unused")
public class Resources implements ResourceFile {

	public static String PLANNER = "SimplePlanner";
	
	public static String EVENTS[];
	public static int[] KEY_EVENTS = {};
	
	public static boolean BACK_BUTTON_EVENT = true;
	public static boolean MENU_EVENTS = true;
	public static boolean ACTIONBARHOME_EVENTS = false;
	public static boolean ORIENTATION_EVENTS = true;
	public static boolean SCROLL_DOWN_EVENT = false;
	
	public static String INPUTS[];
	public static boolean TEXT_VALUES_ID_HASH = false;
	
	// Additional Interactions
	public static String EXTRA_EVENTS[];
	public static String EXTRA_INPUTS[];
	public static ArrayList<InteractorAdapter> ADDITIONAL_EVENTS = new ArrayList<InteractorAdapter>();
	public static ArrayList<InteractorAdapter> ADDITIONAL_INPUTS = new ArrayList<InteractorAdapter>();
	
	// User/Planner Parameters
	public static int MAX_EVENTS_PER_WIDGET = 0; // For GroupViews (0 = try all items in the group)
	public static boolean TAB_EVENTS_START_ONLY = false; // true -> click on tabs only on the start activity
	public static boolean EVENT_WHEN_NO_ID = true; // Whether to inject events on widgets without ID or not

	// Scheduler Parameters
	public static String SCHEDULER_ALGORITHM = "BREADTH_FIRST";
	public static int MAX_TASKS_IN_SCHEDULER = 0;
		
	static {
		Prefs.updateNode("scheduler", Resources.class);
		Prefs.updateNode("planner", Resources.class);
		Prefs.updateNode("interactions", Resources.class);
		
		if (EVENTS != null) {
			UserFactory.resetEvents();
			for (String s: EVENTS) {
				String[] widgets = s.split("( )?,( )?");
				UserFactory.addEvent(widgets[0], Arrays.copyOfRange(widgets, 1, widgets.length));
			}
		}
		
		if (INPUTS != null) {
			UserFactory.resetInputs();
			for (String s: INPUTS) {
				String[] widgets = s.split("( )?,( )?");
				UserFactory.addInput(widgets[0], Arrays.copyOfRange(widgets, 1, widgets.length));
			}
		}
		
		if (EXTRA_EVENTS != null) {
			ADDITIONAL_EVENTS.clear();
			for (String s: EXTRA_EVENTS) {
				String[] widgets = s.split("( )?,( )?");
				if (widgets[0].equals(ENTER_TEXT)){
					InteractorAdapter i = new FixedValueEnterEditor().addIdValuePair(widgets[1], Arrays.copyOfRange(widgets, 2, widgets.length));
					ADDITIONAL_EVENTS.add(i);
				}
				if (widgets[0].equals(WRITE_TEXT)){
					InteractorAdapter i = new FixedValueEditor().addIdValuePair(widgets[1], Arrays.copyOfRange(widgets, 2, widgets.length));
					ADDITIONAL_EVENTS.add(i);
				}
			}
		}
		
		if (EXTRA_INPUTS != null) {
			ADDITIONAL_INPUTS.clear();
			for (String s: EXTRA_INPUTS) {
				String[] widgets = s.split("( )?,( )?");
				if (widgets[0].equals(ENTER_TEXT)){
					InteractorAdapter i = new FixedValueEnterEditor().addIdValuePair(widgets[1], Arrays.copyOfRange(widgets, 2, widgets.length));
					ADDITIONAL_INPUTS.add(i);
				}
				if (widgets[0].equals(WRITE_TEXT)){
					InteractorAdapter i = new FixedValueEditor().addIdValuePair(widgets[1], Arrays.copyOfRange(widgets, 2, widgets.length));
					ADDITIONAL_INPUTS.add(i);
				}
			}
		}
		
	}

}
