package com.pjb.sandbox.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;

import com.pjb.sandbox.persistence.dao.UserDao;
import com.pjb.sandbox.persistence.model.User;

@ContextConfiguration
public class PersistenceTest extends AbstractJUnit38SpringContextTests {

	@Autowired
	private UserDao userDao;
	
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
}
