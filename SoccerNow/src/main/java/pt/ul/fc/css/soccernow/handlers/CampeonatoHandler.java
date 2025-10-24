package pt.ul.fc.css.soccernow.handlers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import pt.ul.fc.css.soccernow.dto.CampeonatoPontosDto;
import pt.ul.fc.css.soccernow.dto.EquipaDto;
import pt.ul.fc.css.soccernow.dto.EstatisticasEquipaDto;
import pt.ul.fc.css.soccernow.entities.Campeonato;
import pt.ul.fc.css.soccernow.entities.CampeonatoPontos;
import pt.ul.fc.css.soccernow.entities.Equipa;
import pt.ul.fc.css.soccernow.entities.Estatisticas;
import pt.ul.fc.css.soccernow.entities.EstatisticasEquipa;
import pt.ul.fc.css.soccernow.entities.Jogo;
import pt.ul.fc.css.soccernow.enums.EstadoEntidade;
import pt.ul.fc.css.soccernow.enums.Status;
import pt.ul.fc.css.soccernow.repository.CampeonatoRepository;

@Service
public class CampeonatoHandler {

    @Autowired
    CampeonatoRepository campeonatoRepository;

    @Autowired
    EquipaHandler equipaHandler;

    @Autowired
    EstatisticasEquipaHandler estatisticasEquipaHandler;

    @Autowired @Lazy
    JogoHandler jogoHandler;


    public Optional<Campeonato> findByNome(String nome) {
        Optional<Campeonato> campeonato = campeonatoRepository.findByNome(nome);
        return campeonato;
    }

    public CampeonatoPontosDto createCampeonato(CampeonatoPontosDto campeonatoDto) {

        if(campeonatoDto.getNome() == null || campeonatoDto.getAno() == 0) {
            return null;
        }

        CampeonatoPontos campeonato = new CampeonatoPontos();
        campeonato.setNome(campeonatoDto.getNome());
        campeonato.setAno(campeonatoDto.getAno());
        campeonato.setEstadoCampeonato(EstadoEntidade.ATIVO);
        campeonatoRepository.save(campeonato);

        if(campeonatoDto.getEquipas() != null && !campeonatoDto.getEquipas().isEmpty()) {

            List<String> equipasNomes = campeonatoDto.getEquipas();
            List<Equipa> equipas = new ArrayList<>();
            for (String nome : equipasNomes) {
                Optional<Equipa> e = equipaHandler.findByNome(nome);
                if(e.isPresent()) {
                    if(e.get().getEstadoEquipa() == EstadoEntidade.ATIVO && !equipas.contains(e.get())){
                        equipas.add(e.get());
                        //adicionar campeonato a equipa
                        equipaHandler.addCampeonatoToEquipa(e.get(), campeonato);
                        //criar estatisticasEquipa para a equipa
                        EstatisticasEquipa estEq = estatisticasEquipaHandler.createBaseEstatisticasEquipa(e.get(), campeonato);
                        equipaHandler.addResultadoToEquipa(e.get(), estEq);
                        campeonato.addLinhaTabela(estEq);
                    }
                }
            }

            campeonato.setEquipas(equipas);
        }

        else {
            campeonato.setEquipas(null);
        }

        CampeonatoPontos savedCampeonato = campeonatoRepository.save(campeonato);

        campeonatoDto.setId(savedCampeonato.getId());
        return mapToDto(savedCampeonato);
    }

    public CampeonatoPontosDto getCampeonatoById(Long id) {
        Optional<Campeonato> campeonato = campeonatoRepository.findById(id);
        if (campeonato.isEmpty()) {
            return null;
        }
        return mapToDto((CampeonatoPontos) campeonato.get());
    }

    public CampeonatoPontosDto mapToDto(CampeonatoPontos campeonatoPontos) {

        CampeonatoPontosDto campeonatoDto = new CampeonatoPontosDto();
        campeonatoDto.setId(campeonatoPontos.getId());
        campeonatoDto.setNome(campeonatoPontos.getNome());
        campeonatoDto.setAno(campeonatoPontos.getAno());
        campeonatoDto.setEstadoCampeonato(campeonatoPontos.getEstadoCampeonato());
        List<String> nomesEquipas = new ArrayList<>();
        List<Equipa> equipas = campeonatoPontos.getEquipas();
        if(equipas != null && !equipas.isEmpty()) {
            for (Equipa equipa : equipas) {
                nomesEquipas.add(equipa.getNome());
            }
        }
        campeonatoDto.setEquipas(nomesEquipas);
        List<EstatisticasEquipaDto> estatisticasEquipaDtos = new ArrayList<>();
        List<EstatisticasEquipa> estatisticasEquipas = campeonatoPontos.getTabela();
        for (EstatisticasEquipa estatisticasEquipa : estatisticasEquipas) {
            estatisticasEquipaDtos.add(estatisticasEquipaHandler.mapToDto(estatisticasEquipa));
        }
        campeonatoDto.setTabela(estatisticasEquipaDtos);

        return campeonatoDto;
    }

    public List<CampeonatoPontosDto> getAllCampeonatos() {
        List<Campeonato> campeonatos = campeonatoRepository.findAll();
        List<CampeonatoPontos> campeonatosPontos = new ArrayList<>();
        for (Campeonato c : campeonatos) {
            campeonatosPontos.add((CampeonatoPontos) c);
        }
        return campeonatosPontos.stream().map(this::mapToDto).toList();
    }

    public CampeonatoPontosDto addEquipaToCampeonato(Long id, String nome) {

        Optional<Campeonato> opt = campeonatoRepository.findById(id);
        if(!opt.isPresent()) {
            return null;
        }

        CampeonatoPontos c = (CampeonatoPontos) opt.get();

        Equipa e = equipaHandler.getEquipaByNomeFH(nome);
        if(e == null) {
            return null;
        }

        List<Equipa> equipas = c.getEquipas();

        if(equipas.contains(e)) {
            return null;
        }

        c.addEquipa(e);
        equipaHandler.addCampeonatoToEquipa(e, c);
        EstatisticasEquipa estEq = estatisticasEquipaHandler.createBaseEstatisticasEquipa(e, c);
        equipaHandler.addResultadoToEquipa(e, estEq);
        c.addLinhaTabela(estEq);
        CampeonatoPontos cp = campeonatoRepository.save(c);

        return mapToDto(cp);
    }

    public CampeonatoPontosDto removeEquipaFromCampeonato(Long id, String nome) {
        Optional<Campeonato> opt = campeonatoRepository.findById(id);
        if(!opt.isPresent()) {
            return null;
        }

        CampeonatoPontos c = (CampeonatoPontos) opt.get();

        Equipa e = equipaHandler.getEquipaByNomeFH(nome);
        if(e == null) {
            return null;
        }

        List<Equipa> equipas = c.getEquipas();

        if(!equipas.contains(e)) {
            return null;
        }

        List<Jogo> jogosCampeonato = jogoHandler.getJogosByCampeonato(c);

        if(!jogosCampeonato.isEmpty()) {
            return null;
        }

        //remove all related to campeonato from equipa and remove equipa and its estatisticasEquipa from campeonato
        List<EstatisticasEquipa> tabela = c.getTabela();
        EstatisticasEquipa eq = new EstatisticasEquipa();
        for (EstatisticasEquipa estEq : tabela) {
            if(estEq.getEquipa().getId() == e.getId()) {
                //equipaHandler.removeResultado(e, estEq);
                //equipaHandler.removeCampeonato(e, c);
                //equipaHandler.removeCampeonatoAndResultado(e, c, estEq);
                //c.removeLinhaTabela(estEq);
                //c.removeEquipa(e);
                eq = estEq;
            }
        }
        equipaHandler.removeCampeonatoAndResultado(e, c, eq);
        c.removeLinhaTabela(eq);
        estatisticasEquipaHandler.delete(eq);
        c.removeEquipa(e);

        CampeonatoPontos cUpdated = campeonatoRepository.save(c);

        return mapToDto(cUpdated);
    }

    public void addEstatistica(CampeonatoPontos campeonato, Equipa equipa, Estatisticas est) {

        List<EstatisticasEquipa> estEq = campeonato.getTabela();
        for (EstatisticasEquipa eEq : estEq) {
            if(eEq.getEquipa().getId() == equipa.getId()) {
                eEq.addEstatistica(est);
                estatisticasEquipaHandler.update(eEq);
            }
        }
        campeonatoRepository.save(campeonato);
    }

    public void registerVitoria(CampeonatoPontos c, Equipa e) {

        // EstatisticasEquipa estEq = new EstatisticasEquipa();
        // for(int i = 0; i < e.getHistoricoResultados().size(); i++) {
        //     if(e.getHistoricoResultados().get(i).getId() == c.getId()) {
        //         estEq = e.getHistoricoResultados().get(i);
        //         estatisticasEquipaHandler.registerVitoria(estEq);
        //     }
        // }

        List<EstatisticasEquipa> estEq = c.getTabela();
        for (EstatisticasEquipa eEq : estEq) {
            if(eEq.getEquipa().getId() == e.getId()) {
                eEq.incNumVitorias();
                eEq.addPontos(3);
                estatisticasEquipaHandler.update(eEq);
            }
        }

        campeonatoRepository.save(c);
    }

    public void registerEmpate(CampeonatoPontos c, Equipa e) {

        // EstatisticasEquipa estEq = new EstatisticasEquipa();
        // for(int i = 0; i < e.getHistoricoResultados().size(); i++) {
        //     if(e.getHistoricoResultados().get(i).getId() == c.getId()) {
        //         estEq = e.getHistoricoResultados().get(i);
        //         estatisticasEquipaHandler.registerEmpate(estEq);
        //     }
        // }

        List<EstatisticasEquipa> estEq = c.getTabela();
        for (EstatisticasEquipa eEq : estEq) {
            if(eEq.getEquipa().getId() == e.getId()) {
                eEq.incNumEmpates();
                eEq.addPontos(1);
                estatisticasEquipaHandler.update(eEq);
            }
        }

        campeonatoRepository.save(c);
    }

    public void registerDerrota(CampeonatoPontos c, Equipa e) {

        // EstatisticasEquipa estEq = new EstatisticasEquipa();
        // for(int i = 0; i < e.getHistoricoResultados().size(); i++) {
        //     if(e.getHistoricoResultados().get(i).getId() == c.getId()) {
        //         estEq = e.getHistoricoResultados().get(i);
        //         estatisticasEquipaHandler.registerDerrota(estEq);
        //     }
        // }

        List<EstatisticasEquipa> estEq = c.getTabela();
        for (EstatisticasEquipa eEq : estEq) {
            if(eEq.getEquipa().getId() == e.getId()) {
                eEq.incNumDerrotas();
                eEq.addPontos(0);
                estatisticasEquipaHandler.update(eEq);
            }
        }

        campeonatoRepository.save(c);
    }

    public CampeonatoPontosDto updateCampeonato(Long id, CampeonatoPontosDto campeonatoDto) {
        if(campeonatoDto.getNome() == null || id == 0 || campeonatoDto.getAno() == 0) {
            return null;
        }

        Optional<Campeonato> c = campeonatoRepository.findById(id);
        if (c.isEmpty()) {
            return null;
        }

        else if(c.get().getEstadoCampeonato() == EstadoEntidade.INATIVO) {
            return null;
        }

        CampeonatoPontos cp = (CampeonatoPontos) c.get();
        cp.setNome(campeonatoDto.getNome());
        cp.setAno(campeonatoDto.getAno());
        campeonatoRepository.save(cp);

        return mapToDto(cp);
    }

    public ResponseEntity<String> deleteCampeonato(Long id) {
        Optional<Campeonato> c = campeonatoRepository.findById(id);

        if(c.isPresent()){
            CampeonatoPontos cp = (CampeonatoPontos) c.get();
            List<Jogo> jogos = jogoHandler.getJogosByCampeonato(cp);
            for (Jogo jogo : jogos) {
                if(jogo.getStatus() == Status.COMECADO) {
                    return ResponseEntity.status(HttpStatus.PRECONDITION_REQUIRED).body("Campeonato ainda tem jogos marcados");
                }
            }

            List<Equipa> equipas = cp.getEquipas();
            for (Equipa equipa : equipas) {
                equipaHandler.removeCampeonato(equipa, cp);
            }

            cp.setEquipas(null);
            cp.setEstadoCampeonato(EstadoEntidade.INATIVO);
            campeonatoRepository.save(cp);

            return ResponseEntity.ok("Campeonato Eliminado");
        }

        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Campeonato não encontrado");
        }
    }

    public List<EquipaDto> getAllEquipasCampeonato(Long id) {
        Optional<Campeonato> campeonato = campeonatoRepository.findById(id);
        if (campeonato.isEmpty()) {
            return null;
        }

        CampeonatoPontos cp = (CampeonatoPontos) campeonato.get();
        List<Equipa> equipas = cp.getEquipas();
        List<EquipaDto> eDtos = new ArrayList<>();
        for (Equipa equipa : equipas) {
            eDtos.add(equipaHandler.mapToDto(equipa));
        }

        return eDtos;
    }

    public List<EstatisticasEquipaDto> getTabelaByCampeonatoId(Long id) {
        Optional<Campeonato> campeonato = campeonatoRepository.findById(id);
        if (campeonato.isEmpty()) {
            return null;
        }

        CampeonatoPontos cp = (CampeonatoPontos) campeonato.get();
        List<EstatisticasEquipa> estEq = cp.getTabela();
        Comparator<EstatisticasEquipa> byPontos = Comparator.comparing(EstatisticasEquipa::getPontos);
        estEq.sort(byPontos);
        //estEq.reversed();
        List<EstatisticasEquipaDto> estEqDtos = new ArrayList<>();
        for (EstatisticasEquipa est : estEq) {
            estEqDtos.add(estatisticasEquipaHandler.mapToDto(est));
        }

        return estEqDtos;
    }

    public ResponseEntity<String> getStringTabelaByCampeonatoId(Long id) {
        Optional<Campeonato> campeonato = campeonatoRepository.findById(id);
        if (campeonato.isEmpty()) {
            return null;
        }

        CampeonatoPontos cp = (CampeonatoPontos) campeonato.get();
        List<EstatisticasEquipa> estEq = cp.getTabela();
        Comparator<EstatisticasEquipa> byPontos = Comparator.comparing(EstatisticasEquipa::getPontos);
        estEq.sort(byPontos);
        //estEq.reversed();
        StringBuilder sb = new StringBuilder();
        sb.append("Posição - Nome - Jogos - Vitórias - Empates - Derrotas - Pontos\n");
        for(int i = 0; i < estEq.size(); i++) {
            sb.append(estatisticasEquipaHandler.mapToLinha(estEq.get(i), i+1));
            sb.append("\n");
        }

        return ResponseEntity.ok(sb.toString());
    }

    //2 FASE DO PROJETO //////////////////////////////////////////////////////////////////////////////////////////

    public List<Campeonato> findCampeonatosByNomeContainingIgnoreCase(String nome) {
        List<Campeonato> campeonatos = campeonatoRepository.findCampeonatosByNomeContainingIgnoreCase(nome);

        List<Campeonato> campeonatosFiltradas = new ArrayList<>(); 
        for(Campeonato c : campeonatos){
            if(c.getEstadoCampeonato() == EstadoEntidade.ATIVO){
                campeonatosFiltradas.add(c);
            }
        }
        return campeonatosFiltradas;
    }

    public List<Campeonato> findCampeonatosByNomeEquipa(String nomeEquipa) {
        return campeonatoRepository.findByEquipasNome(nomeEquipa);
    }

    public List<Campeonato> findCampeonatosByNumJogosRealizados(int num) {
        List<Jogo> jogosDeVariosCampeonatos = jogoHandler.findJogosRealizadosPorCampeonato();

        HashMap<Campeonato, Integer> jogosRealizadosPorCampeonato = new HashMap<>();

        //Colocar em cada linha do hashmap
        for (Jogo jogo : jogosDeVariosCampeonatos) {
            Campeonato campeonato = jogo.getCampeonato();
            jogosRealizadosPorCampeonato.put(campeonato, jogosRealizadosPorCampeonato.getOrDefault(campeonato, 0) + 1);
        }

        //Para cada entrada, verificar se numero de jogos realizados é igual ao parametro de entrada num
        List<Campeonato> campeonatosComNumJogos = new ArrayList<>();
        for (HashMap.Entry<Campeonato, Integer> entry : jogosRealizadosPorCampeonato.entrySet()) {
            if (entry.getValue() == num) {
                campeonatosComNumJogos.add(entry.getKey());
            }
        }

        return campeonatosComNumJogos;
    }

    public List<Campeonato> findCampeonatosByNumJogosPorRealizar(int num) {
        List<Jogo> jogosDeVariosCampeonatos = jogoHandler.findJogosPorRealizarPorCampeonato();

        HashMap<Campeonato, Integer> jogosPorRealizarPorCampeonato = new HashMap<>();

        //Colocar em cada linha do hashmap
        for (Jogo jogo : jogosDeVariosCampeonatos) {
            Campeonato campeonato = jogo.getCampeonato();
            jogosPorRealizarPorCampeonato.put(campeonato, jogosPorRealizarPorCampeonato.getOrDefault(campeonato, 0) + 1);
        }

         //Para cada entrada, verificar se numero de jogos por realizar é igual ao parametro de entrada num
        List<Campeonato> campeonatosComNumJogos = new ArrayList<>();
        for (HashMap.Entry<Campeonato, Integer> entry : jogosPorRealizarPorCampeonato.entrySet()) {
            if (entry.getValue() == num) {
                campeonatosComNumJogos.add(entry.getKey());
            }
        }

        return campeonatosComNumJogos;
    }

    public List<Campeonato> getAllCampeonatosAtivos() {
        List<Campeonato> campeonatos = campeonatoRepository.findAll();

        return campeonatos.stream()
            .filter(c -> c.getEstadoCampeonato() == EstadoEntidade.ATIVO)
            .toList();
    }
}
