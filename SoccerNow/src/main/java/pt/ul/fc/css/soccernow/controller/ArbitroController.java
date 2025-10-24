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
import pt.ul.fc.css.soccernow.dto.ArbitroDto;
import pt.ul.fc.css.soccernow.dto.EstatisticasDto;
import pt.ul.fc.css.soccernow.entities.Arbitro;
import pt.ul.fc.css.soccernow.entities.Utilizador;
import pt.ul.fc.css.soccernow.enums.EstadoEntidade;
import pt.ul.fc.css.soccernow.handlers.EstatisticasHandler;
import pt.ul.fc.css.soccernow.handlers.UtilizadorHandler;

@RestController
@RequestMapping("/api/arbitros")
@Api(value = "Arbitro API", tags = "Arbitros")
public class ArbitroController {
    
    @Autowired
    private UtilizadorHandler utilizadorHandler;

    @Autowired
    private EstatisticasHandler estatisticasHandler;

    //Verificações Feitas
    @PostMapping("/login")
    @ApiOperation(value = "Login Arbitro", notes = "Logs in a new Arbitro")
    public ResponseEntity<String> arbitroLogin(@RequestParam String nome, @RequestParam String password) {
        
        Optional<Utilizador> utilizador = utilizadorHandler.findByNomeAndType(nome, Arbitro.class);
        
        if (utilizador.isPresent()) {
            return ResponseEntity.ok("Player login successful (mock)");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Player not found");
    }

    //Verificações Feitas
    @PostMapping
    @ApiOperation(value = "Create Arbitro", notes = "Register a new Arbitro")
    public ResponseEntity<ArbitroDto> createArbitro(@RequestBody ArbitroDto arbitroDTO) {

        Optional<Utilizador> utilizador = utilizadorHandler.findByNif(arbitroDTO.getNif());

        if (utilizador.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        else {
            ArbitroDto novoA = utilizadorHandler.createArbitro(arbitroDTO);
            if(novoA == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            return ResponseEntity.ok(novoA);
        }
    }

    @GetMapping("/arbitro")
    @ApiOperation(value = "Get Arbitro by Nome", notes = "Returns a Arbitro by their name")
    public ResponseEntity<Arbitro> getArbitroByNome(@RequestParam String nome){
        try {
            Arbitro arbitro = utilizadorHandler.findByArbitroName(nome).get();

            return ResponseEntity.ok(arbitro);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //Verificações Feitas
    @GetMapping("/{id}")
    @ApiOperation(value = "Get Arbitro", notes = "Returns a specific registered Arbitro")
    public ResponseEntity<ArbitroDto> getArbitro(@PathVariable Long id){
        try {
            ArbitroDto arbitroDto = utilizadorHandler.findArbitroById(id);
            //Se o arbitro foi deleted, não vai ser devolvido
            if(arbitroDto.getEstadoArbitro() == EstadoEntidade.INATIVO) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(arbitroDto);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //Verificações Feitas
    @GetMapping
    @ApiOperation(value = "Get All Arbitros", notes = "Returns a list of all registered Arbitros")
    public ResponseEntity<List<ArbitroDto>> getAllArbitros() {
        List<ArbitroDto> arbitros = utilizadorHandler.getAllArbitros();
        List<ArbitroDto> arbitrosAtivos = new ArrayList<>();
        for (ArbitroDto aDto : arbitros) {
            //Verificar se Arbitro já foi deleted
            if(aDto.getEstadoArbitro() == EstadoEntidade.ATIVO) {
                arbitrosAtivos.add(aDto);
            }
        }

        return ResponseEntity.ok(arbitrosAtivos);
    }

    //Verificações Feitas
    @PutMapping("/{id}")
    @ApiOperation(value = "Updates partially an Arbitro")
    public ResponseEntity<ArbitroDto> updateArbitro(@PathVariable Long id, @RequestBody ArbitroDto arbitroDTO) {
        Optional<ArbitroDto> arbitroAtualizado = utilizadorHandler.updateArbitro(id, arbitroDTO);

        if (arbitroAtualizado.isPresent()) {
            return ResponseEntity.ok(arbitroAtualizado.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete Arbitro", notes = "Delete a registered Arbitro")
    public ResponseEntity<String> deleteArbitro(@PathVariable Long id){
        boolean deletedA = utilizadorHandler.deleteArbitro(id);

        if (deletedA) {
            return ResponseEntity.ok("Arbitro Eliminado");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Arbitro não encontrado");
        }
    }

    @DeleteMapping("/deleteByNif")
    @ApiOperation(value = "Delete Arbitro", notes = "Delete a registered Arbitro")
    public ResponseEntity<String> deleteArbitroByNif(@RequestParam int nif){
        boolean deletedA = utilizadorHandler.deleteArbitroByNif(nif);

        if (deletedA) {
            return ResponseEntity.ok("Arbitro Eliminado");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Arbitro não encontrado");
        }
    }

    //PERGUNTA EXTRA
    //Arbitro que oficiou o maior numero de jogos
    @GetMapping("/mais-jogos")
    @ApiOperation(value = "Get Árbitro com mais jogos", notes = "Retorna o árbitro que oficializou mais jogos")
    public ResponseEntity<String> getArbitroComMaisJogos() {
        Optional<ArbitroDto> arbitro = utilizadorHandler.getArbitroComMaisJogos();

        if (arbitro.isPresent()) {
            return ResponseEntity.ok("O Arbitro com mais jogos é " + arbitro.get().getNome() + ". Esteve presente em " + arbitro.get().getNumJogos() + " jogos.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum árbitro encontrado");
        }
    }

    @GetMapping("/{id}/estatisticas")
    @ApiOperation(value = "Get all Estatisticas of a Arbitro", notes = "Returns a list of all Estatisticas of the Arbitro")
    public ResponseEntity<List<EstatisticasDto>> getAllEstatisticasArbitro(@PathVariable Long id){
        List<EstatisticasDto> estatisticas = estatisticasHandler.getAllEstatisticasArbitro(id);
        return ResponseEntity.ok(estatisticas);
    }

    

    //Funções Auxiliares
    /*public static ArbitroDTO transformIntoDTO(Arbitro arbitro) {
        ArbitroDto dto = new ArbitroDto();
        
        dto.setId(arbitro.getId());
        dto.setNome(arbitro.getNome());
        dto.setPassword(arbitro.getPassword());
        dto.setNumJogos(arbitro.getNumJogos());
        dto.setCertificado(arbitro.getCertificado());
        
        return dto;
    }*/
    
}
