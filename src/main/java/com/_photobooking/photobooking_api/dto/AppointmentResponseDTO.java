package com._photobooking.photobooking_api.dto;

import com._photobooking.photobooking_api.model.Appointment;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class AppointmentResponseDTO {
    private Long id;
    private Long clientId;
    private String clientName;
    private String sessionTypeName;
    private Integer portraitPackageId;
    private LocalDate bookingDate;
    private LocalTime bookingTime;
    private String comments;
    private Appointment.Status status;
    private LocalDateTime createdAt;
}