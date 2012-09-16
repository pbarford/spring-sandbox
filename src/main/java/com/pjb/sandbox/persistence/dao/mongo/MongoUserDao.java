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
	
	public User persist(User entity) {
		mongoOperations.save(entity, "users");
		return entity;
	}
	
	public UserQuery query() {
		return new MongoUserQuery(mongoOperations);
	}
	
	public static interface UserQuery {
		UserQuery withName(String name);
		UserQuery withAge(Integer age);
		List<User> execute();
	}
	
	private class MongoUserQuery implements UserQuery {
		private String name;
		private Integer age;
		private MongoOperations mongoOperations;
		
		public MongoUserQuery(MongoOperations mongoOperations) {
			this.mongoOperations = mongoOperations;
		}
		
		public UserQuery withName(String name) {
			this.name = name;
			return this;
		}
		public UserQuery withAge(Integer age) {
			this.age = age;
			return this;
		}
		public List<User> execute() {
			Query q = new Query();
			if(name!=null) q.addCriteria(Criteria.where("name").is(name));
			if(age!=null) q.addCriteria(Criteria.where("age").is(age));
			return mongoOperations.find(q, User.class,"users");
		}
		
	}
}
