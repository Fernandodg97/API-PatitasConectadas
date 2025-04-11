package net.xeill.elpuig.apipatitasconectadas.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.xeill.elpuig.apipatitasconectadas.models.*;
import net.xeill.elpuig.apipatitasconectadas.services.*;

@RestController
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    // Método GET para obtener todos los perfiles
    @GetMapping(path = "/perfiles")
    public ArrayList<PerfilModel> getPerfiles() {
        // Llama al servicio para obtener la lista de perfiles y la retorna
        return this.perfilService.getPerfiles();
    }

    // Método POST para guardar un nuevo perfil
    @PostMapping(path = "/perfiles")
    public ResponseEntity<?> savePerfil(@RequestBody PerfilModel perfil) {
        try {
            // Intenta guardar el perfil recibido en el cuerpo de la petición
            PerfilModel savedPerfil = this.perfilService.savePerfil(perfil);
            // Devuelve el perfil guardado con estado HTTP 201 (CREATED)
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPerfil);
        } catch (Exception e) {
            // En caso de error, devuelve estado HTTP 500 (INTERNAL SERVER ERROR) con el mensaje del error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el perfil: " + e.getMessage());
        }
    }

    // Método GET para obtener un perfil por su ID
    @GetMapping(path = "/usuarios/{id}/perfiles")
    public Optional<PerfilModel> getPerfilById(@PathVariable("id") Long id) {
        // Llama al servicio para buscar el perfil por ID
        return this.perfilService.getById(id);
    }

    // Método POST para actualizar un perfil existente según su ID
    @PostMapping(path = "/usuarios/{id}/perfiles")
    public PerfilModel updatePerfilById(@RequestBody PerfilModel request, @PathVariable("id") Long id) {
        // Llama al servicio para actualizar el perfil con los nuevos datos
        return this.perfilService.updateByID(request, id);
    }

    // Método DELETE para eliminar un perfil por su ID
    @DeleteMapping(path = "/usuarios/{id}/perfiles")
    public String deleteById(@PathVariable("id") Long id) {
        // Intenta eliminar el perfil con el ID especificado
        boolean ok = this.perfilService.deletePerfil(id);

        // Devuelve un mensaje indicando si la eliminación fue exitosa o no
        if (ok) {
            return "Se ha eliminado el perfil con id: " + id;
        } else {
            return "No se ha podido eliminar el perfil con id: " + id;
        }
    }
}
