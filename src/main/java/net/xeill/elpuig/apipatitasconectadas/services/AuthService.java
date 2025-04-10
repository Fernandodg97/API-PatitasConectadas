package net.xeill.elpuig.apipatitasconectadas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import net.xeill.elpuig.apipatitasconectadas.repos.UserRepository;
import net.xeill.elpuig.apipatitasconectadas.security.JwtUtil;
import net.xeill.elpuig.apipatitasconectadas.models.UserModel;

@Service
public class AuthService {
    @Autowired private UserRepository userRepository;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private PasswordEncoder passwordEncoder;

    public String login(String email, String password) {
        UserModel user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // Verificación de la contraseña
        // if (!passwordEncoder.matches(password, user.getPassword())) {
        //     throw new RuntimeException("Contraseña incorrecta");
        // }

        return jwtUtil.generateToken(user);
    }

    public UserModel register(String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email ya registrado");
        }
        UserModel newUser = new UserModel();
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        return userRepository.save(newUser);
    }
}

