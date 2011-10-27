package com.nofatclips.crawler.planning;

import com.nofatclips.androidtesting.model.WidgetState;
import android.text.InputType;
import java.util.Random;

public class InputValuesOfWidget implements InputType{

	public InputValuesOfWidget(){
		this.randomGenerator = new Random();
	}
	
	public String[] inputValueOfWidget(WidgetState w){
		String[] valuesList = null;
		int type=Integer.parseInt(w.getTextType());
		
		//TYPE_NUMBER_DECIMAL_MASK = hex(0x00002002)  int(8194)
		int TYPE_NUMBER_DECIMAL_MASK=(TYPE_CLASS_NUMBER|TYPE_NUMBER_FLAG_DECIMAL);
		if((TYPE_NUMBER_DECIMAL_MASK & type)==TYPE_NUMBER_DECIMAL_MASK){
			valuesList=new String[3];
			valuesList[0]="7";
			valuesList[1]="-13.5";
			valuesList[2]="0";
			return valuesList;
		}
		
		//TYPE_NUMBER_MASK = hex(0x00000002)  int(2)
		int TYPE_NUMBER_MASK=(TYPE_CLASS_NUMBER);
		if((TYPE_NUMBER_MASK & type)==TYPE_NUMBER_MASK){
			valuesList=new String[3];
			valuesList[0]="5";
			valuesList[1]="15";
			valuesList[2]="0";
			return valuesList;
		}
		
		//TYPE_MULTI_LINE_MASK = hex(0x00020001)  int(131073)
		int TYPE_MULTI_LINE_MASK=(TYPE_TEXT_FLAG_MULTI_LINE);
		if((TYPE_MULTI_LINE_MASK & type)==TYPE_MULTI_LINE_MASK){
			valuesList=new String[2];
			valuesList[0]="testo non vuoto";
			valuesList[1]="";
			return valuesList;
		}
		
		//Generazione Valore Random
		valuesList = new String[1];
		valuesList[0] = String.valueOf(randomGenerator.nextInt(this.upperLimit-this.lowerLimit) + this.lowerLimit);
		return valuesList;
	}	
	
	public Random getRandomGenerator() {
		return this.randomGenerator;
	}

	public void setRandomGenerator(Random randomGenerator) {
		this.randomGenerator = randomGenerator;
	}
	
	public void setLimits (int lower, int upper) {
		this.lowerLimit = lower;
		this.upperLimit = upper;
	}
	
	private Random randomGenerator;
	private int lowerLimit = 0;
	private int upperLimit = 100;	
}
