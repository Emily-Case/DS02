package pt.ul.fc.css.soccernow.handlers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import pt.ul.fc.css.soccernow.dto.EstatisticasDto;
import pt.ul.fc.css.soccernow.dto.EstatisticasEquipaDto;
import pt.ul.fc.css.soccernow.entities.Campeonato;
import pt.ul.fc.css.soccernow.entities.Equipa;
import pt.ul.fc.css.soccernow.entities.Estatisticas;
import pt.ul.fc.css.soccernow.entities.EstatisticasEquipa;
import pt.ul.fc.css.soccernow.repository.EstatisticasEquipaRepository;

@Service
public class EstatisticasEquipaHandler {

    @Autowired
    private EstatisticasEquipaRepository estatisticasEquipaRepository;

    @Autowired @Lazy
    private EstatisticasHandler estatisticasHandler;

    public EstatisticasEquipa createBaseEstatisticasEquipa(Equipa e, Campeonato c) {
        EstatisticasEquipa estEq = new EstatisticasEquipa();
        estEq.setEquipa(e);
        estEq.setCampeonato(c);
        estEq.setEstatisticas(new ArrayList<>());
        estEq.setPontos(0);
        estEq.setNumVitorias(0);
        estEq.setNumEmpates(0);
        estEq.setNumDerrotas(0);

        EstatisticasEquipa savedEstEq = estatisticasEquipaRepository.save(estEq);

        return savedEstEq;
    }

    //Passa para Dto um objeto de tipo EstatisticasEquipa
    public EstatisticasEquipaDto mapToDto(EstatisticasEquipa stat){
        EstatisticasEquipaDto dto = new EstatisticasEquipaDto();
        dto.setId(stat.getId());
        dto.setEquipa(stat.getEquipa().getNome());
        dto.setCampeonato(stat.getCampeonato().getNome());
        dto.setPontos(stat.getPontos());
        dto.setNumVitorias(stat.getNumVitorias());
        dto.setNumEmpates(stat.getNumEmpates());
        dto.setNumDerrotas(stat.getNumDerrotas());

        List<Estatisticas> estatisticas = stat.getEstatisticas();
        List<EstatisticasDto> estatisticasDtos = new ArrayList<>();
    
        for (Estatisticas e : estatisticas) {
            estatisticasDtos.add(estatisticasHandler.mapToDto(e));
        }

        dto.setEstatisticas(estatisticasDtos);

        return dto;
    }

    public void update(EstatisticasEquipa eEq) {
        estatisticasEquipaRepository.save(eEq);
    }

    // public void registerVitoria(EstatisticasEquipa estEq) {
    //     estEq.incNumVitorias();
    //     estEq.addPontos(3);
    //     estatisticasEquipaRepository.save(estEq);
    // }

    // public void registerEmpate(EstatisticasEquipa estEq) {
    //     estEq.incNumEmpates();
    //     estEq.addPontos(1);
    //     estatisticasEquipaRepository.save(estEq);
    // }

    // public void registerDerrota(EstatisticasEquipa estEq) {
    //     estEq.incNumDerrotas();
    //     estEq.addPontos(0);
    //     estatisticasEquipaRepository.save(estEq);
    // }

    public void delete(EstatisticasEquipa eq) {
        estatisticasEquipaRepository.delete(eq);
    }  

    public String mapToLinha(EstatisticasEquipa stat, int posicao) {
        int totalJogos = stat.getNumVitorias() + stat.getNumEmpates() + stat.getNumDerrotas();
        String linha = posicao + " - " + stat.getEquipa().getNome() + " - " + totalJogos + " - " + stat.getNumVitorias() 
                                + " - " + stat.getNumEmpates() + " - " + stat.getNumDerrotas() + " - " + stat.getPontos();

        return linha;
    }

    //2 FASE DO PROJETO //////////////////////////////////////////////////////////////////////////////////////////

    public List<Object[]> findEquipasByNumeroVitorias(int numVitorias) {
         return estatisticasEquipaRepository.findEquipasAndCampeonatosByNumVitorias(numVitorias);
    }

    public List<Object[]> findEquipasByNumeroEmpates(int numEmpates) {
         return estatisticasEquipaRepository.findEquipasAndCampeonatosByNumEmpates(numEmpates);
    }

    public List<Object[]> findEquipasByNumeroDerrotas(int numDerrotas) {
         return estatisticasEquipaRepository.findEquipasAndCampeonatosByNumDerrotas(numDerrotas);
    }
    
}
