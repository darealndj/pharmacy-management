package rw.auca.ac.pharmacy.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.auca.ac.pharmacy.dto.PrescriptionCreateRequest;
import rw.auca.ac.pharmacy.dto.PrescriptionDto;
import rw.auca.ac.pharmacy.service.PrescriptionService;

@RestController
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService service;

    @PostMapping
    public ResponseEntity<PrescriptionDto> create(@Valid @RequestBody PrescriptionCreateRequest request) {
        PrescriptionDto dto = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    // PUT / DELETE can be implemented later as needed
}
