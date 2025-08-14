package com.photobooking_api.repository;

import com.photobooking_api.model.Appointment;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByBookingDate(LocalDate bookingDate);
}
