package com.source.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.source.entity.WordAndClue;
@Repository
public interface WordAndClueRepository extends JpaRepository<WordAndClue, Integer>{
	WordAndClue findByWord(String word);
	
	
	@Modifying
	@Transactional
	@Query(value = "DELETE s FROM " + 
			"word_clue s WHERE " + 
			"s.word_clue_id = :id", nativeQuery = true)
	void deleteById(@Param("id")int id);
}
