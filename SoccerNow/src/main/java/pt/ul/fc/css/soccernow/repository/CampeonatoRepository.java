package pt.ul.fc.css.soccernow.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pt.ul.fc.css.soccernow.entities.Campeonato;

@Repository
public interface CampeonatoRepository extends JpaRepository<Campeonato, Long>{

    Optional<Campeonato> findByNome(String nome);

    //2 FASE DO PROJETO

    @Query("SELECT c FROM Campeonato c WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Campeonato> findCampeonatosByNomeContainingIgnoreCase(String nome);

    @Query("SELECT c FROM Campeonato c JOIN c.equipas e WHERE LOWER(e.nome) LIKE LOWER(CONCAT('%', :nomeEquipa, '%'))")
    List<Campeonato> findByEquipasNome(String nomeEquipa);

}
