package net.xeill.elpuig.apipatitasconectadas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.xeill.elpuig.apipatitasconectadas.models.MascotaModel;
import net.xeill.elpuig.apipatitasconectadas.repos.IMascotaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MascotaService {

    @Autowired
    IMascotaRepository mascotaRepository;

    // Obtener todas las mascotas de un usuario
    public List<MascotaModel> getMascotasByUsuario(Long usuarioId) {
        return mascotaRepository.findByUsuarioId(usuarioId);
    }

    // Guardar una nueva mascota para un usuario
    public MascotaModel saveMascota(MascotaModel mascota) {
        return mascotaRepository.save(mascota);
    }

    // Buscar una mascota por su ID y usuario
    public Optional<MascotaModel> getByIdAndUsuarioId(Long id, Long usuarioId) {
        return mascotaRepository.findByIdAndUsuarioId(id, usuarioId);
    }

    // Actualizar una mascota existente de un usuario
    public MascotaModel updateByIdAndUsuarioId(MascotaModel request, Long id, Long usuarioId) {
        Optional<MascotaModel> optionalMascota = mascotaRepository.findByIdAndUsuarioId(id, usuarioId);

        if (optionalMascota.isPresent()) {
            MascotaModel mascota = optionalMascota.get();
            mascota.setNombre(request.getNombre());
            mascota.setGenero(request.getGenero());
            mascota.setRaza(request.getRaza());
            return mascotaRepository.save(mascota);
        } else {
            throw new RuntimeException("Mascota no encontrada con ID: " + id + " para el usuario con ID: " + usuarioId);
        }
    }

    // Eliminar una mascota de un usuario
    public boolean deleteMascotaByUsuario(Long id, Long usuarioId) {
        Optional<MascotaModel> optionalMascota = mascotaRepository.findByIdAndUsuarioId(id, usuarioId);
        if (optionalMascota.isPresent()) {
            mascotaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
