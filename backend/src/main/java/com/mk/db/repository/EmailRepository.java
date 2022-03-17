package com.mk.db.repository;

import com.mk.db.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<Email, Integer> {

    Optional<Email> findByEmailAndKey(String email, String key);

    Optional<Email> findByEmail(String email);
}
