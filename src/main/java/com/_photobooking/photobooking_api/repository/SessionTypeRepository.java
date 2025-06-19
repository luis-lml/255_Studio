package com._photobooking.photobooking_api.repository;

import com._photobooking.photobooking_api.model.SessionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionTypeRepository extends JpaRepository<SessionType, Long> {
}