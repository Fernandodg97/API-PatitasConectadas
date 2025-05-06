package net.xeill.elpuig.apipatitasconectadas.controllers.rest;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.xeill.elpuig.apipatitasconectadas.controllers.dto.ComentarioModelDtoRequest;
import net.xeill.elpuig.apipatitasconectadas.controllers.dto.ComentarioModelDtoResponse;
import net.xeill.elpuig.apipatitasconectadas.models.ComentarioModel;
import net.xeill.elpuig.apipatitasconectadas.models.PostModel;
import net.xeill.elpuig.apipatitasconectadas.models.UserModel;
import net.xeill.elpuig.apipatitasconectadas.repositories.ComentarioRepository;
import net.xeill.elpuig.apipatitasconectadas.services.ComentarioService;
import net.xeill.elpuig.apipatitasconectadas.services.PostService;
import net.xeill.elpuig.apipatitasconectadas.services.UserService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private PostService postService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ComentarioRepository comentarioRepository;

    // Obtener todos los comentarios de un post
    @GetMapping("/posts/{postId}/comentarios")
    public ResponseEntity<?> getComentariosByPostId(@PathVariable Long postId) {
        try {
            List<ComentarioModel> comentarios = comentarioRepository.findByPostId(postId);
            List<ComentarioModelDtoResponse> comentariosDto = comentarios.stream()
                .map(ComentarioModelDtoResponse::new)
                .collect(Collectors.toList());
            return new ResponseEntity<>(comentariosDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // Crear un comentario en un post
    @PostMapping("/posts/{postId}/comentarios")
    public ResponseEntity<?> crearComentario(
            @PathVariable Long postId,
            @RequestBody ComentarioModelDtoRequest comentarioDto) {
        try {
            // Obtener post y usuario
            PostModel post = postService.getById(postId);
            UserModel creador = userService.getById(comentarioDto.getCreadorId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
            
            // Convertir DTO a modelo y guardar
            ComentarioModel comentario = comentarioDto.toDomain(post, creador);
            ComentarioModel guardado = comentarioService.saveComentario(comentario);
            
            return new ResponseEntity<>(new ComentarioModelDtoResponse(guardado), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // Obtener un comentario específico
    @GetMapping("/comentarios/{id}")
    public ResponseEntity<?> getComentarioById(@PathVariable Long id) {
        try {
            Optional<ComentarioModel> comentario = comentarioService.getComentarioById(id);
            if (comentario.isPresent()) {
                return new ResponseEntity<>(new ComentarioModelDtoResponse(comentario.get()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Map.of("error", "Comentario no encontrado"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // Actualizar un comentario
    @PutMapping("/comentarios/{id}")
    public ResponseEntity<?> actualizarComentario(
            @PathVariable Long id,
            @RequestBody ComentarioModelDtoRequest comentarioDto) {
        try {
            Optional<ComentarioModel> optionalComentario = comentarioService.getComentarioById(id);
            
            if (optionalComentario.isEmpty()) {
                return new ResponseEntity<>(Map.of("error", "Comentario no encontrado"), HttpStatus.NOT_FOUND);
            }
            
            ComentarioModel comentario = optionalComentario.get();
            
            // Actualizar solo los campos proporcionados
            if (comentarioDto.getContenido() != null) {
                comentario.setContenido(comentarioDto.getContenido());
            }
            if (comentarioDto.getFecha() != null) {
                comentario.setFecha(comentarioDto.getFecha());
            }
            if (comentarioDto.getImg() != null) {
                comentario.setImg(comentarioDto.getImg());
            }
            
            ComentarioModel actualizado = comentarioService.saveComentario(comentario);
            return new ResponseEntity<>(new ComentarioModelDtoResponse(actualizado), HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // Eliminar un comentario
    @DeleteMapping("/comentarios/{id}")
    public ResponseEntity<?> eliminarComentario(@PathVariable Long id) {
        try {
            Optional<ComentarioModel> comentario = comentarioService.getComentarioById(id);
            
            if (comentario.isEmpty()) {
                return new ResponseEntity<>(Map.of("error", "Comentario no encontrado"), HttpStatus.NOT_FOUND);
            }
            
            comentarioService.deleteComentario(id);
            return new ResponseEntity<>(Map.of("mensaje", "Comentario eliminado con éxito"), HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
