package com.gestionregion.gestionregionfront.Controller;

import com.gestionregion.gestionregionfront.dto.Message;
import com.gestionregion.gestionregionfront.models.Pays;
import com.gestionregion.gestionregionfront.repository.CommentaireRepository;
import com.gestionregion.gestionregionfront.repository.RegionRepository;
import com.gestionregion.gestionregionfront.security.models.User;
import com.gestionregion.gestionregionfront.models.Region;
import com.gestionregion.gestionregionfront.models.Commentaire;
import com.gestionregion.gestionregionfront.security.security.services.CrudService;
import com.gestionregion.gestionregionfront.services.CommentaireService;
import com.gestionregion.gestionregionfront.services.RegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600, allowCredentials="true")
@Api(value = "commentaire", description = "CRUD COMMENTAIRE")
@RestController
@RequestMapping("/commentaire")
public class CommentaireController {
    @Autowired
    CrudService crudService;
    @Autowired
    CommentaireRepository commentaireRepository;
    @Autowired
    RegionRepository regionRepository;

    @Autowired
    RegionService regionService;

    @Autowired
    CommentaireService commentaireService;
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @ApiOperation(value = "Pour la création d'un commentaire")
    @PostMapping(value = "/add/{idUser}/{idRegion}")
    public ResponseEntity<Object> AddCommentaire(@PathVariable(value = "idUser") Long idUser,
                                                 @PathVariable(value = "idRegion") Long idRegion, @RequestBody Commentaire commentaire) {

        User user = crudService.RecupererParId(idUser);
        Region region = regionService.getOne(idRegion).get();

        if (user != null && region != null) {
            commentaire.setUser(user);
            commentaire.setRegion(region);
            return new ResponseEntity<>(commentaireService.createCommentaire(commentaire), HttpStatus.OK);
        } else {
            return new ResponseEntity(new Message("Utilisateur ou solution inexistant !"), HttpStatus.NOT_FOUND);
        }

    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @ApiOperation(value = "Pour la modifier d'un commentaire")
    @PutMapping("/update/{idUser}/{idRegion}/{idCommentaire}")
    public ResponseEntity<Object> update(@RequestBody Commentaire commentaire,
                                         @PathVariable(value = "idUser") Long idUser,
                                         @PathVariable(value = "idRegion") Long idRegion,
                                         @PathVariable(value = "idCommentaire") Long idCommentaire) {
        User user = crudService.RecupererParId(idUser);
        Region region = regionService.getOne(idRegion).get();
        Commentaire commentaireRecuperer = commentaireService.retrouverParId(idCommentaire);

        if (user != null && commentaireRecuperer != null) {
            commentaire.setUser(user);
            commentaire.setRegion(region);
            return new ResponseEntity<>(commentaireService.modificationCommentaire(commentaire, idCommentaire), HttpStatus.OK);
        } else {
            return new ResponseEntity(new Message("Utilisateur ou commentaire inexistant !"), HttpStatus.NOT_FOUND);
        }

    }

    @ApiOperation(value = "Pour lister les commentaires")
    @GetMapping("/read")
    public ResponseEntity<List<Commentaire>> list(){
        List<Commentaire> list = commentaireService.getAllCommentaire();
        return new ResponseEntity(list, HttpStatus.OK);
    }


    @ApiOperation(value = "Pour lister les commentaires")
    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        if(!commentaireRepository.existsById(id))
            return new ResponseEntity(new Message("Id commentaire n'existe pas"), HttpStatus.NOT_FOUND);
        Commentaire c = commentaireService.retrouverParId(id);
        return new ResponseEntity(c, HttpStatus.OK);
    }

    @ApiOperation(value = "Pour lister les commentaires associer à une region")
    @GetMapping("/getByRegion/{id}")
    public ResponseEntity<List<Commentaire>> getCommentaire(@PathVariable("id") Long id) {
        Region region = regionService.getOne(id).get();
        if(!regionRepository.existsById(id))
            return new ResponseEntity(new Message("Id de la region n'existe pas"), HttpStatus.NOT_FOUND);
        List<Commentaire> c = commentaireService.retrouverParRegion(region);
        return new ResponseEntity(c, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @ApiOperation(value = "Pour supprimer les commentaires")
    @DeleteMapping("/delete/{idUser}/{idCommentaire}")
    public String delete(@PathVariable(value = "idUser") Long idUser,
                         @PathVariable(value = "idCommentaire") Long idCommentaire) {
        User user = crudService.RecupererParId(idUser);
        Commentaire commentaire = commentaireService.retrouverParId(idCommentaire);
        //Role admin = roleService.getLibelleRole("ADMIN");

        if (user != null && commentaire != null) {
            commentaireService.deleteCommentaire(commentaire);
        } else {
            return "Utilisateur ou comentaire inexistant";
        }

        return "Comentaire supprimer";
    }





}
