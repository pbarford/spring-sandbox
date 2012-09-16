package com.pjb.sandbox.persistence.dao.mongo;

import com.pjb.sandbox.persistence.dao.mongo.MongoUserDao.UserQuery;
import com.pjb.sandbox.persistence.model.mongo.User;

public interface UserDao {

	User persist(User user);	
	UserQuery query();
}
