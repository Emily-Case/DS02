package pt.ul.fc.css.soccernow.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import pt.ul.fc.css.soccernow.dto.ArbitroDto;
import pt.ul.fc.css.soccernow.dto.JogadorDto;
import pt.ul.fc.css.soccernow.entities.Arbitro;
import pt.ul.fc.css.soccernow.entities.Equipa;
import pt.ul.fc.css.soccernow.entities.Jogador;
import pt.ul.fc.css.soccernow.entities.Utilizador;
import pt.ul.fc.css.soccernow.enums.EstadoEntidade;
import pt.ul.fc.css.soccernow.enums.Posicao;
import pt.ul.fc.css.soccernow.repository.UtilizadorRepository;

@Service
public class UtilizadorHandler {

    @Autowired
    private UtilizadorRepository utilizadorRepository;

    @Autowired
    private EquipaHandler equipaHandler;

    @Autowired
    private EstatisticasHandler estatisticasHandler;

    @Autowired @Lazy
    private JogoHandler jogoHandler;

    //Verificações Feitas
    public Optional<Utilizador> findByNomeAndType(String nome, Class<? extends Utilizador> type) {
        return utilizadorRepository.findByNomeAndType(nome, type);
    }

    //Verificações Feitas
    public JogadorDto createJogador(JogadorDto jogadorDto){

        if(jogadorDto.getNif() == 0 || jogadorDto.getNome() == null || jogadorDto.getPassword() == null || jogadorDto.getPosicao() == null) {
            return null;
        }

        Jogador jogador = new Jogador();
        jogador.setNif(jogadorDto.getNif());
        jogador.setNome(jogadorDto.getNome());
        jogador.setPassword(jogadorDto.getPassword());
        jogador.setNumJogos(jogadorDto.getNumJogos());
        jogador.setPosicao(jogadorDto.getPosicao());
        jogador.setEstadoUtilizador(EstadoEntidade.ATIVO);

        //Ciclo que não só associa o Jogador ao nome das Equipas onde está mas também adiciona Jogador do lado da equipa
        if (jogadorDto.getNomesEquipas() != null && !jogadorDto.getNomesEquipas().isEmpty()) {
            List<Equipa> equipas = equipaHandler.findAllByNome(jogadorDto.getNomesEquipas());
            jogador.setEquipas(equipas);
            for(int i = 0; i < equipas.size(); i++) {
                equipaHandler.addJogadorToEquipa(jogador, equipas.get(i));
            }
        } else if (jogadorDto.getNomesEquipas().isEmpty()){
            jogador.setEquipas(new ArrayList<>());
        }

        Jogador savedJogador = utilizadorRepository.save(jogador);

        //Fazer com que no responseBody não apareçam equipas que não existem
        //JogadorDto updatedDto = transformIntoDTO(savedJogador);
        jogadorDto.setId(savedJogador.getId());
        return jogadorDto;

    }

    //Verificações Feitas
    public ArbitroDto createArbitro(ArbitroDto arbitroDto){

        if(arbitroDto.getNif() == 0 || arbitroDto.getNome() == null || arbitroDto.getPassword() == null || arbitroDto.getCertificado() == null) {
            return null;
        }

        Arbitro arbitro = new Arbitro();
        arbitro.setNif(arbitroDto.getNif());
        arbitro.setNome(arbitroDto.getNome());
        arbitro.setPassword(arbitroDto.getPassword());
        arbitro.setNumJogos(arbitroDto.getNumJogos());
        arbitro.setCertificado(arbitroDto.getCertificado());
        arbitro.setEstadoUtilizador(EstadoEntidade.ATIVO); //inicializa Arbitro como ativo e não deleted

        Arbitro savedArbitro = utilizadorRepository.save(arbitro);

        arbitroDto.setId(savedArbitro.getId());
        return arbitroDto;

    }

    //Verificações Feitas
    public ArbitroDto findArbitroById(Long id){
        Optional<Utilizador> utilizador = utilizadorRepository.findById(id);

        if (utilizador.isPresent() && utilizador.get() instanceof Arbitro) {
            Arbitro arbitro = (Arbitro) utilizador.get();
            return transformIntoArbitroDTO(arbitro);
        }

        throw new EntityNotFoundException("O Arbitro com Id " + id + " não foi encontrado.");
    }

    //Verificações Feitas
    public JogadorDto findJogadorById(Long id){
        Optional<Utilizador> utilizador = utilizadorRepository.findById(id);

        if (utilizador.isPresent() && utilizador.get() instanceof Jogador) {
            Jogador jogador = (Jogador) utilizador.get();
            return transformIntoDTO(jogador);
        }

        throw new EntityNotFoundException("O Jogador com Id " + id + " não foi encontrado.");
    }

    public JogadorDto findJogadorByName(String name){
        Optional<Utilizador> utilizador = utilizadorRepository.findByNomeAndType(name, Jogador.class);

        if (utilizador.isPresent() && utilizador.get() instanceof Jogador) {
            Jogador jogador = (Jogador) utilizador.get();
            return transformIntoDTO(jogador);
        }

        throw new EntityNotFoundException("O Jogador com nome " + name + " não foi encontrado.");
    }

    //Verificações Feitas
    public Optional<JogadorDto> updateJogador(Long id, JogadorDto dto) {

        if(dto.getNome() == null || dto.getPassword() == null || dto.getPosicao() == null) {
            return Optional.empty();
        }

        Optional<Utilizador> utilizador = utilizadorRepository.findById(id);

        if (utilizador.isPresent() && utilizador.get() instanceof Jogador && utilizador.get().getEstadoUtilizador() == EstadoEntidade.ATIVO) {
            Jogador jogador = (Jogador) utilizador.get();

            jogador.setNome(dto.getNome());
            jogador.setPassword(dto.getPassword());
            jogador.setNumJogos(dto.getNumJogos());
            jogador.setPosicao(dto.getPosicao());

            if (dto.getNomesEquipas() != null && !dto.getNomesEquipas().isEmpty()) {
                List<String> nomesAtuaisEquipas = jogador.getEquipas()
                        .stream()
                        .map(Equipa::getNome)
                        .toList();

                List<String> nomesNovosEquipas = dto.getNomesEquipas();

                //IR AO EQUIPAHANDLER REMOVER O JOGADOR DAS DETERMINADAS EQUIPAS
                List<Equipa> equipasRemover = equipaHandler.findAllByNome(nomesAtuaisEquipas);
                for(int i = 0; i < equipasRemover.size(); i++) {
                    equipaHandler.removeJogadorFromEquipa(jogador, equipasRemover.get(i));
                }

                List<Equipa> equipas = equipaHandler.findAllByNome(nomesNovosEquipas);
                jogador.setEquipas(equipas);
                for(int i = 0; i < equipas.size(); i++) {
                    equipaHandler.addJogadorToEquipa(jogador, equipas.get(i));
                }
            }


            Jogador j = utilizadorRepository.save(jogador);

            JogadorDto jA = transformIntoDTO(j);

            return Optional.of(jA);
        }

        return Optional.empty();
    }

    //Verificações Feitas
    public Optional<ArbitroDto> updateArbitro(Long id, ArbitroDto dto) {

        if(dto.getNome() == null || dto.getPassword() == null || dto.getCertificado() == null) {
            return Optional.empty();
        }

        Optional<Utilizador> utilizador = utilizadorRepository.findById(id);

        if (utilizador.isPresent() && utilizador.get() instanceof Arbitro && utilizador.get().getEstadoUtilizador() == EstadoEntidade.ATIVO) {
            Arbitro arbitro = (Arbitro) utilizador.get();

            arbitro.setNome(dto.getNome());
            arbitro.setPassword(dto.getPassword());
            arbitro.setNumJogos(dto.getNumJogos());
            arbitro.setCertificado(dto.getCertificado());

            Arbitro a = utilizadorRepository.save(arbitro);

            ArbitroDto aA = transformIntoArbitroDTO(a);

            return Optional.of(aA);

        }
        return Optional.empty();
    }

    //Verificações Feitas
    public Optional<JogadorDto> addEquipa(Long id, String nomeEquipa) {

        if(nomeEquipa == null) {
            return Optional.empty();
        }

        Optional<Utilizador> jogador = utilizadorRepository.findById(id);

        if (jogador.isPresent() && jogador.get() instanceof Jogador && jogador.get().getEstadoUtilizador() == EstadoEntidade.ATIVO) {
            Jogador j = (Jogador) jogador.get();

            Optional<Equipa> equipa = equipaHandler.findByNome(nomeEquipa);

            if (equipa.isEmpty()){
                return Optional.empty();
            }

            Equipa e = equipa.get();

            Boolean alreadyInEquipa = j.getEquipas().contains(e);
            if (!alreadyInEquipa) {
                j.addEquipa(e);
            }
            else {
                return Optional.empty();
            }

            Jogador updatedJ = utilizadorRepository.save(j);

            //IR AO EQUIPAHANDLER ADICIONAR O JOGADOR DA EQUIPA NOMEEQUIPA: FEITO
            equipaHandler.addJogadorToEquipa(updatedJ, e);


            return Optional.of(transformIntoDTO(updatedJ));
        }

        return Optional.empty();
    }

    //Verificações Feitas
    public Optional<JogadorDto> removeEquipa(Long id, String nomeEquipa) {

        if(nomeEquipa == null) {
            return Optional.empty();
        }

        Optional<Utilizador> jogador = utilizadorRepository.findById(id);

        if (jogador.isPresent() && jogador.get() instanceof Jogador && jogador.get().getEstadoUtilizador() == EstadoEntidade.ATIVO) {
            Jogador j = (Jogador) jogador.get();

            Optional<Equipa> equipa = equipaHandler.findByNome(nomeEquipa);

            if (equipa.isEmpty()) {
                return Optional.empty();
            }

            Equipa e = equipa.get();

            if (j.getEquipas().contains(e)) {
                j.removeEquipa(e);
            }

            Jogador updatedJ = utilizadorRepository.save(j);

            //IR AO EQUIPAHANDLER REMOVER O JOGADOR DA EQUIPA NOMEEQUIPA: FEITO
            equipaHandler.removeJogadorFromEquipa(updatedJ, e);

            return Optional.of(transformIntoDTO(updatedJ));
        }

        return Optional.empty();
    }

    //Verificações Feitas
    public List<ArbitroDto> getAllArbitros() {
        List<Utilizador> utilizadores = utilizadorRepository.findAll();

        return utilizadores.stream()
            .filter(u -> u instanceof Arbitro)
            .map(u -> transformIntoArbitroDTO((Arbitro) u))
            .toList();
    }

    //Verificações Feitas
    public List<JogadorDto> getAllJogadores() {
        List<Utilizador> utilizadores = utilizadorRepository.findAll();

        return utilizadores.stream()
            .filter(u -> u instanceof Jogador)
            .map(u -> transformIntoDTO((Jogador) u))
            .toList();
    }

    public boolean deleteJogador(Long id) {
        Optional<Utilizador> utilizador = utilizadorRepository.findById(id);

        if (utilizador.isPresent() && utilizador.get() instanceof Jogador) {

            Jogador jogador = (Jogador) utilizador.get();
            List<Equipa> equipas = jogador.getEquipas();
            //ver em que equipas tem de remover o Jogador
            for(int i = 0; i < equipas.size(); i++) {
                equipaHandler.removeJogadorFromEquipa(jogador, equipas.get(i));
            }

            jogador.setEquipas(null);
            jogador.setEstadoUtilizador(EstadoEntidade.INATIVO);

            utilizadorRepository.save(jogador);
            return true;
        }

        return false;
    }

    public boolean deleteArbitro(Long id) {
        Optional<Utilizador> utilizador = utilizadorRepository.findById(id);

        if (utilizador.isPresent() && utilizador.get() instanceof Arbitro) {

            Arbitro a = (Arbitro) utilizador.get();
            a.setEstadoUtilizador(EstadoEntidade.INATIVO);

            utilizadorRepository.save(a);
            return true;
        }

        return false;
    }

    //Funções para encontrar por nome

    public Optional<Utilizador> findByJogadorName(String nome) {
        return utilizadorRepository.findByNomeAndType(nome, Jogador.class);
    }

    public Optional<Arbitro> findByArbitroName(String nome_arbitro) {
        Optional<Utilizador> utilizador = utilizadorRepository.findByNome(nome_arbitro);

        if (utilizador.isPresent() && utilizador.get() instanceof Arbitro) {
            return Optional.of((Arbitro) utilizador.get());
        }

        return Optional.empty();
    }

    public List<Arbitro> findByArbitrosNames(List<String> nomes_arbitros) {
        List<Utilizador> utilizadores = utilizadorRepository.findByNomeIn(nomes_arbitros);

        return utilizadores.stream()
            .filter(u -> u instanceof Arbitro)
            .map(u -> (Arbitro) u)
            .toList();
    }

    //PERGUNTA EXTRA
    //Ir buscar Árbitro com mais Jogos
    public Optional<ArbitroDto> getArbitroComMaisJogos() {
        Optional<Utilizador> utilizador = utilizadorRepository.findTopByOrderByNumJogosDesc();

        if (utilizador.isPresent() && utilizador.get() instanceof Arbitro) {
            Arbitro arbitro = (Arbitro) utilizador.get();
            ArbitroDto dto = transformIntoArbitroDTO(arbitro);
            return Optional.of(dto);
        }

        return Optional.empty();
    }

    //Mapear para Dto um determinado Jogador
    public static JogadorDto transformIntoDTO(Jogador jogador) {
        JogadorDto dto = new JogadorDto();

        dto.setId(jogador.getId());
        dto.setNif(jogador.getNif());
        dto.setNome(jogador.getNome());
        dto.setPassword(jogador.getPassword());
        dto.setNumJogos(jogador.getNumJogos());
        dto.setPosicao(jogador.getPosicao());
        dto.setEstadoJogador(jogador.getEstadoUtilizador());

        if (jogador.getEquipas() != null && !jogador.getEquipas().isEmpty()) {
            List<String> nomesEquipas = jogador.getEquipas().stream()
                .map(Equipa::getNome)
                .collect(Collectors.toList());
            dto.setNomesEquipas(nomesEquipas);
        }

        return dto;
    }

    //Mapear para Dto um determinado Árbitro
    public static ArbitroDto transformIntoArbitroDTO(Arbitro arbitro){
        ArbitroDto dto = new ArbitroDto();

        dto.setId(arbitro.getId());
        dto.setNif(arbitro.getNif());
        dto.setNome(arbitro.getNome());
        dto.setPassword(arbitro.getPassword());
        dto.setNumJogos(arbitro.getNumJogos());
        dto.setCertificado(arbitro.getCertificado());
        dto.setEstadoArbitro(arbitro.getEstadoUtilizador());

        return dto;
    }

    public void addEquipaToJogador(Jogador jogador, Equipa equipa) {
        jogador.addEquipa(equipa);
        utilizadorRepository.save(jogador);
    }

    public void removeEquipaFromJogador(Jogador jogador, Equipa equipa) {
        jogador.removeEquipa(equipa);
        utilizadorRepository.save(jogador);
    }

    public void incNumJogos(Utilizador utilizador) {
        utilizador.incNumJogos();
        utilizadorRepository.save(utilizador);
    }

    public Optional<Utilizador> findByNif(int nif) {
        return utilizadorRepository.findByNif(nif);
    }


    //PERGUNTA EXTRA
    //Encontrar os Jogadores com mais cartões vermelhos
    public String getJogadoresWithMoreCartoesVermelhos(){
        List<Utilizador> utilizadores = utilizadorRepository.findAll();

        HashMap<String,Integer> map = estatisticasHandler.getJogadoresWithMoreCartoesVermelhos(utilizadores);

        List<Map.Entry<String, Integer>> ordenado = new ArrayList<>(map.entrySet());
        ordenado.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue())); // ordem decrescente

        // Construir a string com os top 5
        StringBuilder resultado = new StringBuilder();
        int limite = Math.min(5, ordenado.size()); // caso haja menos de 5 jogadores

        for (int i = 0; i < limite; i++) {
            Map.Entry<String, Integer> entry = ordenado.get(i);
            resultado.append(entry.getKey())
                    .append(" - ")
                    .append(entry.getValue())
                    .append(" cartões vermelhos\n");
        }

        return resultado.toString();
    }

    public String getAverageGolosOfJogadores(String nome){
        List<Utilizador> jogadores = utilizadorRepository.findAll().stream()
                                                            .filter(u -> u instanceof Jogador)
                                                            .filter(u -> u.getNome().contains(nome)).toList();

        HashMap<String,Integer> map = estatisticasHandler.getAverageGolosOfJogadores(jogadores);

        return map.toString();
    }

    //2 FASE DO PROJETO /////////////////////////////////////////////////////////////////////////////////////////

    public List<Jogador> findJogadoresByNomeContainingIgnoreCase(String nome) {
        List<Jogador> jogadores = utilizadorRepository.findJogadoresByNomeContainingIgnoreCase(nome);

        List<Jogador> jogadoresFiltrados = new ArrayList<>(); 
        for(Jogador j : jogadores){
            if(j.getEstadoUtilizador() == EstadoEntidade.ATIVO){
                jogadoresFiltrados.add(j);
            }
        }
        return jogadoresFiltrados;
    }

    public List<Jogador> findJogadoresByPosicao(String posicao) {
        List<Utilizador> jogadores = utilizadorRepository.findAllByType(Jogador.class);
        List<Jogador> jogadoresFiltrados = new ArrayList<>();
        Posicao p = Posicao.ATQ;

        if(posicao.equalsIgnoreCase("DEF")){
            p = Posicao.DEF;
        }else if(posicao.equalsIgnoreCase("GK")){
            p = Posicao.GK;
        }else if(posicao.equalsIgnoreCase("MED")){
            p = Posicao.MED;
        }

        //ver quais os jogadores que têm aquela posição
        for(Utilizador u : jogadores){
            Jogador j = (Jogador) u;
            if (j.getPosicao() == p && j.getEstadoUtilizador() == EstadoEntidade.ATIVO){
                jogadoresFiltrados.add(j);
            }
        }

        return jogadoresFiltrados;
    }

    public List<Jogador> findJogadoresByNumJogos(int numJogos) {
        List<Utilizador> utilizadores = utilizadorRepository.findAllByType(Jogador.class);
        List<Jogador> jogadoresFiltrados = new ArrayList<>();

        //ver quais os jogadores que têm aquela posição
        for (Utilizador u : utilizadores) {
            Jogador j = (Jogador) u;
            if (j.getNumJogos() == numJogos && j.getEstadoUtilizador() == EstadoEntidade.ATIVO) {
                jogadoresFiltrados.add(j);
            }
        }

        return jogadoresFiltrados;
    }

    public List<Jogador> findJogadoresByNumGolos(int numGolos) {

        //Ir buscar todos os utilizadores e filtrar apenas os jogadores
        List<Utilizador> utilizadores = utilizadorRepository.findAllByType(Jogador.class);
        List<Jogador> jogadores = new ArrayList<Jogador>();

        for (Utilizador u : utilizadores){
            Jogador ar = (Jogador) u;
            jogadores.add(ar);
        }

        HashMap<Jogador,Integer> jogadoresValores = estatisticasHandler.findJogadoresByNumGolosMarcados(numGolos, jogadores);

        List<Jogador> jogadoresComNumCartoes = new ArrayList<Jogador>();

        for (Jogador j : jogadores){
            if(!jogadoresValores.isEmpty()){
                if(jogadoresValores.containsKey(j) && jogadoresValores.get(j) == numGolos && j.getEstadoUtilizador() == EstadoEntidade.ATIVO){
                    jogadoresComNumCartoes.add(j);
                }
            }
        }

        return jogadoresComNumCartoes;
    }

    public List<Jogador> findJogadoresByNumCartoes(int numCartoes) {
        //Ir buscar todos os utilizadores e filtrar apenas os jogadores
        List<Utilizador> utilizadores = utilizadorRepository.findAllByType(Jogador.class);
        List<Jogador> jogadores = new ArrayList<Jogador>();

        for (Utilizador u : utilizadores){
                Jogador jr = (Jogador) u;
                jogadores.add(jr);
        }

        HashMap<Jogador,Integer> jogadoresValores = estatisticasHandler.findJogadoresByNumCartoesMostrados(numCartoes, jogadores);

        List<Jogador> jogadoresComNumCartoes = new ArrayList<Jogador>();

        for (Jogador j : jogadores){
            if(!jogadoresValores.isEmpty()){
                if(jogadoresValores.containsKey(j) && jogadoresValores.get(j) == numCartoes && j.getEstadoUtilizador() == EstadoEntidade.ATIVO){
                    jogadoresComNumCartoes.add(j);
                }
            }
        }

        return jogadoresComNumCartoes;
    }

    public List<Arbitro> findArbitrosByNomeContainingIgnoreCase(String nome) {
        List<Arbitro> arbitros = utilizadorRepository.findArbitrosByNomeContainingIgnoreCase(nome);

        List<Arbitro> arbitrosFiltrados = new ArrayList<>(); 
        for(Arbitro a : arbitros){
            if(a.getEstadoUtilizador() == EstadoEntidade.ATIVO){
                arbitrosFiltrados.add(a);
            }
        }

        return arbitrosFiltrados;
    }

    public List<Arbitro> findArbitrosByNumJogosOficiados(int numJogos) {
        //Ir buscar todos os utilizadores e filtrar apenas os arbitros
        List<Utilizador> arbitros= utilizadorRepository.findAllByType(Arbitro.class);
        List<Arbitro> arbitrosFiltrados = new ArrayList<>();

        //ver quais os árbitros com o numero de jogos numJogos
        for (Utilizador u : arbitros) {
            Arbitro arbitro = (Arbitro) u;
            if (arbitro.getNumJogos() == numJogos && arbitro.getEstadoUtilizador() == EstadoEntidade.ATIVO) {
                arbitrosFiltrados.add(arbitro);
            }
        }

        return arbitrosFiltrados;
    }

    public List<Arbitro> findArbitrosByNumCartoesMostrados(int numCartoes) {

        //Ir buscar todos os utilizadores e filtrar apenas os arbitros
        List<Utilizador> utilizadores = utilizadorRepository.findAllByType(Arbitro.class);
        List<Arbitro> arbitros = new ArrayList<Arbitro>();

        for (Utilizador u : utilizadores){
                Arbitro ar = (Arbitro) u;
                arbitros.add(ar);
        }

        HashMap<Arbitro,Integer> arbitrosValores = estatisticasHandler.findArbitrosByNumCartoesMostrados(numCartoes, arbitros);

        List<Arbitro> arbitrosComNumCartoes = new ArrayList<Arbitro>();

        for (Arbitro a : arbitros){
            if(!arbitrosValores.isEmpty()){
                if(arbitrosValores.containsKey(a) && arbitrosValores.get(a) == numCartoes){
                    arbitrosComNumCartoes.add(a);
                }
            }
        }

        return arbitrosComNumCartoes;
    }

    public Optional<Utilizador> findByNif(String nif) {
        return utilizadorRepository.findByNif(Integer.parseInt(nif));
    }

    public boolean deleteJogadorByNif(int nif) {
        Optional<Utilizador> utilizador = utilizadorRepository.findByNif(nif);

        if (utilizador.isPresent() && utilizador.get() instanceof Jogador) {

            Jogador jogador = (Jogador) utilizador.get();
            List<Equipa> equipas = jogador.getEquipas();
            //ver em que equipas tem de remover o Jogador
            for(int i = 0; i < equipas.size(); i++) {
                equipaHandler.removeJogadorFromEquipa(jogador, equipas.get(i));
            }

            jogador.setEquipas(null);
            jogador.setEstadoUtilizador(EstadoEntidade.INATIVO);

            utilizadorRepository.save(jogador);
            return true;
        }

        return false;
    }

    public boolean deleteArbitroByNif(int nif) {
       Optional<Utilizador> utilizador = utilizadorRepository.findByNif(nif);

        if (utilizador.isPresent() && utilizador.get() instanceof Arbitro) {

            Arbitro a = (Arbitro) utilizador.get();
            a.setEstadoUtilizador(EstadoEntidade.INATIVO);

            utilizadorRepository.save(a);
            return true;
        }

        return false;
    }

    public List<Jogador> getAllJogadoresAtivos() {
        List<Utilizador> utilizadores = utilizadorRepository.findAll();

        return utilizadores.stream()
            .filter(u -> u instanceof Jogador)
            .map(u -> (Jogador) u)
            .filter(j -> j.getEstadoUtilizador() == EstadoEntidade.ATIVO)
            .toList();
    }

}
