package net.xeill.elpuig.apipatitasconectadas.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.xeill.elpuig.apipatitasconectadas.models.*;
import net.xeill.elpuig.apipatitasconectadas.repositories.*;

@Service
public class UsuarioGrupoService {

    @Autowired
    private UsuarioGrupoRepository usuarioGrupoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    // Obtener todas las relaciones usuario-grupo
    public List<UsuarioGrupoModel> getUsuarioGrupos() {
        return usuarioGrupoRepository.findAll();
    }

    // Guardar una relación usuario-grupo
    public UsuarioGrupoModel saveUsuarioGrupo(UsuarioGrupoModel usuarioGrupo) {
        return usuarioGrupoRepository.save(usuarioGrupo);
    }

    // Obtener una relación por ID
    public Optional<UsuarioGrupoModel> getById(Long id) {
        return usuarioGrupoRepository.findById(id);
    }

    // Actualizar una relación por ID
    public UsuarioGrupoModel updateByID(UsuarioGrupoModel request, Long id) {
        UsuarioGrupoModel usuarioGrupo = usuarioGrupoRepository.findById(id).orElseThrow();

        usuarioGrupo.setUsuario(request.getUsuario());
        usuarioGrupo.setGrupo(request.getGrupo());
        usuarioGrupo.setRol(request.getRol());

        return usuarioGrupoRepository.save(usuarioGrupo);
    }

    // Eliminar una relación por ID
    public boolean deleteUsuarioGrupo(Long id) {
        if (usuarioGrupoRepository.existsById(id)) {
            usuarioGrupoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Obtener relaciones por usuario
    public List<UsuarioGrupoModel> getGruposByUsuarioId(Long usuarioId) {
        UserModel usuario = userRepository.findById(usuarioId).orElse(null);
        if (usuario == null) return new ArrayList<>();
        return usuarioGrupoRepository.findByUsuario(usuario);
    }

    // Obtener relaciones por grupo
    public List<UsuarioGrupoModel> getUsuariosByGrupoId(Long grupoId) {
        GrupoModel grupo = grupoRepository.findById(grupoId).orElse(null);
        if (grupo == null) return new ArrayList<>();
        return usuarioGrupoRepository.findByGrupo(grupo);
    }

    // Obtener relación específica
    public UsuarioGrupoModel getUsuarioGrupoByUsuarioIdAndGrupoId(Long usuarioId, Long grupoId) {
        UserModel usuario = userRepository.findById(usuarioId).orElse(null);
        GrupoModel grupo = grupoRepository.findById(grupoId).orElse(null);
        if (usuario == null || grupo == null) return null;
        return usuarioGrupoRepository.findByUsuarioAndGrupo(usuario, grupo);
    }
}
