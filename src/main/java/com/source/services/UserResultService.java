package com.source.services;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.source.DTO.UserResultDTO;
import com.source.entity.UserResult;
import com.source.repository.UserResultRepository;

@Service
public class UserResultService {
	@Autowired
	private UserResultRepository repo;
	@Autowired
	private UserService userService;
	@Autowired
	private ChallengeService challengeService;
	
	//======= LIST ALL ========
	public List<UserResultDTO> findAll() {
		List<UserResultDTO> allUsers = new ArrayList<>();
		
		for (UserResult ur: repo.findAll()) {
			allUsers.add(this.convertToDTO(ur));
		}
		
		return allUsers;
		
	}
	
	//======= FIND ALL RESULT OF A USER IN A CHALLENGE =========
	
	public List<UserResult> findResultsOfOneInGame(int userId, int challengeID){
		
		return repo.findAllByUserIDAndChallengeID(userId, challengeID);
	}
	
	
	public UserResult updateUserResult (UserResultDTO dto){
		
		UserResult result = this.convertToUserResult(dto);
		Optional<UserResult> results = repo.findById(result.getId());
		
		if (results.isPresent()) {
			UserResult updateResult = results.get();
			updateResult.setUserID(result.getUserID());
			updateResult.setChallengeID(result.getChallengeID());
			updateResult.setWord(result.getWord());
			
			updateResult = repo.save(updateResult);
			System.out.println("has id");
			return updateResult;
		} else {
			
			return null;
		}
		
	}
	
	public UserResult createUserResult(UserResultDTO dto) {
		UserResult user = this.convertToUserResult(dto);
		
		LocalDateTime now = LocalDateTime.now();
		Date date = Date.from(now.atZone( ZoneId.systemDefault()).toInstant());
		Timestamp ts=new Timestamp(date.getTime()); 
		System.out.println(ts);
		UserResult newUser = repo.save(user);
		return newUser;
	}
	
	public void deleteUserById(int id) {
		Optional<UserResult> users = repo.findById(id);
		
		if(users.isPresent()) {
			repo.deleteById(id);
		}
	}
	
	public List<String> countUserTotalPoint(int userId, int challengeId) throws Exception{
		if(userService.findById(userId) == null) {
			throw new Exception("User not found!");
		}
		
		if(challengeService.findById(challengeId) == null) {
			throw new Exception("Challenge not found!");
		}
		List<String> correctWord = repo.countUserTotalPoint(userId, challengeId);
		

		return correctWord;
	}
	
	
	
	//========CONVERT BETWEEN MODEL AND DTO
	
	private UserResult convertToUserResult(UserResultDTO dto) {
		UserResult user = new UserResult();
		user.setId(dto.getId());
		user.setChallengeID(dto.getChallengeID());
		user.setUserID(dto.getUserID());
		user.setWord(dto.getWord());
		
		return user;
		
	}
	
	private UserResultDTO convertToDTO(UserResult user) {
		UserResultDTO dto = new UserResultDTO();
		dto.setId(user.getId());
		dto.setChallengeID(user.getChallengeID());
		dto.setUserID(user.getUserID());
		dto.setWord(user.getWord());
		
		return dto;
	}

}
