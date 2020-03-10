package com.source.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.source.DTO.UserResultDTO;
import com.source.entity.UserResult;
import com.source.services.UserResultService;

@RestController
@RequestMapping(value = "/userresult")
public class UserResultController extends BaseController {
	
	@Autowired
	private UserResultService rs;
	
	
	//============= LIST ALL USER RESULTS ===============
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<List<UserResultDTO>> getAllResult(){
		return new ResponseEntity<List<UserResultDTO>>(rs.findAll(), HttpStatus.OK);
	}
	
	
	// =========== LIST USER RESULTS OF A CHALLENGE =========
	@RequestMapping(value = "/result/{userid}/{challengeid}", method = RequestMethod.GET)
	public ResponseEntity<List<UserResult>> getResultByUserAndChallenge(@PathVariable(name = "userid") int userId, 
			@PathVariable(name = "challengeid") int challengeID){
		
		return new ResponseEntity<List<UserResult>> (rs.findResultsOfOneInGame(userId, challengeID),HttpStatus.OK);
				
	}
	
	//============= GET USER POINT ==============
	@RequestMapping(value = "/point/{userid}/{challengeid}", method = RequestMethod.GET)
	public ResponseEntity<Integer> getResultPoint(@PathVariable(name = "userid") int userId, 
			@PathVariable(name = "challengeid") int challengeId) throws Exception{
			List<String> correctWord = rs.countUserTotalPoint(userId, challengeId);
			int point = correctWord.size();
			
		return new ResponseEntity<Integer>(point,HttpStatus.OK);				
	}
	
	
	//============= CREATE USER RESULT ===========
	@RequestMapping(value = "/create/{user}", method = RequestMethod.POST,  consumes = {"application/json"})
	public ResponseEntity<UserResult> createUserResult(@RequestBody UserResultDTO user) throws Exception{
		UserResult create = rs.createUserResult(user);
		return new ResponseEntity<UserResult>(create,HttpStatus.OK);
	}
	
	
	//============= UPDATE USER RESULT ===========
	@RequestMapping(value = "/update/", method = RequestMethod.POST)
	public ResponseEntity<UserResult> updateUserResult(UserResultDTO user){
		UserResult update = rs.updateUserResult(user);
		return new ResponseEntity<UserResult>(update,HttpStatus.OK);
	}

	//============ DELETE USER RESULT =============
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public String deleteUserByID(@PathVariable(name = "id")int id){
		rs.deleteUserById(id);
		return "Deleted successfully";
	}


}
