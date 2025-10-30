package rw.auca.ac.pharmacy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionDto {
    private Long id;
    private LocalDate dateIssued;
    private String status;
    private Long patientId;
    private Long pharmacistId;
    private String notes;
}