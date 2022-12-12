package com.gestionregion.gestionregionfront.Controller;

import com.gestionregion.gestionregionfront.models.Population;
import com.gestionregion.gestionregionfront.services.PopulationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/population")
@AllArgsConstructor
@Api(value = "hello", description = "CRUD POPULATION")
public class PopulationController {

    @Autowired
    PopulationService populationService;


    //private regionRepository regionRepository;
    @ApiOperation(value = "Creer population")
    @PostMapping("/create")
    public Population create(@RequestBody Population population){
        return populationService.creer(population);
    }

    @ApiOperation(value = "Liste des populations")
    @GetMapping("/read")
    public List<Population> read(){
        return populationService.lire();
    }

    @ApiOperation(value = "Modifier une population")
    @PutMapping("/update/{id_population}")
    public Population update(@PathVariable Long id_population, @RequestBody Population population){
        return populationService.modifier(id_population, population);
    }

    @ApiOperation(value = "supprimer une population")
    @DeleteMapping("/delete/{id_population}")
    public String delete(@PathVariable Long id_population){
        return populationService.supprimer(id_population);
    }
}
