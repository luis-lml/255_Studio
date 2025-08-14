package com.photobooking_api.controller;

import com.photobooking_api.dto.AppointmentRequestDTO;
import com.photobooking_api.dto.AppointmentResponseDTO;
import com.photobooking_api.mapper.AppointmentMapper;
import com.photobooking_api.model.Appointment;
import com.photobooking_api.service.AppointmentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;    
import org.springframework.web.bind.annotation.RestController;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import jakarta.validation.Valid;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService service;
    private final AppointmentMapper mapper;

    public AppointmentController(AppointmentService service, AppointmentMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    // Wrapper para crear una cita
    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> createAppointment(@Valid @RequestBody AppointmentRequestDTO appointmentRequest) {
        Appointment savedAppointment = service.save(appointmentRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedAppointment.getId())
                .toUri();
        return ResponseEntity.created(location).body(mapper.toResponseDTO(savedAppointment));
    }

    // Wrapper para listar todas las citas
    @GetMapping
    public List<AppointmentResponseDTO> getAllAppointments() {
        return service.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    // Wrapper para buscar citas por ID
   @GetMapping("/{id}")
public ResponseEntity<AppointmentResponseDTO> getAppointmentById(@PathVariable Long id) {
    return service.findById(id)
            .map(mapper::toResponseDTO)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
}

@GetMapping("/by-booking-date")
public List<AppointmentResponseDTO> getAppointmentsByBookingDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
    return service.findByBookingDate(date).stream()
            .map(mapper::toResponseDTO)
            .collect(Collectors.toList());
}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
       boolean deleted = service.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build(); //204 No Content for successful deletion
        } else {
            return ResponseEntity.notFound().build(); //404 Not Found if source not found
    }
}


@GetMapping("/test-db")
public ResponseEntity<?> testDatabaseConnection() {
    try {
        long count = service.count();
        return ResponseEntity.ok("Conexión exitosa. Total de citas: " + count);
    } catch (Exception e) {
        return ResponseEntity.internalServerError().body("Error al conectar a la base de datos: " + e.getMessage());
    }
}
}
