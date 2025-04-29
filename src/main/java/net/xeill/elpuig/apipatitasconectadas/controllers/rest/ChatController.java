package net.xeill.elpuig.apipatitasconectadas.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.xeill.elpuig.apipatitasconectadas.models.ChatModel;
import net.xeill.elpuig.apipatitasconectadas.services.ChatService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    // Enviar un nuevo mensaje
    @PostMapping("/enviar")
    public ResponseEntity<?> enviarMensaje(@RequestBody Map<String, Object> body) {
        try {
            Long emisorId = Long.valueOf(body.get("emisorId").toString());
            Long receptorId = Long.valueOf(body.get("receptorId").toString());
            String contenido = (String) body.get("contenido");
            
            ChatModel mensaje = chatService.enviarMensaje(emisorId, receptorId, contenido);
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Obtener conversación entre dos usuarios
    @GetMapping("/conversacion/{usuario1Id}/{usuario2Id}")
    public ResponseEntity<?> obtenerConversacion(
            @PathVariable Long usuario1Id,
            @PathVariable Long usuario2Id) {
        try {
            List<ChatModel> conversacion = chatService.obtenerConversacion(usuario1Id, usuario2Id);
            return ResponseEntity.ok(conversacion);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Marcar mensajes como vistos
    @PutMapping("/marcar-vistos/{usuarioId}/{otroUsuarioId}")
    public ResponseEntity<?> marcarComoVistos(
            @PathVariable Long usuarioId,
            @PathVariable Long otroUsuarioId) {
        try {
            chatService.marcarComoVistos(usuarioId, otroUsuarioId);
            return ResponseEntity.ok(Map.of("message", "Mensajes marcados como vistos"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Obtener mensajes no vistos de un usuario
    @GetMapping("/no-vistos/{usuarioId}")
    public ResponseEntity<?> obtenerMensajesNoVistos(@PathVariable Long usuarioId) {
        try {
            List<ChatModel> mensajes = chatService.obtenerMensajesNoVistos(usuarioId);
            return ResponseEntity.ok(mensajes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Obtener todos los mensajes enviados por un usuario
    @GetMapping("/enviados/{usuarioId}")
    public ResponseEntity<?> obtenerMensajesEnviados(@PathVariable Long usuarioId) {
        try {
            List<ChatModel> mensajes = chatService.obtenerMensajesEnviados(usuarioId);
            return ResponseEntity.ok(mensajes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Obtener todos los mensajes recibidos por un usuario
    @GetMapping("/recibidos/{usuarioId}")
    public ResponseEntity<?> obtenerMensajesRecibidos(@PathVariable Long usuarioId) {
        try {
            List<ChatModel> mensajes = chatService.obtenerMensajesRecibidos(usuarioId);
            return ResponseEntity.ok(mensajes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Eliminar la conversación entre dos usuarios
    @DeleteMapping("/eliminar/{usuario1Id}/{usuario2Id}")
    public ResponseEntity<?> eliminarConversacion(
            @PathVariable Long usuario1Id,
            @PathVariable Long usuario2Id) {
        try {
            chatService.eliminarConversacion(usuario1Id, usuario2Id);
            return ResponseEntity.ok(Map.of("message", "Conversación eliminada"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
} 