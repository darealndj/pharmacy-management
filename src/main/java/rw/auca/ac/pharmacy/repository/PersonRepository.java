package rw.auca.ac.pharmacy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rw.auca.ac.pharmacy.model.core.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

    boolean existsByEmail(String email);

    Person findByEmail(String email);

    @Query("""
        SELECT p FROM Person p
        JOIN p.village v
        JOIN v.cell c
        JOIN c.sector s
        JOIN s.district d
        JOIN d.province pr
        WHERE pr.name = :provinceName
    """)
    Page<Person> findByProvinceName(@Param("provinceName") String provinceName, Pageable pageable);

    @Query("""
        SELECT p FROM Person p
        JOIN p.village v
        JOIN v.cell c
        JOIN c.sector s
        JOIN s.district d
        JOIN d.province pr
        WHERE pr.code = :provinceCode
    """)
    Page<Person> findByProvinceCode(@Param("provinceCode") String provinceCode, Pageable pageable);
}
