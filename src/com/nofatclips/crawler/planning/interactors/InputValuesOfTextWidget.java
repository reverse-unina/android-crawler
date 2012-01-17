package com.nofatclips.crawler.planning.interactors;

import android.text.InputType;

import com.nofatclips.androidtesting.model.WidgetState;

public class InputValuesOfTextWidget implements InputType{
	
	public InputValuesOfTextWidget(){
		
	}
	
	public String[] InputValueOfText(WidgetState w){
		String stringType = w.getTextType();
		if(stringType.equals("")){
			String[] valuesList = new String[1];
			valuesList[0] = "random";
			return valuesList;
		}
		
		//N.B. Occorre ordinare le maschere dalla più rigida alla meno
		//rigida. Per intenderci dalla FFFFFFFF alla 00000000 in modo
		//tale che la generazione dei valori sia quanto più affine possibile
		//con il tipo che il form di input prevede
		
		int type = Integer.parseInt(stringType);
		
		//TYPE_NUMBER_DECIMAL_MASK = hex(0x00002002)  int(8194)
		int TYPE_NUMBER_DECIMAL_MASK = (TYPE_CLASS_NUMBER|TYPE_NUMBER_FLAG_DECIMAL);
		if((TYPE_NUMBER_DECIMAL_MASK & type) == TYPE_NUMBER_DECIMAL_MASK){
			String[] valuesList = new String[3];			
			valuesList[0] = "random";
			valuesList[1] = "0";//valuesList[1]="-3.5";
			valuesList[2]="3.5";			
			return valuesList;
		}
		
		//TYPE_TEXT_MULTI_LINE_CAP_SENTENCES = hex(0x00024001)  int(147457)
		int TYPE_TEXT_MULTI_LINE_CAP_SENTENCES_MASK =(TYPE_TEXT_FLAG_MULTI_LINE|TYPE_TEXT_FLAG_CAP_SENTENCES|TYPE_CLASS_TEXT);
		if((TYPE_TEXT_MULTI_LINE_CAP_SENTENCES_MASK & type) == TYPE_TEXT_MULTI_LINE_CAP_SENTENCES_MASK){
			String[] valuesList = new String[2];
			valuesList[0] = "testo multi linea non vuoto di prova";
			valuesList[1] = "";
			return valuesList;
		}						
					
		//TYPE_TEXT_PASSWORD         = hex(0x00000081)  int(   129)				
		//TYPE_TEXT_FLAG_MULTI_LINE  = hex(0x0020000)   int(131072)
		//TYPE_TEXT_PASSWORD_MASK    = hex(0x00020081)  int(131201)
		int TYPE_TEXT_PASSWORD = (TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_PASSWORD);
		int TYPE_TEXT_PASSWORD_MASK = (TYPE_TEXT_PASSWORD |TYPE_TEXT_FLAG_MULTI_LINE); 
		if((TYPE_TEXT_PASSWORD_MASK & type) == TYPE_TEXT_PASSWORD_MASK){
			String[] valuesList = new String[2];
			valuesList[0] = "";
			valuesList[1] = "passwordNonValida";			
			return valuesList;
		}

		//TYPE_TEXT_MULTI_LINE_MASK = hex(0x00020001)  int(131073)
		int TYPE_TEXT_MULTI_LINE_MASK = (TYPE_TEXT_FLAG_MULTI_LINE);
		if((TYPE_TEXT_MULTI_LINE_MASK & type) == TYPE_TEXT_MULTI_LINE_MASK){
			String[] valuesList = new String[2];
			valuesList[0] = "testo multi linea\nnon vuoto";
			valuesList[1] = "";
			return valuesList;
		}				
				
		//TYPE_NUMBER_MASK = hex(0x00000002)  int(2)
		int TYPE_NUMBER_MASK = (TYPE_CLASS_NUMBER);
		if((TYPE_NUMBER_MASK & type) == TYPE_NUMBER_MASK){
			String[] valuesList=new String[2];
			valuesList[0] = "random";
			valuesList[1] = "0";//valuesList[1]="3.5";			
			return valuesList;
		}
		
		//TYPE_TEXT_MASK = hex(0x00000001)  int(1)
		int TYPE_TEXT_MASK = (TYPE_CLASS_TEXT|TYPE_TEXT_VARIATION_NORMAL);
		if((TYPE_TEXT_MASK & type) == TYPE_TEXT_MASK){
			String[] valuesList = new String[2];
			valuesList[0] = "testo non vuoto";
			valuesList[1] = "";			
			return valuesList;
		}
		
		String[] valuesList = new String[1];
		valuesList[0]="random";
		return valuesList;
	}	
}
