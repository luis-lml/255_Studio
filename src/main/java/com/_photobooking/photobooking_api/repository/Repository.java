package com._photobooking.photobooking_api.repository;

import com._photobooking.photobooking_api.model.Appointment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

public interface Repository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDate(LocalDate date);
    
    Optional<Appointment> findByDate(LocalDateTime dateTime);
}
