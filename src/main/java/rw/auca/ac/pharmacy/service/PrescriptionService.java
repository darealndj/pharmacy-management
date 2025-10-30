package rw.auca.ac.pharmacy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rw.auca.ac.pharmacy.dto.PrescriptionCreateRequest;
import rw.auca.ac.pharmacy.dto.PrescriptionDto;
import rw.auca.ac.pharmacy.model.core.Medicine;
import rw.auca.ac.pharmacy.model.core.Person;
import rw.auca.ac.pharmacy.model.core.Prescription;
import rw.auca.ac.pharmacy.model.core.PrescriptionItem;
import rw.auca.ac.pharmacy.model.enums.PrescriptionStatus;
import rw.auca.ac.pharmacy.model.enums.Role;
import rw.auca.ac.pharmacy.repository.MedicineRepository;
import rw.auca.ac.pharmacy.repository.PersonRepository;
import rw.auca.ac.pharmacy.repository.PrescriptionRepository;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepo;
    private final PersonRepository personRepo;
    private final MedicineRepository medicineRepo;

    // CREATE
    public PrescriptionDto create(PrescriptionCreateRequest request) {
        // Validate patient
        Person patient = personRepo.findById(request.getPatientId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        // Validate pharmacist
        Person pharmacist = personRepo.findById(request.getPharmacistId())
                .orElseThrow(() -> new IllegalArgumentException("Pharmacist not found"));

        if (pharmacist.getRole() != Role.PHARMACIST) {
            throw new IllegalArgumentException("Only pharmacists can issue prescriptions");
        }

        // Build prescription
        Prescription prescription = Prescription.builder()
                .dateIssued(request.getDateIssued())
                .status(PrescriptionStatus.PENDING)
                .patient(patient)
                .pharmacist(pharmacist)
                .notes(request.getNotes())
                .prescriptionItems(new ArrayList<>())
                .build();

        prescription = prescriptionRepo.save(prescription);

        // Process items
        if (request.getItems() != null && !request.getItems().isEmpty()) {
            for (PrescriptionCreateRequest.ItemRequest itemReq : request.getItems()) {
                Medicine medicine = medicineRepo.findById(itemReq.getMedicineId())
                        .orElseThrow(() -> new IllegalArgumentException("Medicine not found"));

                if (medicine.getQuantity() < itemReq.getQuantity()) {
                    throw new IllegalArgumentException(
                        "Insufficient stock for " + medicine.getName() +
                        ". Available: " + medicine.getQuantity() +
                        ", Requested: " + itemReq.getQuantity()
                    );
                }

                // Deduct stock
                medicine.setQuantity(medicine.getQuantity() - itemReq.getQuantity());
                medicineRepo.save(medicine);

                // Create item
                PrescriptionItem item = PrescriptionItem.builder()
                        .prescription(prescription)
                        .medicine(medicine)
                        .quantity(itemReq.getQuantity())
                        .build();

                prescription.addPrescriptionItem(item);
            }
        }

        prescription = prescriptionRepo.save(prescription);

        return toDto(prescription);
    }

    // READ - GET BY ID
    public PrescriptionDto get(Long id) {
        Prescription prescription = prescriptionRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Prescription not found"));

        return toDto(prescription);
    }

    // DTO MAPPER
    private PrescriptionDto toDto(Prescription p) {
        return PrescriptionDto.builder()
                .id(p.getId())
                .dateIssued(p.getDateIssued())
                .status(p.getStatus().name())
                .patientId(p.getPatient().getId())
                .pharmacistId(p.getPharmacist().getId())
                .notes(p.getNotes())
                .build();
    }
}   