package com.actividad3.gutierrez.patronesapi.controller;

import com.actividad3.gutierrez.patronesapi.model.PatronDiseno;
import com.actividad3.gutierrez.patronesapi.service.PatronService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/patrones")
public class PatronController {
    private final PatronService service;

    public PatronController(PatronService service){
        this.service = service;
    }


    @GetMapping
    public ResponseEntity<List<PatronDiseno>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatronDiseno> findById(@PathVariable Long id){
        return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PatronDiseno> create(@Valid @RequestBody PatronDiseno patronDiseno){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(patronDiseno));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatronDiseno> update(@PathVariable Long id, @Valid @RequestBody PatronDiseno patronDiseno){
        return service.update(id,patronDiseno).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        if (service.delete(id)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Holaaa!");
    }

}
