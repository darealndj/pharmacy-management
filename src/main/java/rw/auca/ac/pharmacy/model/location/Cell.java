package rw.auca.ac.pharmacy.model.location;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cells")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String code;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sector_id", nullable = false)
    @JsonIgnore
    private Sector sector;

    @OneToMany(mappedBy = "cell", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Village> villages = new ArrayList<>();
}
