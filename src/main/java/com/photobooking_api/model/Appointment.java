package com.photobooking_api.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "appointments")
@NoArgsConstructor @AllArgsConstructor
@Data

public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El cliente no puede ser nulo.")
    @ManyToOne(cascade = CascadeType.PERSIST) // Opcional: para guardar el cliente junto con la cita
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @NotNull(message = "El tipo de sesión no puede ser nulo.")
    @ManyToOne
    @JoinColumn(name = "session_type_id", nullable = false)
    private SessionType sessionType;

    @Column(name = "portrait_package_id")
    private Integer portraitPackageId;
    
    @NotNull(message = "La fecha de la cita no puede ser nula.")
    @FutureOrPresent(message = "La fecha de la cita debe ser hoy o en el futuro.")
    @Column(name = "booking_date", nullable = false)
    private LocalDate bookingDate;
    @NotNull(message = "La hora de la cita no puede ser nula.")
    @Column(name = "booking_time", nullable = false)
    private LocalTime bookingTime;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "status", nullable = false)
    private Status status = Status.pending;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public enum Status {
        pending, confirmed, cancelled
    }

}
