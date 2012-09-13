package com.pjb.sandbox.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "event_destination")
@SequenceGenerator(sequenceName="event_dest_seq", name="event_dest_seq_gen", allocationSize=10)
public class EventDestination implements Serializable {

	private static final long serialVersionUID = 8887214856137499024L;
	
	private Long id;
	private String description;
	private Event event;

	@Id
	@GeneratedValue(generator="event_dest_seq_gen", strategy=GenerationType.SEQUENCE)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="desc")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne
	@JoinColumn(name = "event_id", nullable=false, insertable=false, updatable=false)
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

}
