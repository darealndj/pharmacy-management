package rw.auca.ac.pharmacy.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionCreateRequest {
    @NotNull
    private LocalDate dateIssued;

    private String notes;

    private Long patientId;

    private Long pharmacistId;

    private List<ItemRequest> items;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ItemRequest {
        @NotNull
        private Long medicineId;

        @NotNull
        @Min(1)
        private Integer quantity;
    }
}