package com.gestionregion.gestionregionfront.Controller;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.gestionregion.gestionregionfront.Configuration.imageConfig;
import com.gestionregion.gestionregionfront.dto.Mensaje;
import com.gestionregion.gestionregionfront.dto.RegionDto;
import com.gestionregion.gestionregionfront.models.Pays;
import com.gestionregion.gestionregionfront.models.Region;
import com.gestionregion.gestionregionfront.repository.RegionRepository;
import com.gestionregion.gestionregionfront.security.models.User;
import com.gestionregion.gestionregionfront.security.security.services.CrudService;
import com.gestionregion.gestionregionfront.services.PaysService;
import com.gestionregion.gestionregionfront.services.RegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600, allowCredentials="true")
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

    @Autowired
    CrudService crudService;


    //@PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Creation de region")
    @PostMapping("/create/new/{nomPays}")
    public ResponseEntity<?> create(@RequestParam(value = "data") String acti,
                                    @PathVariable("nomPays") String nomPays,
                                    @RequestParam(value = "file", required = false) MultipartFile file)
            throws IOException {
        Region region = null;

        //System.out.println(idutilisateur);
        Pays pays = paysServices.getNomPays(nomPays);

        try {
            region = new JsonMapper().readValue(acti, Region.class);
            System.out.println("==========Nom region==========="+region);

            if (file != null && pays != null) {
                try {
//                    User user = crudService.RecupererParId(idutilisateur);
//                    region.setCreateur(user);

                    //System.out.println(user);

                    region.setImageRegion(imageConfig.save("region", file, region.getNomRegion()));


                    regionService.creer(region);
                    return new ResponseEntity(new Mensaje("Region créé avec success"), HttpStatus.OK);


                } catch (Exception e) {
                    // TODO: handle exception
                    return new ResponseEntity(new Mensaje("Pays introuvable ou fichier corrompu"), HttpStatus.OK);

                }
            } else {
                return new ResponseEntity(new Mensaje("Fichier vide"), HttpStatus.OK);


                //return ResponseMessage.generateResponse("error", HttpStatus.OK, "Fichier vide");
            }
        } catch (Exception e) {

            System.out.println(region);

            return new ResponseEntity(new Mensaje("Erreur"), HttpStatus.OK);


            //return ResponseMessage.generateResponse("error", HttpStatus.OK, e.getMessage());
        }








//
//        if(StringUtils.isBlank(regionDto.getCodeRegion()))
//            return new ResponseEntity(new Mensaje("Le code de la region est obligatoire"), HttpStatus.BAD_REQUEST);
//
//        Region regionVerif = regionService.getByNameRegion(regionDto.getNomRegion());
//            if(regionVerif != null){
//                return new ResponseEntity(new Mensaje("Ce nom existe déjà"), HttpStatus.BAD_REQUEST);
//            }
//            /*if(regionRepository.existsByName(regionDto.getNomRegion()))
//            return new ResponseEntity(new Message("Ce nom existe déjà"), HttpStatus.BAD_REQUEST);*/
//        Region region = new Region(regionDto.getImageRegion(), regionDto.getCodeRegion(), regionDto.getNomRegion(),regionDto.getDescriptionRegion(), regionDto.getLangueMajoritaire(),
//                regionDto.getDomaineActiviteRegion(), regionDto.getSuperficie(), regionDto.getPays());
//
//        Pays pays = paysServices.getNomPays(regionDto.getPays().getNomPays());
//        if (pays == null) {
//            paysServices.creer(regionDto.getPays());
//        }
//        regionService.creer(region);
//        return new ResponseEntity(new Mensaje("Region créé avec success"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Modifier une region")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id")Long id, @RequestBody RegionDto regionDto){
        if(!regionRepository.existsById(id))
            return new ResponseEntity(new Mensaje("Id de la region n'existe pas"), HttpStatus.NOT_FOUND);
        /*if(regionRepository.existsByName(regionDto.getNomRegion()) && regionService.getByNameRegion(regionDto.getNomRegion()).get().getIdRegion() != id)
            return new ResponseEntity(new Message("La region existe déjà"), HttpStatus.BAD_REQUEST);*/
        if(StringUtils.isBlank(regionDto.getNomRegion()))
            return new ResponseEntity(new Mensaje("le nom de la region est obligatoire pour effectuer la mise à jour"), HttpStatus.BAD_REQUEST);

        regionService.modifier(id, regionDto);
        return new ResponseEntity(new Mensaje("Mise à jour effectué"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Supprimer une region")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if(!regionRepository.existsById(id))
            return new ResponseEntity(new Mensaje("Id de la region n'existe pas"), HttpStatus.NOT_FOUND);
        regionService.supprimer(id);
        return new ResponseEntity(new Mensaje("Region supprimer avec success"), HttpStatus.OK);
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
    public ResponseEntity<Region> getById(@PathVariable("id") Long id){
        if(!regionRepository.existsById(id))
            return new ResponseEntity(new Mensaje("La region n'existe pas"), HttpStatus.NOT_FOUND);
        Region r = regionService.getOne(id).get();
        return new ResponseEntity(r, HttpStatus.OK);
    }

   /* @GetMapping("/detailname/{nomRegion}")
    public ResponseEntity<Region> getByNombre(@PathVariable("nomRegion") String nomRegion, @RequestBody RegionDto regionDto){
        Region Verifname = regionService.getByNameRegion(nomRegion);
        if(Verifname == null){
            return new ResponseEntity(new Message("Le nom de la region n'existe pas"), HttpStatus.NOT_FOUND);
        }
        Region r = regionService.getByNameRegion(nomRegion);
        return new ResponseEntity(r, HttpStatus.OK);
    }*/




}
