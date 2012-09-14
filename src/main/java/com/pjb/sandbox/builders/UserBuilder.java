package com.pjb.sandbox.builders;

import com.pjb.sandbox.persistence.model.User;

public class UserBuilder {

	private UserBuilder() {	
	}
	
	public static NameStep newUser() {
		return new UserSteps();
	}
	
	public static interface NameStep {
		AgeStep withName(String name);
	}
	
	public static interface AgeStep {
		BuildStep withAge(Integer age);
	}
	
	public static interface BuildStep {
		User build();
	}
	
	private static class UserSteps implements NameStep, AgeStep, BuildStep {
		private User user;
		public UserSteps() {
			user = new User();
		}
		
		public AgeStep withName(String name) {
			user.setName(name);
			return this;
		}
		
		public BuildStep withAge(Integer age) {
			user.setAge(age);
			return this;
		}
		
		public User build() {
			return user;
		}
	}
}
