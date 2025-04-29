package net.xeill.elpuig.apipatitasconectadas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.xeill.elpuig.apipatitasconectadas.models.SeguidoModel;
import net.xeill.elpuig.apipatitasconectadas.repositories.SeguidoRepository;
import net.xeill.elpuig.apipatitasconectadas.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SeguidoService {

    @Autowired
    private SeguidoRepository seguidoRepository;
    @Autowired
    private UserRepository userRepository;

    // Seguir a un usuario
    public SeguidoModel seguirUsuario(Long usuarioId, Long usuarioASeguirId) {
        if (usuarioId.equals(usuarioASeguirId)) {
            throw new RuntimeException("No puedes seguirte a ti mismo.");
        }
    
        // Verificar si el usuario que quiere seguir existe
        if (!userRepository.existsById(usuarioId)) {
            throw new RuntimeException("El usuario que sigue no existe.");
        }
    
        // Verificar si el usuario a seguir existe
        if (!userRepository.existsById(usuarioASeguirId)) {
            throw new RuntimeException("El usuario que quieres seguir no existe.");
        }
    
        // Comprobar si ya existe la relación
        if (seguidoRepository.existsByUsuarioQueSigueIdAndUsuarioQueEsSeguidoId(usuarioId, usuarioASeguirId)) {
            throw new RuntimeException("Ya estás siguiendo a este usuario.");
        }
    
        SeguidoModel seguido = new SeguidoModel();
        seguido.setUsuarioQueSigueId(usuarioId);
        seguido.setUsuarioQueEsSeguidoId(usuarioASeguirId);
        return seguidoRepository.save(seguido);
    }

    // Dejar de seguir a un usuario
    public boolean dejarDeSeguir(Long quienSigueId, Long quienEsSeguidoId) {
        Optional<SeguidoModel> seguido = seguidoRepository.findByUsuarioQueSigueIdAndUsuarioQueEsSeguidoId(quienSigueId, quienEsSeguidoId);
        if (seguido.isPresent()) {
            seguidoRepository.deleteById(seguido.get().getId());
            return true;
        }
        return false;
    }

    // Obtener todos los usuarios que sigue un usuario
    public List<SeguidoModel> obtenerSeguidos(Long usuarioId) {
        return seguidoRepository.findByUsuarioQueSigueId(usuarioId);
    }

    // Verificar si ya sigue a un usuario (opcional)
    public boolean yaSigue(Long quienSigueId, Long quienEsSeguidoId) {
        return seguidoRepository.findByUsuarioQueSigueIdAndUsuarioQueEsSeguidoId(quienSigueId, quienEsSeguidoId).isPresent();
    }
}
