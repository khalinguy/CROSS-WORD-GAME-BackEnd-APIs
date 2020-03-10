package com.source.DTO;



import lombok.Data;

/**
 * Data Transfer Object
 */
@Data
public class UserResultDTO {
	private int id;
	
	private int userID;
	
	private int challengeID;
	
	private String word;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getChallengeID() {
		return challengeID;
	}

	public void setChallengeID(int challengeID) {
		this.challengeID = challengeID;
	}
	
	

}
