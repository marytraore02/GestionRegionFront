package com.gestionregion.gestionregionfront.Controller;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.gestionregion.gestionregionfront.Configuration.imageConfig;
import com.gestionregion.gestionregionfront.dto.Message;
import com.gestionregion.gestionregionfront.models.Pays;
import com.gestionregion.gestionregionfront.models.Population;
import com.gestionregion.gestionregionfront.models.Region;
import com.gestionregion.gestionregionfront.repository.PopulationRepository;
import com.gestionregion.gestionregionfront.repository.RegionRepository;
import com.gestionregion.gestionregionfront.services.PopulationService;
import com.gestionregion.gestionregionfront.services.RegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/population")
@AllArgsConstructor
@Api(value = "hello", description = "CRUD POPULATION")
public class PopulationController {

    @Autowired
    PopulationService populationService;
    @Autowired
    PopulationRepository populationRepository;
    @Autowired
    RegionRepository regionRepository;
    @Autowired
    RegionService regionService;

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Creer population")
    @PostMapping("/create/{idRegion}")
    public ResponseEntity<?> create(@RequestParam(value = "data") String data,
                             @PathVariable("idRegion") Long idRegion)
            throws IOException {
        Population population = null;
        Region r = regionService.RecupererParId(idRegion);
            population = new JsonMapper().readValue(data, Population.class);
            if(!regionRepository.existsById(idRegion))
                return new ResponseEntity(new Message("Id de la region n'existe pas"), HttpStatus.NOT_FOUND);
            try {
                population.setRegion(r);
                populationService.creer(population);
                return new ResponseEntity(new Message("Population créé avec success"), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity(new Message("Erreur de création"), HttpStatus.OK);
            }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Modifier une population")
    @PutMapping("/update/{id_population}")
    public ResponseEntity<?> update(@RequestParam(value = "data") String data,
                                    @PathVariable("id_population") Long id_population)
            throws IOException  {
        Population population = null;
        try {
            population = new JsonMapper().readValue(data, Population.class);
            if(!populationRepository.existsById(id_population))
                return new ResponseEntity(new Message("Id de la population n'existe pas"), HttpStatus.NOT_FOUND);
            try {
                populationService.modifier(id_population, population);
            } catch (Exception e) {
                return new ResponseEntity(new Message("Erreur de création"), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity(new Message("Erreur"), HttpStatus.OK);
        }
        return new ResponseEntity(new Message("Mise à jour effectué"), HttpStatus.OK);
    }

    @ApiOperation(value = "Liste des populations")
    @GetMapping("/read")
    public ResponseEntity<List<Population>> read(){
        List<Population> list = populationService.lire();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @ApiOperation(value = "Voir les details d'une population")
    @GetMapping("/detail/{id}")
    public ResponseEntity<Population> getById(@PathVariable("id") Long id){
        if(!populationRepository.existsById(id))
            return new ResponseEntity(new Message("Id population n'existe pas"), HttpStatus.NOT_FOUND);
        Population p = populationService.GetOne(id);
        return new ResponseEntity(p, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "supprimer une population")
    @DeleteMapping("/delete/{id_population}")
    public String delete(@PathVariable Long id_population){
        return populationService.supprimer(id_population);
    }
}
