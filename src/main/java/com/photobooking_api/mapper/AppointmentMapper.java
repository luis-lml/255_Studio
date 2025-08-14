
package com.photobooking_api.mapper;

import com.photobooking_api.dto.AppointmentResponseDTO;
import com.photobooking_api.model.Appointment;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    public AppointmentResponseDTO toResponseDTO(Appointment appointment) {
        if (appointment == null) {
            return null;
        }

        AppointmentResponseDTO dto = new AppointmentResponseDTO();
        dto.setId(appointment.getId());
        if (appointment.getClient() != null) {
            dto.setClientId(appointment.getClient().getId());
            dto.setClientName(appointment.getClient().getName());
        }

        // Extraemos solo el nombre del tipo de sesión para el DTO
        if (appointment.getSessionType() != null) {
            dto.setSessionTypeName(appointment.getSessionType().getName());
        }
        dto.setPortraitPackageId(appointment.getPortraitPackageId());
        dto.setBookingDate(appointment.getBookingDate());
        dto.setBookingTime(appointment.getBookingTime());
        dto.setComments(appointment.getComments());
        dto.setStatus(appointment.getStatus());
        dto.setCreatedAt(appointment.getCreatedAt());
        return dto;
    }
}
