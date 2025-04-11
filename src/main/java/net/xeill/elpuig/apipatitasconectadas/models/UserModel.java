package net.xeill.elpuig.apipatitasconectadas.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    // Campo que representa el email electrónico del usuario
    @Column(length = 50, nullable = false)
    private String email;

    // Campo que representa la contraseña del usuario
    @JsonIgnore
    @Column(length = 250, nullable = false)
    private String password;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
