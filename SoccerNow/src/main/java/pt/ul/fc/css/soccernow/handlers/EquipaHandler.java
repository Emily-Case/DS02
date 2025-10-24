package pt.ul.fc.css.soccernow.handlers;

import java.util.ArrayList;
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
import pt.ul.fc.css.soccernow.dto.JogoDto;
import pt.ul.fc.css.soccernow.entities.Arbitro;
import pt.ul.fc.css.soccernow.entities.Campeonato;
import pt.ul.fc.css.soccernow.entities.CampeonatoPontos;
import pt.ul.fc.css.soccernow.entities.Equipa;
import pt.ul.fc.css.soccernow.entities.EstatisticasEquipa;
import pt.ul.fc.css.soccernow.entities.Jogador;
import pt.ul.fc.css.soccernow.entities.Jogo;
import pt.ul.fc.css.soccernow.entities.Utilizador;
import pt.ul.fc.css.soccernow.enums.EstadoEntidade;
import pt.ul.fc.css.soccernow.enums.Posicao;
import pt.ul.fc.css.soccernow.enums.Status;
import pt.ul.fc.css.soccernow.repository.EquipaRepository;

@Service
public class EquipaHandler {

    @Autowired
    private EquipaRepository equipaRepository;

    @Autowired @Lazy
    private UtilizadorHandler utilizadorHandler;

    @Autowired @Lazy
    private JogoHandler jogoHandler;

    @Autowired @Lazy
    private EstatisticasHandler estatisticasHandler;

    @Autowired @Lazy
    private EquipaJogoHandler equipaJogoHandler;

    @Autowired @Lazy
    private CampeonatoHandler campeonatoHandler;

    @Autowired @Lazy
    private EstatisticasEquipaHandler estatisticasEquipaHandler;

    public Optional<Equipa> findByNome(String nome) {
        return equipaRepository.findByNome(nome);
    }

    //Verificações Feitas
    public EquipaDto createEquipa(EquipaDto equipaDto) {

        if(equipaDto.getNome() == null) {
            return null;
        }

        if(getEquipaByNome(equipaDto.getNome()) != null) {
            return null;
        }

        Equipa equipa = new Equipa();
        equipa.setNome(equipaDto.getNome());
        List<String> jogadores = equipaDto.getJogadores();
        equipaRepository.save(equipa);

        //coloca efetivamente todos os Jogadores com aqueles nomes dentro da lista de jogadores chamadno do utilizadorHandler o addEquipaToJogador
        for(int i = 0; i < jogadores.size(); i++) {
            Optional<Utilizador> utilizador = utilizadorHandler.findByNomeAndType(jogadores.get(i), Jogador.class);
            Jogador j = (Jogador) utilizador.get();
            equipa.addJogador(j);
            utilizadorHandler.addEquipaToJogador(j, equipa);
        }
        //definir estado da equipa com ativo, uma vez que como a estamos a criar, não pode já estar deleted
        equipa.setEstadoEquipa(EstadoEntidade.ATIVO);

        Equipa savedEquipa = equipaRepository.save(equipa);

        equipaDto.setId(savedEquipa.getId());
        return equipaDto;
    }

    //Verificações Feitas
    public List<EquipaDto> getAllEquipas() {
        List<Equipa> equipas = equipaRepository.findAll();
        return equipas.stream().map(this::mapToDto).toList();
    }

    //Função que coloca uma Equipa específica em formato Dto
    public EquipaDto mapToDto(Equipa equipa) {
        EquipaDto equipaDto = new EquipaDto();
        equipaDto.setId(equipa.getId());
        equipaDto.setNome(equipa.getNome());
        List<Jogador> jogadores = equipa.getJogadores();
        for(int i = 0; i < jogadores.size(); i++) {
            equipaDto.addJogador(jogadores.get(i).getNome());
        }
        List<Campeonato> campeonatos = equipa.getCampeonatos();
        for(int i = 0; i < campeonatos.size(); i++) {
            equipaDto.addCampeonato(campeonatos.get(i).getNome());
        }
        equipaDto.setEstadoEquipa(equipa.getEstadoEquipa());

        return equipaDto;
    }

    //Verificações Feitas
    public EquipaDto getEquipaById(Long id) {
        Optional<Equipa> equipa = equipaRepository.findById(id);
        if (equipa.isEmpty()) {
            return null;
        }
        return mapToDto(equipa.get());
    }

    //Verificações Feitas
    public EquipaDto getEquipaByNome(String nome) {
        Optional<Equipa> equipa = equipaRepository.findByNome(nome);
        if (equipa.isEmpty()) {
            return null;
        }
        return mapToDto(equipa.get());
    }

    //For Handler use - FH
    public Equipa getEquipaByNomeFH(String nome) {
        Optional<Equipa> equipa = equipaRepository.findByNome(nome);
        if (equipa.isEmpty()) {
            return null;
        }
        return equipa.get();
    }

    //Verificações Feitas
    public EquipaDto updateEquipa(Long id, EquipaDto equipaDto) {

        if(equipaDto.getNome() == null || id == 0) {
            return null;
        }

        Optional<Equipa> equipa = equipaRepository.findById(id);
        if (equipa.isEmpty()) {
            return null;
        }

        else if(equipa.get().getEstadoEquipa() == EstadoEntidade.INATIVO) {
            return null;
        }

        else if(!equipa.get().getNome().equals(equipaDto.getNome())) {
            if(getEquipaByNome(equipaDto.getNome()) != null) {
                return null;
            }
        }

        Equipa updatedEquipa = equipa.get();
        updatedEquipa.setNome(equipaDto.getNome());
        equipaRepository.save(updatedEquipa);
        return mapToDto(updatedEquipa);

    }

    public void addJogadorToEquipa(Jogador jogador, Equipa equipa) {
        equipa.addJogador(jogador);
        equipaRepository.save(equipa);
    }

    public void removeJogadorFromEquipa(Jogador jogador, Equipa equipa) {
        equipa.removeJogador(jogador);
        equipaRepository.save(equipa);
    }

    public List<Equipa> findAllByNome(List<String> equipas) {
        return equipaRepository.findAllByNome(equipas);
    }

    //Verificações Feitas
    public EquipaDto removeJogador(Long id, String nome) {

        if(nome == null) {
            return null;
        }

        Optional<Equipa> equipa = equipaRepository.findById(id);
        if (equipa.isEmpty()) {
            return null;
        }

        else if(equipa.get().getEstadoEquipa() == EstadoEntidade.INATIVO) {
            return null;
        }

        else {
            Equipa updatedEquipa = equipa.get();
            Optional<Utilizador> utilizador = utilizadorHandler.findByNomeAndType(nome, Jogador.class);
            //se Jogador não tiver sido deleted
            if(utilizador.isPresent() && utilizador.get().getEstadoUtilizador() == EstadoEntidade.ATIVO){
                Jogador jogador = (Jogador) utilizador.get();
                jogador.removeEquipa(updatedEquipa);
                removeJogadorFromEquipa(jogador, updatedEquipa);
                return mapToDto(updatedEquipa);
            }

            else {
                return null;
            }
        }
    }

    public void addJogo(Jogo savedJogo, Equipa equipa) {
        equipa.addJogo(savedJogo);
        equipaRepository.save(equipa);
    }

    public List<JogoDto> getAllJogosOfEquipa(Long id) {
        List<Jogo> jogos = equipaRepository.findJogosByEquipaId(id);
        List<JogoDto> jogosDTO = new ArrayList<>();
        for (Jogo jogo : jogos) {
            jogosDTO.add(jogoHandler.createDTO(jogo));
        }
        return jogosDTO;
    }

    //Verificações Feitas
    public ResponseEntity<String> deleteEquipa(Long id) {
        Optional<Equipa> equipa = equipaRepository.findById(id);

        if(equipa.isPresent()){
            Equipa e = equipa.get();
            List<Jogo> jogos = e.getHistoricoJogos();
            for (Jogo jogo : jogos) {
                if(jogo.getStatus() == Status.COMECADO) {
                    return ResponseEntity.status(HttpStatus.PRECONDITION_REQUIRED).body("Equipa ainda tem jogos marcados");
                }
            }

            List<Jogador> jogadores = e.getJogadores();
            for (Jogador jogador : jogadores) {
                utilizadorHandler.removeEquipaFromJogador(jogador, e);
            }

            e.setJogador(null);
            e.setEstadoEquipa(EstadoEntidade.INATIVO);
            equipaRepository.save(e);

            return ResponseEntity.ok("Equipa Eliminada");
        }

        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Equipa não encontrada");
        }
    }

    //PERGUNTAS EXTRAS
    //Equipas com menos de 5 Jogadores
    public String getEquipasWithLessThanFiveJogadores() {
        List<Equipa> equipasMenos5 = equipaRepository.findEquipasWithLessThanFiveJogadores();

        List<String> nomesEquipas = new ArrayList<String>();

        for(Equipa e : equipasMenos5){
            nomesEquipas.add(e.getNome());
        }

        return getNomesEquipasComMenosDeCincoJogadores(nomesEquipas);
    }

    //Equipas com mais cartões vermelhos e amarelos
    public String getEquipasComMaisCartoes() {
        HashMap<String,Integer> equipasCartoes = estatisticasHandler.findEquipasOrderByTotalCartoes();
        return equipasCartoes.toString();
    }

    //Equipas com mais vitórias
    public String getEquipasComMaisVitorias() {
        HashMap<String,Integer> equipasVitorias = jogoHandler.getEquipasComMaisVitorias();
        return equipasVitorias.toString();
    }


    //Funções Auxiliares
    //Ir apenas buscar as Equipas
    public String getNomesEquipasComMenosDeCincoJogadores(List<String> nomes) {

        if (nomes == null || nomes.isEmpty()) {
            return "[]";
        }

        StringBuilder listaNomes = new StringBuilder();

        listaNomes.append("As Equipas com menos de 5 elementos são: ");

        for (int i = 0; i < nomes.size(); i++) {
            listaNomes.append(nomes.get(i));
            if (i < nomes.size() - 1) {
                listaNomes.append(", ");
            }
        }

        return listaNomes.toString();
    }

    //Ir buscar as Equipas com mais cartões
    public String getEquipasComMaisCartoes(List<Object> lista) {

        if (lista == null || lista.isEmpty()) {
            return "Nenhuma equipa encontrada.";
        }

        StringBuilder resultado = new StringBuilder();

        for (Object obj : lista) {
            Object[] linha = (Object[]) obj;
            String nomeEquipa = (String) linha[0];
            Long totalCartoes = (Long) linha[1];

            resultado.append("A equipa ")
                     .append(nomeEquipa)
                     .append(" tem ")
                     .append(totalCartoes)
                     .append(" cartões\n");
        }

        return resultado.toString();
    }

    public List<Equipa> getAllEquipasForExtraUC() {
        List<Equipa> equipas = equipaRepository.findAll();
        return equipas;
    }

    public void addCampeonatoToEquipa(Equipa equipa, CampeonatoPontos campeonato) {
        equipa.addCampeonato(campeonato);
        equipaRepository.save(equipa);
    }

    public void addResultadoToEquipa(Equipa equipa, EstatisticasEquipa estEq) {
        equipa.addResultado(estEq);
        equipaRepository.save(equipa);
    }

    public void removeResultado(Equipa equipa, EstatisticasEquipa estEq) {
        equipa.removeResultado(estEq);
        equipaRepository.save(equipa);
    }

    public void removeCampeonato(Equipa equipa, CampeonatoPontos campeonato) {
        equipa.removeCampeonato(campeonato);
        equipaRepository.save(equipa);
    }

    public void removeCampeonatoAndResultado(Equipa equipa, CampeonatoPontos campeonato, EstatisticasEquipa estEq) {
        equipa.removeResultado(estEq);
        equipa.removeCampeonato(campeonato);
        equipaRepository.save(equipa);
    }

    public List<CampeonatoPontosDto> getAllCampeonatosEquipa(Long id) {
        Optional<Equipa> equipa = equipaRepository.findById(id);
        if (equipa.isEmpty()) {
            return null;
        }

        Equipa e = equipa.get();
        List<Campeonato> campeonatos = e.getCampeonatos();
        List<CampeonatoPontos> campeonatosPontos = new ArrayList<>();
        for (Campeonato c : campeonatos) {
            campeonatosPontos.add((CampeonatoPontos) c);
        }
        List<CampeonatoPontosDto> cDtos = new ArrayList<>();
        for (CampeonatoPontos cp : campeonatosPontos) {
            if(cp.getEstadoCampeonato() == EstadoEntidade.ATIVO) {
                cDtos.add(campeonatoHandler.mapToDto(cp));
            }
        }

        return cDtos;
    }

    public List<EstatisticasEquipaDto> getHistoricoEquipa(Long id) {
        Optional<Equipa> eq = equipaRepository.findById(id);
        if(eq.isPresent()) {
            Equipa e  = eq.get();
            List<EstatisticasEquipa> estEq = e.getHistoricoResultados();
            List<EstatisticasEquipaDto> estEqDtos = new ArrayList<>();
            for (EstatisticasEquipa est : estEq) {
                estEqDtos.add(estatisticasEquipaHandler.mapToDto(est));
            }

            return estEqDtos;
        }
        return null;
    }

    //2 FASE DO PROJETO //////////////////////////////////////////////////////////////////////////////////////////

    public List<Equipa> findEquipasByNomeContainingIgnoreCase(String nome) {
        List<Equipa> equipas = equipaRepository.findEquipasByNomeContainingIgnoreCase(nome);

        List<Equipa> equipasFiltradas = new ArrayList<>(); 
        for(Equipa e : equipas){
            if(e.getEstadoEquipa() == EstadoEntidade.ATIVO){
                equipasFiltradas.add(e);
            }
        }
        return equipasFiltradas;
    }

    public List<Equipa> findEquipasByNumeroJogadores(int numJogadores) {
        return equipaRepository.findEquipasByNumeroJogadores(numJogadores);
    }

    public List<Equipa> findEquipasSemPosicao(String posicao) {
        //Ir buscar todas as equipas
        List<Equipa> equipas = equipaRepository.findAll();
        List<Equipa> equipasFiltradas = new ArrayList<>();
        //Inicializar posição
        Posicao p = Posicao.ATQ;

        if(posicao.equalsIgnoreCase("DEF")){
            p = Posicao.DEF;
        }else if(posicao.equalsIgnoreCase("GK")){
            p = Posicao.GK;
        }else if(posicao.equalsIgnoreCase("MED")){
            p = Posicao.MED;
        }

        boolean temPosicao = false;

        //para cada equipa, ver se algum é daquela posição específica
        for(Equipa e : equipas){
            Equipa eq = (Equipa) e;
            List<Jogador> jogadores = eq.getJogadores();

            if(e.getEstadoEquipa() == EstadoEntidade.ATIVO){
                temPosicao = false;
                for(Jogador j : jogadores){
                    if(j.getPosicao() == p){
                        temPosicao = true;
                    }
                }
                //Se no final ninguém tiver essa posição, acrescenta-se à lista
                if(temPosicao == false){
                    equipasFiltradas.add(eq);
                }
            }    
        }

        return equipasFiltradas;
    }

    public List<Object[]> findEquipasByNumeroVitorias(int numVitorias) {
        return estatisticasEquipaHandler.findEquipasByNumeroVitorias(numVitorias);
    }

    public List<Object[]> findEquipasByNumeroEmpates(int numEmpates) {
        return estatisticasEquipaHandler.findEquipasByNumeroEmpates(numEmpates);
    }

    public List<Object[]> findEquipasByNumeroDerrotas(int numDerrotas) {
        return estatisticasEquipaHandler.findEquipasByNumeroDerrotas(numDerrotas);
    }

    public List<Equipa> findEquipasPorNumeroConquitas(int numConquistas) {
        List<Equipa> equipasFiltradas = new ArrayList<Equipa>();

        List<Equipa> equipas = equipaRepository.findAll();

        //para cada equipa, verificar o numero de jogos
        for (Equipa e : equipas){
              int numJogosEmQueEVencedor = jogoHandler.findEquipasPorNumeroConquitas(e.getNome());
              if(numJogosEmQueEVencedor == numConquistas){
                equipasFiltradas.add(e);
              }
        }

        return equipasFiltradas;
    }

    public List<Equipa> getAllEquipasAtivas() {
        List<Equipa> equipas = equipaRepository.findAll();

        return equipas.stream()
            .filter(e -> e.getEstadoEquipa() == EstadoEntidade.ATIVO)
            .toList();
    }
}
