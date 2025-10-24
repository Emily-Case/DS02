package pt.ul.fc.css.soccernow.webcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import pt.ul.fc.css.soccernow.entities.Arbitro;
import pt.ul.fc.css.soccernow.entities.Utilizador;
import pt.ul.fc.css.soccernow.handlers.UtilizadorHandler;

import java.util.Optional;

@Controller
@RequestMapping("/arbitros")
public class ArbitroWebController {

    @Autowired
    private UtilizadorHandler utilizadorHandler;

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Nome ou password inválidos.");
        }
        model.addAttribute("arbitro", new Arbitro());
        
        return "arbitros/loginArb"; 
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String nome, @RequestParam String password, Model model) {

        Optional<Utilizador> utilizador = utilizadorHandler.findByNomeAndType(nome, Arbitro.class);

        //colocar no modelo apenas o nome, já que a password pode ser uma qualquer
        if (utilizador.isPresent()) {
            model.addAttribute("nome", nome);
            return "filtros/navigation_dashboard"; 
        }

        return "redirect:/arbitros/login?error=true";
    }
}
