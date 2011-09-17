package com.pjb.sandbox.aop;

import org.springframework.stereotype.Component;

@Component
public class Magician implements MindReader {
	String thought;
	public void interceptThought(String thought) {
		this.thought = thought;
	}

	public String getThought() {
		return thought;
	}

}
