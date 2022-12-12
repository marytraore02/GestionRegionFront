package com.gestionregion.gestionregionfront.security.controllers;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;


import com.gestionregion.gestionregionfront.security.models.ERole;
import com.gestionregion.gestionregionfront.security.models.Role;
import com.gestionregion.gestionregionfront.security.models.User;
import com.gestionregion.gestionregionfront.security.payload.request.LoginRequest;
import com.gestionregion.gestionregionfront.security.payload.request.SignupRequest;
import com.gestionregion.gestionregionfront.security.payload.response.JwtResponse;
import com.gestionregion.gestionregionfront.security.payload.response.MessageResponse;
import com.gestionregion.gestionregionfront.security.repository.RoleRepository;
import com.gestionregion.gestionregionfront.security.repository.UserRepository;
import com.gestionregion.gestionregionfront.security.security.services.CrudService;
import com.gestionregion.gestionregionfront.security.security.services.UserDetailsImpl;
import com.gestionregion.gestionregionfront.security.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private CrudService crudService;
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;
  @Autowired
  private OAuth2AuthorizedClientService loadAuthorizedClientService;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt,
            userDetails.getId(),
            userDetails.getUsername(),
            userDetails.getEmail(),
            roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
          case "admin":
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);



            break;
          default:
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }



  @RequestMapping("/**")
  private StringBuffer getOauth2LoginInfo(Principal user){

    StringBuffer protectedInfo = new StringBuffer();
    OAuth2User principal = ((OAuth2AuthenticationToken) user).getPrincipal();
    OAuth2AuthenticationToken authToken = ((OAuth2AuthenticationToken) user);
    OAuth2AuthorizedClient authClient =
            this.loadAuthorizedClientService.loadAuthorizedClient(authToken.getAuthorizedClientRegistrationId(), authToken.getName());
    if(authToken.isAuthenticated()){

      Map<String,Object> userAttributes = ((DefaultOAuth2User) authToken.getPrincipal()).getAttributes();

      String userToken = authClient.getAccessToken().getTokenValue();
      protectedInfo.append("Bienvenue, " + userAttributes.get("name")+"<br><br>");
      protectedInfo.append("e-mail: " + userAttributes.get("email")+"<br><br>");
      protectedInfo.append("Access Token: " + userToken+"<br><br>");
      OidcIdToken idToken = getIdToken(principal);
      if(idToken != null) {

        protectedInfo.append("idToken value: " + idToken.getTokenValue()+"<br><br>");
        protectedInfo.append("Token mapped values <br><br>");

        Map<String, Object> claims = idToken.getClaims();

        for (String key : claims.keySet()) {
          protectedInfo.append("  " + key + ": " + claims.get(key)+"<br>");
        }
      }
    }
    else{
      protectedInfo.append("NA");
    }
    return protectedInfo;
  }

  @RolesAllowed({"USER","ADMIN"})
  @RequestMapping("/admin")
  public List<User> AfficherUsers()
  {
    return crudService.Afficher();
  }

  @RolesAllowed("USER")
  @RequestMapping("/*")
  public String getUser()
  {
    return "Tiec tiec User";
  }


  /*@RequestMapping("/*")
  public String getGithub(Principal user)
  {

    return "Bienvenu " ;
  }*/

  private OidcIdToken getIdToken(OAuth2User principal){
    if(principal instanceof DefaultOidcUser) {
      DefaultOidcUser oidcUser = (DefaultOidcUser)principal;
      return oidcUser.getIdToken();
    }
    return null;
  }
}
