package rw.auca.ac.pharmacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.auca.ac.pharmacy.model.core.Prescription;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findByPatientId(Long patientId);
    List<Prescription> findByPharmacistId(Long pharmacistId);
    List<Prescription> findByStatus(String status);
}