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
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    // Petición GET que devuelve todos los grupos existentes
    @GetMapping
    public ArrayList<GrupoModel> getGrupos() {
        return this.grupoService.getGrupos();
    }

    // Petición POST para guardar un nuevo grupo
    @PostMapping
    public ResponseEntity<?> saveGrupo(@RequestBody GrupoModel grupo) {
        try {
            // Se intenta guardar el grupo recibido en el cuerpo de la petición
            GrupoModel savedGrupo = this.grupoService.saveGrupo(grupo);
            // Se devuelve el grupo guardado junto con el estado HTTP 201 (CREATED)
            return ResponseEntity.status(HttpStatus.CREATED).body(savedGrupo);
        } catch (Exception e) {
            // Si ocurre un error al guardar, se devuelve un mensaje de error con estado 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el grupo: " + e.getMessage());
        }
    }

    // Petición GET para obtener un grupo por su ID
    @GetMapping(path = "/{id}")
    public Optional<GrupoModel> getGrupoById(@PathVariable("id") Long id) {
        // Se utiliza @PathVariable para extraer el ID de la URL
        return this.grupoService.getById(id);
    }

    // Petición POST para actualizar un grupo por su ID
    @PostMapping(path = "/{id}")
    public GrupoModel updateGrupoById(@RequestBody GrupoModel request, @PathVariable("id") Long id) {
        // Se llama al servicio para actualizar el grupo correspondiente
        return this.grupoService.updateByID(request, id);
    }

    // Petición DELETE para eliminar un grupo por su ID
    @DeleteMapping(path = "/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        // Se intenta eliminar el grupo con el ID especificado
        boolean ok = this.grupoService.deleteGrupo(id);

        // Se devuelve un mensaje dependiendo de si la eliminación fue exitosa o no
        if (ok) {
            return "Se ha eliminado el grupo con id: " + id;
        } else {
            return "No se ha podido eliminar el grupo con id: " + id;
        }
    }
}
