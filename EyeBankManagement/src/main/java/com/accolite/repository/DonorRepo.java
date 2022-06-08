package com.accolite.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.accolite.entity.Donor;


@Repository
public interface DonorRepo extends JpaRepository<Donor, Long> {

	List<Donor> findByLocation(String location);

	List<Donor> findByBloodGroup(String bloodGroup);

	@Query(value = "select * from donor d where d.approval=true",nativeQuery = true)
	List<Donor> getDonorsByRegistration();

	@Modifying
	@Transactional
	@Query(value="update donor d set d.approval=?2 where d.id=?1",nativeQuery = true)
	int updateApprovalBasedOnName(Long id, String approval);

	@Modifying
	@Transactional
	@Query(value="update donor d set d.extra_info=?2 where d.id=?1",nativeQuery = true)
	int updateExtraInfo(Long id, String extraInfo);

	
	@Query(value="select d.name as name,d.approval as approval from donor d  order by d.approval" ,nativeQuery = true)
	List<Object> findByApproval();

	@Modifying
	@Transactional
	@Query(value="delete from donor d where d.id=?1 and d.approval=?2",nativeQuery = true)
	int deleteByApprovalAndName(Long id, String approval);

	List<Donor> findByAgeBetween(int from, int to);

	@Modifying
	@Transactional
	@Query(value="delete from donor d where d.approval=?1",nativeQuery = true)
	int deleteByApproval(String string);

	Donor findDonorById(Long id);
}
