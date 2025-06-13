package com._photobooking.photobooking_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com._photobooking.photobooking_api.model.Appointment;
import com._photobooking.photobooking_api.repository.Repository;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private Repository repository;

    public Appointment save(Appointment appointment) throws Exception { 
        if (!repository.findByDate(appointment.getDate()).isEmpty()) {
            throw new Exception("Hora no disponible para la fecha seleccionada");
        }
        return repository.save(appointment);
    }

    public List<Appointment> listAll() {
        return repository.findAll();
    }

    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
