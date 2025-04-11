package net.xeill.elpuig.apipatitasconectadas.controllers.rest;
// package net.xeill.elpuig.apipatitasconectadas.controllers;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
// import net.xeill.elpuig.apipatitasconectadas.models.*;
// import net.xeill.elpuig.apipatitasconectadas.services.*;

// @RestController
// @RequestMapping("/usuario-grupo")
// public class UsuarioGrupoController {

//     @Autowired
//     private UsuarioGrupoService usuarioGrupoService;

//     // Peticion GET para obtener todas las relaciones usuario-grupo
//     @GetMapping
//     public ArrayList<UsuarioGrupoModel> getUsuarioGrupos() {
//         return this.usuarioGrupoService.getUsuarioGrupos();
//     }

//     // Peticion POST para guardar una relación usuario-grupo
//     @PostMapping
//     public ResponseEntity<?> saveUsuarioGrupo(@RequestBody UsuarioGrupoModel usuarioGrupo) {
//          try {
//             // Intentamos guardar la relación usuario-grupo
//             UsuarioGrupoModel savedUsuarioGrupo = this.usuarioGrupoService.saveUsuarioGrupo(usuarioGrupo);
//             return ResponseEntity.status(HttpStatus.CREATED).body(savedUsuarioGrupo);  // Devuelve la relación guardada con estado 201 (CREATED)
//         } catch (Exception e) {
//             // Si ocurre un error, devolvemos un mensaje con código de error 500 (Internal Server Error)
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la relación usuario-grupo: " + e.getMessage());
//         }
//     }

//     @GetMapping(path = "/{id}")
//     public Optional<UsuarioGrupoModel> getUsuarioGrupoById(@PathVariable("id") Long id) {
//         return this.usuarioGrupoService.getById(id);
//     }

//     @PostMapping(path = "/{id}")
//     public UsuarioGrupoModel updateUsuarioGrupoById(@RequestBody UsuarioGrupoModel request, @PathVariable("id") Long id) {
//         return this.usuarioGrupoService.updateByID(request, id);
//     }

//     @DeleteMapping(path = "/{id}")
//     public String deleteById(@PathVariable("id") Long id) {
//         boolean ok = this.usuarioGrupoService.deleteUsuarioGrupo(id);

//         if(ok) {
//             return "Se ha eliminado la relación usuario-grupo con id: " + id;
//         } else {
//             return "No se ha podido eliminar la relación usuario-grupo con id: " + id;
//         }
//     }
    
//     // Peticion GET para obtener todos los grupos a los que pertenece un usuario
//     @GetMapping(path = "/usuario/{usuario_id}")
//     public List<UsuarioGrupoModel> getGruposByUsuarioId(@PathVariable("usuario_id") Long usuario_id) {
//         return this.usuarioGrupoService.getGruposByUsuarioId(usuario_id);
//     }
    
//     // Peticion GET para obtener todos los usuarios que pertenecen a un grupo
//     @GetMapping(path = "/grupo/{grupo_id}")
//     public List<UsuarioGrupoModel> getUsuariosByGrupoId(@PathVariable("grupo_id") Long grupo_id) {
//         return this.usuarioGrupoService.getUsuariosByGrupoId(grupo_id);
//     }
    
//     // Peticion GET para obtener la relación específica entre un usuario y un grupo
//     @GetMapping(path = "/usuario-grupo")
//     public UsuarioGrupoModel getUsuarioGrupoByUsuarioIdAndGrupoId(
//             @RequestParam("usuario_id") Long usuario_id,
//             @RequestParam("grupo_id") Long grupo_id) {
//         return this.usuarioGrupoService.getUsuarioGrupoByUsuarioIdAndGrupoId(usuario_id, grupo_id);
//     }
// } 