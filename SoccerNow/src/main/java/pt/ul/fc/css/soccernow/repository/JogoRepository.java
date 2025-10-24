package pt.ul.fc.css.soccernow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pt.ul.fc.css.soccernow.entities.Estatisticas;
import pt.ul.fc.css.soccernow.entities.Jogo;

@Repository
public interface JogoRepository extends JpaRepository<Jogo, Long>{

    @Query("SELECT j.estatisticas FROM Jogo j WHERE j.id = :id")
    List<Estatisticas> findEstatisticasByJogoId(@Param("id") Long id);

    //2 FASE DO PROJETO

    @Query("SELECT j FROM Jogo j WHERE j.status = pt.ul.fc.css.soccernow.enums.Status.ACABADO")
    List<Jogo> findJogosAcabados();

    @Query("SELECT j FROM Jogo j WHERE j.status = pt.ul.fc.css.soccernow.enums.Status.COMECADO")
    List<Jogo> findJogosPorRealizar();

    @Query("SELECT j FROM Jogo j WHERE LOWER(j.localizacao) LIKE LOWER(CONCAT('%', :localizacao, '%'))")
    List<Jogo> findJogosByLocalizacao(@Param("localizacao") String localizacao);

    
    int countByVencedor_Nome(String nomeEquipa);

}

