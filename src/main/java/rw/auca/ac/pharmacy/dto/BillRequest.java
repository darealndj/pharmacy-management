package rw.auca.ac.pharmacy.dto;

import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillRequest {
    private String status;
    private String paymentMethod;
    private String paymentRef;

    @DecimalMin("0.0")
    @Builder.Default
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @DecimalMin("0.0")
    @Builder.Default
    private BigDecimal taxAmount = BigDecimal.ZERO;

    private LocalDate paymentDate;
}