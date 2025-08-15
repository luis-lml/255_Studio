package com.photobooking_api.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class AppointmentRequestDTO {

    @NotBlank(message = "El nombre del cliente no puede estar vacío.")
    private String clientName;

    @NotBlank(message = "El apellido del cliente no puede estar vacío.")
    private String lastName;

    // El email es una buena forma de identificar a un cliente
    @NotBlank(message = "El email del cliente no puede estar vacío.")
    @Email(message = "El formato del email no es válido.")
    private String clientEmail;

    @NotNull(message = "La fecha de la cita no puede ser nula.")
    @FutureOrPresent(message = "La fecha de la cita debe ser hoy o en el futuro.")
    private LocalDate bookingDate;

    @NotNull(message = "La hora de la cita no puede ser nula.")
    private Map<String, Integer> bookingTime;

    private String comments;
}
