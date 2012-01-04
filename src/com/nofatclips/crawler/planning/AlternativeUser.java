package com.nofatclips.crawler.planning;

import java.util.Random;

import com.nofatclips.crawler.model.Abstractor;

public class AlternativeUser extends SimpleUser {	
	
	public AlternativeUser(Abstractor a) {
		super (a);
	}

	public AlternativeUser(Abstractor a, Random random) {
		super (a,random);
	}

}
