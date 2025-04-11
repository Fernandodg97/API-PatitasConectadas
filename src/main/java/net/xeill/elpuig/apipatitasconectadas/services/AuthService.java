package net.xeill.elpuig.apipatitasconectadas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import net.xeill.elpuig.apipatitasconectadas.repos.UserRepository;
import net.xeill.elpuig.apipatitasconectadas.security.JwtUtil;
import net.xeill.elpuig.apipatitasconectadas.models.UserModel;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    @Autowired private UserRepository userRepository;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private PasswordEncoder passwordEncoder;

    public String login(String email, String password) {
        UserModel user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        //Verificación de la contraseña
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return jwtUtil.generateToken(user);
    }

    public Map<String, Object> register(String email, String password, String nombre, String apellido) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email ya registrado");
        }
        
        // Crear y guardar el nuevo usuario
        UserModel newUser = new UserModel();
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setNombre(nombre);
        newUser.setApellido(apellido);
        UserModel savedUser = userRepository.save(newUser);
        
        // Generar el token
        String token = jwtUtil.generateToken(savedUser);
        
        // Crear respuesta con usuario y token
        Map<String, Object> response = new HashMap<>();
        response.put("user", savedUser);
        response.put("token", token);
        
        return response;
    }
}

