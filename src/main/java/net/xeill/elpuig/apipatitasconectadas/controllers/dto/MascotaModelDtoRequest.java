package net.xeill.elpuig.apipatitasconectadas.controllers.dto;

import net.xeill.elpuig.apipatitasconectadas.models.MascotaModel;

public class MascotaModelDtoRequest {
    
    private Long usuarioId;
    
    private String nombre;
    
    private String genero;
    
    private String raza;
    
    public MascotaModel toDomain() {
        MascotaModel mascota = new MascotaModel();
        mascota.setUsuarioId(this.usuarioId);
        mascota.setNombre(this.nombre);
        mascota.setGenero(this.genero);
        mascota.setRaza(this.raza);
        return mascota;
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