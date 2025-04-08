package net.xeill.elpuig.apipatitasconectadas.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.xeill.elpuig.apipatitasconectadas.models.*;
import net.xeill.elpuig.apipatitasconectadas.repos.*;

@Service
public class PerfilService {

    //Sirve para injectar dependencias
    @Autowired
    PerfilRepository perfilRepository;

    //Metodo que me permite obtener todos los perfiles
    public ArrayList<PerfilModel> getPerfiles() {
        //findAll() es un metodo que me permite obtener todos los registros de la tabla
        return (ArrayList<PerfilModel>) perfilRepository.findAll();
    }

    //Metodo que me permite guardar un perfil
    public PerfilModel savePerfil(PerfilModel perfil) {
        //save() es un metodo que me permite guardar un registro en la tabla
        return perfilRepository.save(perfil);
    }

    //Metodo que me permite obtener un perfil por su id
    public Optional<PerfilModel> getById(Long id) {
        //findById() es un metodo que me permite obtener un registro por su id
        return perfilRepository.findById(id);
    }

    //Metodo que me permite actualizar un perfil por su id
    public PerfilModel updateByID(PerfilModel request, Long id) {
        PerfilModel perfil = perfilRepository.findById(id).get();

        perfil.setUsuario_id(request.getUsuario_id());
        perfil.setDescripcion(request.getDescripcion());
        perfil.setFecha_nacimiento(request.getFecha_nacimiento());
        perfil.setImg(request.getImg());

        return perfilRepository.save(perfil);
    }

    //Metodo que me permite eliminar un perfil por su id
    public Boolean deletePerfil(Long id) {
        try {
            perfilRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
} 