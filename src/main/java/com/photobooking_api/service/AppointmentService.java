package com.photobooking_api.service;

import com.photobooking_api.dto.AppointmentRequestDTO;
import com.photobooking_api.model.Appointment;
import com.photobooking_api.model.Client;
import com.photobooking_api.repository.AppointmentRepository;
import com.photobooking_api.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ClientRepository clientRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, ClientRepository clientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.clientRepository = clientRepository;
    }

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> findById(Long id) {
        return appointmentRepository.findById(id);
    }

    public Appointment save(AppointmentRequestDTO appointmentRequest) {

        // Lógica para encontrar o crear un cliente
        Client client = findOrCreateClient(appointmentRequest.getClientName(), appointmentRequest.getLastName(), appointmentRequest.getClientEmail());

        // Map a LocalTime
        Map<String, Integer> bookingTimeMap = appointmentRequest.getBookingTime();
        List<Integer> timeValues = new ArrayList<>(bookingTimeMap.values());
        LocalTime bookingTime = LocalTime.of(
                timeValues.size() > 0 ? timeValues.get(0) : 0,
                timeValues.size() > 1 ? timeValues.get(1) : 0,
                timeValues.size() > 2 ? timeValues.get(2) : 0
        );

        // CEntidad Appointment
        Appointment appointment = new Appointment();
        appointment.setClient(client);
        appointment.setBookingDate(appointmentRequest.getBookingDate());
        appointment.setBookingTime(bookingTime);
        appointment.setComments(appointmentRequest.getComments());

        return appointmentRepository.save(appointment);
    }

    private Client findOrCreateClient(String name, String lastname, String email) {
        // Busca al cliente por email si se proporciona. Si no, crea uno nuevo.
        return clientRepository.findByEmail(email)
                .orElseGet(() -> {
                    Client newClient = new Client();
                    newClient.setName(name);
                    newClient.setLastname(lastname);
                    newClient.setEmail(email);
                    return clientRepository.save(newClient);
                });
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

    public List<Appointment> findByBookingDate(LocalDate date) {
        return appointmentRepository.findByBookingDate(date);
    }

}
