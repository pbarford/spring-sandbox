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
@Table(name = "market_destination")
@SequenceGenerator(sequenceName="market_dest_seq", name="market_dest_seq_gen", allocationSize=150)
public class MarketDestination implements Serializable {

	private static final long serialVersionUID = 4161631204466912986L;
	
	private Long id;
	private String description;
	private Market market;

	public MarketDestination() {		
	}
	
	public MarketDestination(String description) {
		this.description = description;
	}
	
	@Id
	@GeneratedValue(generator="market_dest_seq_gen", strategy=GenerationType.SEQUENCE)
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
	@JoinColumn(name = "market_id", nullable=false, insertable=true, updatable=true)
	public Market getMarket() {
		return market;
	}

	public void setMarket(Market market) {
		this.market = market;
	}
}
