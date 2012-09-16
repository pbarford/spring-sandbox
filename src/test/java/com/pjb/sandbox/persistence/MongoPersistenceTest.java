package com.pjb.sandbox.persistence;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.pjb.sandbox.persistence.dao.mongo.UserDao;
import com.pjb.sandbox.persistence.model.mongo.User;
import com.pjb.sandbox.persistence.model.mongo.UserBuilder;


@ContextConfiguration(locations={"classpath:/com/pjb/sandbox/persistence/PersistenceTest-context.xml"})
public class MongoPersistenceTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private MongoOperations mongoOperations;
	
	@Autowired
	private UserDao userDao;
	
	@BeforeMethod(groups="mongo")
	public void init() {
		mongoOperations.dropCollection("users");
		
		userDao.persist(UserBuilder.newUser().withName("paulo").withAge(38).build());
		userDao.persist(UserBuilder.newUser().withName("anita").withAge(43).build());
		userDao.persist(UserBuilder.newUser().withName("alexandra").withAge(6).build());
	}
	
	@Test(groups="mongo")
	public void userQueryTest() {		
		List<User> res = userDao.query().withName("paulo").execute();	
		assertThat(res.size(), equalTo(1));
		res = userDao.query().execute();
		assertThat(res.size(), equalTo(3));
	}
	
	@AfterMethod(groups="mongo")
	public void clear() {
		mongoOperations.dropCollection("users");		
	}
}
