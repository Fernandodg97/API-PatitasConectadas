package net.xeill.elpuig.apipatitasconectadas.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.xeill.elpuig.apipatitasconectadas.models.*;

import java.util.List;

//Clase que me permite realizar las consultas a la base de datos

@Repository
public interface ComentarioRepository extends JpaRepository<ComentarioModel, Long> {

    List<ComentarioModel> findByCreador(UserModel creador);
    
    List<ComentarioModel> findByGrupo(GrupoModel grupo);
    
    List<ComentarioModel> findByCreadorAndGrupo(UserModel creador, GrupoModel grupo);
    
    @Query("SELECT p FROM PostModel p WHERE p.contenido LIKE %:searchTerm%")
    List<ComentarioModel> searchByContent(@Param("searchTerm") String searchTerm);
    
    @Query("SELECT p FROM PostModel p WHERE p.fecha >= :startDate AND p.fecha <= :endDate")
    List<ComentarioModel> findByDateRange(@Param("startDate") java.time.LocalDateTime startDate, 
                                  @Param("endDate") java.time.LocalDateTime endDate);
}