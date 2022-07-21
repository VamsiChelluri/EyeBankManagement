package com.example.EyeBank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.EyeBank.entities.DonorEntity;


public interface DonorRepository extends JpaRepository<DonorEntity, Long> {
	void deleteByStatus(String status);
	List<DonorEntity> findByAgeBetween(Integer startAge, Integer endAge);
	@Query(value = "select d.first_name, d.status from Donor_Entity d group by d.status", nativeQuery=true)
	List<Object> getNameAndStatus();
	@Modifying
	@Transactional
	@Query(value="update donor_entity d set d.status=?2 where d.id=?1",nativeQuery = true)
	DonorEntity updateApprovalBasedOnName(Long id, String status);
	
	@Modifying
	@Transactional
	@Query(value="update donor_entity d set d.extra_info=?2 where d.id=?1",nativeQuery = true)
	DonorEntity updateExtraInfo(Long id, String extraInfo);
}
