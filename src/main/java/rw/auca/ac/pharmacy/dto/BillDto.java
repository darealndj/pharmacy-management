package rw.auca.ac.pharmacy.dto;

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
public class BillDto {
    private Long id;
    private Long prescriptionId;
    private String status;
    private String paymentMethod;
    private String paymentRef;
    private BigDecimal subtotal;
    private BigDecimal discountAmount;
    private BigDecimal taxAmount;
    private BigDecimal netTotal;
    private String currency;
    private LocalDate paymentDate;
}