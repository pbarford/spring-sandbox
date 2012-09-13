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
@Table(name = "selection_destination")
@SequenceGenerator(sequenceName="selection_dest_seq", name="selection_dest_seq_gen", allocationSize=500)
public class SelectionDestination implements Serializable {

	private static final long serialVersionUID = 8575662638229096430L;
	
	private Long id;
	private String description;
	private Selection selection;
	
	public SelectionDestination() {		
	}
	
	public SelectionDestination(String description) {
		this.description = description;
	}

	@Id
	@GeneratedValue(generator="selection_dest_seq_gen", strategy=GenerationType.SEQUENCE)
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
	@JoinColumn(name = "selection_id", nullable=false, insertable=true, updatable=true)
	public Selection getSelection() {
		return selection;
	}

	public void setSelection(Selection selection) {
		this.selection = selection;
	}
}
