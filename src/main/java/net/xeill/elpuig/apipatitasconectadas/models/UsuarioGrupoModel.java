// package net.xeill.elpuig.apipatitasconectadas.models;
// import jakarta.persistence.*;

// @Entity
// @Table(name = "usuario_grupo")
// public class UsuarioGrupoModel {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;
    
//     @Column
//     private Long grupo_id;

//     @Column(nullable = false)
//     private Long usuario_id;

//     @Column
//     private String rol;

//     public Long getId() {
//         return id;
//     }

//     public void setId(Long id) {
//         this.id = id;
//     }

//     public Long getGrupo_id() {
//         return grupo_id;
//     }

//     public void setGrupo_id(Long grupo_id) {
//         this.grupo_id = grupo_id;
//     }

//     public Long getUsuario_id() {
//         return usuario_id;
//     }

//     public void setUsuario_id(Long usuario_id) {
//         this.usuario_id = usuario_id;
//     }

//     public String getRol() {
//         return rol;
//     }

//     public void setRol(String rol) {
//         this.rol = rol;
//     }
// } 