package com.sena.meciccolombia.mediccolombia.service.impl;

import com.sena.meciccolombia.mediccolombia.dao.SuscriptorDAO;
import com.sena.meciccolombia.mediccolombia.domain.Suscriptor;
import com.sena.meciccolombia.mediccolombia.service.SuscriptorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SuscriptorServiceImpl implements SuscriptorService {

    private final SuscriptorDAO suscriptorDAO;

    @Override
    @Transactional
    public void guardar(String correo) {
        if (correo == null || correo.isBlank()) {
            throw new IllegalArgumentException("El correo no puede estar vacío.");
        }

        if (suscriptorDAO.existsByCorreo(correo)) {
            throw new IllegalStateException("Este correo ya está suscrito.");
        }

        Suscriptor suscriptor = Suscriptor.builder()
                .correo(correo.trim())
                .fechaSuscripcion(LocalDateTime.now())
                .build();

        suscriptorDAO.save(suscriptor);
    }
}