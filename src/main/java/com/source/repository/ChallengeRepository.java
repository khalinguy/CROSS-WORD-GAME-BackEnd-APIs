package com.source.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.source.entity.Challenge;

public interface ChallengeRepository extends JpaRepository<Challenge, Integer>{

}
