package com.actividad3.gutierrez.patronesapi.service;

import com.actividad3.gutierrez.patronesapi.model.PatronDiseno;
import com.actividad3.gutierrez.patronesapi.repository.PatronRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatronService {

    private final PatronRepository patronRepository;

    public PatronService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    public List<PatronDiseno> findAll() {
        return patronRepository.findAll();
    }

    public Optional<PatronDiseno> findById(Long id) {
        return patronRepository.findById(id);
    }

    public PatronDiseno create(PatronDiseno patronDiseno) {
        return patronRepository.save(patronDiseno);
    }

    public Optional<PatronDiseno> update(Long id, PatronDiseno patronDiseno) {
        if (!patronRepository.existsById(id)){
            return Optional.empty();
        }
        patronDiseno.setId(id);
        return Optional.of(patronRepository.save(patronDiseno));
    }

    public boolean delete(Long id) {
        return patronRepository.deleteById(id);
    }
}
