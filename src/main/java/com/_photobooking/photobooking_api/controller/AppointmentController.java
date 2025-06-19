package com._photobooking.photobooking_api.controller;

import com._photobooking.photobooking_api.model.Appointment;
import com._photobooking.photobooking_api.service.AppointmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService service;

    // Wrapper para crear una cita
    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody Appointment appointment) {
        try {
            Appointment saved = service.save(appointment);
            return ResponseEntity.ok().body(saved);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear la cita: " + e.getMessage());
        }
    }

    // Wrapper para listar todas las citas
    @GetMapping
    public List<Appointment> listAppointments() {
        return service.listAll();
    }

    @DeleteMapping(
        value = "/{id}",
        produces = "application/json"
    )
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id) {
       boolean deleted = service.delete(id);
        if (deleted) {
            return ResponseEntity.ok().body("Cita eliminada con éxito");
        } else {
            return ResponseEntity.badRequest().body("Cita no encontrada o no se pudo eliminar");
        }
    }


@GetMapping("/test-db")
public ResponseEntity<?> testDatabaseConnection() {
    try {
        // Intenta contar las citas en la base de datos
        long count = service.listAll().size();
        return ResponseEntity.ok("Conexión exitosa. Total de citas: " + count);
    } catch (Exception e) {
        return ResponseEntity.status(500).body("Error de conexión a la base de datos: " + e.getMessage());
    }
}

    
}
