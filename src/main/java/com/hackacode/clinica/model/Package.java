package com.hackacode.clinica.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "package_code", unique = true)
    private String packageCode;

    @ManyToMany
    @JoinTable(
            name = "package_service",
            joinColumns = @JoinColumn(name = "package_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<Service> services = new ArrayList<>();


    public Double calculatePrice() {
        return 0.0;
    }

    public void addService(Service service) {
        this.services.add(service);
    }
}
