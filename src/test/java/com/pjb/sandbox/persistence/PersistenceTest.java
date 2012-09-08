package com.pjb.sandbox.persistence;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pjb.sandbox.persistence.dao.EventDao;
import com.pjb.sandbox.persistence.dao.UserDao;
import com.pjb.sandbox.persistence.model.Event;
import com.pjb.sandbox.persistence.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/com/pjb/sandbox/persistence/PersistenceTest-context.xml"})
public class PersistenceTest {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private EventDao eventDao;
	
	
	private void createUser(String name, Integer age) {
		User u = new User();
		u.setName(name);
		u.setAge(age);
		userDao.persist(u);
	}
	
	private void createEvent(String desc) {
		Event e = new Event();
		e.setDescription(desc);
		eventDao.persist(e);
	}
	
	@Test
	public void testUser() {
		
		createUser("paulo", 38);
		createUser("anita", 41);
		createUser("alexandra", 6);
		
		User u = userDao.getById(Long.valueOf(1));
		assertThat(u.getVersion(), equalTo(Long.valueOf(0)));
		u.setName("Paulo");
		u = userDao.update(u);
		assertThat(u.getVersion(), equalTo(Long.valueOf(1)));
		
		assertThat(userDao.getAll().size(), equalTo(3));
		
		List<Long> ids = Arrays.asList(Long.valueOf(1), Long.valueOf(2));
		Collection<User> res = userDao.query("from User u where u.id in (?1)", ids);
		assertThat(res.size(), equalTo(2));
	}
	
	@Test
	public void testEvent() {
		
		createEvent("event-1");
		createEvent("test-1");
		createEvent("test-2");

		assertThat(eventDao.getAll().size(), equalTo(3));
		Collection<Event> res = eventDao.query("from Event e where e.description like ?1", "test%");
		assertThat(res.size(), equalTo(2));
	}
}
