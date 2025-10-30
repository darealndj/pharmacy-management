package rw.auca.ac.pharmacy.repository.location;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.auca.ac.pharmacy.model.location.Sector;

import java.util.List;

public interface SectorRepository extends JpaRepository<Sector, Long> {
    List<Sector> findByDistrictId(Long districtId);
}