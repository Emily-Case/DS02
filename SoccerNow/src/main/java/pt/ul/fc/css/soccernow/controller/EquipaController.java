package pt.ul.fc.css.soccernow.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pt.ul.fc.css.soccernow.dto.CampeonatoPontosDto;
import pt.ul.fc.css.soccernow.dto.EquipaDto;
import pt.ul.fc.css.soccernow.dto.EstatisticasDto;
import pt.ul.fc.css.soccernow.dto.EstatisticasEquipaDto;
import pt.ul.fc.css.soccernow.dto.JogoDto;
import pt.ul.fc.css.soccernow.enums.EstadoEntidade;
import pt.ul.fc.css.soccernow.handlers.EquipaHandler;
import pt.ul.fc.css.soccernow.handlers.EstatisticasHandler;

@RestController
@RequestMapping("/api/equipas")
@Api(value = "Equipa API", tags = "Equipas")
public class EquipaController {
    
    @Autowired
    private EquipaHandler equipaHandler;

    @Autowired
    private EstatisticasHandler estatisticasHandler;

    //Verificações Feitas
    @GetMapping
    @ApiOperation(value = "Get all Equipas", notes = "Returns a list of all Equipas.")
    public ResponseEntity<List<EquipaDto>> getAllEquipas(){
        List<EquipaDto> equipas = equipaHandler.getAllEquipas();
        List<EquipaDto> equipasAtivas = new ArrayList<>();
        for (EquipaDto eDto : equipas) {
            if(eDto.getEstadoEquipa() == EstadoEntidade.ATIVO) {
                equipasAtivas.add(eDto);
            }
        }

        return ResponseEntity.ok(equipasAtivas);
    }

    //Verificações Feitas
    @PostMapping
    @ApiOperation(value = "Create Equipa", notes = "Creates a new Equipa and returns the created Equipa DTO.")
    public ResponseEntity<EquipaDto> createEquipa(@RequestBody EquipaDto equipaDto) {
        EquipaDto responseDto = equipaHandler.createEquipa(equipaDto);
        if(responseDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(responseDto);
    }

    //Verificações Feitas
    @GetMapping("/{id}")
    @ApiOperation(value = "Get Equipa by ID", notes = "Returns a Equipa given its ID.")
    public ResponseEntity<EquipaDto> getEquipaById(@PathVariable("id") Long id) {
        EquipaDto equipaDto = equipaHandler.getEquipaById(id);
        //Se equipa não tiver sido deleted e o equipa dto não vier a NULL do equipaHandler
        if (equipaDto != null && equipaDto.getEstadoEquipa() == EstadoEntidade.ATIVO) {
            return ResponseEntity.ok(equipaDto);
        }
        return ResponseEntity.notFound().build();
    }

    //Verificações Feitas
    @GetMapping("/by-nome/{nome}")
    @ApiOperation(value = "Get Equipa by nome", notes = "Returns a Equipa given its nome.")
    public ResponseEntity<EquipaDto> getEquipaByNome(@PathVariable("nome") String nome) {
        EquipaDto equipaDto = equipaHandler.getEquipaByNome(nome);
        if (equipaDto != null && equipaDto.getEstadoEquipa() == EstadoEntidade.ATIVO) {
            return ResponseEntity.ok(equipaDto);
        }
        return ResponseEntity.notFound().build();
    }

    //Verificações Feitas
    @PutMapping("/update/{id}")
    @ApiOperation(value = "Update Equipa", notes = "Updates a Equipa and returns the updated DTO.")
    public ResponseEntity<EquipaDto> updateEquipa(@PathVariable("id") Long id, @RequestBody EquipaDto equipaDto) {
        EquipaDto responseDto = equipaHandler.updateEquipa(id, equipaDto);
        if(responseDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(responseDto);
    }

    //Verificações Feitas
    @DeleteMapping("/remove/jogador")
    @ApiOperation(value = "Removes a Jogador from a Equipa")
    public ResponseEntity<EquipaDto> removeJogadorFromEquipa(@RequestParam Long id, @RequestParam String nome) {
        EquipaDto responseDto = equipaHandler.removeJogador(id, nome);
        if(responseDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}/jogos")
    @ApiOperation(value = "Get Histórico de Jogos of an Equipa", notes = "Returns a list of the Jogos of an Equipa.")
    public ResponseEntity<List<JogoDto>> getHistoricoJogos(@PathVariable("id") Long id){
        List<JogoDto> jogos = equipaHandler.getAllJogosOfEquipa(id);
        return ResponseEntity.ok(jogos);
    }

    //Verificações Feitas
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete Equipa", notes = "Delete a Equipa")
    public ResponseEntity<String> deleteEquipa(@PathVariable Long id){
        ResponseEntity<String> response = equipaHandler.deleteEquipa(id);
        return response;
    }

    @DeleteMapping("/deleteByNome")
    @ApiOperation(value = "Delete Equipa", notes = "Delete a Equipa")
    public ResponseEntity<String> deleteEquipaByNome(@RequestParam String nome){
        EquipaDto equipaDto = equipaHandler.getEquipaByNome(nome);
        if (equipaDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Equipa não encontrada");
        }
        ResponseEntity<String> response = equipaHandler.deleteEquipa(equipaDto.getId());
        return response;
    }

    @GetMapping("/{id}/estatisticas")
    @ApiOperation(value = "Get all Estatisticas of an Equipa", notes = "Returns a list of all Estatisticas of the Equipa")
    public ResponseEntity<List<EstatisticasDto>> getAllEstatisticasEquipa(@PathVariable Long id){
        List<EstatisticasDto> estatisticas = estatisticasHandler.getAllEstatisticasEquipa(id);
        return ResponseEntity.ok(estatisticas);
    }

    @GetMapping("/{id}/campeonatos")
    @ApiOperation(value = "Get all Campeonatos of an Equipa", notes = "Returns a list of all Campeonatos of the Equipa")
    public ResponseEntity<List<CampeonatoPontosDto>> getAllCampeonatosEquipa(@PathVariable Long id){
        List<CampeonatoPontosDto> campeonatos = equipaHandler.getAllCampeonatosEquipa(id);
        return ResponseEntity.ok(campeonatos);
    }

    @GetMapping("/{id}/historicoCampeonatos")
    @ApiOperation(value = "Get all Historico of an Equipa", notes = "Returns the Historico of the Equipa")
    public ResponseEntity<List<EstatisticasEquipaDto>> getHistoricoEquipa(@PathVariable Long id){
        List<EstatisticasEquipaDto> historico = equipaHandler.getHistoricoEquipa(id);
        return ResponseEntity.ok(historico);
    }

    //PERGUNTAS EXTRA
    //Equipas que possuem menos de 5 jogadores
    @GetMapping("/menos-de-cinco-jogadores")
    @ApiOperation(value = "Get all Equipas with less than 5 Jogadores", notes = "Returns a list of the Equipas' names")
    public String getEquipasWithLessThanFiveJogadores() {
        String equipasMenos5 = equipaHandler.getEquipasWithLessThanFiveJogadores();   
        
        return equipasMenos5;
    }

    //Equipa que tem mais cartoes amarelos e vermelhos
    @GetMapping("/mais-cartoes")
    @ApiOperation(value = "Get all Equipas and their specific number of cards", notes = "Returns a list of the Equipas' names + number of cards")
    public String getEquipasComMaisCartoes() {
        String equipasComMaisCartoes = equipaHandler.getEquipasComMaisCartoes();
        
        return equipasComMaisCartoes;
    }

    //Top 5 Equipas que tem mais vitórias
    @GetMapping("/top5-mais-vitorias")
    @ApiOperation(value = "Get top5 Equipas with more victories", notes = "Returns a string with the Equipas' names + number of Victories")
    public String getEquipasComMaisVitorias() {
        String equipas = equipaHandler.getEquipasComMaisVitorias();

        return equipas;
    }    
}
