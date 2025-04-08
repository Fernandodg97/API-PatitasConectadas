// package net.xeill.elpuig.apipatitasconectadas.services;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import net.xeill.elpuig.apipatitasconectadas.models.*;
// import net.xeill.elpuig.apipatitasconectadas.repos.*;

// @Service
// public class UsuarioGrupoService {

//     //Sirve para injectar dependencias
//     @Autowired
//     IUsuarioGrupoRepository usuarioGrupoRepository;

//     //Metodo que me permite obtener todas las relaciones usuario-grupo
//     public ArrayList<UsuarioGrupoModel> getUsuarioGrupos() {
//         //findAll() es un metodo que me permite obtener todos los registros de la tabla
//         return (ArrayList<UsuarioGrupoModel>) usuarioGrupoRepository.findAll();
//     }

//     //Metodo que me permite guardar una relación usuario-grupo
//     public UsuarioGrupoModel saveUsuarioGrupo(UsuarioGrupoModel usuarioGrupo) {
//         //save() es un metodo que me permite guardar un registro en la tabla
//         return usuarioGrupoRepository.save(usuarioGrupo);
//     }

//     //Metodo que me permite obtener una relación usuario-grupo por su id
//     public Optional<UsuarioGrupoModel> getById(Long id) {
//         //findById() es un metodo que me permite obtener un registro por su id
//         return usuarioGrupoRepository.findById(id);
//     }

//     //Metodo que me permite actualizar una relación usuario-grupo por su id
//     public UsuarioGrupoModel updateByID(UsuarioGrupoModel request, Long id) {
//         UsuarioGrupoModel usuarioGrupo = usuarioGrupoRepository.findById(id).get();

//         usuarioGrupo.setGrupo_id(request.getGrupo_id());
//         usuarioGrupo.setUsuario_id(request.getUsuario_id());
//         usuarioGrupo.setRol(request.getRol());

//         return usuarioGrupoRepository.save(usuarioGrupo);
//     }

//     //Metodo que me permite eliminar una relación usuario-grupo por su id
//     public Boolean deleteUsuarioGrupo(Long id) {
//         try {
//             usuarioGrupoRepository.deleteById(id);
//             return true;
//         } catch (Exception e) {
//             return false;
//         }
//     }
    
//     //Metodo que me permite obtener todos los grupos a los que pertenece un usuario
//     public List<UsuarioGrupoModel> getGruposByUsuarioId(Long usuario_id) {
//         return usuarioGrupoRepository.findByUsuario_id(usuario_id);
//     }
    
//     //Metodo que me permite obtener todos los usuarios que pertenecen a un grupo
//     public List<UsuarioGrupoModel> getUsuariosByGrupoId(Long grupo_id) {
//         return usuarioGrupoRepository.findByGrupo_id(grupo_id);
//     }
    
//     //Metodo que me permite obtener la relación específica entre un usuario y un grupo
//     public UsuarioGrupoModel getUsuarioGrupoByUsuarioIdAndGrupoId(Long usuario_id, Long grupo_id) {
//         return usuarioGrupoRepository.findByUsuario_idAndGrupo_id(usuario_id, grupo_id);
//     }
// } 