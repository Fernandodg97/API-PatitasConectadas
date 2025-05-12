package net.xeill.elpuig.apipatitasconectadas.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.xeill.elpuig.apipatitasconectadas.models.*;
import net.xeill.elpuig.apipatitasconectadas.repositories.*;

/**
 * Servicio que gestiona las operaciones relacionadas con los usuarios.
 * Proporciona métodos para crear, leer, actualizar y eliminar usuarios,
 * así como para realizar búsquedas específicas.
 */
@Service
public class UserService {

    //Sirve para injectar dependencias
    @Autowired
    UserRepository userRepository;

    /**
     * Obtiene todos los usuarios registrados en el sistema.
     * @return ArrayList con todos los usuarios
     */
    public ArrayList<UserModel> getUsers() {
        //findAll() es un metodo que me permite obtener todos los registros de la tabla
        return (ArrayList<UserModel>) userRepository.findAll();
    }

    /**
     * Busca usuarios por apellido.
     * @param apellido Apellido o inicio del apellido para filtrar usuarios
     * @return Lista de usuarios que coinciden con el criterio de búsqueda
     */
    public List<UserModel> getUsers(String apellido){
        return userRepository.findByApellidoStartsWith(apellido);
    }

    /**
     * Guarda un nuevo usuario o actualiza uno existente.
     * @param user Objeto UserModel con los datos del usuario a guardar
     * @return El usuario guardado con su ID asignado
     */
    public UserModel saveUser(UserModel user) {
        //save() es un metodo que me permite guardar un registro en la tabla
        return userRepository.save(user);
    }

    /**
     * Busca un usuario por su ID.
     * @param id ID del usuario a buscar
     * @return Optional con el usuario si existe, o vacío si no se encuentra
     */
    public Optional<UserModel> getById(Long id) {
        //findById() es un metodo que me permite obtener un registro por su id
        return userRepository.findById(id);
    }

    /**
     * Actualiza los datos de un usuario existente.
     * @param request Objeto UserModel con los nuevos datos
     * @param id ID del usuario a actualizar
     * @return El usuario actualizado
     */
    public UserModel updateByID(UserModel request, Long id) {
        UserModel user = userRepository.findById(id).get();

        user.setNombre(request.getNombre());
        user.setApellido(request.getApellido());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        return user;
    }

    /**
     * Elimina un usuario por su ID.
     * @param id ID del usuario a eliminar
     * @return true si el usuario fue eliminado con éxito, false en caso contrario
     */
    public Boolean deleteUser (Long id){
        try{
            userRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public UserModel getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Busca usuarios por nombre y apellido
     * @param nombre Nombre del usuario
     * @param apellido Apellido del usuario
     * @return Lista de usuarios que coinciden con los criterios de búsqueda
     */
    public List<UserModel> searchUsers(String nombre, String apellido) {
        System.out.println("UserService.searchUsers - nombre: [" + nombre + "], apellido: [" + apellido + "]");
        
        try {
            if (nombre != null && apellido != null) {
                System.out.println("Buscando por nombre y apellido");
                List<UserModel> results = userRepository.findByNombreContainingIgnoreCaseAndApellidoContainingIgnoreCase(nombre, apellido);
                System.out.println("Resultados encontrados: " + results.size());
                return results;
            } else if (nombre != null) {
                System.out.println("Buscando solo por nombre");
                List<UserModel> results = userRepository.findByNombreContainingIgnoreCase(nombre);
                System.out.println("Resultados encontrados: " + results.size());
                return results;
            } else if (apellido != null) {
                System.out.println("Buscando solo por apellido");
                List<UserModel> results = userRepository.findByApellidoContainingIgnoreCase(apellido);
                System.out.println("Resultados encontrados: " + results.size());
                return results;
            } else {
                System.out.println("No se proporcionaron criterios de búsqueda");
                return new ArrayList<>();
            }
        } catch (Exception e) {
            System.err.println("Error en UserService.searchUsers: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}