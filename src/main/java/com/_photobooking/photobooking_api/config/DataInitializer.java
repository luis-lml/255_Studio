package com._photobooking.photobooking_api.config;

import com._photobooking.photobooking_api.model.Appointment;
import com._photobooking.photobooking_api.model.Client;
import com._photobooking.photobooking_api.model.SessionType;
import com._photobooking.photobooking_api.repository.AppointmentRepository;
import com._photobooking.photobooking_api.repository.ClientRepository;
import com._photobooking.photobooking_api.repository.SessionTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final SessionTypeRepository sessionTypeRepository;
    private final AppointmentRepository appointmentRepository;
    private final ClientRepository clientRepository;

    public DataInitializer(SessionTypeRepository sessionTypeRepository, AppointmentRepository appointmentRepository, ClientRepository clientRepository) {
        this.sessionTypeRepository = sessionTypeRepository;
        this.appointmentRepository = appointmentRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Solo inicializar si no hay datos
        if (appointmentRepository.count() > 0) {
            System.out.println("La base de datos ya contiene citas. No se requiere inicialización.");
            return;
        }
        System.out.println("No se encontraron citas, cargando datos de prueba...");

        // Usar un patrón "find or create" para que la inicialización sea idempotente.
        // Esto evita crear duplicados si el inicializador se ejecuta varias veces.
        SessionType portraitSession = findOrCreateSessionType("Sesión de Retrato");
        findOrCreateSessionType("Sesión de Producto");

        // Crear y guardar un cliente de prueba
        Client testClient = clientRepository.findByEmail("test@example.com")
            .orElseGet(() -> {
                Client newClient = new Client();
                newClient.setName("Cliente de Prueba");
                newClient.setEmail("test@example.com");
                return clientRepository.save(newClient);
            });

        // Crear y guardar citas de prueba
        Appointment app1 = new Appointment();
        app1.setClient(testClient); // Asignar el objeto Client persistido
        app1.setSessionType(portraitSession);
        app1.setBookingDate(LocalDate.now().plusDays(10));
        app1.setBookingTime(LocalTime.of(14, 30));
        app1.setComments("Cita para retrato familiar.");
        app1.setStatus(Appointment.Status.confirmed);
        appointmentRepository.save(app1);
        System.out.println("Datos de prueba cargados exitosamente.");
    }

    private SessionType findOrCreateSessionType(String name) {
        return sessionTypeRepository.findByName(name)
                .orElseGet(() -> {
                    SessionType newSessionType = new SessionType();
                    newSessionType.setName(name);
                    return sessionTypeRepository.save(newSessionType);
                });
    }
}
