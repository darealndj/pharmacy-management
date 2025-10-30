package rw.auca.ac.pharmacy.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.auca.ac.pharmacy.dto.BillDto;
import rw.auca.ac.pharmacy.dto.BillRequest;
import rw.auca.ac.pharmacy.service.BillService;

@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor
public class BillController {

    private final BillService service;

    @PostMapping("/for-prescription/{prescriptionId}")
    public ResponseEntity<BillDto> create(@PathVariable Long prescriptionId,
                                          @Valid @RequestBody BillRequest request) {
        BillDto dto = service.createForPrescription(prescriptionId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    // Future update/delete endpoints can follow this same pattern
}
