package rw.auca.ac.pharmacy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.auca.ac.pharmacy.model.core.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {
    Bill findByPrescriptionId(Long prescriptionId);
    List<Bill> findByStatus(String status);
}