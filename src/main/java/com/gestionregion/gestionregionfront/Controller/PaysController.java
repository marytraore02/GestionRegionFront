package com.gestionregion.gestionregionfront.Controller;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.gestionregion.gestionregionfront.Configuration.imageConfig;
import com.gestionregion.gestionregionfront.dto.Message;
import com.gestionregion.gestionregionfront.models.Pays;
import com.gestionregion.gestionregionfront.models.Region;
import com.gestionregion.gestionregionfront.repository.PaysRepository;
import com.gestionregion.gestionregionfront.services.PaysService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600, allowCredentials="true")
@Api(value = "hello", description = "CRUD PAYS")
@RestController
@RequestMapping("/pays")
@AllArgsConstructor
public class PaysController {

    @Autowired
    PaysService paysService;
    @Autowired
    PaysRepository paysRepository;


    //private regionRepository regionRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Creer pays")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestParam(value = "data") String data,
                       @RequestParam(value = "file", required = false) MultipartFile file)
            throws IOException {
        Pays pays = null;
            try{
                pays = new JsonMapper().readValue(data, Pays.class);
                if (file != null) {
                    if(paysService.existsByNomPays(pays.getNomPays()))
                        return new ResponseEntity(new Message("Ce nom existe déjà"), HttpStatus.BAD_REQUEST);
                    pays.setImagePays(imageConfig.save("pays", file, pays.getNomPays()));
                    paysService.creer(pays);
                } else {
                    return new ResponseEntity(new Message("Fichier vide"), HttpStatus.OK);
                }

            } catch (Exception e) {
                return new ResponseEntity(new Message("Erreur"), HttpStatus.OK);
            }
        return new ResponseEntity(new Message("Pays créer avec success"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Modifier pays")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestParam(value = "data") String data,
                                    @PathVariable("id") Long id,
                                    @RequestParam(value = "file", required = false) MultipartFile file)
            throws IOException {
        Pays pays = null;
        Pays p = paysService.RecupererParId(id);
        try {
            pays = new JsonMapper().readValue(data, Pays.class);
            if(!paysRepository.existsById(p.getIdPays()))
                return new ResponseEntity(new Message("Id du pays n'existe pas"), HttpStatus.NOT_FOUND);
            if (file != null) {
                try {
                    if (StringUtils.isBlank(pays.getNomPays()))
                        return new ResponseEntity(new Message("Le nom du pays est obligatoire"), HttpStatus.BAD_REQUEST);
                    pays.setImagePays(imageConfig.save("pays", file, pays.getNomPays()));
                    paysService.modifier(id, pays);
                } catch (Exception e) {
                    return new ResponseEntity(new Message("Pays introuvable ou fichier corrompu"), HttpStatus.OK);
                }
            } else {
                return new ResponseEntity(new Message("Fichier vide"), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity(new Message("Erreur"), HttpStatus.OK);
        }
        return new ResponseEntity(new Message("Mise à jour effectué"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Supprimer pays")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        if(!paysRepository.existsById(id))
            return new ResponseEntity(new Message("Id du pays n'existe pas"), HttpStatus.NOT_FOUND);
        paysService.supprimer(id);
        return new ResponseEntity(new Message("Pays supprimer avec success"), HttpStatus.OK);
    }

    @ApiOperation(value = "Liste pays")
    @GetMapping("/read")
    public ResponseEntity<List<Pays>> list(){
        List<Pays> list = paysService.lire();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @ApiOperation(value = "Voir les details d'un pays")
    @GetMapping("/detail/{id}")
    public ResponseEntity<Pays> getById(@PathVariable("id") Long id){
        if(!paysRepository.existsById(id))
            return new ResponseEntity(new Message("Id du pays n'existe pas"), HttpStatus.NOT_FOUND);
        Pays p = paysService.RecupererParId(id);
        return new ResponseEntity(p, HttpStatus.OK);
    }


}
