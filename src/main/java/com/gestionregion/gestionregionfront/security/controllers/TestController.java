package com.gestionregion.gestionregionfront.security.controllers;


import com.gestionregion.gestionregionfront.security.models.ERole;
import com.gestionregion.gestionregionfront.security.repository.RoleRepositoty;
import com.gestionregion.gestionregionfront.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/test")
public class TestController {
  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepositoty roleRepositoty;
  @GetMapping("/all")
  public String allAccess(Principal all) {
    return "Public content.";

//    String user = "NOM D'UTILISATEUR: " + userRepository.findByUsername(all.getName()).get().getUsername() + "  EMAIL:  "+
//            userRepository.findByUsername(all.getName()).get().getEmail();
//    return "Bienvenue, " + user;
  }

  @GetMapping("/user")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public String userAccess(Principal user)
  {
    return "User board.";

//    return "Bienvenue, " + userRepository.findByUsername(user.getName()).get().getUsername() +
//             roleRepositoty.findByName(ERole.ROLE_USER);
  }

  @GetMapping("/mod")
  @PreAuthorize("hasRole('MODERATOR')")
  public String moderatorAccess() {
    return "Moderator Board.";
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String adminAccess(Principal admin ) {
    return "Admin board";

//    return "Bienvenue " + " "+ userRepository.findByUsername(admin.getName()).get().getUsername()  + " "+
//            roleRepositoty.findByName(ERole.ROLE_ADMIN)
//            ;
  }
}
