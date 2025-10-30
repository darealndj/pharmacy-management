package rw.auca.ac.pharmacy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rw.auca.ac.pharmacy.dto.BillDto;
import rw.auca.ac.pharmacy.dto.BillRequest;
import rw.auca.ac.pharmacy.model.core.Bill;
import rw.auca.ac.pharmacy.model.core.Prescription;
import rw.auca.ac.pharmacy.model.enums.BillStatus;
import rw.auca.ac.pharmacy.model.enums.PaymentMethod;
import rw.auca.ac.pharmacy.repository.BillRepository;
import rw.auca.ac.pharmacy.repository.PrescriptionRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BillService {
    private final BillRepository repository;
    private final PrescriptionRepository prescriptionRepo;

    public BillDto createForPrescription(Long prescriptionId, BillRequest request) {
        Prescription p = prescriptionRepo.findById(prescriptionId).orElseThrow(() -> new IllegalArgumentException("Prescription not found"));
        BigDecimal subtotal = BigDecimal.ZERO;  // Calc from items (expand)
        Bill bill = Bill.builder()
                .prescription(p)
                .status(request.getStatus() != null ? BillStatus.valueOf(request.getStatus()) : BillStatus.PENDING)
                .paymentMethod(request.getPaymentMethod() != null ? PaymentMethod.valueOf(request.getPaymentMethod()) : null)
                .paymentRef(request.getPaymentRef())
                .subtotal(subtotal)
                .discountAmount(request.getDiscountAmount())
                .taxAmount(request.getTaxAmount())
                .currency("RWF")  // Fixed Rwanda twist
                .paymentDate(request.getPaymentDate())
                .build();
        Bill saved = repository.save(bill);
        return toDto(saved);
    }

    public BillDto get(Long id) {
        Bill bill = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Bill not found"));
        return toDto(bill);
    }

    private BillDto toDto(Bill bill) {
        return BillDto.builder()
                .id(bill.getId())
                .prescriptionId(bill.getPrescription().getId())
                .status(bill.getStatus().name())
                .paymentMethod(bill.getPaymentMethod() != null ? bill.getPaymentMethod().name() : null)
                .paymentRef(bill.getPaymentRef())
                .subtotal(bill.getSubtotal())
                .discountAmount(bill.getDiscountAmount())
                .taxAmount(bill.getTaxAmount())
                .netTotal(bill.getNetTotal())
                .currency(bill.getCurrency())
                .paymentDate(bill.getPaymentDate())
                .build();
    }
}