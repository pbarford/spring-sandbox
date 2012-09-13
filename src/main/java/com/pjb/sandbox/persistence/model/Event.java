package com.pjb.sandbox.persistence.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "event")
@SequenceGenerator(sequenceName="event_seq", name="event_seq_gen", allocationSize=10)
public class Event implements Serializable {

	private static final long serialVersionUID = 6259047063762129291L;

	private Long id;
	private String description;
	
	private Set<EventDestination> eventDestinations;
	private Set<Market> markets;

	@Id
	@GeneratedValue(generator="event_seq_gen", strategy=GenerationType.SEQUENCE)
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

	@OneToMany(fetch=FetchType.LAZY, mappedBy = "event")
	public Set<EventDestination> getEventDestinations() {
		return eventDestinations;
	}
	
	public void setEventDestinations(Set<EventDestination> eventDestinations) {
		this.eventDestinations = eventDestinations;
	}
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "event")	
	public Set<Market> getMarkets() {
		return markets;
	}
	
	public void setMarkets(Set<Market> markets) {
		this.markets = markets;
	}
}
