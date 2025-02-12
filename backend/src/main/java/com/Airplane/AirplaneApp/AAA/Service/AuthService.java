    package com.Airplane.AirplaneApp.AAA.Service;

    import com.Airplane.AirplaneApp.AAA.DTO.AuthResponse;
    import com.Airplane.AirplaneApp.AAA.DTO.UserDTO;
    import com.Airplane.AirplaneApp.AAA.Entity.User;
    import com.Airplane.AirplaneApp.AAA.Repository.UserRepository;
    import com.Airplane.AirplaneApp.AAA.Security.JwtUtil;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;

    @Service
    public class AuthService {
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtUtil jwtUtil;
        private final AuthenticationManager authenticationManager;


        public AuthService(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil,
                           AuthenticationManager authenticationManager) {
            this.userRepository = userRepository;
            this.passwordEncoder = passwordEncoder;
            this.jwtUtil = jwtUtil;
            this.authenticationManager = authenticationManager;
        }

        public AuthResponse register(UserDTO userDTO) {
            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userRepository.save(user);

            String token = jwtUtil.generateToken(user.getUsername());
            return new AuthResponse(token, user.getUsername());
        }

        public AuthResponse login(String username, String password) {
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(username, password)
                );

                String token = jwtUtil.generateToken(username);
                return new AuthResponse(token, username);
            } catch (Exception e) {
                throw new RuntimeException("Invalid username or password");
            }
        }
    }