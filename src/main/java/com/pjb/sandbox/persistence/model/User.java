package com.pjb.sandbox.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="users")
public class User extends AbstractBase implements Serializable {
	
	private static final long serialVersionUID = 347048703751000806L;
	
	@Column
	private String name;

	@Column
	private Integer age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
		
	}
}
