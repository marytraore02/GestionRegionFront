package com.gestionregion.gestionregionfront.security.controllers;

import com.gestionregion.gestionregionfront.security.models.User;
import com.gestionregion.gestionregionfront.security.security.services.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class CrudController {

    @Autowired
    CrudService crudService;


    // µµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµ

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/afficher")
    public  List<User> AfficherUsers(){
        return crudService.Afficher();
    }

    // µµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµ   MODIFIER
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping({"/modifier/{id}"})
    public String ModierUser(@PathVariable("id") Long id, @RequestBody User users){

        crudService.Modifier(id, users);
        return "Modification reussie avec succès";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/supprimer/{id_users}")
    public String Supprimer(@PathVariable("id_users") Long id_users){
        crudService.Supprimer(id_users);
        return "Suppression reussie";
    }




}