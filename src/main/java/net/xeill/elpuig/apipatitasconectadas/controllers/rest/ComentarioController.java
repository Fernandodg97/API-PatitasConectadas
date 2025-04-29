package net.xeill.elpuig.apipatitasconectadas.controllers.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.xeill.elpuig.apipatitasconectadas.models.ComentarioModel;
import net.xeill.elpuig.apipatitasconectadas.models.PostModel;
import net.xeill.elpuig.apipatitasconectadas.repositories.ComentarioRepository;
import net.xeill.elpuig.apipatitasconectadas.repositories.PostRepository;

@RestController
@RequestMapping
public class ComentarioController {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private PostRepository postRepository;

    // Obtener todos los comentarios de un post
    @GetMapping("/posts/{postId}/comentarios")
    public ResponseEntity<List<ComentarioModel>> getComentariosByPostId(@PathVariable Long postId) {
        List<ComentarioModel> comentarios = comentarioRepository.findByPostId(postId);
        return ResponseEntity.ok(comentarios);
    }

    // Crear un comentario en un post
    @PostMapping("/posts/{postId}/comentarios")
    public ResponseEntity<?> crearComentario(
            @PathVariable Long postId,
            @RequestBody ComentarioModel comentario) {

        Optional<PostModel> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            return ResponseEntity.badRequest().body("El post no existe");
        }

        comentario.setPost(optionalPost.get());
        ComentarioModel guardado = comentarioRepository.save(comentario);
        return ResponseEntity.ok(guardado);
    }

    // Obtener un comentario específico
    @GetMapping("/comentarios/{id}")
    public ResponseEntity<?> getComentarioById(@PathVariable Long id) {
        return comentarioRepository.findById(id)
                .map(comentario -> ResponseEntity.ok(comentario))
                .orElse(ResponseEntity.notFound().build());
    }

    // Actualizar un comentario
    @PutMapping("/comentarios/{id}")
    public ResponseEntity<?> actualizarComentario(
            @PathVariable Long id,
            @RequestBody ComentarioModel datosActualizados) {

        return comentarioRepository.findById(id).map(comentario -> {
            comentario.setContenido(datosActualizados.getContenido());
            comentario.setFecha(datosActualizados.getFecha());
            ComentarioModel actualizado = comentarioRepository.save(comentario);
            return ResponseEntity.ok(actualizado);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Eliminar un comentario
    @DeleteMapping("/comentarios/{id}")
    public ResponseEntity<?> eliminarComentario(@PathVariable Long id) {
        return comentarioRepository.findById(id).map(comentario -> {
            comentarioRepository.deleteById(id);
            return ResponseEntity.ok("Comentario eliminado");
        }).orElse(ResponseEntity.notFound().build());
    }
}
