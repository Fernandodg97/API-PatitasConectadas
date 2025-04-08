package net.xeill.elpuig.apipatitasconectadas.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.xeill.elpuig.apipatitasconectadas.models.*;

//Clase que me permite realizar las consultas a la base de datos
@Repository
public interface EventoRepository extends JpaRepository<EventoModel, Long> {

} 