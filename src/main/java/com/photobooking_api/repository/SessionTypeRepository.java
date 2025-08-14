package com.photobooking_api.repository;

import com.photobooking_api.model.SessionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionTypeRepository extends JpaRepository<SessionType, Long> {
    Optional<SessionType> findByName(String name);
}