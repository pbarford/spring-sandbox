package com.pjb.sandbox.aop;

import org.springframework.stereotype.Component;

@Component
public class Volunteer implements Thinker {

	private String thought;
	
	public void think(String thought) {
		this.thought = thought;

	}

	public String getThought() {
		return thought;
	}
}
