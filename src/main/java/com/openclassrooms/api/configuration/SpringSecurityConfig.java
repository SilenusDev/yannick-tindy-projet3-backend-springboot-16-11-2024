package com.openclassrooms.api.configuration;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.openclassrooms.api.repositories.UserRepository;

@Configuration
public class SpringSecurityConfig {
	
	private String jwtKey = "maSuperCleSecretePourMonJWTQuiDoitEtreTresLonguePourLaSecurite123456789";
	private final UserRepository userRepository;

    // Constructor injection pour UserRepository
    public SpringSecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    // Routes accessibles sans token
                    auth.requestMatchers(
                            "/auth/register",
                            "/auth/login",
							"/rentals",
                            "/rentals/**",
                            "/user/**"
                    ).permitAll();

                    // Routes nécessitant un token
                    auth.anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults()) // Permet d'utiliser le Basic Auth pour le debug si besoin
                .build();
    }

    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //     return http
    //             .csrf(csrf -> csrf.disable())
    //             .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    //             .authorizeHttpRequests(auth -> {
    //                 // Routes accessibles sans token
    //                 auth.requestMatchers(
    //                         "/auth/register",
    //                         "/auth/login",
    //                         "/rentals",
    //                         "/rentals/",
    //                         "/user/"
    //                 ).permitAll();

    //                 // Routes nécessitant un token
    //                 auth.requestMatchers(
    //                         "/auth/me",
    //                         "/messages",
    //                         "/rentals",
    //                         "/rentals/**"
    //                 ).authenticated();

    //                 // Toute autre route est refusée par défaut
    //                 auth.anyRequest().denyAll();
    //             })
    //             .httpBasic(Customizer.withDefaults()) // Permet d'utiliser le Basic Auth pour le debug si besoin
    //             .build();
    // }	

    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {       
    //     return http
    //             .csrf(csrf -> csrf.disable())
    //             .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    //             .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // Autorise toutes les requêtes sans authentification
	// 			// .authorizeHttpRequests(auth -> {
    //             //     auth.requestMatchers("/login").permitAll();  // Permet l'accès à /login sans authentification
	// 			// 	auth.requestMatchers("/error").permitAll(); // Permet l'accès aux pages d'erreur
    //             //     auth.anyRequest().authenticated();           // Requiert une authentification pour tout le reste
    //             // })
    //             .httpBasic(Customizer.withDefaults())
    //             .build();       
    // }

	@Bean
	public JwtEncoder jwtEncoder() {
		return new NimbusJwtEncoder(new ImmutableSecret<>(this.jwtKey.getBytes()));
	}

	@Bean
	public JwtDecoder jwtDecoder() {
		SecretKeySpec secretKey = new SecretKeySpec(this.jwtKey.getBytes(), "HmacSHA256"); // Correct algorithme pour HS256
		return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return email -> userRepository.findByEmail(email)
			.map(user -> User.builder()
				.username(user.getEmail())
				.password(user.getPassword()) // Le mot de passe est déjà crypté en base
				.roles("USER")
				.build())
			.orElseThrow(() -> new RuntimeException("User not found"));
	}

	
	// @Bean
	// public UserDetailsService users() {
	// 	UserDetails user = User.builder().username("user").password(passwordEncoder().encode("password")).roles("USER")
	// 			.build();		
	// 	return new InMemoryUserDetailsManager(user);
	// }




	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.Customizer;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
// import org.springframework.security.oauth2.jwt.JwtDecoder;
// import org.springframework.security.oauth2.jwt.JwtEncoder;
// import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
// import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
// import org.springframework.security.web.SecurityFilterChain;

// import com.nimbusds.jose.jwk.source.ImmutableSecret;

// import javax.crypto.spec.SecretKeySpec;
// import java.security.SecureRandom;
// import java.util.Base64;

// @Configuration
// @EnableWebSecurity
// public class SpringSecurityConfig {
    
//     @Value("${jwt.secret}")
//     private String jwtKey;
    
//     // Méthode pour générer une clé JWT sécurisée
//     private String generateSecureJwtKey() {
//         SecureRandom secureRandom = new SecureRandom();
//         byte[] key = new byte[32]; // 256 bits
//         secureRandom.nextBytes(key);
//         return Base64.getEncoder().encodeToString(key);
//     }
    
//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//         return http
//                 .csrf(csrf -> csrf.disable())
//                 .sessionManagement(session -> 
//                     session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                 )
//                 .authorizeHttpRequests(auth -> {
//                     auth.requestMatchers("/api/public/**").permitAll()  // URLs publiques
//                         .requestMatchers("/api/auth/**").permitAll()    // URLs d'authentification
//                         .requestMatchers("/api/admin/**").hasRole("ADMIN")  // URLs admin
//                         .anyRequest().authenticated();
//                 })
//                 .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
//                 .httpBasic(Customizer.withDefaults())
//                 .build();
//     }

//     @Bean
//     public JwtEncoder jwtEncoder() {
//         byte[] jwtKeyBytes = jwtKey.getBytes();
//         ImmutableSecret<SecretKeySpec> secretKey = new ImmutableSecret<>(jwtKeyBytes);
//         return new NimbusJwtEncoder(secretKey);
//     }

//     @Bean
//     public JwtDecoder jwtDecoder() {
//         byte[] jwtKeyBytes = jwtKey.getBytes();
//         SecretKeySpec secretKey = new SecretKeySpec(jwtKeyBytes, 0, jwtKeyBytes.length, "HmacSHA256");
//         return NimbusJwtEncoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
//     }

//     @Bean
//     public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
//         UserDetails user = User.builder()
//                 .username("user")
//                 .password(passwordEncoder.encode("password"))
//                 .roles("USER")
//                 .build();
                
//         UserDetails admin = User.builder()
//                 .username("admin")
//                 .password(passwordEncoder.encode("admin"))
//                 .roles("USER", "ADMIN")
//                 .build();
                
//         return new InMemoryUserDetailsManager(user, admin);
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }
// }

