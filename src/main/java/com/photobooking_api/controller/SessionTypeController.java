package com.photobooking_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import com.photobooking_api.model.SessionType;
import com.photobooking_api.repository.SessionTypeRepository;
import java.util.List;


@RestController
@RequestMapping("/session-types")

public class SessionTypeController {
    
    private final SessionTypeRepository sessionTypeRepository;

    public SessionTypeController(SessionTypeRepository sessionTypeRepository) {
        this.sessionTypeRepository = sessionTypeRepository;
    }
    @GetMapping
    public List<SessionType> getAllSessionTypes() {
        return sessionTypeRepository.findAll();
    }
    


}
