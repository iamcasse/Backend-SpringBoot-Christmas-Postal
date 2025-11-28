package com.backend.service.postales.repository;

import com.backend.service.postales.entity.Postal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostalRepository extends JpaRepository<Postal, Long> {

    Optional<Postal> findByCode(String code);


}
