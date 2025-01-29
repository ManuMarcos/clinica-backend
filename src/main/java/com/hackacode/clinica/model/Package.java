package com.hackacode.clinica.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class Package extends Service {

    @Column(name = "package_code", unique = true)
    private String packageCode;

    @ManyToMany
    @JoinTable(
            name = "package_service",
            joinColumns = @JoinColumn(name = "package_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<IndividualService> services = new ArrayList<>();

    @Override
    public Double calculatePrice() {
        return 0.0;
    }

    public void addIndividualService(IndividualService individualService) {
        this.services.add(individualService);
    }
}
