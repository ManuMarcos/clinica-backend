package com.hackacode.clinica.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("INDIVIDUAL")
public class Service extends Service {

    private String description;
    @Column(name = "service_code", unique = true)
    private String serviceCode;
    private Double price;
    private int durationInMinutes;

    @Override
    public Double calculatePrice() {
        return 0.0;
    }

    @Override
    public int getDuration() {
        return this.durationInMinutes;
    }


}
