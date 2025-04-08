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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import net.xeill.elpuig.apipatitasconectadas.models.*;
import net.xeill.elpuig.apipatitasconectadas.services.*;

@RestController
@RequestMapping("/perfiles")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    // Peticion GET para obtener todos los perfiles
    @GetMapping
    public ArrayList<PerfilModel> getPerfiles() {
        return this.perfilService.getPerfiles();
    }

    // Peticion POST para guardar un perfil
    @PostMapping
    public ResponseEntity<?> savePerfil(@RequestBody PerfilModel perfil) {
         try {
            // Intentamos guardar el perfil
            PerfilModel savedPerfil = this.perfilService.savePerfil(perfil);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPerfil);  // Devuelve el perfil guardado con estado 201 (CREATED)
        } catch (Exception e) {
            // Si ocurre un error, devolvemos un mensaje con código de error 500 (Internal Server Error)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el perfil: " + e.getMessage());
        }
    }

    @GetMapping(path = "/{id}")
    public Optional<PerfilModel> getPerfilById(@PathVariable("id") Long id) {
        return this.perfilService.getById(id);
    }

    @PostMapping(path = "/{id}")
    public PerfilModel updatePerfilById(@RequestBody PerfilModel request, @PathVariable("id") Long id) {
        return this.perfilService.updateByID(request, id);
    }

    @DeleteMapping(path = "/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        boolean ok = this.perfilService.deletePerfil(id);

        if(ok) {
            return "Se ha eliminado el perfil con id: " + id;
        } else {
            return "No se ha podido eliminar el perfil con id: " + id;
        }
    }
} 