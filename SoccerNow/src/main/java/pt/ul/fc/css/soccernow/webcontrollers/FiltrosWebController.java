package pt.ul.fc.css.soccernow.webcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pt.ul.fc.css.soccernow.entities.Arbitro;
import pt.ul.fc.css.soccernow.entities.Campeonato;
import pt.ul.fc.css.soccernow.entities.Equipa;
import pt.ul.fc.css.soccernow.entities.Jogador;
import pt.ul.fc.css.soccernow.entities.Jogo;
import pt.ul.fc.css.soccernow.handlers.CampeonatoHandler;
import pt.ul.fc.css.soccernow.handlers.EquipaHandler;
import pt.ul.fc.css.soccernow.handlers.JogoHandler;
import pt.ul.fc.css.soccernow.handlers.UtilizadorHandler;

@Controller
@RequestMapping("/filtros")
public class FiltrosWebController {
    

    @Autowired
    private UtilizadorHandler utilizadorHandler;

    @Autowired
    private EquipaHandler equipaHandler;

    @Autowired
    private JogoHandler jogoHandler;

    @Autowired
    private CampeonatoHandler campeonatoHandler;

    @GetMapping
    public String mostrarDashboardFiltros() {
        return "navigation_dashboard"; 
    }

    @GetMapping("/jogadores")
    public String mostrarFiltrosJogadores() {
        return "filtrosJogadores";
    }

    @GetMapping("/arbitros")
    public String mostrarFiltrosArbitros() {
        return "filtrosArbitros";
    }

    @GetMapping("/equipas")
    public String mostrarFiltrosEquipas() {
        return "filtrosEquipas";
    }

    @GetMapping("/jogos")
    public String mostrarFiltrosJogos() {
        return "filtrosJogos";
    }

    @GetMapping("/campeonatos")
    public String mostrarFiltrosCampeonatos() {
        return "filtrosCampeonatos";
    }

    //Caso os resultados dos filtros não existam, vai aparecer o erro.html

    //FILTROS DE JOGADORES/////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/jogadores/nome")
    public String filtrarPorNome(@RequestParam String nome, Model model) { 
        List<Jogador> jogadoresFiltrados = utilizadorHandler.findJogadoresByNomeContainingIgnoreCase(nome);
            
        if (!jogadoresFiltrados.isEmpty()){
            model.addAttribute("jogadores", jogadoresFiltrados);
            model.addAttribute("criterio", nome);

            return "filtros/resultadosFiltroJogador";
        }
        return "filtros/erro";
        
    }

    @GetMapping("/jogadores/posicao")
    public String filtrarPorPosicao(@RequestParam String posicao, Model model) {
        List<Jogador> jogadoresFiltrados = utilizadorHandler.findJogadoresByPosicao(posicao);

        if (!jogadoresFiltrados.isEmpty()){
            model.addAttribute("jogadores", jogadoresFiltrados);
            model.addAttribute("criterio", posicao);

            return "filtros/resultadosFiltroJogador";
        }
        return "filtros/erro";
    }

    @GetMapping("/jogadores/golos")
    public String filtrarPorGolos(@RequestParam int numGolos, Model model) {
        List<Jogador> jogadoresFiltrados = utilizadorHandler.findJogadoresByNumGolos(numGolos);
        
        if (!jogadoresFiltrados.isEmpty()){
            model.addAttribute("jogadores", jogadoresFiltrados);
            model.addAttribute("criterio", numGolos);

            return "filtros/resultadosFiltroJogador";
        }
        return "filtros/erro";
    }

    @GetMapping("/jogadores/cartoes")
    public String filtrarPorCartoes(@RequestParam int numCartoes, Model model) {
        List<Jogador> jogadoresFiltrados = utilizadorHandler.findJogadoresByNumCartoes(numCartoes);
        
        if (!jogadoresFiltrados.isEmpty()){
            model.addAttribute("jogadores", jogadoresFiltrados);
            model.addAttribute("criterio", numCartoes);

            return "filtros/resultadosFiltroJogador";
        }
        return "filtros/erro";
    }

    @GetMapping("/jogadores/jogos")
    public String filtrarPorJogos(@RequestParam int numJogos, Model model) {
        List<Jogador> jogadoresFiltrados = utilizadorHandler.findJogadoresByNumJogos(numJogos);

        if (!jogadoresFiltrados.isEmpty()){
            model.addAttribute("jogadores", jogadoresFiltrados);
            model.addAttribute("criterio", numJogos);

            return "filtros/resultadosFiltroJogador";
        }
        return "filtros/erro";
    }

    //FILTROS DE ARBITROS/////////////////////////////////////////////////////////////////////////////////////////

        
    @GetMapping("/arbitros/nome")
    public String filtrarArbitroPorNome(@RequestParam String nome, Model model) {
        List<Arbitro> arbitrosFiltrados = utilizadorHandler.findArbitrosByNomeContainingIgnoreCase(nome);
        if (!arbitrosFiltrados.isEmpty()) {
            model.addAttribute("arbitros", arbitrosFiltrados);
            model.addAttribute("criterio", nome);
            return "filtros/resultadosFiltroArbitro";
        }
        return "filtros/erro";
    }

    @GetMapping("/arbitros/jogos")
    public String filtrarArbitroPorNumJogos(@RequestParam int numJogos, Model model) {
        List<Arbitro> arbitrosFiltrados = utilizadorHandler.findArbitrosByNumJogosOficiados(numJogos);
        if (!arbitrosFiltrados.isEmpty()) {
            model.addAttribute("arbitros", arbitrosFiltrados);
            model.addAttribute("criterio", numJogos);
            return "filtros/resultadosFiltroArbitro";
        }
        return "filtros/erro";
    }

    @GetMapping("/arbitros/cartoes")
    public String filtrarArbitroPorNumCartoes(@RequestParam int numCartoes, Model model) {
        List<Arbitro> arbitrosFiltrados = utilizadorHandler.findArbitrosByNumCartoesMostrados(numCartoes);
        if (!arbitrosFiltrados.isEmpty()) {
            model.addAttribute("arbitros", arbitrosFiltrados);
            model.addAttribute("criterio", numCartoes);
            return "filtros/resultadosFiltroArbitro";
        }
        return "filtros/erro";
    }

    //FILTROS DAS EQUIPAS/////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/equipas/nome")
    public String filtrarEquipaPorNome(@RequestParam String nome, Model model) {
        List<Equipa> equipas = equipaHandler.findEquipasByNomeContainingIgnoreCase(nome);
        if (!equipas.isEmpty()) {
            model.addAttribute("equipas", equipas);
            model.addAttribute("criterio", nome);
            return "filtros/resultadosFiltroEquipa";
        }
        return "filtros/erro";
    }

    @GetMapping("/equipas/numero-jogadores")
    public String filtrarEquipaPorNumeroJogadores(@RequestParam int numJogadores, Model model) {    
        List<Equipa> equipas = equipaHandler.findEquipasByNumeroJogadores(numJogadores);
        if (!equipas.isEmpty()) {
            model.addAttribute("equipas", equipas);
            model.addAttribute("criterio", numJogadores);
            return "filtros/resultadosFiltroEquipa";
        }
        return "filtros/erro";
    }

    //Aqueles das vitórias, empates ou derrotas
    @GetMapping("/equipas/numero-vitorias")
    public String filtrarEquipaPorNumeroVitorias(@RequestParam int numVitorias, Model model) {    
        List<Object[]> equipas = equipaHandler.findEquipasByNumeroVitorias(numVitorias);
        if (!equipas.isEmpty()) {
            model.addAttribute("equipas", equipas);
            model.addAttribute("criterio", "Número de vitórias: " + numVitorias);
            return "filtros/resultadosFiltroEstatisticasEquipa";
        }
        return "filtros/erro";
    }

    @GetMapping("/equipas/numero-empates")
    public String filtrarEquipaPorNumeroEmpates(@RequestParam int numEmpates, Model model) {    
        List<Object[]> equipas = equipaHandler.findEquipasByNumeroEmpates(numEmpates);
        if (!equipas.isEmpty()) {
            model.addAttribute("equipas", equipas);
            model.addAttribute("criterio", numEmpates);
            return "filtros/resultadosFiltroEstatisticasEquipa";
        }
        return "filtros/erro";
    }

    @GetMapping("/equipas/numero-derrotas")
    public String filtrarEquipaPorNumeroDerrotas(@RequestParam int numDerrotas, Model model) {    
        List<Object[]> equipas = equipaHandler.findEquipasByNumeroDerrotas(numDerrotas);
        if (!equipas.isEmpty()) {
            model.addAttribute("equipas", equipas);
            model.addAttribute("criterio", numDerrotas);
            return "filtros/resultadosFiltroEstatisticasEquipa";
        }
        return "filtros/erro";
    }
    
    //Número de conquistas == número de vezes que são vencedores
    @GetMapping("/equipas/numero-conquistas")
    public String filtrarEquipaPorNumeroConquitas(@RequestParam int numConquistas, Model model) {    
        List<Equipa> equipas = equipaHandler.findEquipasPorNumeroConquitas(numConquistas);
        if (!equipas.isEmpty()) {
            model.addAttribute("equipas", equipas);
            model.addAttribute("criterio", numConquistas);
            return "filtros/resultadosFiltroEquipa";
        }
        return "filtros/erro";
    }


    @GetMapping("/equipas/ausencia-posicao")
    public String filtrarEquipaPorAusenciaDePosicao(@RequestParam String posicao, Model model) {
        List<Equipa> equipas = equipaHandler.findEquipasSemPosicao(posicao);
        if (!equipas.isEmpty()) {
            model.addAttribute("equipas", equipas);
            model.addAttribute("criterio", "Sem jogadores na posição: " + posicao);
            return "filtros/resultadosFiltroEquipa";
        }
        return "filtros/erro";
    }

    //FILTROS DOS JOGOS/////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/jogos/realizados")
    public String filtrarJogosRealizados(Model model) {
        List<Jogo> jogos = jogoHandler.findJogosRealizados();
        if (!jogos.isEmpty()) {
            model.addAttribute("jogos", jogos);
            model.addAttribute("criterio", "Jogos Realizados");
            return "filtros/resultadosFiltroJogo";
        }
        return "filtros/erro";
    }

    @GetMapping("/jogos/por-realizar")
    public String filtrarJogosPorRealizar(Model model) {
        List<Jogo> jogos = jogoHandler.findJogosPorRealizar();
        if (!jogos.isEmpty()) {
            model.addAttribute("jogos", jogos);
            model.addAttribute("criterio", "Jogos por Realizar");
            return "filtros/resultadosFiltroJogo";
        }
        return "filtros/erro";
    }

    @GetMapping("/jogos/golos")
    public String filtrarJogosPorGolos(@RequestParam int totalGolos, Model model) {
        List<Jogo> jogos = jogoHandler.findJogosByTotalGolos(totalGolos);
        if (!jogos.isEmpty()) {
            model.addAttribute("jogos", jogos);
            model.addAttribute("criterio", "Total de Golos: " + totalGolos);
            return "filtros/resultadosFiltroJogo";
        }
        return "filtros/erro";
    }

    @GetMapping("/jogos/localizacao")
    public String filtrarJogosPorLocalizacao(@RequestParam String localizacao, Model model) {
        List<Jogo> jogos = jogoHandler.findJogosByLocalizacao(localizacao);
        if (!jogos.isEmpty()) {
            model.addAttribute("jogos", jogos);
            model.addAttribute("criterio", "Localização: " + localizacao);
            return "filtros/resultadosFiltroJogo";
        }
        return "filtros/erro";
    }

    @GetMapping("/jogos/turno")
    public String filtrarJogosPorTurno(@RequestParam String turno, Model model) {
        List<Jogo> jogos = jogoHandler.findJogosPorTurno(turno);
        if (!jogos.isEmpty()) {
            model.addAttribute("jogos", jogos);
            model.addAttribute("criterio", "Turno: " + turno);
            return "filtros/resultadosFiltroJogo";
        }
        return "filtros/erro";
    }

    //FILTROS DOS CAMPEONATOS/////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/campeonatos/nome")
    public String filtrarCampeonatoPorNome(@RequestParam String nome, Model model) {
        List<Campeonato> campeonatos = campeonatoHandler.findCampeonatosByNomeContainingIgnoreCase(nome);
        if (!campeonatos.isEmpty()) {
            model.addAttribute("campeonatos", campeonatos);
            model.addAttribute("criterio", nome);
            return "filtros/resultadosFiltroCampeonato";
        }
        return "filtros/erro";
    }

    @GetMapping("/campeonatos/equipa")
    public String filtrarCampeonatoPorNomeEquipa(@RequestParam String nomeEquipa, Model model) {
        List<Campeonato> campeonatos = campeonatoHandler.findCampeonatosByNomeEquipa(nomeEquipa);
        
        if (!campeonatos.isEmpty()) {
            model.addAttribute("campeonatos", campeonatos);
            model.addAttribute("criterio", "Equipa: " + nomeEquipa);
            return "filtros/resultadosFiltroCampeonato";
        }

        return "filtros/erro";
    }

    @GetMapping("/campeonatos/jogos-realizados")
    public String filtrarCampeonatosPorJogosRealizados(@RequestParam int num, Model model) {
        List<Campeonato> campeonatos = campeonatoHandler.findCampeonatosByNumJogosRealizados(num);
        if (!campeonatos.isEmpty()) {
            model.addAttribute("campeonatos", campeonatos);
            model.addAttribute("criterio", "com " + num + " jogos realizados");
            return "filtros/resultadosFiltroCampeonato";
        }
        return "filtros/erro";
    }

    @GetMapping("/campeonatos/jogos-por-realizar")
    public String filtrarCampeonatosPorJogosPorRealizar(@RequestParam int num, Model model) {
        List<Campeonato> campeonatos = campeonatoHandler.findCampeonatosByNumJogosPorRealizar(num);
        if (!campeonatos.isEmpty()) {
            model.addAttribute("campeonatos", campeonatos);
            model.addAttribute("criterio", "com " + num + " jogos por realizar");
            return "filtros/resultadosFiltroCampeonato";
        }
        return "filtros/erro";
    }

}
