package com.pjb.sandbox.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pjb.sandbox.aop.MindReader;
import com.pjb.sandbox.aop.Thinker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/com/pjb/sandbox/test/AopTest-context.xml"})
public class AopTest extends AbstractJUnit38SpringContextTests {

	@Autowired
	Thinker volunteer;
	
	@Autowired
	MindReader magician;
	
	@Test
	public void testMindReader() {
		volunteer.think("hello there");
		
		assertThat(volunteer.getThought(), equalTo(magician.getThought()));
	}
}
