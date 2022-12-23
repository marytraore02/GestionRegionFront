package com.gestionregion.gestionregionfront.Controller;

import com.gestionregion.gestionregionfront.dto.Mensaje;
import com.gestionregion.gestionregionfront.security.models.Role;
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
    RegionService regionService;

    @Autowired
    CommentaireService commentaireService;





    @ApiOperation(value = "Pour la création d'un commentaire")
    @PostMapping(value = "/add/{idUser}/{idRegion}")
    public ResponseEntity<Object> AddCommentaire(@PathVariable(value = "idUser") Long idUser,
                                                 @PathVariable(value = "idRegion") Long idRegion, @RequestBody Commentaire commentaire) {
        // TODO: process POST request
        User user = crudService.RecupererParId(idUser);
        Region region = regionService.getOne(idRegion).get();

        if (user != null && region != null) {
            commentaire.setUser(user);
            commentaire.setRegion(region);
            return new ResponseEntity<>(commentaireService.createCommentaire(commentaire), HttpStatus.OK);
        } else {
            return new ResponseEntity(new Mensaje("Utilisateur ou solution inexistant !"), HttpStatus.NOT_FOUND);
        }

    }


    @ApiOperation(value = "Pour la modifier d'un commentaire")
    @PutMapping("/update/{idUser}/{idCommentaire}")
    public ResponseEntity<Object> update(@RequestBody Commentaire commentaire,
                                         @PathVariable(value = "idUser") Long idUser,
                                         @PathVariable(value = "idCommentaire") Long idCommentaire) {
        User user = crudService.RecupererParId(idUser);
        Commentaire commentaireRecuperer = commentaireService.retrouverParId(idCommentaire);

        if (user != null && commentaireRecuperer != null) {
            return new ResponseEntity<>(commentaireService.modificationCommentaire(commentaire, idCommentaire), HttpStatus.OK);
        } else {
            return new ResponseEntity(new Mensaje("Utilisateur ou commentaire inexistant !"), HttpStatus.NOT_FOUND);
        }

    }


    @ApiOperation(value = "Pour lister les commentaires")
    @GetMapping("/getAll")
    public List<Commentaire> read() {
        return commentaireService.getAllCommentaire();
    }



    @ApiOperation(value = "Pour lister les commentaires associer à une region")
    @GetMapping("/get/{idRegion}")
    public ResponseEntity<Object> getCommentaire(@PathVariable Long idRegion) {
        Region p = regionService.getOne(idRegion).get();
        if (p != null) {
            return null;
            //return new ResponseEntity<>(commentaireService.retrouverParCommentaire(p).getCommetaireListe(), HttpStatus.INTERNAL_SERVER_ERROR);

        } else {
            return new ResponseEntity(new Mensaje("Erreur"), HttpStatus.NOT_FOUND);
        }

    }



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
