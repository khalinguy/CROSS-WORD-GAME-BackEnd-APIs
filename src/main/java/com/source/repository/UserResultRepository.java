package com.source.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.source.entity.UserResult;

@Repository
public interface UserResultRepository extends JpaRepository<UserResult, Integer>{
	
	 List<UserResult> findAllByUserIDAndChallengeID(int userId, int challengeId);
	 
	 @Query(value = "SELECT s.word " + 
	 		"FROM user_result s, word_clue w " + 
	 		"WHERE s.challenge_id = :challenge_id " + 
	 		"AND s.user_id = :user_id " +
	 		"AND (s.word = w.word " + 
	 		"OR REVERSE(s.word) = w.word)", nativeQuery = true)
	 List<String> countUserTotalPoint(@Param("user_id") int userId, @Param("challenge_id") int challengeId);
	 
}
