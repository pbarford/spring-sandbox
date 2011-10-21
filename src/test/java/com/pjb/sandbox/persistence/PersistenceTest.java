package com.pjb.sandbox.persistence;

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
	
	@Test
	public void testAddUser() {
		User u = new User();
		u.setName("Paulo");
		u.setAge(21);
		u = userDao.persist(u);
		
		System.out.println(u.getId() + ":" + u.getVersion());
		u.setName("Billy");
		u = userDao.update(u);
		System.out.println(u.getId() + ":" + u.getVersion());
		
		User u2 = new User();
		u2.setName("Paulo");
		u2.setAge(21);
		u2 = userDao.persist(u2);
		
		System.out.println(u2.getId());
		
		User u3 = userDao.getById(Long.valueOf(1));
		System.out.println(u3.getId() + ":" + u3.getVersion());
		
		System.out.println(userDao.getAll().size());
		
		userDao.delete(Long.valueOf(1));
		
		System.out.println(userDao.getAll().size());
	}
	
	@Test
	public void testAddEvent() {
		Event e = new Event();
		e.setDescription("test");
		e = eventDao.persist(e);
		System.out.printf("id : %s%n", e.getId());
		
		Event v = new Event();
		v.setDescription("test");
		v = eventDao.persist(v);
		System.out.printf("id : %s%n", v.getId());
		
	}
}
