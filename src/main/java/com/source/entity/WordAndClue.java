package com.source.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "word_clue")
public class WordAndClue implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "word_clue_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Basic
	@Column(name = "challenge_id")
	private int challengeId;
	
	@Basic
	@Column(name = "clue")
	private String clue;
	
	@Basic
	@Column(name = "word")
	private String word;
	
	private String challengeName;
	
	
	@Basic
	@Column(name = "path")
	private String path;

	
	public String getChallengeName() {
		return challengeName;
	}

	public void setChallengeName(String challengeName) {
		this.challengeName = challengeName;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "challenge_id", insertable = false, updatable = false)
	@JsonIgnoreProperties("wordAndClue")
	private Challenge challenge;

	
	
	public String getClue() {
		return clue;
	}

	public void setClue(String clue) {
		this.clue = clue;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getChallengeId() {
		return challengeId;
	}

	public void setChallengeId(int challengeId) {
		this.challengeId = challengeId;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Challenge getChallenge() {
		return challenge;
	}

	public void setChallenge(Challenge challenge) {
		this.challenge = challenge;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
	
	
}
