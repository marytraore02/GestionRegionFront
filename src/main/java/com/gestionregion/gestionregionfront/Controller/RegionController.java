package com.gestionregion.gestionregionfront.Controller;

import com.gestionregion.gestionregionfront.dto.Message;
import com.gestionregion.gestionregionfront.dto.RegionDto;
import com.gestionregion.gestionregionfront.models.Pays;
import com.gestionregion.gestionregionfront.models.Region;
import com.gestionregion.gestionregionfront.repository.RegionRepository;
import com.gestionregion.gestionregionfront.services.PaysService;
import com.gestionregion.gestionregionfront.services.RegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/region")
@AllArgsConstructor
@Api(value = "hello", description = "CRUD REGION")
public class RegionController {

    @Autowired
    RegionService regionService;

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    PaysService paysServices;


    @ApiOperation(value = "Creation de region")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody RegionDto regionDto){
        if(StringUtils.isBlank(regionDto.getCodeRegion()))
            return new ResponseEntity(new Message("Le code de la region est obligatoire"), HttpStatus.BAD_REQUEST);

        Region regionVerif = regionService.getByNameRegion(regionDto.getNomRegion()).get();

            if(regionVerif != null){
                return new ResponseEntity(new Message("Ce nom existe déjà"), HttpStatus.BAD_REQUEST);
            }
            /*if(regionRepository.existsByName(regionDto.getNomRegion()))
            return new ResponseEntity(new Message("Ce nom existe déjà"), HttpStatus.BAD_REQUEST);*/
        Region region = new Region(regionDto.getCodeRegion(), regionDto.getNomRegion(), regionDto.getLangueMajoritaire(),
                regionDto.getDomaineActiviteRegion(), regionDto.getSuperficie(), regionDto.getPays());

        Pays pays = paysServices.getNomPays(regionDto.getPays().getNomPays());
        if (pays == null) {
            paysServices.creer(regionDto.getPays());
        }
        regionService.creer(region);
        return new ResponseEntity(new Message("Region créé avec success"), HttpStatus.OK);
    }

    @ApiOperation(value = "Modifier une region")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id")Long id, @RequestBody RegionDto regionDto){
        if(!regionRepository.existsById(id))
            return new ResponseEntity(new Message("Id n'existe pas"), HttpStatus.NOT_FOUND);
        /*if(regionRepository.existsByName(regionDto.getNomRegion()) && regionService.getByNameRegion(regionDto.getNomRegion()).get().getIdRegion() != id)
            return new ResponseEntity(new Message("La region existe déjà"), HttpStatus.BAD_REQUEST);*/
        if(StringUtils.isBlank(regionDto.getNomRegion()))
            return new ResponseEntity(new Message("le nom de la region est obligatoire"), HttpStatus.BAD_REQUEST);

        regionService.modifier(id, regionDto);
        return new ResponseEntity(new Message("Mise à jour effectué"), HttpStatus.OK);
    }

    @ApiOperation(value = "Supprimer une region")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if(!regionRepository.existsById(id))
            return new ResponseEntity(new Message("la n'existe pas"), HttpStatus.NOT_FOUND);
        regionService.supprimer(id);
        return new ResponseEntity(new Message("Region supprimer avec success"), HttpStatus.OK);
    }

    @ApiOperation(value = "Liste des regions")
    @GetMapping("/read")
    public ResponseEntity<List<Region>> list(){
        List<Region> list = regionService.lire();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @ApiOperation(value = "Liste des regions sans pays")
    @GetMapping("/lireSP")
    public Iterable<Object[]> getRegionsSP(){
        return regionService.getRegionsSP();
    }


    @ApiOperation(value = "Voir les details d'une region")
    @GetMapping("/detail/{id}")
    public ResponseEntity<Region> getById(@PathVariable("id") Long id, @RequestBody Region region){
        if(!regionRepository.existsById(id))
            return new ResponseEntity(new Message("La region n'existe pas"), HttpStatus.NOT_FOUND);
        Region r = regionService.getOne(id).get();
        return new ResponseEntity(r, HttpStatus.OK);
    }

   /* @ApiOperation(value = "Chercher une region")
    @GetMapping("/detailname/{nombre}")
    public ResponseEntity<Region> getByNombre(@PathVariable("nombre") String nombre){
        if(!regionRepository.existsByName(nombre))
            return new ResponseEntity(new Message("La region {} n'existe pas"+regionRepository.existsByName(nombre)), HttpStatus.NOT_FOUND);
        Region regio = regionService.getByNameRegion(nombre).get();
        return new ResponseEntity(regio, HttpStatus.OK);
    }*/


}
