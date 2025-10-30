package rw.auca.ac.pharmacy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.auca.ac.pharmacy.model.core.PrescriptionItem;

public interface PrescriptionItemRepository extends JpaRepository<PrescriptionItem, Long> {
    List<PrescriptionItem> findByPrescriptionId(Long prescriptionId);
    List<PrescriptionItem> findByMedicineId(Long medicineId);
}