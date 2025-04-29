package net.xeill.elpuig.apipatitasconectadas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.xeill.elpuig.apipatitasconectadas.models.*;
import net.xeill.elpuig.apipatitasconectadas.repositories.ComentarioRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    // Guardar un nuevo comentario
    public ComentarioModel saveComentario(ComentarioModel comentario) {
        return comentarioRepository.save(comentario);
    }

    // Obtener un comentario por su ID
    public Optional<ComentarioModel> getComentarioById(Long id) {
        return comentarioRepository.findById(id);
    }

    // Obtener comentarios por usuario
    public List<ComentarioModel> getComentariosByCreador(UserModel usuario) {
        return comentarioRepository.findByCreador(usuario);
    }

    // Buscar comentarios por contenido
    public List<ComentarioModel> searchByContent(String searchTerm) {
        return comentarioRepository.searchByContent(searchTerm);
    }

    // Buscar comentarios por rango de fechas
    public List<ComentarioModel> getComentariosByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return comentarioRepository.findByDateRange(startDate, endDate);
    }

    // Eliminar comentario por ID
    public void deleteComentario(Long id) {
        comentarioRepository.deleteById(id);
    }
}
