package rw.auca.ac.pharmacy.model.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "medicine")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"createdAt", "updatedAt", "deletedAt"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sku", unique = true, length = 50)
    private String sku;

    @Column(nullable = false, unique = true, length = 120)
    private String name;

    @Column(length = 50)
    private String type;

    @Column(length = 20)
    private String unit;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "reorder_level", nullable = false)
    @Builder.Default
    private int reorderLevel = 10;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(length = 120)
    private String manufacturer;

    @Column(name = "is_controlled", nullable = false)
    @Builder.Default
    private boolean controlled = false;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public boolean isLowStock() {
        return quantity <= reorderLevel;
    }

    public boolean isExpired() {
        return expiryDate != null && expiryDate.isBefore(LocalDate.now());
    }

    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }

    public boolean isActive() {
        return deletedAt == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medicine medicine = (Medicine) o;
        return Objects.equals(id, medicine.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}