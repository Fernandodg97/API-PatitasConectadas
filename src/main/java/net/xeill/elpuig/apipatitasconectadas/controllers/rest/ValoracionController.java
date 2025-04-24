package net.xeill.elpuig.apipatitasconectadas.controllers.rest;

import net.xeill.elpuig.apipatitasconectadas.models.ValoracionModel;
import net.xeill.elpuig.apipatitasconectadas.models.UserModel;
import net.xeill.elpuig.apipatitasconectadas.services.UserService;
import net.xeill.elpuig.apipatitasconectadas.services.ValoracionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/valoraciones")
public class ValoracionController {

    @Autowired
    private ValoracionService valoracionService;

    @Autowired
    private UserService userService;

    // Obtener todas las valoraciones
    @GetMapping
    public List<ValoracionModel> obtenerTodas() {
        return valoracionService.obtenerTodas();
    }

    // Obtener una valoración por ID
    @GetMapping("/{id}")
    public ResponseEntity<ValoracionModel> obtenerPorId(@PathVariable Long id) {
        Optional<ValoracionModel> valoracion = valoracionService.obtenerPorId(id);
        return valoracion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una nueva valoración desde autor a receptor
    @PostMapping("/usuarios/{autorId}/receptor/{receptorId}")
    public ResponseEntity<ValoracionModel> crearValoracion(
            @PathVariable Long autorId,
            @PathVariable Long receptorId,
            @RequestBody ValoracionModel valoracion) {

        Optional<UserModel> autor = userService.getById(autorId);
        Optional<UserModel> receptor = userService.getById(receptorId);

        if (autor.isEmpty() || receptor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        valoracion.setAutor(autor.get());
        valoracion.setReceptor(receptor.get());

        ValoracionModel guardada = valoracionService.guardar(valoracion);
        return ResponseEntity.ok(guardada);
    }

    // Actualizar una valoración
    @PutMapping("/{id}")
    public ResponseEntity<ValoracionModel> actualizar(
            @PathVariable Long id,
            @RequestBody ValoracionModel nuevaValoracion) {

        Optional<ValoracionModel> existente = valoracionService.obtenerPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ValoracionModel actual = existente.get();
        actual.setContenido(nuevaValoracion.getContenido());
        actual.setPuntuacion(nuevaValoracion.getPuntuacion());
        actual.setFecha(nuevaValoracion.getFecha());

        return ResponseEntity.ok(valoracionService.guardar(actual));
    }

    // Eliminar una valoración
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (valoracionService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        valoracionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // Obtener valoraciones recibidas por un usuario
    @GetMapping("/usuarios/{receptorId}/recibidas")
    public ResponseEntity<List<ValoracionModel>> valoracionesRecibidas(@PathVariable Long receptorId) {
        List<ValoracionModel> valoraciones = valoracionService.obtenerPorReceptorId(receptorId);
        return ResponseEntity.ok(valoraciones);
    }

    // Obtener valoraciones hechas por un usuario (opcional)
    @GetMapping("/usuarios/{autorId}/enviadas")
    public ResponseEntity<List<ValoracionModel>> valoracionesEnviadas(@PathVariable Long autorId) {
        List<ValoracionModel> valoraciones = valoracionService.obtenerPorAutorId(autorId);
        return ResponseEntity.ok(valoraciones);
    }
}