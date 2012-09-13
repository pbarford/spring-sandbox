package com.pjb.sandbox.persistence.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "market")
@SequenceGenerator(sequenceName="market_seq", name="market_seq_gen", allocationSize=150)
public class Market implements Serializable {

	private static final long serialVersionUID = -5022872175625655635L;
	
	private Long id;
	private String description;
	
	private Event event;
	private Set<MarketDestination> marketDestinations;
	private Set<Selection> selections;

	public Market() {		
	}
	
	public Market(String description) {
		this.description = description;
	}
	
	@Id
	@GeneratedValue(generator="market_seq_gen", strategy=GenerationType.SEQUENCE)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@ManyToOne
	@JoinColumn(name = "event_id", nullable=false, insertable=true, updatable=true)
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy = "market", cascade=CascadeType.PERSIST)
	public Set<MarketDestination> getMarketDestinations() {
		return marketDestinations;
	}
	
	public void setMarketDestinations(Set<MarketDestination> marketDestinations) {
		this.marketDestinations = marketDestinations;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy = "market", cascade=CascadeType.PERSIST)
	public Set<Selection> getSelections() {
		return selections;
	}

	public void setSelections(Set<Selection> selections) {
		this.selections = selections;
	}
}
