package com._photobooking.photobooking_api.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.*;
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

     @Column(name = "client_id", nullable = false)
    private Integer clientId;

    @Column(name = "session_type_id", nullable = false, length = 30)
    private String sessionTypeId;

    @Column(name = "portrait_package_id")
    private Integer portraitPackageId;

    @Column(name = "booking_date", nullable = false)
    private LocalDate bookingDate;

    @Column(name = "booking_time", nullable = false)
    private LocalTime bookingTime;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.pending;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public enum Status {
        pending, confirmed, cancelled
    }


    // Getters y setters

    // public Long getId() { return id; }
    // public void setId(Long id) { this.id = id; }

    // public Integer getClientId() { return clientId; }
    // public void setClientId(Integer clientId) { this.clientId = clientId; }

    // public String getSessionTypeId() { return sessionTypeId; }
    // public void setSessionTypeId(String sessionTypeId) { this.sessionTypeId = sessionTypeId; }

    // public Integer getPortraitPackageId() { return portraitPackageId; }
    // public void setPortraitPackageId(Integer portraitPackageId) { this.portraitPackageId = portraitPackageId; }

    // public LocalDate getBookingDate() { return bookingDate; }
    // public void setBookingDate(LocalDate bookingDate) { this.bookingDate = bookingDate; }

    // public LocalTime getBookingTime() { return bookingTime; }
    // public void setBookingTime(LocalTime bookingTime) { this.bookingTime = bookingTime; }

    // public String getComments() { return comments; }
    // public void setComments(String comments) { this.comments = comments; }

    // public Status getStatus() { return status; }
    // public void setStatus(Status status) { this.status = status; }

    // public LocalDateTime getCreatedAt() { return createdAt; }
    // public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
