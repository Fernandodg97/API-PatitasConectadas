package net.xeill.elpuig.apipatitasconectadas.controllers.dto;

import java.sql.Date;
import net.xeill.elpuig.apipatitasconectadas.models.EventoModel;

public class EventoModelDtoResponse {
    
    private Long id;
    private String nombre;
    private String descripcion;
    private String ubicacion;
    private Date fecha;
    
    public EventoModelDtoResponse(EventoModel evento) {
        this.id = evento.getId();
        this.nombre = evento.getNombre();
        this.descripcion = evento.getDescripcion();
        this.ubicacion = evento.getUbicacion();
        this.fecha = evento.getFecha();
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
} 