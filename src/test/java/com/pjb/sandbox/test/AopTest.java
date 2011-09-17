package com.pjb.sandbox.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;

import com.pjb.sandbox.aop.MindReader;
import com.pjb.sandbox.aop.Thinker;

@ContextConfiguration
public class AopTest extends AbstractJUnit38SpringContextTests {

	@Autowired
	Thinker volunteer;
	
	@Autowired
	MindReader magician;
	
	public void testMindReader() {
		volunteer.think("hello there");
		
		assertEquals(volunteer.getThought(), magician.getThought());
	}
}
