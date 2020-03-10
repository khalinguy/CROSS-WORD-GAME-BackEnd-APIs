package com.source.DTO;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.source.entity.WordAndClue;

import lombok.Data;

/**
 * Data Transfer Object
 */
@Data
public class ChallengeDTO {
	
	private int id;
	private String name;
	private String startTime;
	private String endTime;
	private String status;
	private String tableContent;
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
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
