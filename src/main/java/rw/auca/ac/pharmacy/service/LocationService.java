package rw.auca.ac.pharmacy.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

import rw.auca.ac.pharmacy.model.location.*;
import rw.auca.ac.pharmacy.repository.location.*;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final ProvinceRepository provinceRepo;
    private final DistrictRepository districtRepo;
    private final SectorRepository sectorRepo;
    private final CellRepository cellRepo;
    private final VillageRepository villageRepo;

    public List<Province> getAllProvinces() { return provinceRepo.findAll(); }
    public List<District> getDistrictsByProvince(Long provinceId) { return districtRepo.findByProvinceId(provinceId); }
    public List<Sector> getSectorsByDistrict(Long districtId) { return sectorRepo.findByDistrictId(districtId); }
    public List<Cell> getCellsBySector(Long sectorId) { return cellRepo.findBySectorId(sectorId); }
    public List<Village> getVillagesByCell(Long cellId) { return villageRepo.findByCellId(cellId); }
}
