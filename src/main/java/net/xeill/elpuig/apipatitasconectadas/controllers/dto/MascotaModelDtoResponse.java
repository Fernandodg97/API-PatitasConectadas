package net.xeill.elpuig.apipatitasconectadas.controllers.dto;

import net.xeill.elpuig.apipatitasconectadas.models.MascotaModel;

public class MascotaModelDtoResponse {
    
    private Long id;
    private Long usuarioId;
    private String nombre;
    private String genero;
    private String raza;
    
    public MascotaModelDtoResponse(MascotaModel mascota) {
        this.id = mascota.getId();
        this.usuarioId = mascota.getUsuarioId();
        this.nombre = mascota.getNombre();
        this.genero = mascota.getGenero();
        this.raza = mascota.getRaza();
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getUsuarioId() {
        return usuarioId;
    }
    
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getGenero() {
        return genero;
    }
    
    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    public String getRaza() {
        return raza;
    }
    
    public void setRaza(String raza) {
        this.raza = raza;
    }
} 