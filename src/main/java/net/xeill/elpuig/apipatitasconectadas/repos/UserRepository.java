package net.xeill.elpuig.apipatitasconectadas.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.xeill.elpuig.apipatitasconectadas.models.*;
import java.util.List;


//Clase que me permite realizar las consultas a la base de datos

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    List<UserModel> findByApellidoStartsWith(String apellido);
}