package net.xeill.elpuig.apipatitasconectadas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.xeill.elpuig.apipatitasconectadas.models.MascotaModel;
import net.xeill.elpuig.apipatitasconectadas.repositories.*;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona las operaciones relacionadas con las mascotas de los usuarios.
 * Proporciona métodos para crear, leer, actualizar y eliminar mascotas,
 * siempre asociadas a un usuario específico.
 */
@Service
public class MascotaService {

    @Autowired
    MascotaRepository mascotaRepository;

    /**
     * Obtiene todas las mascotas asociadas a un usuario específico.
     * @param usuarioId ID del usuario propietario de las mascotas
     * @return Lista de mascotas pertenecientes al usuario
     */
    public List<MascotaModel> getMascotasByUsuario(Long usuarioId) {
        return mascotaRepository.findByUsuarioId(usuarioId);
    }

    /**
     * Guarda una nueva mascota o actualiza una existente.
     * @param mascota Objeto MascotaModel con los datos de la mascota a guardar
     * @return La mascota guardada con su ID asignado
     */
    public MascotaModel saveMascota(MascotaModel mascota) {
        return mascotaRepository.save(mascota);
    }

    /**
     * Busca una mascota específica por su ID y el ID del usuario propietario.
     * @param id ID de la mascota a buscar
     * @param usuarioId ID del usuario propietario
     * @return Optional con la mascota si existe, o vacío si no se encuentra
     */
    public Optional<MascotaModel> getByIdAndUsuarioId(Long id, Long usuarioId) {
        return mascotaRepository.findByIdAndUsuarioId(id, usuarioId);
    }

    /**
     * Actualiza los datos de una mascota existente de un usuario específico.
     * @param request Objeto MascotaModel con los nuevos datos
     * @param id ID de la mascota a actualizar
     * @param usuarioId ID del usuario propietario
     * @return La mascota actualizada
     * @throws RuntimeException si la mascota no existe para el usuario especificado
     */
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

    /**
     * Elimina una mascota de un usuario específico.
     * @param id ID de la mascota a eliminar
     * @param usuarioId ID del usuario propietario
     * @return true si la mascota fue eliminada con éxito, false si no se encontró
     */
    public boolean deleteMascotaByUsuario(Long id, Long usuarioId) {
        Optional<MascotaModel> optionalMascota = mascotaRepository.findByIdAndUsuarioId(id, usuarioId);
        if (optionalMascota.isPresent()) {
            mascotaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
