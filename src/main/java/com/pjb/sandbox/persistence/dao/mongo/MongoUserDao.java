package com.pjb.sandbox.persistence.dao.mongo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.pjb.sandbox.persistence.model.mongo.User;


@Repository
public class MongoUserDao implements UserDao{
	
	@Autowired
	private MongoOperations mongoOperations;
	
	public User persist(User user) {
		mongoOperations.save(user, "users");
		return user;
	}
	
	public UserQuery query() {
		return new MongoUserQuery(mongoOperations);
	}
	
	public static interface UserQuery {
		UserQuery withNameEqualTo(String name);
		UserQuery withAgeEqualTo(Integer age);
		List<User> execute();
	}
	
	private class MongoUserQuery implements UserQuery {		
		private MongoOperations mongoOperations;
		private Query theQuery;
		
		public MongoUserQuery(MongoOperations mongoOperations) {
			theQuery = new Query();
			this.mongoOperations = mongoOperations;
		}
		
		public UserQuery withNameEqualTo(String name) {
			theQuery.addCriteria(Criteria.where("name").is(name));			
			return this;
		}
		public UserQuery withAgeEqualTo(Integer age) {			
			theQuery.addCriteria(Criteria.where("age").is(age));
			return this;
		}
		public List<User> execute() {
			return mongoOperations.find(theQuery, User.class,"users");
		}
		
	}
}
