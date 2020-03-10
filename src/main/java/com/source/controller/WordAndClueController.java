package com.source.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.source.DTO.WordAndClueDTO;
import com.source.entity.WordAndClue;
import com.source.services.WordAndClueService;

@RestController
@RequestMapping(value = "/wordandclue")
@CrossOrigin("*")
public class WordAndClueController {
	
	@Autowired
	private WordAndClueService wcs;
	

	//============= LIST ALL WORDS AND CLUES ===============
	@RequestMapping(value = "/allwordandclue", method = RequestMethod.GET)
	public ResponseEntity<List<WordAndClueDTO>> getAllWordAndClue(){
		return new ResponseEntity<List<WordAndClueDTO>>(wcs.findAll(), HttpStatus.OK);
	}
	//============= SEARCH WORDS AND CLUES =================
	@RequestMapping(value = "/findbyid/{id}", method = RequestMethod.GET)
	public ResponseEntity<WordAndClueDTO> findByID(@PathVariable(name = "id") int id) {
		WordAndClueDTO w = wcs.findById(id);
		return new ResponseEntity<WordAndClueDTO>(w,HttpStatus.OK);
	}

	@RequestMapping(value = "/findbyname/{name}", method = RequestMethod.GET)
	public ResponseEntity<WordAndClueDTO> findByName(@RequestParam(name = "name") String name) {
		WordAndClueDTO user = wcs.findByContent(name);
		return new ResponseEntity<WordAndClueDTO>(user,HttpStatus.OK);
	}
	
	//============= CREATE WORDS AND CLUES ===========
	@RequestMapping(value = "/createwordandclue/", method = RequestMethod.POST)
	public ResponseEntity<WordAndClue> createWordAndClue(WordAndClueDTO dto){
		WordAndClue create = wcs.createWordAndClue(dto);
		return new ResponseEntity<WordAndClue>(create,HttpStatus.OK);
	}
	
	//============= UPDATE WORDS AND CLUES ===========
	@RequestMapping(value = "/update/", method = RequestMethod.POST)
	public ResponseEntity<WordAndClue> updateUser(WordAndClueDTO dto){
		WordAndClue update = wcs.updateWordAndClue(dto);
		return new ResponseEntity<WordAndClue>(update,HttpStatus.OK);
	}

	//============ DELETE  WORDS AND CLUES =============
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public String deleteByID(@PathVariable(name = "id")int id){
		wcs.deleteById(id);
		return "Deleted successfully";
	}
	
}
