package com.javaudemy.SpringBoot_Ionic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javaudemy.SpringBoot_Ionic.domain.Client;


@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>{

}
