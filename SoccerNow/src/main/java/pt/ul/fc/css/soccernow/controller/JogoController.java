package pt.ul.fc.css.soccernow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pt.ul.fc.css.soccernow.dto.EstatisticasDto;
import pt.ul.fc.css.soccernow.dto.JogoDto;
import pt.ul.fc.css.soccernow.handlers.JogoHandler;
//programar para os casos de falha
@RestController
@RequestMapping("/api/jogos")
@Api(value = "Jogo API", tags = "Jogos")
public class JogoController {
    
    @Autowired
    private JogoHandler jogoHandler;
    
    //Verificações Feitas
    @PostMapping("/create")
    @ApiOperation(value = "Create jogo", notes = "Creates a new jogo")
    public ResponseEntity<JogoDto> createJogo(@RequestBody JogoDto jogoDto) {
        if (jogoDto.getData() == null || jogoDto.getHorario() == null || jogoDto.getLocalizacao() == null) {
            throw new IllegalArgumentException("Campos obrigatórios estão ausentes no JogoDTO.");
        }
        JogoDto jDTO = jogoHandler.createJogo(jogoDto);
        if(jDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(jDTO);
    }

    //Verificações Feitas
    @GetMapping("/{jogo_id}")
    @ApiOperation(value = "Gets a specific Jogo", notes = "Returns a specific registered Jogo")
    public ResponseEntity<JogoDto> getJogo(@PathVariable Long jogo_id){
        JogoDto jDTO = jogoHandler.getJogoById(jogo_id);
        
        if(jDTO != null){
            return ResponseEntity.ok(jDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //Verificações Feitas
    @GetMapping
    @ApiOperation(value = "Get all Jogos", notes = "Returns a list of all Jogos.")
    public ResponseEntity<List<JogoDto>> getAllJogos(){
        List<JogoDto> jogos = jogoHandler.getAllJogos();
        return ResponseEntity.ok(jogos);
    }

    //Verificações Feitas
    @PutMapping("/{jogo_Id}/update")
    @ApiOperation(value = "Update Jogo", notes = "Updates a Jogo")
    public ResponseEntity<JogoDto> updateJogo(@PathVariable Long jogo_Id, @RequestBody JogoDto jogoDTO){
        JogoDto jDTO = jogoHandler.updateJogo(jogo_Id,jogoDTO);
        if(jDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(jDTO);
    }

    //Verificações Feitas
    @PatchMapping("/{jogo_Id}/updateFinalScore")
    @ApiOperation(value = "Update final score", notes = "Updates a jogo's final score")
    public ResponseEntity<JogoDto> updateFinalScoreJogo(@PathVariable Long jogo_Id, @RequestBody JogoDto jogoDto){
        //Como descrito no relatório, o updateFinalScore apenas vai apenas mudar alguns parametros
        JogoDto jDTO = jogoHandler.updateFinalScoreJogo(jogo_Id, jogoDto, jogoDto.getEstatisticas());
        if(jDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(jDTO);
    }

    //Verificações Feitas
    @PatchMapping("/{jogo_Id}/cancel")
    @ApiOperation(value = "Cancel jogo", notes = "Cancels a jogo")
    public ResponseEntity<JogoDto> cancelJogo(@PathVariable Long jogo_Id){
        JogoDto jDTO = jogoHandler.cancelJogo(jogo_Id);
        if(jDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(jDTO);
    }

    //Verificações Feitas
    @GetMapping("/{id}/estatisticas")
    @ApiOperation(value = "Get all Estatisticas of a Jogo", notes = "Returns a list of all Estatisticas of the Jogo")
    public ResponseEntity<List<EstatisticasDto>> getAllEstatisticasJogo(@PathVariable Long id){
        List<EstatisticasDto> estatisticas = jogoHandler.getAllEstatisticasJogo(id);
        return ResponseEntity.ok(estatisticas);
    }
}
