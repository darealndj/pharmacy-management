package rw.auca.ac.pharmacy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.auca.ac.pharmacy.dto.PersonDto;
import rw.auca.ac.pharmacy.dto.PersonRequest;
import rw.auca.ac.pharmacy.model.core.Person;
import rw.auca.ac.pharmacy.model.enums.Role;
import rw.auca.ac.pharmacy.model.location.Village;
import rw.auca.ac.pharmacy.repository.PersonRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PersonService {
    private final PersonRepository repository;

    public Page<PersonDto> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::toDto);
    }

    public PersonDto create(PersonRequest request) {
        validateRequest(request, true);
        Person person = Person.builder()
                .name(request.getName().trim())
                .email(request.getEmail().toLowerCase().trim())
                .phone(request.getPhone().trim())
                .role(request.getRole())
                .village(Village.builder().id(request.getVillageId()).build())
                .build();
        Person saved = repository.save(person);
        log.info("✅ Created new person: {}", saved.getName());
        return toDto(saved);
    }

    public PersonDto get(Long id) {
        Person person = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Person not found with ID: " + id));
        return toDto(person);
    }

    public Page<PersonDto> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::toDto);
    }

    public PersonDto update(Long id, PersonRequest request) {
        validateRequest(request, false);
        Person person = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Person not found with ID: " + id));
        person.setName(request.getName().trim());
        person.setEmail(request.getEmail().toLowerCase().trim());
        person.setPhone(request.getPhone().trim());
        person.setRole(request.getRole());
        person.setVillage(Village.builder().id(request.getVillageId()).build());
        Person updated = repository.save(person);
        log.info("✅ Updated person ID {}: {}", id, updated.getName());
        return toDto(updated);
    }

    public void delete(Long id) {
        Person person = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Person not found with ID: " + id));
        person.setDeletedAt(LocalDateTime.now());
        repository.save(person);
        log.warn("🗑️ Soft-deleted person ID {}", id);
    }

    public Page<PersonDto> getByProvinceName(String name, Pageable pageable) {
        return repository.findByProvinceName(name.trim(), pageable).map(this::toDto);
    }

    public Page<PersonDto> getByProvinceCode(String code, Pageable pageable) {
        return repository.findByProvinceCode(code.trim(), pageable).map(this::toDto);
    }

    public String getPersonProvince(Long personId) {
        Person p = repository.findById(personId)
                .orElseThrow(() -> new IllegalArgumentException("Person not found with ID: " + personId));

        return Optional.ofNullable(p.getVillage())
                .map(v -> v.getCell()
                        .getSector()
                        .getDistrict()
                        .getProvince()
                        .getName())
                .orElse("Unknown province");
    }

    private PersonDto toDto(Person person) {
        Long villageId = Optional.ofNullable(person.getVillage())
                .map(Village::getId)
                .orElse(null);

        return PersonDto.builder()
                .id(person.getId())
                .name(person.getName())
                .email(person.getEmail())
                .phone(person.getPhone())
                .role(person.getRole())
                .villageId(villageId)
                .build();
    }

    private void validateRequest(PersonRequest request, boolean creating) {
        if (request == null) {
            throw new IllegalArgumentException("Person request cannot be null");
        }
        if (creating && (request.getEmail() == null || request.getEmail().isBlank())) {
            throw new IllegalArgumentException("Email is required");
        }
        if (request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        if (request.getRole() == null) {
            throw new IllegalArgumentException("Role is required");
        }
        if (request.getVillageId() == null) {
            throw new IllegalArgumentException("Village ID is required");
        }
    }
}