package net.xeill.elpuig.apipatitasconectadas.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.xeill.elpuig.apipatitasconectadas.controllers.dto.ChatModelDtoRequest;
import net.xeill.elpuig.apipatitasconectadas.controllers.dto.ChatModelDtoResponse;
import net.xeill.elpuig.apipatitasconectadas.models.ChatModel;
import net.xeill.elpuig.apipatitasconectadas.models.UserModel;
import net.xeill.elpuig.apipatitasconectadas.services.ChatService;
import net.xeill.elpuig.apipatitasconectadas.services.UserService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;
    
    @Autowired
    private UserService userService;

    // Enviar un nuevo mensaje
    @PostMapping("/enviar")
    public ResponseEntity<?> enviarMensaje(@RequestBody ChatModelDtoRequest chatDto) {
        try {
            UserModel emisor = userService.getById(chatDto.getEmisorId())
                .orElseThrow(() -> new RuntimeException("Usuario emisor no encontrado"));
            UserModel receptor = userService.getById(chatDto.getReceptorId())
                .orElseThrow(() -> new RuntimeException("Usuario receptor no encontrado"));
            
            ChatModel mensaje = chatService.enviarMensaje(
                chatDto.getEmisorId(), 
                chatDto.getReceptorId(), 
                chatDto.getContenido()
            );
            
            return new ResponseEntity<>(new ChatModelDtoResponse(mensaje), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // Obtener conversación entre dos usuarios
    @GetMapping("/conversacion/{usuario1Id}/{usuario2Id}")
    public ResponseEntity<?> obtenerConversacion(
            @PathVariable Long usuario1Id,
            @PathVariable Long usuario2Id) {
        try {
            List<ChatModel> conversacion = chatService.obtenerConversacion(usuario1Id, usuario2Id);
            List<ChatModelDtoResponse> conversacionDto = conversacion.stream()
                .map(ChatModelDtoResponse::new)
                .collect(Collectors.toList());
                
            return new ResponseEntity<>(conversacionDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // Marcar mensajes como vistos
    @PutMapping("/marcar-vistos/{usuarioId}/{otroUsuarioId}")
    public ResponseEntity<?> marcarComoVistos(
            @PathVariable Long usuarioId,
            @PathVariable Long otroUsuarioId) {
        try {
            chatService.marcarComoVistos(usuarioId, otroUsuarioId);
            return new ResponseEntity<>(Map.of("message", "Mensajes marcados como vistos"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // Obtener mensajes no vistos de un usuario
    @GetMapping("/no-vistos/{usuarioId}")
    public ResponseEntity<?> obtenerMensajesNoVistos(@PathVariable Long usuarioId) {
        try {
            List<ChatModel> mensajes = chatService.obtenerMensajesNoVistos(usuarioId);
            List<ChatModelDtoResponse> mensajesDto = mensajes.stream()
                .map(ChatModelDtoResponse::new)
                .collect(Collectors.toList());
                
            return new ResponseEntity<>(mensajesDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // Obtener todos los mensajes enviados por un usuario
    @GetMapping("/enviados/{usuarioId}")
    public ResponseEntity<?> obtenerMensajesEnviados(@PathVariable Long usuarioId) {
        try {
            List<ChatModel> mensajes = chatService.obtenerMensajesEnviados(usuarioId);
            List<ChatModelDtoResponse> mensajesDto = mensajes.stream()
                .map(ChatModelDtoResponse::new)
                .collect(Collectors.toList());
                
            return new ResponseEntity<>(mensajesDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // Obtener todos los mensajes recibidos por un usuario
    @GetMapping("/recibidos/{usuarioId}")
    public ResponseEntity<?> obtenerMensajesRecibidos(@PathVariable Long usuarioId) {
        try {
            List<ChatModel> mensajes = chatService.obtenerMensajesRecibidos(usuarioId);
            List<ChatModelDtoResponse> mensajesDto = mensajes.stream()
                .map(ChatModelDtoResponse::new)
                .collect(Collectors.toList());
                
            return new ResponseEntity<>(mensajesDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // Eliminar la conversación entre dos usuarios
    @DeleteMapping("/eliminar/{usuario1Id}/{usuario2Id}")
    public ResponseEntity<?> eliminarConversacion(
            @PathVariable Long usuario1Id,
            @PathVariable Long usuario2Id) {
        try {
            chatService.eliminarConversacion(usuario1Id, usuario2Id);
            return new ResponseEntity<>(Map.of("message", "Conversación eliminada"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
} 