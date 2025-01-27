package com.hackacode.clinica.model;

import jakarta.persistence.*;

@Entity
public class IndividualService extends Service {

    private String description;
    @Column(name = "service_code")
    private String serviceCode;
    private Double price;

    @Override
    public Double calculatePrice() {
        return 0.0;
    }


}
