package pt.ul.fc.css.soccernow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pt.ul.fc.css.soccernow.dto.EquipaJogoDto;
import pt.ul.fc.css.soccernow.handlers.EquipaJogoHandler;

@RestController
@RequestMapping("/api/equipaJogos")
@Api(value = "EquipaJogo API", tags = "EquipaJogos")
public class EquipaJogoController {

    @Autowired
    EquipaJogoHandler equipaJogoHandler;

    //Verificações Feitas
    @GetMapping("/{ej_id}")
    @ApiOperation(value = "Gets a specific EquipaJogo", notes = "Returns a specific registered EquipaJogo")
    public ResponseEntity<EquipaJogoDto> getJogo(@PathVariable Long ej_id){
        EquipaJogoDto ejDTO = equipaJogoHandler.getEJById(ej_id);
        
        if(ejDTO != null){
            return ResponseEntity.ok(ejDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //Verificações Feitas
    @GetMapping
    @ApiOperation(value = "Get all Equipa Jogos", notes = "Returns a list of all Equipa Jogos.")
    public ResponseEntity<List<EquipaJogoDto>> getAllEJs(){
        List<EquipaJogoDto> ejs = equipaJogoHandler.getAllEJs();
        return ResponseEntity.ok(ejs);
    }

    //Verificações Feitas
    @PutMapping("/{ej_Id}/updateEJ")
    @ApiOperation(value = "Updates EquipaJogo", notes = "Updates EquipaJogo")
    public ResponseEntity<EquipaJogoDto> updateJogo(@PathVariable Long ej_Id, @RequestBody EquipaJogoDto ejDto){
        EquipaJogoDto ejDTO = equipaJogoHandler.updateEJ(ej_Id,ejDto);
        if(ejDTO == null) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
        return ResponseEntity.ok(ejDTO);
    }
    
}
