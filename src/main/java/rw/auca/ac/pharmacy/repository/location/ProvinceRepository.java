package rw.auca.ac.pharmacy.repository.location;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.auca.ac.pharmacy.model.location.Province;

import java.util.Optional;

public interface ProvinceRepository extends JpaRepository<Province, Long> {
    Optional<Province> findByCode(String code);
}
