// package net.xeill.elpuig.apipatitasconectadas.repos;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// import net.xeill.elpuig.apipatitasconectadas.models.*;
// import java.util.List;

// //Clase que me permite realizar las consultas a la base de datos
// @Repository
// public interface UsuarioGrupoRepository extends JpaRepository<UsuarioGrupoModel, Long> {
    
//     // Método para encontrar todos los grupos a los que pertenece un usuario
//     List<UsuarioGrupoModel> findByUsuario_id(Long usuario_id);
    
//     // Método para encontrar todos los usuarios que pertenecen a un grupo
//     List<UsuarioGrupoModel> findByGrupo_id(Long grupo_id);
    
//     // Método para encontrar la relación específica entre un usuario y un grupo
//     UsuarioGrupoModel findByUsuario_idAndGrupo_id(Long usuario_id, Long grupo_id);
// } 