package pt.ul.fc.css.soccernow.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pt.ul.fc.css.soccernow.entities.Equipa;
import pt.ul.fc.css.soccernow.entities.Jogo;

@Repository
public interface EquipaRepository extends JpaRepository<Equipa, Long>{
    Optional<Equipa> findByNome(String nome);

    @Query("SELECT e FROM Equipa e WHERE e.nome IN :nomesEquipas")
    List<Equipa> findAllByNome(@Param("nomesEquipas") List<String> nomesEquipas);

    @Query("SELECT e.historicoJogos FROM Equipa e WHERE e.id = :equipaId")
    List<Jogo> findJogosByEquipaId(@Param("equipaId") Long equipaId);

    @Query("SELECT e FROM Equipa e WHERE SIZE(e.jogadores) < 5")
    List<Equipa> findEquipasWithLessThanFiveJogadores();

    //2 FASE DO PROJETO

    @Query("SELECT e FROM Equipa e WHERE LOWER(e.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Equipa> findEquipasByNomeContainingIgnoreCase(@Param("nome") String nome);

    @Query("SELECT e FROM Equipa e JOIN e.jogadores j GROUP BY e HAVING COUNT(j) = :numJogadores")
    List<Equipa> findEquipasByNumeroJogadores(@Param("numJogadores") long numJogadores);

}
