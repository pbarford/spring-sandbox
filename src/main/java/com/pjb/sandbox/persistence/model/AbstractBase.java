package com.pjb.sandbox.persistence.model;

import static javax.persistence.GenerationType.*;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class AbstractBase {

	@Id
	@GeneratedValue(strategy=AUTO)
	private Long id;
	
	@Version
	private Long version;
	
	public Long getId() {
		return id;
	}

	protected void setId(Long id) {
		this.id = id;
	}
	
	public Long getVersion() {
		return version;
	}

	protected void setVersion(Long version) {
		this.version = version;
	}

}
