package com._photobooking.photobooking_api.controller;

import com._photobooking.photobooking_api.model.Appointment;
import com._photobooking.photobooking_api.service.AppointmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;    
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    
    private AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    // Wrapper para crear una cita
    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody Appointment appointment) {
        try {
           Appointment savedAppointment = service.save(appointment);
           URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedAppointment.getId())
                    .toUri();
            return ResponseEntity.created(location).body(savedAppointment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear la cita: " + e.getMessage());
        }
    }

    // Wrapper para listar todas las citas
    @GetMapping
    public List<Appointment> getAllAppointments() {
        return service.findAll();
    }
    
    // Wrapper para buscar citas por ID
   @GetMapping("/{id}")
public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
    return service.findById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
}

//     @GetMapping("/by-booking-date")
// public List<Appointment> getByBookingDate(@RequestParam("date") LocalDate date) {
//     return service.findByAppointmentDate(date);
// }


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
        // Intenta contar las citas en la base de datos
        long count = service.count();
        return ResponseEntity.ok("Conexión exitosa. Total de citas: " + count);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al conectar a la base de datos: " + e.getMessage());
    }
}
}
