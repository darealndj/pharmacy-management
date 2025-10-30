package rw.auca.ac.pharmacy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rw.auca.ac.pharmacy.model.location.*;
import rw.auca.ac.pharmacy.repository.location.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/locations")
@RequiredArgsConstructor
public class LocationController {

    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;
    private final SectorRepository sectorRepository;
    private final CellRepository cellRepository;
    private final VillageRepository villageRepository;

    @GetMapping("/provinces")
    public List<Province> getProvinces() {
        return provinceRepository.findAll();
    }

    @GetMapping("/provinces/{provinceId}/districts")
    public List<District> getDistricts(@PathVariable Long provinceId) {
        return districtRepository.findByProvinceId(provinceId);
    }

    @GetMapping("/districts/{districtId}/sectors")
    public List<Sector> getSectors(@PathVariable Long districtId) {
        return sectorRepository.findByDistrictId(districtId);
    }

    @GetMapping("/sectors/{sectorId}/cells")
    public List<Cell> getCells(@PathVariable Long sectorId) {
        return cellRepository.findBySectorId(sectorId);
    }

    @GetMapping("/cells/{cellId}/villages")
    public List<Village> getVillages(@PathVariable Long cellId) {
        return villageRepository.findByCellId(cellId);
    }
}
