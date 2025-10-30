package rw.auca.ac.pharmacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rw.auca.ac.pharmacy.model.core.Medicine;

import java.util.List;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    List<Medicine> findByNameContainingIgnoreCase(String name);
    List<Medicine> findByType(String type);
    @Query("SELECT m FROM Medicine m WHERE m.quantity <= m.reorderLevel")
    List<Medicine> findLowStock();
}