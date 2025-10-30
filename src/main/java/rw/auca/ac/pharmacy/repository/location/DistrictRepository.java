package rw.auca.ac.pharmacy.repository.location;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.auca.ac.pharmacy.model.location.District;

import java.util.List;

public interface DistrictRepository extends JpaRepository<District, Long> {
    List<District> findByProvinceId(Long provinceId);
}