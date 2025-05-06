package net.xeill.elpuig.apipatitasconectadas.controllers.rest;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.xeill.elpuig.apipatitasconectadas.models.UserModel;
import net.xeill.elpuig.apipatitasconectadas.models.PerfilModel;
import net.xeill.elpuig.apipatitasconectadas.models.MascotaModel;
import net.xeill.elpuig.apipatitasconectadas.repositories.*;
import net.xeill.elpuig.apipatitasconectadas.security.JwtUtil;
import net.xeill.elpuig.apipatitasconectadas.services.AuthService;

/**
 * Controlador REST para gestionar operaciones de autenticación y registro.
 * Proporciona endpoints para iniciar sesión, registrar nuevos usuarios
 * y obtener información del usuario autenticado.
 * Todas las respuestas son encapsuladas en objetos ResponseEntity para un manejo
 * consistente de la comunicación HTTP.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired private AuthService authService;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private UserRepository userRepository;
    @Autowired private PerfilRepository perfilRepository;
    @Autowired private MascotaRepository mascotaRepository;

    /**
     * Autentica a un usuario mediante email y contraseña
     * @param body Mapa con las credenciales del usuario (email y password)
     * @return ResponseEntity con token JWT o mensaje de error
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String token = authService.login(body.get("email"), body.get("password"));
        return ResponseEntity.ok(Map.of("token", token));
    }

    /**
     * Registra un nuevo usuario en el sistema
     * @param body Mapa con los datos del usuario (email, password, nombre, apellido)
     * @return ResponseEntity con datos del usuario creado o mensaje de error
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        try {
            // Verificar que todos los campos requeridos estén presentes
            String email = body.get("email");
            String password = body.get("password");
            String nombre = body.get("nombre");
            String apellido = body.get("apellido");
            
            if (email == null || password == null || nombre == null || apellido == null) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Todos los campos son obligatorios: email, password, nombre, apellido"));
            }
            
            Map<String, Object> result = authService.register(email, password, nombre, apellido);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Obtiene información del usuario autenticado
     * @param authHeader Cabecera de autorización con el token JWT
     * @return ResponseEntity con datos del usuario, su perfil y mascotas o mensaje de error
     */
    @GetMapping("/me")
    public ResponseEntity<?> getMe(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String email = jwtUtil.extractUsername(token);
            
            // Obtener el usuario
            UserModel user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            // Crear un objeto con la información que queremos devolver
            Map<String, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("nombre", user.getNombre());
            response.put("apellido", user.getApellido());
            response.put("email", user.getEmail());
            
            // Buscar el perfil del usuario si existe
            Optional<PerfilModel> perfil = perfilRepository.findById(user.getId());
            if (perfil.isPresent()) {
                response.put("perfil", perfil.get());
            }
            
            // Buscar las mascotas del usuario
            List<MascotaModel> mascotas = mascotaRepository.findByUsuarioId(user.getId());
            if (!mascotas.isEmpty()) {
                response.put("mascotas", mascotas);
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", e.getMessage()));
        }
    }
}