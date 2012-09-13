package com.pjb.sandbox.persistence;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pjb.sandbox.persistence.dao.EventDao;

@ContextConfiguration(locations={"classpath:/com/pjb/sandbox/persistence/PersistenceTest-context.xml"})
public class PersistenceTestNg extends AbstractTestNGSpringContextTests {

	@Autowired
	private EventDao eventDao;
	
	@Test
	public void testQuery() {
		eventDao.loadAll(Long.valueOf(1234));
	}
}
