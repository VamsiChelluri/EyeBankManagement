package com.accolite.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.accolite.entity.Donor;
import com.accolite.entity.Recipient;

@Repository
public interface RecipientRepo extends JpaRepository<Recipient, Long>{

	@Modifying
	@Transactional
	@Query(value="update Recipient r set r.approval=?2 where r.id=?1",nativeQuery = true)
	int updateRecipientBasedOnName(Long id, String approval);

	List<Recipient> findByAgeBetween(int from, int to);



}
