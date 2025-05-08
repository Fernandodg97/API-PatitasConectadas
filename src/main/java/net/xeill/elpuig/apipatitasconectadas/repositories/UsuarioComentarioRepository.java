package net.xeill.elpuig.apipatitasconectadas.repositories;

import net.xeill.elpuig.apipatitasconectadas.models.UsuarioComentarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioComentarioRepository extends JpaRepository<UsuarioComentarioModel, Long> {
    
    // Encontrar todas las relaciones de un usuario específico
    List<UsuarioComentarioModel> findByUsuarioId(Long usuarioId);
    
    // Encontrar todas las relaciones de un comentario específico
    List<UsuarioComentarioModel> findByComentarioId(Long comentarioId);
    
    // Encontrar una relación específica por usuario y comentario
    UsuarioComentarioModel findByUsuarioIdAndComentarioId(Long usuarioId, Long comentarioId);
    
    // Verificar si existe una relación entre un usuario y un comentario
    boolean existsByUsuarioIdAndComentarioId(Long usuarioId, Long comentarioId);
    
    // Eliminar todas las relaciones de un usuario específico
    void deleteByUsuarioId(Long usuarioId);
    
    // Eliminar todas las relaciones de un comentario específico
    void deleteByComentarioId(Long comentarioId);
    
    // Eliminar una relación específica por usuario y comentario
    void deleteByUsuarioIdAndComentarioId(Long usuarioId, Long comentarioId);
} 