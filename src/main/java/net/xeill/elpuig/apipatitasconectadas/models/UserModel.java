
package net.xeill.elpuig.apipatitasconectadas.models;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class UserModel {

    // ID autogenerado
    @Id
    // La base de datos genera automáticamente el ID (auto-incremental)
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    // Campo que representa el nombre del usuario
    @Column(length = 50, nullable = false)
    private String nombre;

    // Campo que representa el apellido del usuario
    @Column(length = 50, nullable = false)
    private String apellido;

    // Campo que representa el correo electrónico del usuario
    @Column(length = 50, nullable = false)
    private String correo;

    // Campo que representa la contraseña del usuario
    @Column(length = 250, nullable = false)
    private String contrasena;

    // Getters y Setters

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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
