package com.photobooking_api.service;

import org.springframework.stereotype.Service;
import com.photobooking_api.dto.AppointmentRequestDTO;
import com.photobooking_api.exception.ResourceNotFoundException;
import com.photobooking_api.model.Appointment;
import com.photobooking_api.model.Client;
import com.photobooking_api.model.SessionType;
import com.photobooking_api.repository.AppointmentRepository;
import com.photobooking_api.repository.ClientRepository;
import com.photobooking_api.repository.SessionTypeRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final SessionTypeRepository sessionTypeRepository;
    private final ClientRepository clientRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, SessionTypeRepository sessionTypeRepository, ClientRepository clientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.sessionTypeRepository = sessionTypeRepository;
        this.clientRepository = clientRepository;
    }

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> findById(Long id) {
        return appointmentRepository.findById(id);
    }

    public Appointment save(AppointmentRequestDTO appointmentRequest) {
        // Buscamos la entidad SessionType a partir del ID que viene en el DTO
        SessionType sessionType = sessionTypeRepository.findById(appointmentRequest.getSessionTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("SessionType no encontrado con id: " + appointmentRequest.getSessionTypeId()));

        // Lógica para encontrar o crear un cliente
        Client client = findOrCreateClient(appointmentRequest.getClientName(), appointmentRequest.getClientEmail());

        // Creamos la entidad Appointment
        Appointment appointment = new Appointment();
        appointment.setClient(client);
        appointment.setSessionType(sessionType);
        appointment.setPortraitPackageId(appointmentRequest.getPortraitPackageId());
        appointment.setBookingDate(appointmentRequest.getBookingDate());
        appointment.setBookingTime(appointmentRequest.getBookingTime());
        appointment.setComments(appointmentRequest.getComments());
        // El status y createdAt se manejan automáticamente

        return appointmentRepository.save(appointment);
    }

    private Client findOrCreateClient(String name, String email) {
        // Busca al cliente por email si se proporciona. Si no, crea uno nuevo.
        return clientRepository.findByEmail(email)
                .orElseGet(() -> {
                    Client newClient = new Client();
                    newClient.setName(name);
                    newClient.setLastname(newClient.getLastname());
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
