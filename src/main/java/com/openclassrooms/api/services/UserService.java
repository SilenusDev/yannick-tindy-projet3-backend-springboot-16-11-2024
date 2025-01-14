package com.openclassrooms.api.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.openclassrooms.api.dto.UserDTO;
import com.openclassrooms.api.models.User;
import com.openclassrooms.api.repositories.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {
    
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(AuthenticationManager authenticationManager, 
                      JWTService jwtService,
                      PasswordEncoder passwordEncoder,
                      UserRepository userRepository
                      ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public String register(String name, String email, String password) {
        // Vérifier si l'email existe déjà
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email déjà utilisé");
        }

        // Créer un nouvel utilisateur
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");
        LocalDateTime dateOnly = LocalDate.now().atStartOfDay();
        user.setCreated_at(dateOnly);
        user.setUpdated_at(dateOnly);


        // Sauvegarder l'utilisateur
        userRepository.save(user);

        // Générer le token directement pour le nouvel utilisateur
        return jwtService.generateSimpleToken(user.getEmail());

    }

    public boolean emailExists(String email) {
        // Vérifier dans la base de données si l'email existe déjà
        return userRepository.findByEmail(email).isPresent();
    }

    public String authenticate(String email, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
            );

            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(authentication);
            } else {
                throw new RuntimeException("Invalid credentials");
            }
        } catch (Exception e) {
            throw new RuntimeException("Invalid credentials");
        }
    }



    // public String authenticate(String email, String password) {
    //     try {
    //         Authentication authentication = authenticationManager.authenticate(
    //             new UsernamePasswordAuthenticationToken(email, password)
    //         );
            
    //         if (authentication.isAuthenticated()) {
    //             return jwtService.generateToken(authentication);
    //         }
    //         throw new RuntimeException("Invalid credentials");
    //     } catch (Exception e) {
    //         throw new RuntimeException("Invalid credentials");
    //     }
    // }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public UserDTO getCurrentUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return new UserDTO(user.getId(), user.getEmail(), user.getName(), user.getRole());
    }

}


// package com.openclassrooms.api.services;

// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;
// import com.openclassrooms.api.dto.UserDTO;
// import com.openclassrooms.api.models.User;
// import com.openclassrooms.api.repositories.UserRepository;
// import java.time.LocalDateTime;

// @Service
// public class UserService {
    
//     private final AuthenticationManager authenticationManager;
//     private final JWTService jwtService;

//     public UserService(AuthenticationManager authenticationManager, 
//                       JWTService jwtService,
//                       PasswordEncoder passwordEncoder,
//                       UserRepository userRepository) {
//         this.authenticationManager = authenticationManager;
//         this.jwtService = jwtService;
//         this.passwordEncoder = passwordEncoder;
//         this.userRepository = userRepository;
//     }

//     public String register(String name, String email, String password) {
//         // Vérifier si l'email existe déjà
//         if (userRepository.findByEmail(email).isPresent()) {
//             throw new RuntimeException("Email déjà utilisé");
//         }

//         // Créer un nouvel utilisateur
//         User user = new User();
//         user.setName(name);
//         user.setEmail(email);
//         user.setPassword(passwordEncoder.encode(password));
//         user.setCreatedAt(LocalDateTime.now());
//         user.setUpdatedAt(LocalDateTime.now());

//         // Sauvegarder l'utilisateur
//         userRepository.save(user);

//         // Générer le token
//         Authentication authentication = authenticationManager.authenticate(
//             new UsernamePasswordAuthenticationToken(email, password)
//         );
        
//         return jwtService.generateToken(authentication);
//     }

//     public String authenticate(String email, String password) {
//         Authentication authentication = authenticationManager.authenticate(
//             new UsernamePasswordAuthenticationToken(email, password)
//         );
        
//         return jwtService.generateToken(authentication);
//     }

//     public UserDTO getCurrentUser(String email) {
//         // On utilise le constructeur avec paramètres
//         // id = null car on ne l'a pas encore
//         // name = email temporairement (vous pourrez le changer plus tard)
//         return new UserDTO(null, email, email);
//     }
// }