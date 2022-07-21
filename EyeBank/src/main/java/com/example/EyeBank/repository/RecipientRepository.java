package com.example.EyeBank.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.EyeBank.entities.RecipientEnitity;

public interface RecipientRepository extends JpaRepository<RecipientEnitity, Long> {

	List<RecipientEnitity> findByAgeBetween(Integer startAge, Integer endAge);
	//RecipientEnitity findById(Integer id);
	@Modifying
	@Transactional
	@Query("update RecipientEnitity re set re.status = ?1 where re.id = ?2")
	Long setForClosure(String status, Long id);
	
}
