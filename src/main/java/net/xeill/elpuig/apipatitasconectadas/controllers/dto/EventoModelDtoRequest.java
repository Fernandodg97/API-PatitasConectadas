package net.xeill.elpuig.apipatitasconectadas.controllers.dto;

import java.sql.Date;
import net.xeill.elpuig.apipatitasconectadas.models.EventoModel;
import net.xeill.elpuig.apipatitasconectadas.models.UserModel;

public class EventoModelDtoRequest {
    
    private String nombre;
    
    private String descripcion;
    
    private String ubicacion;
    
    private Date fecha;
    
    private Long creadorId;
    
    public EventoModel toDomain(UserModel creador) {
        EventoModel evento = new EventoModel();
        evento.setNombre(this.nombre);
        evento.setDescripcion(this.descripcion);
        evento.setUbicacion(this.ubicacion);
        evento.setFecha(this.fecha);
        evento.setCreador(creador);
        return evento;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getUbicacion() {
        return ubicacion;
    }
    
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    
    public Date getFecha() {
        return fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Long getCreadorId() {
        return creadorId;
    }

    public void setCreadorId(Long creadorId) {
        this.creadorId = creadorId;
    }
} 