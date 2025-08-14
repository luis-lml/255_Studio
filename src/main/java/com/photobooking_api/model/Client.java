package com.photobooking_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del cliente no puede estar vacío.")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "El apellido del cliente no puede estar vacío.")
    @Column(nullable = false)
    private String lastname;

    @Email(message = "El formato del email no es válido.")
    @Column(unique = true) // Opcional: hacer que el email sea único
    private String email;
}
