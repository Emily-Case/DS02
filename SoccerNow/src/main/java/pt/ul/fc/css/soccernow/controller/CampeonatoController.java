package pt.ul.fc.css.soccernow.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pt.ul.fc.css.soccernow.dto.CampeonatoPontosDto;
import pt.ul.fc.css.soccernow.dto.EquipaDto;
import pt.ul.fc.css.soccernow.dto.EstatisticasEquipaDto;
import pt.ul.fc.css.soccernow.entities.Campeonato;
import pt.ul.fc.css.soccernow.enums.EstadoEntidade;
import pt.ul.fc.css.soccernow.handlers.CampeonatoHandler;

@RestController
@RequestMapping("/api/campeonatos")
@Api(value = "Campeonato API", tags = "Campeonatos")
public class CampeonatoController {
    
    @Autowired
    private CampeonatoHandler campeonatoHandler;

    @PostMapping
    @ApiOperation(value = "Create Campeonato", notes = "Register a new Campeonato")
    public ResponseEntity<CampeonatoPontosDto> createCampeonato(@RequestBody CampeonatoPontosDto campeonatoDto) {

        Optional<Campeonato> campeonato = campeonatoHandler.findByNome(campeonatoDto.getNome());

        if (campeonato.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        else {
            CampeonatoPontosDto novoC = campeonatoHandler.createCampeonato(campeonatoDto);
            if(novoC == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            return ResponseEntity.ok(novoC);
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get Campeonato by ID", notes = "Returns a Campeonato given its ID.")
    public ResponseEntity<CampeonatoPontosDto> getCampeonatoById(@PathVariable("id") Long id) {
        CampeonatoPontosDto campeonatoPontosDto = campeonatoHandler.getCampeonatoById(id);

        if (campeonatoPontosDto != null && campeonatoPontosDto.getEstadoCampeonato() == EstadoEntidade.ATIVO) {
            return ResponseEntity.ok(campeonatoPontosDto);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    @ApiOperation(value = "Get all Campeonatos", notes = "Returns a list of all Campeonatos.")
    public ResponseEntity<List<CampeonatoPontosDto>> getAllCampeonatos(){
        List<CampeonatoPontosDto> campeonatos = campeonatoHandler.getAllCampeonatos();
        List<CampeonatoPontosDto> campeonatosAtivos = new ArrayList<>();
        for (CampeonatoPontosDto cDto : campeonatos) {
            if(cDto.getEstadoCampeonato() == EstadoEntidade.ATIVO) {
                campeonatosAtivos.add(cDto);
            }
        }

        return ResponseEntity.ok(campeonatosAtivos);
    }

    @PutMapping("/{id}/addEquipa")
    @ApiOperation(value = "Add Equipa to Campeonato", notes = "Add an Equipa to a Campeonato.")
    public ResponseEntity<CampeonatoPontosDto> addEquipaToCampeonato(@PathVariable("id") Long id, @RequestParam String nome) {
        CampeonatoPontosDto responseDto = campeonatoHandler.addEquipaToCampeonato(id, nome);
        if(responseDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}/removeEquipa")
    @ApiOperation(value = "Remove Equipa from Campeonato", notes = "Remove an Equipa from a Campeonato.")
    public ResponseEntity<CampeonatoPontosDto> removeEquipaFromCampeonato(@PathVariable("id") Long id, @RequestParam String nome) {
        CampeonatoPontosDto responseDto = campeonatoHandler.removeEquipaFromCampeonato(id, nome);
        if(responseDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/update/{id}")
    @ApiOperation(value = "Update Campeonato", notes = "Updates a Campeonato and returns the updated DTO.")
    public ResponseEntity<CampeonatoPontosDto> updateCampeonato(@PathVariable("id") Long id, @RequestBody CampeonatoPontosDto campeonatoDto) {
        CampeonatoPontosDto responseDto = campeonatoHandler.updateCampeonato(id, campeonatoDto);
        if(responseDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete Campeonato", notes = "Delete a Campeonato")
    public ResponseEntity<String> deleteCampeonato(@PathVariable Long id){
        ResponseEntity<String> response = campeonatoHandler.deleteCampeonato(id);
        return response;
    }

    @GetMapping("/{id}/equipas")
    @ApiOperation(value = "Get all Equipas of a Campeonato", notes = "Returns a list of all Equipas of the Campeonato")
    public ResponseEntity<List<EquipaDto>> getAllEquipasCampeonato(@PathVariable Long id){
        List<EquipaDto> equipas = campeonatoHandler.getAllEquipasCampeonato(id);
        return ResponseEntity.ok(equipas);
    }

    @GetMapping("/{id}/Tabela")
    @ApiOperation(value = "Get Tabela of Campeonato", notes = "Returns the Tabela of Campeonato with the given ID.")
    public ResponseEntity<List<EstatisticasEquipaDto>> getTabelaByCampeonatoId(@PathVariable("id") Long id) {
        List<EstatisticasEquipaDto> responseDto = campeonatoHandler.getTabelaByCampeonatoId(id);

        if (responseDto != null) {
            return ResponseEntity.ok(responseDto);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/TabelaString")
    @ApiOperation(value = "Get Tabela String of Campeonato", notes = "Returns the Tabela String of Campeonato with the given ID.")
    public ResponseEntity<String> getTabelaStringByCampeonatoId(@PathVariable("id") Long id) {
        ResponseEntity<String> response = campeonatoHandler.getStringTabelaByCampeonatoId(id);

        if (response != null) {
            return response;
        }
        return ResponseEntity.notFound().build();
    }
}
