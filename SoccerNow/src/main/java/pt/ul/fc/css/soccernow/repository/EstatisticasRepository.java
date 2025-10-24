package pt.ul.fc.css.soccernow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pt.ul.fc.css.soccernow.entities.Estatisticas;

@Repository
public interface EstatisticasRepository extends JpaRepository<Estatisticas, Long>{

    List<Estatisticas> findEstatisticasByEquipaId(Long id);

    List<Estatisticas> findEstatisticasByJogadorId(Long id);

    @Query("SELECT e FROM Estatisticas e WHERE e.class = CartaoVermelho OR e.class = CartaoAmarelo")
    List<Estatisticas> findCartoes();

    //2 FASE DO PROJETO

    @Query("SELECT e FROM Estatisticas e WHERE TYPE(e) = :type")
    List<Estatisticas> findAllByType(@Param("type") Class<? extends Estatisticas> type);
}
