package net.xeill.elpuig.apipatitasconectadas.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.xeill.elpuig.apipatitasconectadas.models.*;
import net.xeill.elpuig.apipatitasconectadas.repos.*;

@Service
public class GrupoService {

    //Sirve para injectar dependencias
    @Autowired
    IGrupoRepository grupoRepository;

    //Metodo que me permite obtener todos los grupos
    public ArrayList<GrupoModel> getGrupos() {
        //findAll() es un metodo que me permite obtener todos los registros de la tabla
        return (ArrayList<GrupoModel>) grupoRepository.findAll();
    }

    //Metodo que me permite guardar un grupo
    public GrupoModel saveGrupo(GrupoModel grupo) {
        //save() es un metodo que me permite guardar un registro en la tabla
        return grupoRepository.save(grupo);
    }

    //Metodo que me permite obtener un grupo por su id
    public Optional<GrupoModel> getById(Long id) {
        //findById() es un metodo que me permite obtener un registro por su id
        return grupoRepository.findById(id);
    }

    //Metodo que me permite actualizar un grupo por su id
    public GrupoModel updateByID(GrupoModel request, Long id) {
        GrupoModel grupo = grupoRepository.findById(id).get();

        grupo.setNombre(request.getNombre());
        grupo.setDescripcion(request.getDescripcion());

        return grupoRepository.save(grupo);
    }

    //Metodo que me permite eliminar un grupo por su id
    public Boolean deleteGrupo(Long id) {
        try {
            grupoRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
} 