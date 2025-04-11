package net.xeill.elpuig.apipatitasconectadas.controllers.rest;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.xeill.elpuig.apipatitasconectadas.models.EventoModel;
import net.xeill.elpuig.apipatitasconectadas.services.EventoService;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    // Petición GET que devuelve todos los eventos existentes
    @GetMapping
    public ArrayList<EventoModel> getEventos() {
        return this.eventoService.getEventos();
    }

    // Petición POST para guardar un nuevo evento
    @PostMapping
    public ResponseEntity<?> saveEvento(@RequestBody EventoModel evento) {
        try {
            // Se intenta guardar el evento recibido en el cuerpo de la petición
            EventoModel savedEvento = this.eventoService.saveEvento(evento);
            // Se devuelve el evento guardado junto con el estado HTTP 201 (CREATED)
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEvento);
        } catch (Exception e) {
            // Si ocurre un error al guardar, se devuelve un mensaje de error con estado 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el evento: " + e.getMessage());
        }
    }

    // Petición GET para obtener un evento por su ID
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getEventoById(@PathVariable("id") Long id) {
        // Se utiliza @PathVariable para extraer el ID de la URL
        Optional<EventoModel> evento = this.eventoService.getById(id);
        return evento.isPresent()
            ? ResponseEntity.ok(evento.get())
            : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Evento no encontrado con ID: " + id);
    }

    // Petición PUT para actualizar un evento por su ID
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateEventoById(@RequestBody EventoModel request, @PathVariable("id") Long id) {
        try {
            EventoModel updatedEvento = this.eventoService.updateByID(request, id);
            return ResponseEntity.ok(updatedEvento);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al actualizar el evento: " + e.getMessage());
        }
    }

    // Petición DELETE para eliminar un evento por su ID
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteEventoById(@PathVariable("id") Long id) {
        boolean ok = this.eventoService.deleteEvento(id);
        if (ok) {
            return ResponseEntity.ok("Evento eliminado con ID: " + id);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("No se pudo eliminar el evento con ID: " + id);
        }
    }
}
