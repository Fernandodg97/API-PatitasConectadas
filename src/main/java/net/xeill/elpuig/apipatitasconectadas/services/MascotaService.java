package net.xeill.elpuig.apipatitasconectadas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.xeill.elpuig.apipatitasconectadas.models.MascotaModel;
import net.xeill.elpuig.apipatitasconectadas.repos.IMascotaRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MascotaService {

    // Inyección del repositorio de mascotas
    @Autowired
    IMascotaRepository mascotaRepository;

    // Obtener todas las mascotas
    public ArrayList<MascotaModel> getMascotas() {
        return (ArrayList<MascotaModel>) mascotaRepository.findAll();
    }

    // Guardar una nueva mascota
    public MascotaModel saveMascota(MascotaModel mascota) {
        return mascotaRepository.save(mascota);
    }

    // Buscar una mascota por su ID
    public Optional<MascotaModel> getById(Long id) {
        return mascotaRepository.findById(id);
    }

    // Actualizar una mascota existente
    public MascotaModel updateByID(MascotaModel request, Long id) {
        Optional<MascotaModel> optionalMascota = mascotaRepository.findById(id);

        if (optionalMascota.isPresent()) {
            MascotaModel mascota = optionalMascota.get();
            mascota.setUsuarioId(request.getUsuarioId());
            mascota.setNombre(request.getNombre());
            mascota.setGenero(request.getGenero());
            mascota.setRaza(request.getRaza());
            return mascotaRepository.save(mascota);
        } else {
            throw new RuntimeException("Mascota no encontrada con ID: " + id);
        }
    }

    // Eliminar una mascota por ID
    public Boolean deleteMascota(Long id) {
        try {
            mascotaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
