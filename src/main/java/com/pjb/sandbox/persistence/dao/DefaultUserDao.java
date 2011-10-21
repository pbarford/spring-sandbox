package com.pjb.sandbox.persistence.dao;

import org.springframework.stereotype.Repository;

import com.pjb.sandbox.persistence.model.User;

@Repository("userDao")
public class DefaultUserDao extends AbstractDao<User, Long> implements UserDao {
}
