package pt.ul.fc.css.soccernow.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ul.fc.css.soccernow.dto.EstatisticasDto;
import pt.ul.fc.css.soccernow.entities.Arbitro;
import pt.ul.fc.css.soccernow.entities.Campeonato;
import pt.ul.fc.css.soccernow.entities.CampeonatoPontos;
import pt.ul.fc.css.soccernow.entities.CartaoAmarelo;
import pt.ul.fc.css.soccernow.entities.CartaoVermelho;
import pt.ul.fc.css.soccernow.entities.Equipa;
import pt.ul.fc.css.soccernow.entities.Estatisticas;
import pt.ul.fc.css.soccernow.entities.Golo;
import pt.ul.fc.css.soccernow.entities.Jogador;
import pt.ul.fc.css.soccernow.entities.Jogo;
import pt.ul.fc.css.soccernow.entities.Utilizador;
import pt.ul.fc.css.soccernow.enums.EstatisticaTipo;
import pt.ul.fc.css.soccernow.repository.EstatisticasRepository;


@Service
public class EstatisticasHandler {

    @Autowired
    private EstatisticasRepository estatisticasRepository;

    @Autowired
    private EquipaHandler equipaHandler;

    @Autowired
    private CampeonatoHandler campeonatoHandler;

    //Criar um golo para o repositório
    public Golo createGolo(Jogador j, Equipa equipa, Campeonato campeonato, Jogo jogo) {
        Golo g = new Golo();
        g.setJogador(j);
        g.setEquipa(equipa);
        g.setCampeonato(campeonato);
        g.setJogo(jogo);
        estatisticasRepository.save(g);

        if(campeonato != null) {
            //adicionar estatistica à linha de EstatisticasEquipa de Equipa no campeonato
            campeonatoHandler.addEstatistica((CampeonatoPontos) campeonato, equipa, g);
        }

        return g;
    }

    //Criar um cartao amarelo para o repositório
    public CartaoAmarelo createCartaoAmarelo(Jogador j, Equipa equipa, Campeonato campeonato, Jogo jogo, Arbitro a) {
        CartaoAmarelo ca = new CartaoAmarelo();
        ca.setJogador(j);
        ca.setEquipa(equipa);
        ca.setCampeonato(campeonato);
        ca.setJogo(jogo);
        ca.setArbitro(a);
        estatisticasRepository.save(ca);

        if(campeonato != null) {
            //adicionar estatistica à linha de EstatisticasEquipa de Equipa no campeonato
            campeonatoHandler.addEstatistica((CampeonatoPontos) campeonato, equipa, ca);
        }

        return ca;
    }

    //Criar um cartao vermelho para o repositório
    public CartaoVermelho createCartaoVermelho(Jogador j, Equipa equipa, Campeonato campeonato, Jogo jogo, Arbitro a) {
        CartaoVermelho cv = new CartaoVermelho();
        cv.setJogador(j);
        cv.setEquipa(equipa);
        cv.setCampeonato(campeonato);
        cv.setJogo(jogo);
        cv.setArbitro(a);
        estatisticasRepository.save(cv);

        if(campeonato != null) {
            //adicionar estatistica à linha de EstatisticasEquipa de Equipa no campeonato
            campeonatoHandler.addEstatistica((CampeonatoPontos) campeonato, equipa, cv);
        }

        return cv;
    }

    public List<EstatisticasDto> getAllEstatisticasEquipa(Long id) {
        List<Estatisticas> estatisticas = estatisticasRepository.findEstatisticasByEquipaId(id);
        return estatisticas.stream().map(this::mapToDto).toList();
    }

    //Passar qualquer tipo de Estatistica para um Dto
    public EstatisticasDto mapToDto(Estatisticas estatistica) {
        EstatisticasDto estatisticasDto = new EstatisticasDto();
        estatisticasDto.setId(estatistica.getId());
        if(estatistica.getCampeonato() == null)  {
            estatisticasDto.setCampeonato(null);
        }
        else {
            estatisticasDto.setCampeonato(estatistica.getCampeonato().getNome());
        }
        if(estatistica.getEquipa() != null) {
            estatisticasDto.setEquipa(estatistica.getEquipa().getNome());
        }
        else {
            estatisticasDto.setEquipa(null);
        }
        estatisticasDto.setJogador(estatistica.getJogador().getNome());
        if(estatistica instanceof Golo) {
            estatisticasDto.setTipoDeEstatistica(EstatisticaTipo.GOLO);
        }
        else if(estatistica instanceof CartaoAmarelo) {
            estatisticasDto.setTipoDeEstatistica(EstatisticaTipo.CARTAOAMARELO);
            CartaoAmarelo ca = (CartaoAmarelo) estatistica;
            estatisticasDto.setArbitro(ca.geArbitro().getNome());
        }
        else if(estatistica instanceof CartaoVermelho) {
            estatisticasDto.setTipoDeEstatistica(EstatisticaTipo.CARTAOVERMELHO);
            CartaoVermelho cv = (CartaoVermelho) estatistica;
            estatisticasDto.setArbitro(cv.geArbitro().getNome());
        }

        return estatisticasDto;
    }

    public List<EstatisticasDto> getAllEstatisticasJogador(Long id) {
        List<Estatisticas> estatisticas = estatisticasRepository.findEstatisticasByJogadorId(id);
        return estatisticas.stream().map(this::mapToDto).toList();
    }

    public List<EstatisticasDto> getAllEstatisticasArbitro(Long id) {
        List<Estatisticas> estatisticas = estatisticasRepository.findCartoes();
        List<Estatisticas> toReturn = new ArrayList<>();
        for (Estatisticas estatistica : estatisticas) {
            if(estatistica instanceof CartaoAmarelo) {
                CartaoAmarelo ca = (CartaoAmarelo) estatistica;
                if(ca.geArbitro().getId() == id) {
                    toReturn.add(ca);
                }
            }
            else if(estatistica instanceof CartaoVermelho) {
                CartaoVermelho cv = (CartaoVermelho) estatistica;
                if(cv.geArbitro().getId() == id) {
                    toReturn.add(cv);
                }
            }
        }

        return toReturn.stream().map(this::mapToDto).toList();
    }

    public List<Estatisticas> getAllEstatisticasArbitroForDeleteArbitro(Long id) {
        List<Estatisticas> estatisticas = estatisticasRepository.findCartoes();
        List<Estatisticas> toReturn = new ArrayList<>();
        for (Estatisticas estatistica : estatisticas) {
            if(estatistica instanceof CartaoAmarelo) {
                CartaoAmarelo ca = (CartaoAmarelo) estatistica;
                if(ca.geArbitro().getId() == id) {
                    toReturn.add(ca);
                }
            }
            else if(estatistica instanceof CartaoVermelho) {
                CartaoVermelho cv = (CartaoVermelho) estatistica;
                if(cv.geArbitro().getId() == id) {
                    toReturn.add(cv);
                }
            }
        }

        return toReturn.stream().toList();
    }

    public void updateEquipa(Estatisticas estatistica) {
        estatisticasRepository.save(estatistica);
    }

    //PERGUNTAS EXTRA
    //Ir buscar os Cartoes Amarelos e Vermelhos para todas as Equipas e meter num Hashmap    
    public  HashMap<String,Integer> findEquipasOrderByTotalCartoes() {
        List<Equipa> equipas = equipaHandler.getAllEquipasForExtraUC();
        
        HashMap<String,Integer> nomeNumCartoes = new HashMap<String,Integer>();

        for (Equipa e : equipas){
            List<Estatisticas> estEquipa = estatisticasRepository.findEstatisticasByEquipaId(e.getId());
            String nome = e.getNome();
            int cartoes = 0;
            for (Estatisticas est : estEquipa){
                if (est instanceof CartaoVermelho || est instanceof CartaoAmarelo) {
                    cartoes++;
                }    
            }

            nomeNumCartoes.put(nome,cartoes);
        }

        return nomeNumCartoes;
    }

    //Ir buscar os Cartoes Vermelhos para todas os Jogadores e meter num Hashmap
    public HashMap<String,Integer> getJogadoresWithMoreCartoesVermelhos(List<Utilizador> utilizadores){
        List<Utilizador> jogadores = utilizadores.stream().filter(u -> u instanceof Jogador).toList();

        HashMap<String,Integer> cartoesV = new HashMap<String,Integer>();

        for (Utilizador utilizador : jogadores) {
            List<Estatisticas> statsPerJogador = estatisticasRepository.findEstatisticasByJogadorId(utilizador.getId());
            String nome = utilizador.getNome();
            int cartoes = 0;
            for (Estatisticas stat : statsPerJogador) {
                if (stat instanceof CartaoVermelho) {
                    cartoes++;
                }
            }

            cartoesV.put(nome, cartoes);

        }
        return cartoesV;
    }

    //Ir buscar os Golos de todos os Jogadores, dividir pelo número de Jogos em que cada um participou e meter num Hashmap
    public HashMap<String,Integer> getAverageGolosOfJogadores(List<Utilizador> jogadores){
        HashMap<String,Integer> medias = new HashMap<String,Integer>();

        for (Utilizador utilizador : jogadores) {
            List<Estatisticas> stats = estatisticasRepository.findEstatisticasByJogadorId(utilizador.getId());
            List<Jogo> jogosDistintintos = new ArrayList<Jogo>();
            String nome = utilizador.getNome();
            int golos = 0;
            for (Estatisticas stat : stats) {
                if (stat instanceof Golo) {
                    golos++;
                    if(!jogosDistintintos.contains(stat.getJogo())){
                        jogosDistintintos.add(stat.getJogo());
                    }
                }
            }
            int media = golos/jogosDistintintos.size();
            medias.put(nome, media);
        }
        return medias;
    }

    //Função Extra
    public void updateEst(Estatisticas e) {
        estatisticasRepository.save(e);
    }


    //2 FASE DO PROJETO///////////////////////////////////////////////////////////////////////////////////////////

    //FILTROS DOS ARBITROS

    public HashMap<Arbitro,Integer> findArbitrosByNumCartoesMostrados(int numCartoes, List<Arbitro> arbs) {
        HashMap<Arbitro,Integer> arbitrosNumCartoes = new HashMap<Arbitro,Integer>();
        
        //preparar o hashmap para os arbitros e o número de cartões que cada um mostrou
        for(Arbitro a : arbs){ 
            arbitrosNumCartoes.put(a, 0);
        }
        
        //encontrar todos os amarelos, ver a que arbitro pertence e colocar +1 no hashmap
        List<Estatisticas> amarelos = estatisticasRepository.findAllByType(CartaoAmarelo.class);
        for(Estatisticas e : amarelos){
            
                CartaoAmarelo ca = (CartaoAmarelo) e;
                if(arbitrosNumCartoes.containsKey(ca.geArbitro())){
                    int valorAtual = arbitrosNumCartoes.get(ca.geArbitro());
                    arbitrosNumCartoes.put(ca.geArbitro(), valorAtual+1);
                }

        } 
        
        //encontrar todos os vermelhos, ver a que arbitro pertence e colocar +1 no hashmap
        List<Estatisticas> vermelhos = estatisticasRepository.findAllByType(CartaoVermelho.class);
        for(Estatisticas e : vermelhos){
               
                CartaoVermelho cv = (CartaoVermelho) e;
                if(arbitrosNumCartoes.containsKey(cv.geArbitro())){
                    int valorAtual = arbitrosNumCartoes.get(cv.geArbitro());
                    arbitrosNumCartoes.put(cv.geArbitro(), valorAtual+1);
                }
              
        }

        return arbitrosNumCartoes;
    }

    //FILTROS DOS JOGADORES

    public HashMap<Jogador, Integer> findJogadoresByNumGolosMarcados(int numGolos, List<Jogador> jogs) {
        HashMap<Jogador,Integer> jogadoresNumGolos = new HashMap<Jogador,Integer>();
        //encontrar todos os golos
        List<Estatisticas> ests = estatisticasRepository.findAllByType(Golo.class);
        
        for(Jogador j : jogs){
            jogadoresNumGolos.put(j, 0);
        }

        //ver a que Jogador cada golo pertence e depois colocar +1
        for(Estatisticas e : ests){
            Golo g = (Golo) e;
            if(jogadoresNumGolos.containsKey(g.getJogador())){
                int valorAtual = jogadoresNumGolos.get(g.getJogador());
                jogadoresNumGolos.put(g.getJogador(), valorAtual+1);
            }
            
        }

        return jogadoresNumGolos;

    }

    public HashMap<Jogador, Integer> findJogadoresByNumCartoesMostrados(int numCartoes, List<Jogador> jogs) {
        HashMap<Jogador,Integer> jogadoresNumCartoes = new HashMap<Jogador,Integer>();
        
        //preparar o hashmap
        for(Jogador j : jogs){
            jogadoresNumCartoes.put(j, 0);
        }

        //encontrar todos os amarelos
        //ver a que Jogador cada amarelo pertence e depois colocar +1
        List<Estatisticas> amarelos = estatisticasRepository.findAllByType(CartaoAmarelo.class);
        for(Estatisticas e : amarelos){
            CartaoAmarelo ca = (CartaoAmarelo) e;
            if(jogadoresNumCartoes.containsKey(ca.getJogador())){
                int valorAtual = jogadoresNumCartoes.get(ca.getJogador());
                jogadoresNumCartoes.put(ca.getJogador(), valorAtual+1);
            }
        }

        //encontrar todos os vermelhos
        //ver a que Jogador cada vermelho pertence e depois colocar +1
        List<Estatisticas> vermelhos = estatisticasRepository.findAllByType(CartaoVermelho.class);
        for(Estatisticas e : vermelhos){    
                CartaoVermelho cv = (CartaoVermelho) e;
                if(jogadoresNumCartoes.containsKey(cv.getJogador())){
                    int valorAtual = jogadoresNumCartoes.get(cv.getJogador());
                    jogadoresNumCartoes.put(cv.getJogador(), valorAtual+1);
                }
        }

        return jogadoresNumCartoes;
    }

}
