package rw.auca.ac.pharmacy.repository.location;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.auca.ac.pharmacy.model.location.Village;

import java.util.List;

public interface VillageRepository extends JpaRepository<Village, Long> {
    List<Village> findByCellId(Long cellId);
}