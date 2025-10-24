package pt.ul.fc.css.soccernow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pt.ul.fc.css.soccernow.entities.EstatisticasEquipa;

@Repository
public interface EstatisticasEquipaRepository extends JpaRepository<EstatisticasEquipa, Long> {

    //2 FASE DO PROJETO //////////////////////////////////////////////////////////////////////////////////////////

    @Query("SELECT ee.equipa.nome, ee.campeonato.nome FROM EstatisticasEquipa ee WHERE ee.numVitorias = :numVitorias")
    List<Object[]> findEquipasAndCampeonatosByNumVitorias(@Param("numVitorias") int numVitorias);

    @Query("SELECT ee.equipa.nome, ee.campeonato.nome FROM EstatisticasEquipa ee WHERE ee.numEmpates = :numEmpates")
    List<Object[]> findEquipasAndCampeonatosByNumEmpates(int numEmpates);

    @Query("SELECT ee.equipa.nome, ee.campeonato.nome FROM EstatisticasEquipa ee WHERE ee.numDerrotas = :numDerrotas")
    List<Object[]> findEquipasAndCampeonatosByNumDerrotas(int numDerrotas);
    
}