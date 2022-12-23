package com.gestionregion.gestionregionfront;

import com.gestionregion.gestionregionfront.security.models.ERole;
import com.gestionregion.gestionregionfront.security.models.Role;
import com.gestionregion.gestionregionfront.security.models.User;
import com.gestionregion.gestionregionfront.security.repository.RoleRepositoty;
import com.gestionregion.gestionregionfront.security.repository.UserRepository;
import com.gestionregion.gestionregionfront.security.security.services.CrudService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@AllArgsConstructor
public class GestionRegionFrontApplication implements CommandLineRunner{


    @Autowired
    PasswordEncoder encoder;
    final private UserRepository userRepository;
    final private RoleRepositoty roleRepository;


    public static void main(String[] args) {
            SpringApplication.run(GestionRegionFrontApplication.class, args);

//        ApplicationContext ctx = SpringApplication.run(GestionRegionFrontApplication.class, args);
//
//        RoleRepositoty roleRepository = ctx.getBean(RoleRepositoty.class);
//        UserRepository userRepository = ctx.getBean(UserRepository.class);
//
//        if(roleRepository.findAll().size() == 0){
//            // creation des roles
//            Role user = new Role();
//            user.setId(1L);
//            user.setName("ROLE_USER");
//
//            Role admin = new Role();
//            admin.setId(2L);
//            admin.setName("ROLE_ADMIN");
//
//            Role moderateur = new Role();
//            user.setId(3L);
//            user.setName("ROLE_MODERATEUR");
//
//            roleRepository.save(user);
//            roleRepository.save(admin);
//            roleRepository.save(moderateur);
//
//        }
//
//
//        if (userRepository.findAll().size() == 0) {
//            Set<Role> roles = new HashSet<>();
//            Role role = roleRepository.findByName("ROLE_ADMIN");
//            roles.add(role);
//            // creation des super administrateurs
//            User adminInfo = new User("admin", "admin@gmail.com", passwordEncoder().encode("admin123"));
//            adminInfo.setRoles(roles);
//            userRepository.save(adminInfo);
//        }




    }


    @Override
    public void run(String... args) throws Exception {
        //VERIFICATION DE L'EXISTANCE DU ROLE ADMIN AVANT SA CREATION
        if (roleRepository.findAll().size() == 0){
            roleRepository.save(new Role(ERole.ROLE_ADMIN));
            roleRepository.save(new Role(ERole.ROLE_USER));
        }
        if (userRepository.findAll().size() == 0){
            Set<Role> roles = new HashSet<>();
            Role role = roleRepository.findByName(ERole.ROLE_ADMIN).get();
            roles.add(role);
            User collaborateur = new User("admin","admin@gmail.com",encoder.encode( "admin123"));
            collaborateur.setRoles(roles);
            userRepository.save(collaborateur);

        }
    }

}

