package com.javaudemy.SpringBoot_Ionic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javaudemy.SpringBoot_Ionic.domain.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>{

}
