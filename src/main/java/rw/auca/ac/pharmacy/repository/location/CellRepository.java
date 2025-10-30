package rw.auca.ac.pharmacy.repository.location;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.auca.ac.pharmacy.model.location.Cell;

import java.util.List;

public interface CellRepository extends JpaRepository<Cell, Long> {
    List<Cell> findBySectorId(Long sectorId);
}