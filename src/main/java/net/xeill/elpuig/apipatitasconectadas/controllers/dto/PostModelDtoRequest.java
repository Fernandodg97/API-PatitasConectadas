package net.xeill.elpuig.apipatitasconectadas.controllers.dto;

import java.time.LocalDateTime;
import net.xeill.elpuig.apipatitasconectadas.models.PostModel;
import net.xeill.elpuig.apipatitasconectadas.models.GrupoModel;
import net.xeill.elpuig.apipatitasconectadas.models.UserModel;

public class PostModelDtoRequest {
    
    private Long grupoId;
    
    private Long creadorId;
    
    private String contenido;
    
    private LocalDateTime fecha;
    
    private String img;
    
    public PostModel toDomain(UserModel creador, GrupoModel grupo) {
        PostModel post = new PostModel();
        post.setGrupo(grupo);
        post.setCreador(creador);
        post.setContenido(this.contenido);
        post.setFecha(this.fecha != null ? this.fecha : LocalDateTime.now());
        post.setImg(this.img);
        return post;
    }
    
    public Long getGrupoId() {
        return grupoId;
    }
    
    public void setGrupoId(Long grupoId) {
        this.grupoId = grupoId;
    }
    
    public Long getCreadorId() {
        return creadorId;
    }
    
    public void setCreadorId(Long creadorId) {
        this.creadorId = creadorId;
    }
    
    public String getContenido() {
        return contenido;
    }
    
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    
    public LocalDateTime getFecha() {
        return fecha;
    }
    
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    
    public String getImg() {
        return img;
    }
    
    public void setImg(String img) {
        this.img = img;
    }
} 