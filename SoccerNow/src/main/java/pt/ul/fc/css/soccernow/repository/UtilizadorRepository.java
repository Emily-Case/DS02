package pt.ul.fc.css.soccernow.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pt.ul.fc.css.soccernow.entities.Arbitro;
import pt.ul.fc.css.soccernow.entities.Jogador;
import pt.ul.fc.css.soccernow.entities.Utilizador;

@Repository
public interface UtilizadorRepository extends JpaRepository<Utilizador, Long>{

    Optional<Utilizador> findByNome(String nome);

    List<Utilizador> findByNomeIn(List<String> nomes);

    @Query("SELECT u FROM Utilizador u WHERE u.nome = :nome AND TYPE(u) = :type")
    Optional<Utilizador> findByNomeAndType(@Param("nome") String nome, @Param("type") Class<? extends Utilizador> type);

    @Query("SELECT a FROM Arbitro a ORDER BY a.numJogos DESC LIMIT 1")
    Optional<Utilizador> findTopByOrderByNumJogosDesc();

    Optional<Utilizador> findByNif(int nif);

    //2 FASE DO PROJETO

    @Query("SELECT j FROM Jogador j WHERE LOWER(j.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Jogador> findJogadoresByNomeContainingIgnoreCase(@Param("nome") String nome);

    @Query("SELECT a FROM Arbitro a WHERE LOWER(a.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Arbitro> findArbitrosByNomeContainingIgnoreCase(@Param("nome") String nome);

    @Query("SELECT u FROM Utilizador u WHERE TYPE(u) = :type")
    List<Utilizador> findAllByType(@Param("type") Class<? extends Utilizador> type);

}
