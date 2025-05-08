package net.xeill.elpuig.apipatitasconectadas.services;

import net.xeill.elpuig.apipatitasconectadas.controllers.dto.UsuarioComentarioModelDtoRequest;
import net.xeill.elpuig.apipatitasconectadas.controllers.dto.UsuarioComentarioModelDtoResponse;
import net.xeill.elpuig.apipatitasconectadas.models.ComentarioModel;
import net.xeill.elpuig.apipatitasconectadas.models.UsuarioComentarioModel;
import net.xeill.elpuig.apipatitasconectadas.models.UserModel;
import net.xeill.elpuig.apipatitasconectadas.repositories.ComentarioRepository;
import net.xeill.elpuig.apipatitasconectadas.repositories.UsuarioComentarioRepository;
import net.xeill.elpuig.apipatitasconectadas.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioComentarioService {

    @Autowired
    private UsuarioComentarioRepository usuarioComentarioRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    // Obtener todas las relaciones
    public List<UsuarioComentarioModelDtoResponse> getAllUsuarioComentarios() {
        return usuarioComentarioRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Obtener una relación por ID
    public UsuarioComentarioModelDtoResponse getUsuarioComentarioById(Long id) {
        UsuarioComentarioModel usuarioComentario = usuarioComentarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relación no encontrada"));
        return convertToDto(usuarioComentario);
    }

    // Obtener relaciones por usuario
    public List<UsuarioComentarioModelDtoResponse> getUsuarioComentariosByUsuario(Long usuarioId) {
        return usuarioComentarioRepository.findByUsuarioId(usuarioId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Obtener relaciones por comentario
    public List<UsuarioComentarioModelDtoResponse> getUsuarioComentariosByComentario(Long comentarioId) {
        return usuarioComentarioRepository.findByComentarioId(comentarioId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Crear una nueva relación
    public UsuarioComentarioModelDtoResponse createUsuarioComentario(UsuarioComentarioModelDtoRequest request) {
        // Verificar si ya existe la relación
        if (usuarioComentarioRepository.existsByUsuarioIdAndComentarioId(request.getUsuarioId(), request.getComentarioId())) {
            throw new RuntimeException("Ya existe una relación entre este usuario y comentario");
        }

        // Verificar que el usuario y el comentario existen
        UserModel usuario = userRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        ComentarioModel comentario = comentarioRepository.findById(request.getComentarioId())
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));

        UsuarioComentarioModel usuarioComentario = new UsuarioComentarioModel();
        usuarioComentario.setUsuario(usuario);
        usuarioComentario.setComentario(comentario);
        usuarioComentario.setLike(request.isLike());

        return convertToDto(usuarioComentarioRepository.save(usuarioComentario));
    }

    // Eliminar una relación por ID
    public void deleteUsuarioComentario(Long id) {
        if (!usuarioComentarioRepository.existsById(id)) {
            throw new RuntimeException("Relación no encontrada");
        }
        usuarioComentarioRepository.deleteById(id);
    }

    // Eliminar todas las relaciones de un usuario
    public void deleteUsuarioComentariosByUsuario(Long usuarioId) {
        usuarioComentarioRepository.deleteByUsuarioId(usuarioId);
    }

    // Eliminar todas las relaciones de un comentario
    public void deleteUsuarioComentariosByComentario(Long comentarioId) {
        usuarioComentarioRepository.deleteByComentarioId(comentarioId);
    }

    // Método auxiliar para convertir de Model a DTO
    private UsuarioComentarioModelDtoResponse convertToDto(UsuarioComentarioModel model) {
        UsuarioComentarioModelDtoResponse dto = new UsuarioComentarioModelDtoResponse();
        dto.setId(model.getId());
        dto.setUsuarioId(model.getUsuario().getId());
        dto.setComentarioId(model.getComentario().getId());
        dto.setLike(model.isLike());
        dto.setFecha(model.getFecha());
        return dto;
    }
} 