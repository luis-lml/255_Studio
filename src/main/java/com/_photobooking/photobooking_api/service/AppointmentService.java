package com._photobooking.photobooking_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com._photobooking.photobooking_api.model.Appointment;
import com._photobooking.photobooking_api.repository.AppointmentRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> findById(Long id) {
        return appointmentRepository.findById(id);
    }

    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public boolean delete(Long id) {
        if (appointmentRepository.existsById(id)) {
            appointmentRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

     public long count() {
        return appointmentRepository.count();
    }

    // public List<Appointment> findByAppointmentDate(LocalDate date) {
    //     return appointmentRepository.findByAppointmentDate(date);
    // }

    public List<Appointment> findByBookingDate(LocalDate date) {
        return appointmentRepository.findByBookingDate(date);
    }

   
}
