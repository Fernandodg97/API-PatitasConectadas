package net.xeill.elpuig.apipatitasconectadas.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.xeill.elpuig.apipatitasconectadas.models.*;
import net.xeill.elpuig.apipatitasconectadas.repositories.*;

/**
 * Servicio que gestiona las operaciones relacionadas con los perfiles de usuario.
 * Proporciona métodos para crear, leer, actualizar y eliminar perfiles,
 * permitiendo a los usuarios personalizar su información adicional en la plataforma.
 */
@Service
public class PerfilService {

    //Sirve para injectar dependencias
    @Autowired
    PerfilRepository perfilRepository;

    /**
     * Obtiene todos los perfiles registrados en el sistema.
     * @return ArrayList con todos los perfiles
     */
    public ArrayList<PerfilModel> getPerfiles() {
        //findAll() es un metodo que me permite obtener todos los registros de la tabla
        return (ArrayList<PerfilModel>) perfilRepository.findAll();
    }

    /**
     * Guarda un nuevo perfil o actualiza uno existente.
     * @param perfil Objeto PerfilModel con los datos del perfil a guardar
     * @return El perfil guardado con su ID asignado
     */
    public PerfilModel savePerfil(PerfilModel perfil) {
        //save() es un metodo que me permite guardar un registro en la tabla
        return perfilRepository.save(perfil);
    }

    /**
     * Busca un perfil por su ID.
     * @param id ID del perfil a buscar
     * @return Optional con el perfil si existe, o vacío si no se encuentra
     */
    public Optional<PerfilModel> getById(Long id) {
        //findById() es un metodo que me permite obtener un registro por su id
        return perfilRepository.findById(id);
    }

    /**
     * Actualiza los datos de un perfil existente.
     * @param request Objeto PerfilModel con los nuevos datos
     * @param id ID del perfil a actualizar
     * @return El perfil actualizado
     */
    public PerfilModel updateByID(PerfilModel request, Long id) {
        PerfilModel perfil = perfilRepository.findById(id).get();

        perfil.setUsuario_id(request.getUsuario_id());
        perfil.setDescripcion(request.getDescripcion());
        perfil.setFecha_nacimiento(request.getFecha_nacimiento());
        perfil.setImg(request.getImg());

        return perfilRepository.save(perfil);
    }

    /**
     * Elimina un perfil por su ID.
     * @param id ID del perfil a eliminar
     * @return true si el perfil fue eliminado con éxito, false en caso contrario
     */
    public Boolean deletePerfil(Long id) {
        try {
            perfilRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
} 