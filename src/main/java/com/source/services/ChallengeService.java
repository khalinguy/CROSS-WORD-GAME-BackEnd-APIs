package com.source.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Spliterator;
import java.util.Stack;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.source.DTO.ChallengeDTO;
import com.source.core.CrossWord;
import com.source.core.WordPositionManagement;
import com.source.core.WordPosition;
import com.source.entity.Challenge;
import com.source.entity.WordAndClue;
import com.source.repository.ChallengeRepository;
import com.source.repository.WordAndClueRepository;


@Service
public class ChallengeService {
	@Autowired
	private ChallengeRepository repo;
	
	@Autowired
	private WordAndClueService wandService;
	
	@Autowired
	private WordAndClueRepository wandRepo;
	
	@Bean
	public CrossWord crossWord() {
		return new CrossWord();
	}
	public List<ChallengeDTO> findAll() {
		List<ChallengeDTO> allChallenges = new ArrayList<ChallengeDTO>();
		
		for (Challenge c: repo.findAll()) {
			allChallenges.add(this.convertToDTO(c));
		}
		return allChallenges;
		
	}
	
	public ChallengeDTO findById(int id) {
		Optional<Challenge> challenge = repo.findById(id);
        if(challenge.isPresent()) {
            return this.convertToDTO(challenge.get());
        } else {
            return null;
        }
	}
	
	public ChallengeDTO findByName(String name) {
		Challenge challengeByName = new Challenge();
		for(Challenge challenge : repo.findAll()) {
			if(challenge.getName().equalsIgnoreCase(name)) {
				challengeByName = challenge; 
				
			}
		}
		return this.convertToDTO(challengeByName);
	
	}
	
	public Map<String,char[][]> createChallenge (ChallengeDTO dto) throws Exception {
		Challenge challenge = this.convertToChallenge(dto);
		
		System.out.println(challenge.getWordAndClue());
		//Stack of challenge words 
		Stack<String> arrayWord = new Stack<>();

		//Set of word and clue in the challenge 
		Set<WordAndClue> names = challenge.getWordAndClue();


		Spliterator<WordAndClue> splitor = names.spliterator();

		splitor.forEachRemaining((w) -> arrayWord.push(w.getWord()));
		
		char[][] table = this.generateTableContent(challenge.getSize(), arrayWord);
		
		
		
		//Make a new challenge with challenge data 
		Challenge newChallenge = new Challenge();
		newChallenge.setName(challenge.getName());
		newChallenge.setStartTime(challenge.getStartTime());
		newChallenge.setEndTime(challenge.getEndTime());
		newChallenge.setSize(challenge.getSize());
		
		//Set created date
				LocalDateTime now = LocalDateTime.now();
				Date date = Date.from(now.atZone( ZoneId.systemDefault()).toInstant());
				newChallenge.setCreatedTime(date);
		repo.save(newChallenge);
		
		//Set new challenge id for word and clue
		for (WordAndClue w: challenge.getWordAndClue()) {
			w.setChallengeId(newChallenge.getId());
	
		}
		
		newChallenge.setWordAndClue(challenge.getWordAndClue());
		
		getPositionForEachKeyWords(newChallenge);
		updateChallenge(this.convertToDTO(newChallenge));
		
		Map<String,char[][]> returnO = new HashMap<>();
		returnO.put("Challenge ID: " + newChallenge.getId() + " " + newChallenge.getName(), table);
		
		return returnO;
	}
	
	public char[][] putTableContent(int challengeId) throws Exception, ParseException {
		
		Challenge challenge = convertToChallenge(findById(challengeId));
		
		//Stack of challenge words 
		Stack<String> arrayWord = new Stack<>();
	  
		//Set of word and clue in the challenge 
		Set<WordAndClue> names = challenge.getWordAndClue();
	  
	  
		Spliterator<WordAndClue> splitor = names.spliterator();
	  
		splitor.forEachRemaining((w) -> arrayWord.push(w.getWord()));
	  
		char[][] character = generateTableContent(challenge.getSize(), arrayWord);
		
		
		//TABLE CONTENT IN JSON STRING
		/*
		 * String tableContent = "";
		 * 
		 * JSONObject jsonString = new JSONObject();
		 * 
		 * for (int i = 0; i < challenge.getSize(); i++) { for (int j = 0; j <
		 * challenge.getSize(); j++) { JSONArray jsonObj = new JSONArray();
		 * jsonObj.put(i); jsonObj.put(j);
		 * 
		 * jsonString.put("character", String.valueOf(character[i][j]));
		 * jsonString.put("row", i); jsonString.put("column", j);
		 * 
		 * tableContent += jsonString.toString(); } }
		 */
		
		//GET POSITION FOR KEY WORDS
		getPositionForEachKeyWords(challenge);
			
		
		
		
		//Put table content 
		//challenge.setTableContent(tableContent);
		
		return character;
		
	}
	
	public Challenge updateChallenge (ChallengeDTO dto) throws ParseException{
		
		Challenge challenge = this.convertToChallenge(dto);
		Optional<Challenge> challenges = repo.findById(challenge.getId());
	
		if (challenges.isPresent()) {
			Challenge updateChallenge = challenges.get();
			updateChallenge.setName(challenge.getName());
			updateChallenge.setSize(challenge.getSize());
			//updateChallenge.setStartTime(challenge.getStartTime());
			//updateChallenge.setEndTime(challenge.getEndTime());
			updateChallenge.setStatus(challenge.getStatus());
			updateChallenge.setTableContent(challenge.getTableContent());
			
			
			//Set new challenge id for word and clue
			for (WordAndClue w: challenge.getWordAndClue()) {				
				w.setChallengeId(updateChallenge.getId());
			}
			
			List<WordAndClue> wList = new ArrayList<WordAndClue>();
			for (WordAndClue w: wandRepo.findAll()) {
				if (w.getChallengeId() == updateChallenge.getId()) {
					wList.add(w);
					System.out.println("------------" + w.getId());
					int id = w.getId();
					wandService.deleteById(id);
					System.out.println("ttttttDELETE");
				}
			}
			
			System.out.println(wList.toString());
			
			
			updateChallenge.setWordAndClue(challenge.getWordAndClue());
		

			updateChallenge = repo.save(updateChallenge);
			return updateChallenge;
		} else {

			return null;
		}

	}
	
	public void deleteChallengeById(int id) {
		Optional<Challenge> challenges = repo.findById(id);
		
		if(challenges.isPresent()) {
			repo.deleteById(id);
		}
	}
	
	public char[][]  generateTableContent(int TABLE_LENGTH, Stack<String> arrayWord) throws Exception{
		char[][]  tableContent = CrossWord.generateTable(TABLE_LENGTH, arrayWord);
		
		return tableContent;
	}
	
	//=================== GET DEMO POSITION FOR KEYWORD ============
	
	public List<String> getDEMOPositionForEachKeyWords(Challenge challenge) {		
		List<String> positionKeyWord = new ArrayList<>();
		
		JSONObject jsonNew = new JSONObject();
		WordPositionManagement management = crossWord().returnWordPositionManagement();
		Set<Map.Entry<String, WordPosition>> wP = management.getWordPositionMap().entrySet();
		for(Map.Entry<String, WordPosition> eachWP : wP) {
			String position = "";
			for(int i = 0; i < eachWP.getKey().length(); i++) {
				
				if (eachWP.getValue().getRedirect() == 0) {					
					jsonNew.put("character", String.valueOf(eachWP.getKey().toUpperCase().charAt(i)));
					jsonNew.put("row", eachWP.getValue().getRow());
					jsonNew.put("column", eachWP.getValue().getColumn()+i);
					position += jsonNew.toString();
				}
				if (eachWP.getValue().getRedirect() == 1) {
					jsonNew.put("character", String.valueOf(eachWP.getKey().toUpperCase().charAt(i)));
					jsonNew.put("row", eachWP.getValue().getRow()+i);
					jsonNew.put("column", eachWP.getValue().getColumn());
					position += jsonNew.toString();
				}
				if (eachWP.getValue().getRedirect() == 2) {
					jsonNew.put("character", String.valueOf(eachWP.getKey().toUpperCase().charAt(i)));
					jsonNew.put("row", eachWP.getValue().getRow()+i);
					jsonNew.put("column", eachWP.getValue().getColumn()+i);
					position += jsonNew.toString();
				}
			}
			
			for (WordAndClue word: challenge.getWordAndClue()) {
				
				StringBuilder wReverse = new StringBuilder(); 
				wReverse.append(eachWP.getKey()); 
				wReverse = wReverse.reverse(); 
				
				if(eachWP.getKey().trim().toUpperCase().equalsIgnoreCase(word.getWord().toUpperCase()) || 
						wReverse.toString().trim().toUpperCase().equalsIgnoreCase(word.getWord().toUpperCase())) {
					word.setPath(position);
						
				}
			}
			
			positionKeyWord.add(position);
			
			System.out.println( eachWP.getKey() + ": " + position);
		}
		
		management.getWordPositionMap().clear();
		
		return positionKeyWord;
	}
	//================== GET POSITION OF KEY WORD AND PUT TO DB ===========
	
	public void getPositionForEachKeyWords(Challenge challenge) {
		JSONObject jsonNew = new JSONObject();
		WordPositionManagement management = crossWord().returnWordPositionManagement();
		Set<Map.Entry<String, WordPosition>> wP = management.getWordPositionMap().entrySet();
		for(Map.Entry<String, WordPosition> eachWP : wP) {
			String position = "";
			for(int i = 0; i < eachWP.getKey().length(); i++) {
				
				if (eachWP.getValue().getRedirect() == 0) {					
					jsonNew.put("character", String.valueOf(eachWP.getKey().toUpperCase().charAt(i)));
					jsonNew.put("row", eachWP.getValue().getRow());
					jsonNew.put("column", eachWP.getValue().getColumn()+i);
					position += jsonNew.toString();
				}
				if (eachWP.getValue().getRedirect() == 1) {
					jsonNew.put("character", String.valueOf(eachWP.getKey().toUpperCase().charAt(i)));
					jsonNew.put("row", eachWP.getValue().getRow()+i);
					jsonNew.put("column", eachWP.getValue().getColumn());
					position += jsonNew.toString();
				}
				if (eachWP.getValue().getRedirect() == 2) {
					jsonNew.put("character", String.valueOf(eachWP.getKey().toUpperCase().charAt(i)));
					jsonNew.put("row", eachWP.getValue().getRow()+i);
					jsonNew.put("column", eachWP.getValue().getColumn()+i);
					position += jsonNew.toString();
				}
			}
			
			for (WordAndClue word: challenge.getWordAndClue()) {
				
				StringBuilder wReverse = new StringBuilder(); 
				wReverse.append(eachWP.getKey()); 
				wReverse = wReverse.reverse(); 
				
				if(eachWP.getKey().trim().toUpperCase().equalsIgnoreCase(word.getWord().toUpperCase()) || 
						wReverse.toString().trim().toUpperCase().equalsIgnoreCase(word.getWord().toUpperCase())) {
					word.setPath(position);
					wandService.updateWordAndClue(wandService.convertToDTO(word));
						
				}
			}
			
		
			System.out.println( eachWP.getKey() + ": " + position);
		}
		
		management.getWordPositionMap().clear();
		
		
	}
	
	//============ CONVERT TO CHALLENGE =========
	
	public Challenge convertToChallenge(ChallengeDTO dto) throws ParseException {
		String sDate = dto.getStartTime();
		String eDate = dto.getEndTime();
		SimpleDateFormat formatter1=new SimpleDateFormat("dd/MM/yyyy"); 
		
		Challenge challenge = new Challenge();
		challenge.setId(dto.getId());
		challenge.setName(dto.getName());
		challenge.setStartTime(formatter1.parse(sDate));
		challenge.setEndTime(formatter1.parse(eDate));
		challenge.setStatus(dto.getStatus());
		challenge.setTableContent(dto.getTableContent());
		challenge.setSize(dto.getSize());
		challenge.setWordAndClue(dto.getWordAndClue());
			
		return challenge;
		
	}
	
	
	
	//=========== CONVERT TO DTO ============
	public ChallengeDTO convertToDTO(Challenge challenge) {
		Date sdate = challenge.getStartTime();
		Date edate = challenge.getEndTime();

        // Display date in day, month, year format
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String s = formatter.format(sdate);
        String e = formatter.format(edate);
       
		
		ChallengeDTO dto = new ChallengeDTO();
		dto.setId(challenge.getId());
		dto.setName(challenge.getName());
		dto.setStartTime(s);
		dto.setEndTime(e);
		dto.setStatus(challenge.getStatus());
		dto.setTableContent(challenge.getTableContent());
		dto.setSize(challenge.getSize());
		dto.setWordAndClue(challenge.getWordAndClue());
		
	
		
		return dto;
	}
}
