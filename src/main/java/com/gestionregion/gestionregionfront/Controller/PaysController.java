package com.gestionregion.gestionregionfront.Controller;

import com.gestionregion.gestionregionfront.models.Pays;
import com.gestionregion.gestionregionfront.services.PaysService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600, allowCredentials="true")
@Api(value = "hello", description = "CRUD PAYS")
@RestController
@RequestMapping("/pays")
@AllArgsConstructor
public class PaysController {

    @Autowired
    PaysService paysService;


    //private regionRepository regionRepository;

    @ApiOperation(value = "Creer pays")
    @PostMapping("/create")
    public Pays create(@RequestBody Pays pays){
        return paysService.creer(pays);
    }

    @ApiOperation(value = "Liste pays")
    @GetMapping("/read")
    public List<Pays> read(){
        return paysService.lire();
    }

    @ApiOperation(value = "Modifier pays")
    @PutMapping("/update/{id_pays}")
    public Pays update(@PathVariable Long id_pays, @RequestBody Pays pays){
        return paysService.modifier(id_pays, pays);
    }

    @ApiOperation(value = "Supprimer pays")
    @DeleteMapping("/delete/{id_pays}")
    public String delete(@PathVariable Long id_pays){
        return paysService.supprimer(id_pays);
    }
}
