package pt.ul.fc.css.soccernow.webcontrollers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pt.ul.fc.css.soccernow.dto.JogadorDto;
import pt.ul.fc.css.soccernow.entities.Campeonato;
import pt.ul.fc.css.soccernow.entities.Equipa;
import pt.ul.fc.css.soccernow.entities.Jogador;
import pt.ul.fc.css.soccernow.entities.Utilizador;
import pt.ul.fc.css.soccernow.handlers.CampeonatoHandler;
import pt.ul.fc.css.soccernow.handlers.EquipaHandler;
import pt.ul.fc.css.soccernow.handlers.UtilizadorHandler;

@Controller
@RequestMapping("/buscas")
public class BuscasWebController {

    @Autowired
    private UtilizadorHandler utilizadorHandler;

    @Autowired
    private EquipaHandler equipaHandler;

    @Autowired
    private CampeonatoHandler campeonatoHandler;

    @GetMapping("/jogadores")
    public String mostrarBuscasJogadores() {
        return "buscasJogadores";
    }

    @GetMapping("/equipas")
    public String mostrarBuscasEquipas() {
        return "buscasEquipas";
    }

    @GetMapping("/campeonatos")
    public String mostrarBuscasCampeonatos() {
        return "buscasCampeonatos";
    }

    //Caso os resultados das buscas não existam, vai aparecer o erro.html

    //BUSCAR POR JOGADORES

    @GetMapping("/jogadores/nif")
    public String filtrarPorNif(@RequestParam String nif, Model model) {
        Optional<Utilizador> jogador = utilizadorHandler.findByNif(nif);

        if (jogador.isPresent() && jogador.get() instanceof Jogador j){
            model.addAttribute("jogadores", j);
            model.addAttribute("criterio", nif);

            return "buscas/resultadosBuscaJogador";
        }
        return "filtros/erro";
    }

    @GetMapping("/jogadores/todos")
    public String listarTodosJogadores(Model model) {
        List<Jogador> jogadores = utilizadorHandler.getAllJogadoresAtivos();

        if (!jogadores.isEmpty()) {
        model.addAttribute("jogadores", jogadores);
        model.addAttribute("criterio", "Todos os Jogadores");
        return "buscas/resultadosBuscaJogador";
    }

        return "filtros/erro";
    }

    //BUSCAR POR EQUIPAS

    @GetMapping("/equipas/nome")
    public String filtrarPorNome(@RequestParam String nome, Model model) {
        List<Equipa> equipas = equipaHandler.findEquipasByNomeContainingIgnoreCase(nome);
        if (!equipas.isEmpty()) {
            model.addAttribute("equipas", equipas);
            model.addAttribute("criterio", nome);
            return "buscas/resultadosBuscaEquipa";
        }
        return "filtros/erro";
    }

    @GetMapping("/equipas/todas")
    public String listarTodasEquipas(Model model) {
        List<Equipa> equipas = equipaHandler.getAllEquipasAtivas();

        if (!equipas.isEmpty()) {
        model.addAttribute("equipas", equipas);
        model.addAttribute("criterio", "Todas as Equipas");
        return "buscas/resultadosBuscaEquipa";
    }

        return "filtros/erro";
    }

    //BUSCAR POR CAMPEONATOS

    @GetMapping("/campeonatos/nome")
    public String filtrarCampeonatoPorNome(@RequestParam String nome, Model model) {
        List<Campeonato> campeonatos = campeonatoHandler.findCampeonatosByNomeContainingIgnoreCase(nome);

        if (!campeonatos.isEmpty()) {
            model.addAttribute("campeonatos", campeonatos);
            model.addAttribute("criterio", nome);
            return "buscas/resultadosBuscaCampeonato";
        }

        return "filtros/erro";
    }

    @GetMapping("/campeonatos/todos")
    public String listarTodosCampeonatos(Model model) {
        List<Campeonato> campeonatos = campeonatoHandler.getAllCampeonatosAtivos();

        if (!campeonatos.isEmpty()) {
        model.addAttribute("campeonatos", campeonatos);
        model.addAttribute("criterio", "Todos os Campeonatos");
        return "buscas/resultadosBuscaCampeonato";
    }

        return "filtros/erro";
    }
}
