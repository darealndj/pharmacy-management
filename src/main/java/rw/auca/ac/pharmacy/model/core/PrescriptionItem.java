package rw.auca.ac.pharmacy.model.core;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.util.Objects;

@Entity
@Table(name = "prescription_item",
      uniqueConstraints = @UniqueConstraint(columnNames = {"prescription_id", "medicine_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"prescription", "medicine"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PrescriptionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescription_id", nullable = false)
    @JsonBackReference
    private Prescription prescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;

    @Column(nullable = false)
    private int quantity;

    @Column(length = 50)
    private String dosage;

    @Column(length = 50)
    private String frequency;

    @Column(length = 50)
    private String duration;

    @Column(columnDefinition = "TEXT")
    private String instructions;

    public boolean isValidQuantity() {
        return quantity > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrescriptionItem that = (PrescriptionItem) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}