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
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    // Peticion GET para obtener todos los usuarios
    @GetMapping
    public ArrayList<UserModel> getUsers() {
        return this.userService.getUsers();
    }

    // Peticion POST para guardar un usuario
    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody UserModel user) {
         try {
            // Intentamos guardar al usuario
            UserModel savedUser = this.userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);  // Devuelve el usuario guardado con estado 201 (CREATED)
        } catch (Exception e) {
            // Si ocurre un error, devolvemos un mensaje con código de error 500 (Internal Server Error)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el usuario: " + e.getMessage());
        }
    }

    // Peticion GET para obtener un usuario por su ID
    @GetMapping(path = "/{id}")
    public Optional<UserModel> getUserById(@PathVariable("id") Long id) {
        return this.userService.getById(id);
    }

    // Peticion POST para actualizar un usuario por su ID
    @PostMapping(path = "/{id}")
    public UserModel updateUserById(@RequestBody UserModel request,@PathVariable("id") Long id){
        return this.userService.updateByID(request, id);
    }

    // Peticion DELETE para eliminar un usuario por su ID
    @DeleteMapping(path = "/{id}")
    public String deleteById(@PathVariable("id") Long id){
        boolean ok = this.userService.deleteUser(id);

        if(ok){
            return "Se ha eliminado el usuario con id: " + id + " eliminado";
        }else{
            return "No se ha podido eliminar el usuario con id: " + id;
        }
    }

}
