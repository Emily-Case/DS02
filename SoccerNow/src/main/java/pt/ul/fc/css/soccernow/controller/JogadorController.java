package pt.ul.fc.css.soccernow.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.RequestBody;

import pt.ul.fc.css.soccernow.dto.EstatisticasDto;
import pt.ul.fc.css.soccernow.dto.JogadorDto;
import pt.ul.fc.css.soccernow.entities.Jogador;
import pt.ul.fc.css.soccernow.entities.Utilizador;
import pt.ul.fc.css.soccernow.enums.EstadoEntidade;
import pt.ul.fc.css.soccernow.handlers.EstatisticasHandler;
import pt.ul.fc.css.soccernow.handlers.UtilizadorHandler;

@RestController
@RequestMapping("/api/jogadores")
@Api(value = "Jogador API", tags = "Jogadores")
public class JogadorController {

    @Autowired
    private UtilizadorHandler utilizadorHandler;

    @Autowired
    private EstatisticasHandler estatisticasHandler;

    //Verificações Feitas
    @PostMapping("/login")
    @ApiOperation(value = "Jogador Login", notes = "Logs in a Jogador")
    public ResponseEntity<String> jogadorLogin(@RequestParam String nome, @RequestParam String password) {

        Optional<Utilizador> utilizador = utilizadorHandler.findByNomeAndType(nome, Jogador.class);
        
        if (utilizador.isPresent()) {
            return ResponseEntity.ok("Player login successful (mock)");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Player not found");
    }

    //Verificações Feitas
    @PostMapping
    @ApiOperation(value = "Create Jogador", notes = "Register a new Jogador")
    public ResponseEntity<JogadorDto> createJogador(@RequestBody JogadorDto jogadorDTO) {

        Optional<Utilizador> utilizador = utilizadorHandler.findByNif(jogadorDTO.getNif());
        
        if (utilizador.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        else {
            JogadorDto novoJ  = utilizadorHandler.createJogador(jogadorDTO);
            if(novoJ == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            return ResponseEntity.ok(novoJ);
        }
    }

    //Verificações Feitas
    @GetMapping("/{id}")
    @ApiOperation(value = "Get Jogador", notes = "Returns a specific registered Jogador")
    public ResponseEntity<JogadorDto> getJogador(@PathVariable Long id){
        try {
            JogadorDto jogadorDto = utilizadorHandler.findJogadorById(id);
            //Se o jogador for deleted, não vai ser devolvido
            if(jogadorDto.getEstadoJogador() == EstadoEntidade.INATIVO){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(jogadorDto);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //Verificações Feitas
    @GetMapping
    @ApiOperation(value = "Get All Jogadores", notes = "Returns a list of all registered Jogadores")
    public ResponseEntity<List<JogadorDto>> getAllJogadores() {
        List<JogadorDto> jogadores = utilizadorHandler.getAllJogadores();
        List<JogadorDto> jogadoresAtivos = new ArrayList<>();
        for (JogadorDto jDto : jogadores) {
             //Verificar se Jogador já foi deleted
            if(jDto.getEstadoJogador() == EstadoEntidade.ATIVO) {
                jogadoresAtivos.add(jDto);
            }
        }

        return ResponseEntity.ok(jogadoresAtivos);
    }

    //Verificações Feitas
    @PutMapping("/{id}")
    @ApiOperation(value = "Updates partially a Jogador")
    public ResponseEntity<JogadorDto> updateJogador(@PathVariable Long id, @RequestBody JogadorDto jogadorDto) {
        Optional<JogadorDto> jogadorAtualizado = utilizadorHandler.updateJogador(id, jogadorDto);

        if (jogadorAtualizado.isPresent()) {
            return ResponseEntity.ok(jogadorAtualizado.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //Verificações Feitas
    @PostMapping("/add/equipa")
    @ApiOperation(value = "Adds an Equipa to a Jogador")
    public ResponseEntity<JogadorDto> adicionarEquipaAoJogador(@RequestParam Long id, @RequestParam String nomeEquipa) {
    
        Optional<JogadorDto> updatedJogador = utilizadorHandler.addEquipa(id, nomeEquipa);

        if (updatedJogador.isPresent()) {
            return ResponseEntity.ok(updatedJogador.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Verificações Feitas
    @DeleteMapping("/remove/equipa")
    @ApiOperation(value = "Removes an Equipa from a Jogador")
    public ResponseEntity<JogadorDto> removerEquipaDoJogador(@RequestParam Long jogadorId, @RequestParam String nomeEquipa) {
    
        Optional<JogadorDto> updatedJogador = utilizadorHandler.removeEquipa(jogadorId, nomeEquipa);

        if (updatedJogador.isPresent()) {
            return ResponseEntity.ok(updatedJogador.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete Jogador", notes = "Delete a registered Jogador")
    public ResponseEntity<String> deleteJogador(@PathVariable Long id){
        boolean deletedJ = utilizadorHandler.deleteJogador(id);

        if (deletedJ) {
            return ResponseEntity.ok("Jogador Eliminado");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Jogador não encontrado");
        }
    }

    @DeleteMapping("/deleteByNif")
    @ApiOperation(value = "Delete Jogador", notes = "Delete a registered Jogador")
    public ResponseEntity<String> deleteJogadorByNif(@RequestParam int nif){
        boolean deletedJ = utilizadorHandler.deleteJogadorByNif(nif);

        if (deletedJ) {
            return ResponseEntity.ok("Jogador Eliminado");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Jogador não encontrado");
        }
    }

    @GetMapping("/{id}/estatisticas")
    @ApiOperation(value = "Get all Estatisticas of a Jogador", notes = "Returns a list of all Estatisticas of the Jogador")
    public ResponseEntity<List<EstatisticasDto>> getAllEstatisticasJogador(@PathVariable Long id){
        List<EstatisticasDto> estatisticas = estatisticasHandler.getAllEstatisticasJogador(id);
        return ResponseEntity.ok(estatisticas);
    }

    @GetMapping("/jogador")
    @ApiOperation(value = "Get Jogador by Nome", notes = "Returns a Jogador by their name")
    public ResponseEntity<JogadorDto> getJogadorByNome(@RequestParam String nome){
        try {
            JogadorDto dto = utilizadorHandler.findJogadorByName(nome);

            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //PERGUNTAS EXTRA
    //Jogadores com mais cartões vermelhos
    @GetMapping("/top5-jogadores-mais-vermelhos")
    @ApiOperation(value = "Get top 5 Jogadores with more CartoesVermelhos", notes = "Returns the top 5 Jogadores with more CartoesVermelhos")
    public String getJogadoresWithMoreCartoesVermelhos(){
        String jogadoresCartoes = utilizadorHandler.getJogadoresWithMoreCartoesVermelhos();
        return jogadoresCartoes;
    }

    //Media de golos por jogo de um Jogador específico
    @GetMapping("/media-golos-de-um-jogador")
    @ApiOperation(value = "Get the average of Golos of Jogadores with a certain name", notes = "Returns the average of Golos of Jogadores with a certain name")
    public String getAverageGolosOfJogadores(@RequestParam String nome){
        String result = utilizadorHandler.getAverageGolosOfJogadores(nome);
        return result;
    }
}