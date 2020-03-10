package com.source.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.source.DTO.ChallengeDTO;
import com.source.entity.Challenge;
import com.source.entity.WordAndClue;
import com.source.services.ChallengeService;

@RestController
@RequestMapping(value = "/challenge")
public class ChallengeController extends BaseController {
	
	@Autowired
	private ChallengeService cs;
	

	//============= LIST ALL CHALLENGES ===============
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<List<ChallengeDTO>> getAllChallenge(){
		return new ResponseEntity<List<ChallengeDTO>>(cs.findAll(), HttpStatus.OK);
	}
	
	//============= SEARCH CHALLENGES =================
	@RequestMapping(value = "/findbyid/{id}", method = RequestMethod.GET)
	public ResponseEntity<ChallengeDTO> findByID(@PathVariable(name = "id") int id) {
		ChallengeDTO c = cs.findById(id);
		return new ResponseEntity<ChallengeDTO>(c,HttpStatus.OK);
	}

	@RequestMapping(value = "/findbyname/{name}", method = RequestMethod.GET)
	public ResponseEntity<ChallengeDTO> findByName(@RequestParam(name = "name") String name) {
		ChallengeDTO c = cs.findByName(name);
		return new ResponseEntity<ChallengeDTO>(c,HttpStatus.OK);
	}
	//============= CREATE DEMO ===========
	@RequestMapping(value = "/createdemo/", method = RequestMethod.POST)
	public ResponseEntity<Map<List<String>, char[][]>> createDemoChallenge(@RequestBody ChallengeDTO challenge) throws Exception, ParseException{
		Challenge demo = cs.convertToChallenge(challenge);
		
		System.out.println(demo.getWordAndClue());
		//Stack of challenge words 
		Stack<String> arrayWord = new Stack<>();

		//Set of word and clue in the challenge 
		Set<WordAndClue> names = demo.getWordAndClue();


		Spliterator<WordAndClue> splitor = names.spliterator();

		splitor.forEachRemaining((w) -> arrayWord.push(w.getWord()));
		char[][] table = cs.generateTableContent(demo.getSize(), arrayWord);
		
		List<String> keyWordPosition = cs.getDEMOPositionForEachKeyWords(demo);
		System.out.println(keyWordPosition);
		
		Map<List<String>, char[][]> demoData = new HashMap<>();
		
		demoData.put(keyWordPosition,table);
		
		return new ResponseEntity<Map<List<String>, char[][]>>(demoData,HttpStatus.OK);
	}

	//============= CREATE CHALLENGE ===========
	@RequestMapping(value = "/create/", method = RequestMethod.POST)
	public ResponseEntity<Map<String,char[][]>> createChallenge(@RequestBody ChallengeDTO challenge) throws Exception, ParseException{
		Map<String,char[][]> create = cs.createChallenge(challenge);
		return new ResponseEntity<Map<String,char[][]>>(create,HttpStatus.OK);
	}
	//============= PUT TABLE CHALLENGES =================
	@RequestMapping(value = "/putTable/{id}", method = RequestMethod.GET)
	public ResponseEntity<char[][]> putTable(@PathVariable(name = "id") int id) throws ParseException, Exception {
		if(cs.findById(id) == null) {
			throw new Exception ("Challenge is not exist! Check challenge ID");
		}
		
		char[][] c = cs.putTableContent(id);
		return new ResponseEntity<char[][]>(c,HttpStatus.OK);
	}
	
	
	//============= UPDATE CHALLENGE ===========
	@RequestMapping(value = "/update/", method = RequestMethod.POST)
	public ResponseEntity<Challenge> updateChallenge(@RequestBody ChallengeDTO challenge) throws ParseException{
		Challenge update = cs.updateChallenge(challenge);
		return new ResponseEntity<Challenge>(update,HttpStatus.OK);
	}
	
	//============= DELETE CHALLENGE ==========
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public String deleteChallenge(@PathVariable(name = "id")int id) throws ParseException{
		cs.deleteChallengeById(id);
		return "Delete sucessfully!";
	}
	
}
