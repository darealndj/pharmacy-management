package rw.auca.ac.pharmacy.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import rw.auca.ac.pharmacy.model.location.*;
import rw.auca.ac.pharmacy.repository.location.*;

import java.io.InputStream;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class LocationSeeder {

    private final ProvinceRepository provinceRepo;
    private final DistrictRepository districtRepo;
    private final SectorRepository sectorRepo;
    private final CellRepository cellRepo;
    private final VillageRepository villageRepo;

    private final ObjectMapper mapper = new ObjectMapper();

    @PostConstruct
    @Transactional
    public void seed() {
        try {
            // Skip if already seeded
            if (provinceRepo.count() > 0) {
                log.info("🌍 Rwanda locations already exist. Skipping seeding.");
                return;
            }

            log.info("🌍 Loading Rwanda location data...");
            InputStream stream = new ClassPathResource("data/location/locations.json").getInputStream();
            List<LocationRecord> records = mapper.readValue(stream, new TypeReference<>() {});

            if (records.isEmpty()) {
                log.warn("⚠️ No locations found in JSON file.");
                return;
            }

            Map<String, Province> provinceMap = new HashMap<>();
            Map<String, District> districtMap = new HashMap<>();
            Map<String, Sector> sectorMap = new HashMap<>();
            Map<String, Cell> cellMap = new HashMap<>();

            for (LocationRecord r : records) {
                Province province = provinceMap.computeIfAbsent(r.provinceCode, c ->
                        Province.builder()
                                .code(c)
                                .name(r.provinceName)
                                .build());

                District district = districtMap.computeIfAbsent(r.districtCode, c ->
                        District.builder()
                                .code(c)
                                .name(r.districtName)
                                .province(province)
                                .build());

                Sector sector = sectorMap.computeIfAbsent(r.sectorCode, c ->
                        Sector.builder()
                                .code(c)
                                .name(r.sectorName)
                                .district(district)
                                .build());

                Cell cell = cellMap.computeIfAbsent(r.cellCode, c ->
                        Cell.builder()
                                .code(c)
                                .name(r.cellName)
                                .sector(sector)
                                .build());

                villageRepo.save(Village.builder()
                        .code(r.villageCode)
                        .name(r.villageName)
                        .cell(cell)
                        .build());
            }

            provinceRepo.saveAll(provinceMap.values());
            districtRepo.saveAll(districtMap.values());
            sectorRepo.saveAll(sectorMap.values());
            cellRepo.saveAll(cellMap.values());

            log.info("✅ Rwanda location data seeded successfully.");
        } catch (Exception e) {
            log.error("❌ Failed to seed Rwanda locations", e);
        }
    }

    private record LocationRecord(
            String provinceCode,
            String provinceName,
            String districtCode,
            String districtName,
            String sectorCode,
            String sectorName,
            String cellCode,
            String cellName,
            String villageCode,
            String villageName
    ) {}
}
