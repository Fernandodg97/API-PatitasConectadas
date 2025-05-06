package net.xeill.elpuig.apipatitasconectadas.controllers.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.xeill.elpuig.apipatitasconectadas.controllers.dto.EventoModelDtoRequest;
import net.xeill.elpuig.apipatitasconectadas.controllers.dto.EventoModelDtoResponse;
import net.xeill.elpuig.apipatitasconectadas.models.EventoModel;
import net.xeill.elpuig.apipatitasconectadas.services.EventoService;

/**
 * Controlador REST para gestionar operaciones relacionadas con eventos.
 * Proporciona endpoints para crear, leer, actualizar y eliminar eventos
 * en el sistema.
 * Todas las respuestas son encapsuladas en objetos ResponseEntity para un manejo
 * consistente de la comunicación HTTP.
 */
@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    /**
     * Obtiene todos los eventos existentes en el sistema
     * @return ResponseEntity con lista de eventos en formato DTO o mensaje de error
     */
    @GetMapping
    public ResponseEntity<?> getEventos() {
        try {
            ArrayList<EventoModel> eventos = this.eventoService.getEventos();
            List<EventoModelDtoResponse> eventosDto = eventos.stream()
                .map(EventoModelDtoResponse::new)
                .collect(Collectors.toList());
            return new ResponseEntity<>(eventosDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Crea un nuevo evento en el sistema
     * @param eventoDto Datos del evento en formato DTO
     * @return ResponseEntity con el evento creado o mensaje de error
     */
    @PostMapping
    public ResponseEntity<?> saveEvento(@RequestBody EventoModelDtoRequest eventoDto) {
        try {
            // Convertir DTO a modelo
            EventoModel evento = eventoDto.toDomain();
            
            // Guardar evento
            EventoModel savedEvento = this.eventoService.saveEvento(evento);
            
            // Convertir a DTO para la respuesta
            EventoModelDtoResponse response = new EventoModelDtoResponse(savedEvento);
            
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Error al guardar el evento: " + e.getMessage()), 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene un evento específico por su ID
     * @param id ID del evento a buscar
     * @return ResponseEntity con el evento encontrado o mensaje de error
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getEventoById(@PathVariable("id") Long id) {
        try {
            Optional<EventoModel> evento = this.eventoService.getById(id);
            
            if (evento.isPresent()) {
                EventoModelDtoResponse eventoDto = new EventoModelDtoResponse(evento.get());
                return new ResponseEntity<>(eventoDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Map.of("error", "Evento no encontrado con ID: " + id), 
                    HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Actualiza un evento existente
     * @param eventoDto Datos actualizados del evento
     * @param id ID del evento a actualizar
     * @return ResponseEntity con el evento actualizado o mensaje de error
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateEventoById(@RequestBody EventoModelDtoRequest eventoDto, @PathVariable("id") Long id) {
        try {
            // Convertir DTO a modelo
            EventoModel evento = eventoDto.toDomain();
            
            // Actualizar evento
            EventoModel updatedEvento = this.eventoService.updateByID(evento, id);
            
            // Convertir a DTO para la respuesta
            EventoModelDtoResponse response = new EventoModelDtoResponse(updatedEvento);
            
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Error al actualizar el evento: " + e.getMessage()), 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Elimina un evento existente
     * @param id ID del evento a eliminar
     * @return ResponseEntity con mensaje de confirmación o error
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteEventoById(@PathVariable("id") Long id) {
        try {
            boolean deleted = this.eventoService.deleteEvento(id);
            
            if (deleted) {
                return new ResponseEntity<>(Map.of("mensaje", "Evento eliminado con ID: " + id), 
                    HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Map.of("error", "No se pudo eliminar el evento con ID: " + id), 
                    HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
