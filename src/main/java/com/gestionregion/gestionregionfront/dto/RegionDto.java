package com.gestionregion.gestionregionfront.dto;

import com.gestionregion.gestionregionfront.models.Pays;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegionDto {

    @NotBlank
    private String codeRegion;
    @NotBlank
    private String nomRegion;
    @NotBlank
    private String domaineActiviteRegion;
    @NotBlank
    private String superficie;
    @NotBlank
    private String langueMajoritaire;

    private Pays pays;

}
