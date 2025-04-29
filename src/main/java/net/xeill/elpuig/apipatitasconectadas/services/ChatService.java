package net.xeill.elpuig.apipatitasconectadas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.xeill.elpuig.apipatitasconectadas.models.ChatModel;
import net.xeill.elpuig.apipatitasconectadas.models.UserModel;
import net.xeill.elpuig.apipatitasconectadas.repositories.ChatRepository;
import net.xeill.elpuig.apipatitasconectadas.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;
    
    @Autowired
    private UserRepository userRepository;

    // Enviar un nuevo mensaje
    @Transactional
    public ChatModel enviarMensaje(Long emisorId, Long receptorId, String contenido) {
        Optional<UserModel> emisor = userRepository.findById(emisorId);
        Optional<UserModel> receptor = userRepository.findById(receptorId);
        
        if (emisor.isEmpty() || receptor.isEmpty()) {
            throw new RuntimeException("Usuario emisor o receptor no encontrado");
        }
        
        ChatModel mensaje = new ChatModel();
        mensaje.setEmisor(emisor.get());
        mensaje.setReceptor(receptor.get());
        mensaje.setContenido(contenido);
        mensaje.setVisto(false);
        mensaje.setFechaHora(LocalDateTime.now());
        
        return chatRepository.save(mensaje);
    }

    // Obtener conversación entre dos usuarios
    public List<ChatModel> obtenerConversacion(Long usuario1Id, Long usuario2Id) {
        return chatRepository.findConversacion(usuario1Id, usuario2Id);
    }

    // Marcar mensajes como vistos
    @Transactional
    public void marcarComoVistos(Long usuarioId, Long otroUsuarioId) {
        List<ChatModel> mensajesNoVistos = chatRepository.findMensajesNoVistos(usuarioId);
        for (ChatModel mensaje : mensajesNoVistos) {
            if (mensaje.getEmisor().getId().equals(otroUsuarioId)) {
                mensaje.setVisto(true);
                chatRepository.save(mensaje);
            }
        }
    }

    // Obtener mensajes no vistos de un usuario
    public List<ChatModel> obtenerMensajesNoVistos(Long usuarioId) {
        return chatRepository.findMensajesNoVistos(usuarioId);
    }

    // Obtener todos los mensajes enviados por un usuario
    public List<ChatModel> obtenerMensajesEnviados(Long usuarioId) {
        return chatRepository.findByEmisorId(usuarioId);
    }

    // Obtener todos los mensajes recibidos por un usuario
    public List<ChatModel> obtenerMensajesRecibidos(Long usuarioId) {
        return chatRepository.findByReceptorId(usuarioId);
    }

    // Método para eliminar la conversación entre dos usuarios
    public void eliminarConversacion(Long usuario1Id, Long usuario2Id) {
        List<ChatModel> conversacion = chatRepository.findConversacion(usuario1Id, usuario2Id);
        
        if (conversacion != null && !conversacion.isEmpty()) {
            chatRepository.deleteAll(conversacion);
        } else {
            throw new RuntimeException("No se encontró una conversación entre los usuarios.");
        }
    }
} 