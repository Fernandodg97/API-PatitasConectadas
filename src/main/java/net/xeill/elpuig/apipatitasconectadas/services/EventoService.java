package net.xeill.elpuig.apipatitasconectadas.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.xeill.elpuig.apipatitasconectadas.models.*;
import net.xeill.elpuig.apipatitasconectadas.repos.*;

@Service
public class EventoService {

    @Autowired
    EventoRepository eventoRepository;

    // Método que me permite obtener todos los eventos
    public ArrayList<EventoModel> getEventos() {
        // findAll() es un método que me permite obtener todos los registros de la tabla
        return (ArrayList<EventoModel>) eventoRepository.findAll();
    }

    // Método que me permite guardar un evento
    public EventoModel saveEvento(EventoModel evento) {
        // save() es un método que me permite guardar un registro en la tabla
        return eventoRepository.save(evento);
    }

    // Método que me permite obtener un evento por su ID
    public Optional<EventoModel> getById(Long id) {
        // findById() es un método que me permite obtener un registro por su id
        return eventoRepository.findById(id);
    }

    // Método que me permite actualizar un evento por su ID
    public EventoModel updateByID(EventoModel request, Long id) {
        Optional<EventoModel> optionalEvento = eventoRepository.findById(id);

        if (optionalEvento.isPresent()) {
            EventoModel evento = optionalEvento.get();
            evento.setNombre(request.getNombre());
            evento.setDescripcion(request.getDescripcion());
            evento.setUbicacion(request.getUbicacion());
            evento.setFecha(request.getFecha());
            return eventoRepository.save(evento);
        } else {
            throw new RuntimeException("Evento no encontrado con ID: " + id);
        }
    }

    // Método que me permite eliminar un evento por su ID
    public Boolean deleteEvento(Long id) {
        try {
            eventoRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
