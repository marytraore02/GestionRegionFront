package com.gestionregion.gestionregionfront.security.security.services;

import com.gestionregion.gestionregionfront.security.models.ERole;
import com.gestionregion.gestionregionfront.security.models.Role;
import com.gestionregion.gestionregionfront.security.models.User;
import com.gestionregion.gestionregionfront.security.repository.RoleRepositoty;
import com.gestionregion.gestionregionfront.security.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
        import org.springframework.stereotype.Service;
        import java.util.List;

@Slf4j
@Service
public class CrudServiceImpl implements CrudService {


    //@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository repositoryUsers;

    @Autowired
    RoleRepositoty roleRepositoty;





            @Override
            public User creerUser(User user) {
                user.setPassword(passwordEncoder().encode(user.getPassword()));
                return repositoryUsers.save(user);
            }


            @Override
            public void addRoleToUser(String username, ERole roleName) {
                log.info("Adding role {} to user {}", username,roleName);
                User user = repositoryUsers.findByUsername(username).get();
                Role role = roleRepositoty.findByName(roleName).get();
                user.getRoles().add(role);
            }

            @Override
    public String Supprimer(Long id_users) {
        repositoryUsers.deleteById(id_users);
        return "Supprimer avec succes";
    }


    @Override
    public String Modifier(Long id, User users) {
        return repositoryUsers.findById(users.getId()).map(
                use ->{
                    use.setEmail(users.getEmail());
                    //use.setName(users.getName());
                    use.setUsername(users.getUsername());
                    use.setPassword(passwordEncoder.encode(users.getPassword()));
                    use.setRoles(users.getRoles());
                    repositoryUsers.save(use);
                    return "Modification reussie avec succès";
                }
        ).orElseThrow(() -> new RuntimeException("Cet utilisateur n'existe pas"));

    }

    @Override
    public List<User> Afficher() {
        return repositoryUsers.findAll();
    }

            @Override
            public User getEmailUser(String email) {
                return repositoryUsers.findByEmail(email);
            }

            @Override
            public User Login(String email, String password) {
                User user = repositoryUsers.findByEmail(email);
                if (user != null) {
                    if (passwordEncoder().matches(password, user.getPassword())) {
                        return user;
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            }

            @Override
            public List<User> recupererParRole(Role role) {
                try {
                    return repositoryUsers.findById(role);

                } catch (Exception e) {
                    // TODO: handle exception
                    return null;
                }
            }


            @Override
            public User RecupererParId(Long id) {
                try {
                    return repositoryUsers.findById(id).get();

                } catch (Exception e) {
                    // TODO: handle exception
                    return null;
                }
            }

        }