package pt.ul.fc.css.soccernow.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ul.fc.css.soccernow.dto.EquipaJogoDto;
import pt.ul.fc.css.soccernow.dto.EstatisticasDto;
import pt.ul.fc.css.soccernow.dto.JogoDto;
import pt.ul.fc.css.soccernow.entities.Arbitro;
import pt.ul.fc.css.soccernow.entities.Campeonato;
import pt.ul.fc.css.soccernow.entities.CampeonatoPontos;
//import pt.ul.fc.css.soccernow.entities.Campeonato;
import pt.ul.fc.css.soccernow.entities.CartaoAmarelo;
import pt.ul.fc.css.soccernow.entities.CartaoVermelho;
import pt.ul.fc.css.soccernow.entities.Equipa;
import pt.ul.fc.css.soccernow.entities.EquipaJogo;
import pt.ul.fc.css.soccernow.entities.Estatisticas;
import pt.ul.fc.css.soccernow.entities.Golo;
import pt.ul.fc.css.soccernow.entities.Jogador;
import pt.ul.fc.css.soccernow.entities.Jogo;
import pt.ul.fc.css.soccernow.entities.Utilizador;
import pt.ul.fc.css.soccernow.enums.EstatisticaTipo;
import pt.ul.fc.css.soccernow.enums.Status;
import pt.ul.fc.css.soccernow.repository.JogoRepository;

@Service
public class JogoHandler {

    @Autowired
    private JogoRepository jogoRepository;
    
    @Autowired
    private EquipaHandler equipaHandler;

    @Autowired
    private CampeonatoHandler campeonatoHandler;

    @Autowired
    private EquipaJogoHandler equipaJogoHandler;

    @Autowired
    private UtilizadorHandler utilizadorHandler;

    @Autowired
    private EstatisticasHandler estatisticasHandler;

    //Verificações Feitas
    public JogoDto createJogo(JogoDto jogoDTO){

        if(jogoDTO.getData() == null || jogoDTO.getHorario() == null || jogoDTO.getLocalizacao() == null || jogoDTO.getNomeArbitroPrincipal() == null 
            || jogoDTO.getEquipa1() == null || jogoDTO.getEquipa2() == null || jogoDTO.getEquipa1() == jogoDTO.getEquipa2()) {
            return null;
        }

        Jogo jogo = new Jogo();
        jogo.setData(jogoDTO.getData());
        jogo.setHorario(jogoDTO.getHorario());
        jogo.setLocalizacao(jogoDTO.getLocalizacao());
        if(jogoDTO.getNomeCampeonato() == null) {
            jogo.setCampeonato(null);
        }
        //Confirma que o campeonato existe e tem pelo menos 8 equipas
        else {
            Optional<Campeonato> c  = campeonatoHandler.findByNome(jogoDTO.getNomeCampeonato());
            if(!c.isPresent()) {
                return null;
            }
            else if(c.get().getEquipas().size() >= 8) {
                jogo.setCampeonato((CampeonatoPontos) c.get());
            }
            else {
                return null;
            }
        }
        
        jogo.setEstatisticas(new ArrayList<>());
        
        String nome_Campeonato = jogoDTO.getNomeCampeonato();
        String nome_arbitro = jogoDTO.getNomeArbitroPrincipal();
        List<String> nomes_arbitros = jogoDTO.getNomesArbitros();

        /*Campeonato campeonato = campeonatoHandler.findByName(nome_Campeonato)
            .orElseThrow(() -> new IllegalArgumentException("Equipa não encontrada: " + nome_Campeonato));*/
        

        EquipaJogoDto ejDto1 = jogoDTO.getEquipa1();
        EquipaJogoDto ejDto2 = jogoDTO.getEquipa2();

        //Verifica que não tem jogadores repetidos
        for(int i = 0; i < 5; i++) {
            if(ejDto1.getNomes_jogadores().contains(ejDto2.getNomes_jogadores().get(i))
                || ejDto2.getNomes_jogadores().contains(ejDto1.getNomes_jogadores().get(i))) {
                return null;
            }
        }

        List<EquipaJogo> equipas = new ArrayList<EquipaJogo>();

        EquipaJogo equipa1 = equipaJogoHandler.createEquipaJogo(ejDto1);
        if(equipa1 == null) {
            return null;
        }
        equipas.add(equipa1);
        EquipaJogo equipa2 = equipaJogoHandler.createEquipaJogo(ejDto2);
        if(equipa2 == null) {
            return null;
        }
        equipas.add(equipa2);

        if(equipa1 == null || equipa2 == null) {
            return null;
        }

        if(nome_Campeonato != null) {
            Equipa e1 = equipa1.getEquipa();
            Equipa e2 = equipa2.getEquipa();
            List<Campeonato> e1c = e1.getCampeonatos();
            List<Campeonato> e2c = e2.getCampeonatos();
            Optional<Campeonato> c  = campeonatoHandler.findByNome(nome_Campeonato);
            CampeonatoPontos cp = (CampeonatoPontos) c.get();

            //Confirma que se for Jogo de Campeonato ambas as equipas pertencem ao Campeonato
            if(!e1c.contains(cp) || !e2c.contains(cp)) {
                return null;
            }
        }
            
        Arbitro arbitro = utilizadorHandler.findByArbitroName(nome_arbitro)
            .orElseThrow(() -> new IllegalArgumentException("Arbitro não encontrado: " + nome_arbitro));

        List<Arbitro> arbitros = utilizadorHandler.findByArbitrosNames(nomes_arbitros);

        //Confirma que se for Jogo de Campeonato pelo menos um arbitro é certificado
        Boolean arbitroCertificadoExists = false;
        if(nome_Campeonato != null) {
            for (Arbitro a : arbitros) {
                if(a.getCertificado()) {
                    arbitroCertificadoExists = true;
                }
            }

            if(arbitro.getCertificado()) {
                arbitroCertificadoExists = true;
            }

            if(!arbitroCertificadoExists) {
                return null;
            }
        }

        jogo.setEquipas(equipas);
        jogo.setArbitroPrincipal(arbitro);
        jogo.setArbitros(arbitros);
        jogo.setPlacarFinal(null);
        jogo.setVencedor(null);
        jogo.setStatus(Status.COMECADO);

        Jogo savedJogo = jogoRepository.save(jogo);

        for (EquipaJogo ej : equipas) {
            equipaJogoHandler.updateJogo(ej.getId(), savedJogo);
        }

        equipaHandler.addJogo(savedJogo, equipa1.getEquipa());
        equipaHandler.addJogo(savedJogo, equipa2.getEquipa());

        //JogoDto novoJogoDTO = createDTO(savedJogo);
        jogoDTO.setId(savedJogo.getId());

        return jogoDTO;
    }

    //Verificações Feitas
    public JogoDto getJogoById(Long jogo_Id){
        Optional<Jogo> jogo = jogoRepository.findById(jogo_Id);
        if (jogo.isEmpty()) {
            return null;
        }
        JogoDto novoJogoDTO = createDTO(jogo.get());
        return novoJogoDTO;
    }

    //Verificações Feitas
    public List<JogoDto> getAllJogos(){
        List<Jogo> jogos = jogoRepository.findAll();
        List<JogoDto> jogosDTO = new ArrayList<>();
        for (Jogo jogo : jogos) {
            jogosDTO.add(createDTO(jogo));
        }
        return jogosDTO;
    }

    //Verificações Feitas
    public JogoDto updateJogo(Long jogo_Id, JogoDto jogoDTO){

        if(jogoDTO.getNomeArbitroPrincipal() == null || jogoDTO.getData() == null || jogoDTO.getHorario() == null || jogoDTO.getLocalizacao() == null) {
            return null;
        }

        //String nome_Campeonato = jogoDTO.getNomeCampeonato();
        String nome_arbitro = jogoDTO.getNomeArbitroPrincipal();
        List<String> nomes_arbitros = jogoDTO.getNomesArbitros();

        //Campeonato campeonato = campeonatoHandler.findByName(nome_Campeonato)
            //.orElseThrow(() -> new IllegalArgumentException("Equipa não encontrada: " + nome_Campeonato));
        
        Optional<Utilizador> user = utilizadorHandler.findByNomeAndType(nome_arbitro, Arbitro.class);

        if(!user.isPresent()) {
            return null;
        }

        Arbitro arbitro = (Arbitro) user.get();

        List<Arbitro> arbitros = new ArrayList<>();
        for (String a : nomes_arbitros) {
            Optional<Utilizador> a1 = utilizadorHandler.findByNomeAndType(a, Arbitro.class);
            if(!a1.isPresent()) {
                return null;
            }
            else {
                Arbitro a2 = (Arbitro) a1.get();
                arbitros.add(a2);
            }
        }

        Optional<Jogo> j = jogoRepository.findById(jogo_Id);

        if(!j.isPresent()) {
            return null;
        }

        Jogo jogo = j.get();
        
        //jogo.setCampeonato(campeonato);
        jogo.setArbitroPrincipal(arbitro);
        jogo.setArbitros(arbitros);
        jogo.setData(jogoDTO.getData());
        jogo.setHorario(jogoDTO.getHorario());
        jogo.setLocalizacao(jogoDTO.getLocalizacao());

        
        Jogo savedJogo = jogoRepository.save(jogo);

        return createDTO(savedJogo);
    }

    //Verificações Feitas
    public JogoDto updateFinalScoreJogo(Long jogo_Id, JogoDto jogoDTO, List<EstatisticasDto> estatisticasDto){

        if(jogoDTO.getNomeVencedor() == null || jogoDTO.getPlacarFinal() == null) {
            return null;
        }

        String nome_EquipaVencedora = jogoDTO.getNomeVencedor();
        String placarFinal = jogoDTO.getPlacarFinal();

        Optional<Jogo> jo = jogoRepository.findById(jogo_Id);
        if(!jo.isPresent()) {
            return null;
        }

        Jogo jogo = jo.get();

        if(nome_EquipaVencedora.equals("EMPATE")) {
            jogo.setVencedor(null);
        }
        
        else {
            Optional<Equipa> e = equipaHandler.findByNome(nome_EquipaVencedora);
            if(!e.isPresent()) {
                return null;
            }

            Equipa equipa = e.get();
            jogo.setVencedor(equipa);
        }

        jogo.setPlacarFinal(placarFinal);
        jogo.setStatus(Status.ACABADO);

        List<Estatisticas> e = new ArrayList<>();
        //jogoRepository.save(jogo);

        //Dar update das estatísticas colocadas no Dto para updatar o FinalScore
        if(estatisticasDto != null && !estatisticasDto.isEmpty()) {
            for (EstatisticasDto estastistica : estatisticasDto) {
                if(estastistica.getTipoDeEstatistica() == EstatisticaTipo.GOLO){
                    if(estastistica.getEquipa() != null && estastistica.getJogador() != null) {
                        Jogador j = (Jogador) utilizadorHandler.findByNomeAndType(estastistica.getJogador(), Jogador.class).get();
                        Equipa equipaEstatistica = equipaHandler.getEquipaByNomeFH(estastistica.getEquipa());
                        if(jogo.getCampeonato() != null) {
                            Golo g = estatisticasHandler.createGolo(j, equipaEstatistica, jogo.getCampeonato(), jogo);
                            e.add(g);
                        }
                        else {
                            Golo g = estatisticasHandler.createGolo(j, equipaEstatistica, null, jogo);
                            e.add(g);
                        }
                    }
                }
                else if(estastistica.getTipoDeEstatistica() == EstatisticaTipo.CARTAOAMARELO) {
                    if(estastistica.getEquipa() != null && estastistica.getJogador() != null) {
                        Jogador j = (Jogador) utilizadorHandler.findByNomeAndType(estastistica.getJogador(), Jogador.class).get();
                        Equipa equipaEstatistica = equipaHandler.getEquipaByNomeFH(estastistica.getEquipa());
                        if(jogo.getCampeonato() != null) {
                            CartaoAmarelo ca = estatisticasHandler.createCartaoAmarelo(j, equipaEstatistica, jogo.getCampeonato(), jogo, jogo.getArbitroPrincipal());
                            e.add(ca);
                        }
                        else {
                            CartaoAmarelo ca = estatisticasHandler.createCartaoAmarelo(j, equipaEstatistica, null, jogo, jogo.getArbitroPrincipal());
                            e.add(ca);
                        }
                    }
                }
                else if(estastistica.getTipoDeEstatistica() == EstatisticaTipo.CARTAOVERMELHO) {
                    if(estastistica.getEquipa() != null && estastistica.getJogador() != null) {
                        Jogador j = (Jogador) utilizadorHandler.findByNomeAndType(estastistica.getJogador(), Jogador.class).get();
                        Equipa equipaEstatistica = equipaHandler.getEquipaByNomeFH(estastistica.getEquipa());
                        if(jogo.getCampeonato() != null) {
                            CartaoVermelho cv = estatisticasHandler.createCartaoVermelho(j, equipaEstatistica, jogo.getCampeonato(), jogo, jogo.getArbitroPrincipal());
                            e.add(cv);
                        }
                        else {
                            CartaoVermelho cv = estatisticasHandler.createCartaoVermelho(j, equipaEstatistica, null, jogo, jogo.getArbitroPrincipal());
                            e.add(cv);
                        }
                    }
                }
            }
        }
        jogo.setEstatisticas(e);
        //Jogo savedJogo = jogoRepository.save(jogo);

        //incrementar número de jogos para todos os jogadores das equipasJogo efetivamente em campo
        EquipaJogo ej1 = jogo.getEquipas().get(0);
        List<Jogador> jogadores1 = ej1.getJogadores();
        for (Jogador jogador : jogadores1) {
            utilizadorHandler.incNumJogos(jogador);
        }

        EquipaJogo ej2 = jogo.getEquipas().get(1);
        List<Jogador> jogadores2 = ej2.getJogadores();
        for (Jogador jogador : jogadores2) {
            utilizadorHandler.incNumJogos(jogador);
        }

        //fazer o mesmo para os arbitros
        utilizadorHandler.incNumJogos(jogo.getArbitroPrincipal());
        List<Arbitro> arbitros = jogo.getArbitros();
        for (Arbitro arbitro : arbitros) {
            utilizadorHandler.incNumJogos(arbitro);
        }

        //Se for Jogo de Campeonato adiciona a EstatisticasEquipa mudanças nos atributos baseado no resultado do Jogo
        if(jogo.getCampeonato() != null) {
            System.out.println("I'm here");
            Equipa vencedor = jogo.getVencedor();
            CampeonatoPontos c = (CampeonatoPontos) jogo.getCampeonato();
            Equipa e1 = ej1.getEquipa();
            Equipa e2 = ej2.getEquipa();
            List<Equipa> equipas = new ArrayList<>();
            equipas.add(e1);
            equipas.add(e2);
            if(vencedor == null) {
                for(int i = 0; i < equipas.size(); i++) {
                    campeonatoHandler.registerEmpate(c, equipas.get(i));
                }
            }
            else if(vencedor.getId() == e1.getId()) {
                for(int i = 0; i < equipas.size(); i++) {
                    if(i == 0){
                        campeonatoHandler.registerVitoria(c, equipas.get(i));
                    }
                    else if(i == 1) {
                        campeonatoHandler.registerDerrota(c, equipas.get(i));
                    }
                }
            }
            else if(vencedor.getId() == e2.getId()) {
                for(int i = 0; i < equipas.size(); i++) {
                    if(i == 0){
                        campeonatoHandler.registerDerrota(c, equipas.get(i));
                    }
                    else if(i == 1) {
                        campeonatoHandler.registerVitoria(c, equipas.get(i));
                    }
                }
            }
        }

        jogoRepository.save(jogo);
        JogoDto novoJogoDTO = createDTO(jogo);
        return novoJogoDTO;
    }

    //Verificações Feitas
    public JogoDto cancelJogo(Long jogo_Id) {

        Jogo jogo = jogoRepository.findById(jogo_Id)
            .orElseThrow(() -> new IllegalArgumentException("Jogo com o seguinte id não encontrado: " + jogo_Id));

        if(jogo.getStatus() == Status.COMECADO){
            jogo.setStatus(Status.CANCELADO);
        }else if(jogo.getStatus() == Status.CANCELADO){
            System.out.println("O jogo já foi cancelado e não pode ser cancelado novamente.");
            return null;
        }else{
            System.out.println("O jogo já terminou, logo não pode ser cancelado");
            return null;
        }

        Jogo savedJogo = jogoRepository.save(jogo);

        JogoDto novoJogoDTO = createDTO(savedJogo);

        return novoJogoDTO;
    }

    //Passar para Dto um Jogo
    public JogoDto createDTO(Jogo jogo){
        JogoDto jogoDTO = new JogoDto();
        jogoDTO.setId(jogo.getId());
        jogoDTO.setData(jogo.getData());
        jogoDTO.setHorario(jogo.getHorario());
        jogoDTO.setLocalizacao(jogo.getLocalizacao());

        if(jogo.getCampeonato() != null){
            jogoDTO.setNomeCampeonato(jogo.getCampeonato().getNome());
        }else{
            jogoDTO.setNomeCampeonato(null);
        }
        List<EquipaJogo> lista = jogo.getEquipas();
        EquipaJogoDto equipaJ1 = equipaJogoHandler.mapToDto(lista.get(0));
        jogoDTO.setEquipa1(equipaJ1);

        EquipaJogoDto equipaJ2 = equipaJogoHandler.mapToDto(lista.get(1));
        jogoDTO.setEquipa2(equipaJ2);

        List<Estatisticas> estatisticas = jogo.getEstatisticas();
        List<EstatisticasDto> estatisticasDtos = new ArrayList<>();
        for (Estatisticas estatistica : estatisticas) {
            estatisticasDtos.add(estatisticasHandler.mapToDto(estatistica));
        }

        jogoDTO.setEstatisticas(estatisticasDtos);
        if(jogo.getArbitroPrincipal() != null){
            jogoDTO.setNomeArbitroPrincipal(jogo.getArbitroPrincipal().getNome());
        } else {
            jogoDTO.setNomeArbitroPrincipal(null);
        }
        
        List<String> arbitros = new ArrayList<>();
        for (Arbitro arbitro: jogo.getArbitros()) {
            arbitros.add(arbitro.getNome());
        }

        jogoDTO.setNomesArbitros(arbitros);

        if (jogo.getPlacarFinal() != null) {
            jogoDTO.setPlacarFinal(jogo.getPlacarFinal());
        }else{
            jogoDTO.setPlacarFinal(null);
        }

        if(jogo.getVencedor() != null){
            jogoDTO.setNomeVencedor(jogo.getVencedor().getNome());
        }else{
            jogoDTO.setNomeVencedor(null);
        }
        
        jogoDTO.setStatus(jogo.getStatus());

        return jogoDTO;
    }

    //Verificações Feitas
    public List<EstatisticasDto> getAllEstatisticasJogo(Long id) {
        List<Estatisticas> estatisticas = jogoRepository.findEstatisticasByJogoId(id);
        List<EstatisticasDto> estatisticasDto = new ArrayList<>();
        for (Estatisticas estatistica : estatisticas) {
            estatisticasDto.add(estatisticasHandler.mapToDto(estatistica));
        }
        return estatisticasDto;
    }

    public void updateVencedor(Jogo jogo) {
        jogoRepository.save(jogo);
    }

    //PERGUNTAS EXTRA
    //Ir buscar as equipas com mais vitórias
    public HashMap<String, Integer> getEquipasComMaisVitorias() {
        List<Equipa> equipas = equipaHandler.getAllEquipasForExtraUC();
        List<Jogo> jogos = jogoRepository.findAll();

        //meter no hashmap logo os nomes das equipas antes das contagens
        HashMap<String, Integer> equipasVitorias = new HashMap<>();
        for (Equipa equipa : equipas) {
            equipasVitorias.put(equipa.getNome(), 0);
        }

        // Incrementar contagem das equipas caso sejam vencedoras
        for (Jogo jogo : jogos) {
            Equipa vencedora = jogo.getVencedor();
            if (vencedora != null) {
                String nomeVencedora = vencedora.getNome();
                int n = equipasVitorias.get(nomeVencedora);
                equipasVitorias.put(nomeVencedora, n + 1);
            }
        }

        return equipasVitorias;
    }

    public List<Jogo> getAllJogosForExtraUC(){
        return jogoRepository.findAll();
    }

    public void updateArbitro(Jogo j){
        jogoRepository.save(j);
    }

    public List<Jogo> getJogosByCampeonato(CampeonatoPontos c) {
        List<Jogo> jogos = jogoRepository.findAll();
        List<Jogo> jogosCampeonato = new ArrayList<>();
        for (Jogo j : jogos) {
            if(j.getCampeonato() != null) {
                if(j.getCampeonato().getId() == c.getId()) {
                    jogosCampeonato.add(j);
                }
            }
        }

        return jogosCampeonato;
    }

    //2 FASE DO PROJETO///////////////////////////////////////////////////////////////////////////////////////////

    public List<Jogo> findJogosRealizados() {
        return jogoRepository.findJogosAcabados();
    }

    public List<Jogo> findJogosPorRealizar() {
        return jogoRepository.findJogosPorRealizar();
    }

    public List<Jogo> findJogosByTotalGolos(int totalGolos) {
        List<Jogo> jogos = jogoRepository.findAll();
        List<Jogo> jogosFiltrados = new ArrayList<>();

        int cont = 0;
        //ver nos jogos, quantos golos é que houveram em cada um
        for(Jogo j : jogos){
            List<Estatisticas> ests = j.getEstatisticas();
            cont = 0;
            for(Estatisticas e : ests){
                if(e instanceof Golo){
                    cont++;
                }
            }
            if(cont == totalGolos){
                jogosFiltrados.add(j);
            }
        }
        
        return jogosFiltrados;
    }

    public List<Jogo> findJogosByLocalizacao(String localizacao) {
        return jogoRepository.findJogosByLocalizacao(localizacao);
    }

    public List<Jogo> findJogosPorTurno(String turno) {
        List<Jogo> jogos = jogoRepository.findAll();
        int inicio = 0;
        int fim = 0;
        List<Jogo> jogosFiltrados = new ArrayList<>();
        
        //Dependendo do turno do jogo
        switch (turno.toLowerCase()) {
            case "manha":
                inicio = 6;
                fim = 11;
                break;
            case "tarde":
                inicio = 12;
                fim = 17;
                break;
            case "noite":
                inicio = 18;
                fim = 23;
                break;
            default:
                throw new IllegalArgumentException("Turno inválido");
        }

        //ver quais jogos correspondem a estas horas
        for(Jogo j : jogos){
            String horas = j.getHorario().toLocalTime().toString();
            String h = horas.substring(0,2);

            if(Integer.parseInt(h)<=fim && Integer.parseInt(h)>=inicio){
                jogosFiltrados.add(j);
            }

        }

        return jogosFiltrados;
    }

    public List<Jogo> findJogosRealizadosPorCampeonato() {
        return jogoRepository.findJogosAcabados();
    }

    public List<Jogo> findJogosPorRealizarPorCampeonato() {
       return jogoRepository.findJogosPorRealizar();
    }

    public int findEquipasPorNumeroConquitas(String nomeEquipa) {
        return jogoRepository.countByVencedor_Nome(nomeEquipa);
    }


}
