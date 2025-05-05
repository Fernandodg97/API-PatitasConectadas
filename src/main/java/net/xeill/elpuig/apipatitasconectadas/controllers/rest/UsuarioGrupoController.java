package net.xeill.elpuig.apipatitasconectadas.controllers.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.xeill.elpuig.apipatitasconectadas.models.*;
import net.xeill.elpuig.apipatitasconectadas.services.*;

@RestController
@RequestMapping("/usuario-grupo")
public class UsuarioGrupoController {

    @Autowired
    private UsuarioGrupoService usuarioGrupoService;

    @Autowired
    private UserService userService;

    @Autowired
    private GrupoService grupoService;

    // GET todas las relaciones
    @GetMapping
    public List<UsuarioGrupoModel> getUsuarioGrupos() {
        return usuarioGrupoService.getUsuarioGrupos();
    }

    // POST para crear una relación usuario-grupo
    @PostMapping
    public ResponseEntity<?> saveUsuarioGrupo(@RequestBody UsuarioGrupoModel usuarioGrupo) {
        try {
            Long usuarioId = usuarioGrupo.getUsuario().getId();
            Long grupoId = usuarioGrupo.getGrupo().getId();

            Optional<UserModel> usuario = userService.getById(usuarioId);
            Optional<GrupoModel> grupo = grupoService.getById(grupoId);

            if (usuario.isEmpty() || grupo.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario o Grupo no encontrado");
            }

            usuarioGrupo.setUsuario(usuario.get());
            usuarioGrupo.setGrupo(grupo.get());

            UsuarioGrupoModel saved = usuarioGrupoService.saveUsuarioGrupo(usuarioGrupo);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar: " + e.getMessage());
        }
    }

    // GET por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuarioGrupoById(@PathVariable Long id) {
        return usuarioGrupoService.getById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontró la relación usuario-grupo"));
    }

    // POST para actualizar relación
    @PostMapping("/{id}")
    public ResponseEntity<?> updateUsuarioGrupoById(@PathVariable Long id,
            @RequestBody UsuarioGrupoModel usuarioGrupo) {
        Long usuarioId = usuarioGrupo.getUsuario().getId();
        Long grupoId = usuarioGrupo.getGrupo().getId();

        Optional<UserModel> usuario = userService.getById(usuarioId);
        Optional<GrupoModel> grupo = grupoService.getById(grupoId);

        if (usuario.isEmpty() || grupo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario o Grupo no encontrado");
        }

        usuarioGrupo.setUsuario(usuario.get());
        usuarioGrupo.setGrupo(grupo.get());

        UsuarioGrupoModel updated = usuarioGrupoService.updateByID(usuarioGrupo, id);
        return ResponseEntity.ok(updated);
    }

    // DELETE por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        boolean ok = usuarioGrupoService.deleteUsuarioGrupo(id);
        if (ok) {
            return ResponseEntity.ok("Eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado para eliminar");
        }
    }

    // GET grupos por usuario
    @GetMapping("/usuario/{usuario_id}")
    public List<UsuarioGrupoModel> getGruposByUsuarioId(@PathVariable("usuario_id") Long usuarioId) {
        return usuarioGrupoService.getGruposByUsuarioId(usuarioId);
    }

    // GET usuarios por grupo
    @GetMapping("/grupo/{grupo_id}")
    public List<UsuarioGrupoModel> getUsuariosByGrupoId(@PathVariable("grupo_id") Long grupoId) {
        return usuarioGrupoService.getUsuariosByGrupoId(grupoId);
    }

    // GET relación específica
    @GetMapping("/usuario-grupo")
    public ResponseEntity<?> getUsuarioGrupoByUsuarioIdAndGrupoId(
            @RequestParam("usuario_id") Long usuarioId,
            @RequestParam("grupo_id") Long grupoId) {
        UsuarioGrupoModel relation = usuarioGrupoService.getUsuarioGrupoByUsuarioIdAndGrupoId(usuarioId, grupoId);
        if (relation != null) {
            return ResponseEntity.ok(relation);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Relación no encontrada");
        }
    }
}
