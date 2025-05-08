package net.xeill.elpuig.apipatitasconectadas.controllers.rest;

import java.util.List;
import java.util.stream.Collectors;
import net.xeill.elpuig.apipatitasconectadas.controllers.dto.EventoModelDtoRequest;
import net.xeill.elpuig.apipatitasconectadas.controllers.dto.EventoModelDtoResponse;
import net.xeill.elpuig.apipatitasconectadas.models.EventoModel;
import net.xeill.elpuig.apipatitasconectadas.models.UserModel;
import net.xeill.elpuig.apipatitasconectadas.services.EventoService;
import net.xeill.elpuig.apipatitasconectadas.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private UserService userService;

    /**
     * Obtiene todos los eventos existentes en el sistema
     * @return ResponseEntity con lista de eventos en formato DTO
     */
    @GetMapping
    public ResponseEntity<List<EventoModelDtoResponse>> getAllEventos() {
        List<EventoModel> eventos = eventoService.getAllEventos();
        List<EventoModelDtoResponse> eventosDto = eventos.stream()
                .map(EventoModelDtoResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(eventosDto);
    }

    /**
     * Crea un nuevo evento en el sistema y asigna al usuario como Creador
     * @param eventoDto Datos del evento en formato DTO
     * @return ResponseEntity con el evento creado
     */
    @PostMapping
    public ResponseEntity<EventoModelDtoResponse> saveEvento(@RequestBody EventoModelDtoRequest eventoDto) {
        if (eventoDto.getNombre() == null || eventoDto.getNombre().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        UserModel creador = userService.getUserById(eventoDto.getCreadorId());
        if (creador == null) {
            return ResponseEntity.badRequest().build();
        }

        EventoModel evento = eventoDto.toDomain(creador);
        EventoModel savedEvento = eventoService.saveEvento(evento);
        return ResponseEntity.status(HttpStatus.CREATED).body(new EventoModelDtoResponse(savedEvento));
    }

    /**
     * Obtiene un evento específico por su ID
     * @param id ID del evento a buscar
     * @return ResponseEntity con el evento encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<EventoModelDtoResponse> getEventoById(@PathVariable Long id) {
        EventoModel evento = eventoService.getEventoById(id);
        if (evento != null) {
            return ResponseEntity.ok(new EventoModelDtoResponse(evento));
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Actualiza un evento existente
     * @param id ID del evento a actualizar
     * @param eventoDto Datos actualizados del evento
     * @return ResponseEntity con el evento actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<EventoModelDtoResponse> updateEvento(@PathVariable Long id, @RequestBody EventoModelDtoRequest eventoDto) {
        EventoModel existingEvento = eventoService.getEventoById(id);
        if (existingEvento == null) {
            return ResponseEntity.notFound().build();
        }

        if (eventoDto.getNombre() == null || eventoDto.getNombre().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        UserModel creador = userService.getUserById(eventoDto.getCreadorId());
        if (creador == null) {
            return ResponseEntity.badRequest().build();
        }

        EventoModel evento = eventoDto.toDomain(creador);
        evento.setId(id);
        EventoModel updatedEvento = eventoService.saveEvento(evento);
        return ResponseEntity.ok(new EventoModelDtoResponse(updatedEvento));
    }

    /**
     * Elimina un evento existente
     * @param id ID del evento a eliminar
     * @return ResponseEntity con mensaje de confirmación
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvento(@PathVariable Long id) {
        EventoModel evento = eventoService.getEventoById(id);
        if (evento != null) {
            eventoService.deleteEvento(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
