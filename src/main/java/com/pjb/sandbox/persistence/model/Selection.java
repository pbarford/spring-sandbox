package com.pjb.sandbox.persistence.model;

import java.io.Serializable;
import java.util.Set;

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
@Table(name = "selection")
@SequenceGenerator(sequenceName="selection_seq", name="selection_seq_gen", allocationSize=10)
public class Selection implements Serializable {

	private static final long serialVersionUID = -5505079224290755667L;
	private Long id;
	private String description;
	
	private Market market;
	private Set<SelectionDestination> selectionDestinations;

	@Id
	@GeneratedValue(generator="selection_seq_gen", strategy=GenerationType.SEQUENCE)
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
	@JoinColumn(name = "market_id", nullable=false, insertable=false, updatable=false)
	public Market getMarket() {
		return market;
	}

	public void setMarket(Market market) {
		this.market = market;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy = "selection")
	public Set<SelectionDestination> getSelectionDestinations() {
		return selectionDestinations;
	}
	
	public void setSelectionDestinations(Set<SelectionDestination> selectionDestinations) {
		this.selectionDestinations = selectionDestinations;
	}
}
