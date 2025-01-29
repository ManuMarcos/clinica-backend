package com.hackacode.clinica.controller;

import com.hackacode.clinica.service.IPatientService;
import com.hackacode.clinica.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {

    private final IPatientService patientService;



}
