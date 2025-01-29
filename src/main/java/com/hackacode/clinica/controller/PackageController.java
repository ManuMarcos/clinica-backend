package com.hackacode.clinica.controller;

import com.hackacode.clinica.dto.PackageDTO;
import com.hackacode.clinica.service.IPackageService;
import com.hackacode.clinica.service.PackageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/packages")
public class PackageController {

    private final IPackageService packageService;

    @GetMapping
    public ResponseEntity<List<PackageDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(packageService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<PackageDTO> save(@RequestBody @Valid PackageDTO packageDTO) {
        return new ResponseEntity<>(packageService.save(packageDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{packageId}")
    public ResponseEntity<PackageDTO> findById(@PathVariable Long packageId) {
        return ResponseEntity.ok(packageService.findById(packageId));
    }

    @DeleteMapping("/{packageId}")
    public ResponseEntity<String> delete(@PathVariable Long packageId) {
        packageService.deleteById(packageId);
        return ResponseEntity.ok("Package deleted successfully.");
    }



}
