package rw.auca.ac.pharmacy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rw.auca.ac.pharmacy.dto.MedicineDto;
import rw.auca.ac.pharmacy.dto.MedicineRequest;
import rw.auca.ac.pharmacy.model.core.Medicine;
import rw.auca.ac.pharmacy.repository.MedicineRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicineService {
    private final MedicineRepository repository;

    public Page<MedicineDto> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::toDto);
    }

    public MedicineDto create(MedicineRequest request) {
        Medicine medicine = Medicine.builder()
                .sku(request.getSku())
                .name(request.getName())
                .type(request.getType())
                .unit(request.getUnit())
                .quantity(request.getQuantity())
                .reorderLevel(request.getReorderLevel())
                .price(request.getPrice())
                .manufacturer(request.getManufacturer())
                .controlled(request.getIsControlled())
                .expiryDate(request.getExpiryDate())
                .build();
        Medicine saved = repository.save(medicine);
        return toDto(saved);
    }

    public MedicineDto get(Long id) {
        Medicine medicine = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Medicine not found"));
        return toDto(medicine);
    }

    public MedicineDto update(Long id, MedicineRequest request) {
        Medicine medicine = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Medicine not found"));
        medicine.setSku(request.getSku());
        medicine.setName(request.getName());
        medicine.setType(request.getType());
        medicine.setUnit(request.getUnit());
        medicine.setQuantity(request.getQuantity());
        medicine.setReorderLevel(request.getReorderLevel());
        medicine.setPrice(request.getPrice());
        medicine.setManufacturer(request.getManufacturer());
        medicine.setControlled(request.getIsControlled());
        medicine.setExpiryDate(request.getExpiryDate());
        return toDto(repository.save(medicine));
    }

    public void delete(Long id) {
        Medicine medicine = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Medicine not found"));
        medicine.setDeletedAt(LocalDateTime.now());
        repository.save(medicine);
    }

    public List<MedicineDto> findByNameContaining(String name) {
        return repository.findByNameContainingIgnoreCase(name).stream().map(this::toDto).collect(Collectors.toList());
    }

    private MedicineDto toDto(Medicine medicine) {
        return MedicineDto.builder()
                .id(medicine.getId())
                .sku(medicine.getSku())
                .name(medicine.getName())
                .type(medicine.getType())
                .unit(medicine.getUnit())
                .quantity(medicine.getQuantity())
                .reorderLevel(medicine.getReorderLevel())
                .price(medicine.getPrice())
                .manufacturer(medicine.getManufacturer())
                .isControlled(medicine.isControlled())
                .expiryDate(medicine.getExpiryDate())
                .build();
    }
}