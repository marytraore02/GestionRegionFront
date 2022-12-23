package com.gestionregion.gestionregionfront.Controller;


import com.gestionregion.gestionregionfront.models.Pays;
import com.gestionregion.gestionregionfront.models.Region;
import com.gestionregion.gestionregionfront.security.controllers.CrudController;
import com.gestionregion.gestionregionfront.security.models.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gestionregion.gestionregionfront.security.repository.UserRepository;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600, allowCredentials="true")
@Api(value = "user", description = "CRUD USER")
@RequestMapping("/user")
@RestController
public class userController {

    @Autowired
    CrudController crudController;

    @ApiOperation(value = "Liste des regions")
    @GetMapping("/read")
    public ResponseEntity<List<User>> list(){
        List<User> list = crudController.AfficherUsers();
        return new ResponseEntity(list, HttpStatus.OK);
    }

}
