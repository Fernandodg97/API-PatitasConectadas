package net.xeill.elpuig.apipatitasconectadas.controllers.dto;

import jakarta.validation.constraints.NotNull;

public class UsuarioComentarioModelDtoRequest {
    
    @NotNull(message = "El ID del comentario es obligatorio")
    private Long comentarioId;
    
    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;

    @NotNull(message = "El estado del like es obligatorio")
    private boolean like;

    // Getters y Setters
    public Long getComentarioId() {
        return comentarioId;
    }

    public void setComentarioId(Long comentarioId) {
        this.comentarioId = comentarioId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }
} 