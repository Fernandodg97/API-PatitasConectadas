package net.xeill.elpuig.apipatitasconectadas.services;

import net.xeill.elpuig.apipatitasconectadas.models.ValoracionModel;
import net.xeill.elpuig.apipatitasconectadas.repositories.ValoracionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Marca esta clase como un servicio de Spring para que pueda ser inyectado donde se necesite
public class ValoracionService {

    @Autowired // Inyección automática del repositorio de valoraciones
    private ValoracionRepository valoracionRepository;

    // Método para obtener todas las valoraciones de la base de datos
    public List<ValoracionModel> obtenerTodas() {
        return valoracionRepository.findAll();
    }

    // Método para obtener una única valoración por su ID
    public Optional<ValoracionModel> obtenerPorId(Long id) {
        return valoracionRepository.findById(id);
    }

    // Método para guardar una nueva valoración o actualizar una existente
    public ValoracionModel guardar(ValoracionModel valoracion) {
        return valoracionRepository.save(valoracion);
    }

    // Método para eliminar una valoración por su ID
    public void eliminar(Long id) {
        valoracionRepository.deleteById(id);
    }

    // Método para obtener todas las valoraciones donde el receptor sea un usuario específico
    public List<ValoracionModel> obtenerPorReceptorId(Long receptorId) {
        return valoracionRepository.findByReceptorId(receptorId);
    }
    
    // Método para obtener todas las valoraciones donde el autor sea un usuario específico
    public List<ValoracionModel> obtenerPorAutorId(Long autorId) {
        return valoracionRepository.findByAutorId(autorId);
    }
}
