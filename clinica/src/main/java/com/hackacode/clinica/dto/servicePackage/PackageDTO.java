package com.hackacode.clinica.dto.servicePackage;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.hackacode.clinica.dto.DiscountDTO;
import com.hackacode.clinica.dto.service.ServiceResponseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@JsonPropertyOrder({"id", "package_code","services", "discounts", "base_price", "final_price"})
public class PackageDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty("package_code")
    @NotNull(message = "The package_code cannot be null")
    @NotBlank(message = "The package_code cannot be empty")
    private String packageCode;

    @JsonProperty(value = "base_price",access = JsonProperty.Access.READ_ONLY)
    private BigDecimal basePrice;

    @JsonProperty(value = "final_price",access = JsonProperty.Access.READ_ONLY)
    private BigDecimal finalPrice;

    @JsonProperty(value = "discounts",access = JsonProperty.Access.READ_ONLY)
    private List<DiscountDTO> discounts;

    @JsonProperty(value = "services",access = JsonProperty.Access.READ_ONLY)
    private List<ServiceResponseDTO> services;

    @NotNull(message = "The services_ids cannot be null")
    @JsonProperty(value = "services_id",access = JsonProperty.Access.WRITE_ONLY)
    private List<Integer> servicesId;

    public void addDiscount(DiscountDTO discountDTO) {
        this.discounts.add(discountDTO);
    }

}
