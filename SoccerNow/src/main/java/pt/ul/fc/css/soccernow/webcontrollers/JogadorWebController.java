package pt.ul.fc.css.soccernow.webcontrollers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pt.ul.fc.css.soccernow.entities.Jogador;
import pt.ul.fc.css.soccernow.entities.Utilizador;
import pt.ul.fc.css.soccernow.handlers.UtilizadorHandler;

@Controller
@RequestMapping("/jogadores")
public class JogadorWebController {
    
    @Autowired
    private UtilizadorHandler utilizadorHandler;

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error, Model model) { 
        if (error != null) {
            model.addAttribute("errorMessage", "Nome ou password inválidos.");
        }
        model.addAttribute("jogador", new Jogador());

        return "jogadores/loginJog"; 
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String nome, @RequestParam String password, Model model) {

        Optional<Utilizador> utilizador = utilizadorHandler.findByNomeAndType(nome, Jogador.class);

        //colocar no modelo apenas o nome, já que a password pode ser uma qualquer
        if (utilizador.isPresent()) {
            model.addAttribute("nome", nome);
            return "filtros/navigation_dashboard";
        }

        return "redirect:/jogadores/login?error=true";
    }
}
