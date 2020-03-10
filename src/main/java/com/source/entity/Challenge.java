package com.source.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * Persistent class for database
 *
 */
@Entity
@Table(name = "challenge")
public class Challenge implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "challenge_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Basic
	@Column(name = "name")
	private String name;
	@Basic
	@Column(name = "created_time")
	private Date createdTime;
	@Basic
	@Column(name = "start_time")
	private Date startTime;
	@Basic
	@Column(name = "end_time")
	private Date endTime;
	@Basic
	@Column(name = "status")
	private String status;
	@Basic
	@Column(name = "table_content")
	private String tableContent;
	@Basic
	@Column(name = "size")
	private int size;
	
	
	@OneToMany(targetEntity=WordAndClue.class, mappedBy="challenge",cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnoreProperties("challenge")
	private Set<WordAndClue> wordAndClue;
	
	
	
	public Set<WordAndClue> getWordAndClue() {
		return wordAndClue;
	}

	public void setWordAndClue(Set<WordAndClue> wordAndClue) {
		this.wordAndClue = wordAndClue;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getTableContent() {
		return tableContent;
	}

	public void setTableContent(String tableContent) {
		this.tableContent = tableContent;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	
	
}
