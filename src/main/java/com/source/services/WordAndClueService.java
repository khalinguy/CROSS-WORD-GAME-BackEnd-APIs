package com.source.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.source.DTO.WordAndClueDTO;
import com.source.entity.WordAndClue;
import com.source.repository.ChallengeRepository;
import com.source.repository.WordAndClueRepository;

@Service
public class WordAndClueService {
	
	@Autowired
	private WordAndClueRepository repo;
	@Autowired
	private ChallengeRepository cr;
	
	public List<WordAndClueDTO> findAll() {
		List<WordAndClueDTO> allWordAndClues = new ArrayList<WordAndClueDTO>();
		
		for (WordAndClue u: repo.findAll()) {
			allWordAndClues.add(this.convertToDTO(u));
		}
		return allWordAndClues;
		
	}
	
	public WordAndClue findByWord(String word) {
		return repo.findByWord(word);
	}
	public WordAndClueDTO findById(int id) {
		
		Optional<WordAndClue> wordAndClue = repo.findById(id);
		
        if(wordAndClue.isPresent()) {
            return this.convertToDTO(wordAndClue.get());
        } else {
            return null;
        }
	}
	
	public WordAndClueDTO findByContent(String content) {
		WordAndClue wandcByContent = new WordAndClue();
		for(WordAndClue u : repo.findAll()) {
			if(u.getWord().equalsIgnoreCase(content)) {
				wandcByContent = u; 
				
			}
		}
		return this.convertToDTO(wandcByContent);
	
	}
	
	public WordAndClue updateWordAndClue (WordAndClueDTO dto){
		
		WordAndClue wandc = this.convertToWordAndClue(dto);
		Optional<WordAndClue> wandcs = repo.findById(wandc.getId());
		
		if (wandcs.isPresent()) {
			WordAndClue updateW = wandcs.get();
			updateW.setWord(wandc.getWord());
			updateW.setClue(wandc.getClue());
			updateW.setChallengeId(dto.getChallengeId());
			updateW.setChallengeName(cr.findById(dto.getChallengeId()).get().getName());
			
			updateW = repo.save(updateW);
			return updateW;
		} else {
			
			return null;
		}
		
	}
	
	public WordAndClue createWordAndClue(WordAndClueDTO dto) {
		WordAndClue wandc = this.convertToWordAndClue(dto);
		wandc.setChallengeName(cr.findById(dto.getChallengeId()).get().getName());
		
		WordAndClue newWordAndClue = repo.save(wandc);
		return newWordAndClue;
	}
	
	public void deleteById(int id) {
		repo.deleteById(id);
	}
	
	
	
	public WordAndClue convertToWordAndClue(WordAndClueDTO dto) {
		WordAndClue wordAndClue = new WordAndClue();
		wordAndClue.setId(dto.getId());
		wordAndClue.setChallengeId(dto.getChallengeId());
		wordAndClue.setClue(dto.getClue());
		wordAndClue.setWord(dto.getWord());
		wordAndClue.setChallengeName(cr.findById(dto.getChallengeId()).get().getName());
		
		return wordAndClue;
		
	}
	
	public WordAndClueDTO convertToDTO(WordAndClue wandc) {
		WordAndClueDTO dto = new WordAndClueDTO();
		dto.setId(wandc.getId());
		dto.setClue(wandc.getClue());
		dto.setWord(wandc.getWord());
		dto.setChallengeId(wandc.getChallengeId());
		return dto;
	}
	
	
	
}
