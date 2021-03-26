package com.javaudemy.SpringBoot_Ionic.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.javaudemy.SpringBoot_Ionic.domain.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer>{

	@Transactional(readOnly=true)
	List<City> findByStateIdOrderByName(Integer stateId);
}
