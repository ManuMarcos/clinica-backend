package com.hackacode.clinica.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IndividualService extends Service {

    private String description;
    @Column(name = "service_code", unique = true)
    private String serviceCode;
    private Double price;


    @Override
    public Double calculatePrice() {
        return 0.0;
    }


}
