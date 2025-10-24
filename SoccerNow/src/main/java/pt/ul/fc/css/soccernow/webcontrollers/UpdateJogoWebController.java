package pt.ul.fc.css.soccernow.webcontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/jogos")
public class UpdateJogoWebController {


    @GetMapping("/update-resultado")
    public String mostrarFormularioUpdate() {
        return "atualizarResultadoJogo"; 
    }

    //PostMapping se funcionasse
    @PostMapping("/update-resultado")
    public String processarAtualizacaoResultado() {
        return "filtros/naoImplementado";
    }
}
