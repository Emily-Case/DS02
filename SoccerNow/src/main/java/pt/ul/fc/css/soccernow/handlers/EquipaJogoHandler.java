package pt.ul.fc.css.soccernow.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ul.fc.css.soccernow.dto.EquipaJogoDto;
import pt.ul.fc.css.soccernow.entities.Equipa;
import pt.ul.fc.css.soccernow.entities.EquipaJogo;
import pt.ul.fc.css.soccernow.entities.Jogador;
import pt.ul.fc.css.soccernow.entities.Jogo;
import pt.ul.fc.css.soccernow.entities.Utilizador;
import pt.ul.fc.css.soccernow.repository.EquipaJogoRepository;

@Service
public class EquipaJogoHandler {

    @Autowired
    private EquipaJogoRepository equipaJogoRepository;

    @Autowired
    private EquipaHandler equipaHandler;

    @Autowired
    private UtilizadorHandler utilizadorHandler;
    
    //Verificações Feitas
    public EquipaJogo createEquipaJogo(EquipaJogoDto ejDto) {

        if(ejDto.getNome_equipa() == null || ejDto.getNome_gk() == null || ejDto.getNomes_jogadores() == null || ejDto.getNomes_jogadores().size() != 5 || !ejDto.getNomes_jogadores().contains(ejDto.getNome_gk())) {
            return null;
        }

        EquipaJogo ej = new EquipaJogo();
        Optional<Equipa> equipaOptional = equipaHandler.findByNome(ejDto.getNome_equipa());

        if (equipaOptional.isPresent()) {
            ej.setEquipa(equipaOptional.get());
        } else {
            return null;
        }

        Equipa e = equipaOptional.get();

        List<Jogador> jogadores = new ArrayList<Jogador>();
        for (String nome : ejDto.getNomes_jogadores()) {
            Optional<Utilizador> u = utilizadorHandler.findByNomeAndType(nome, Jogador.class);
            if(!u.isPresent()){
                return null;
            }
            else {
                Jogador jogador = (Jogador) u.get();
                List<Equipa> equipas = jogador.getEquipas();
                Boolean belongs = false;
                for (Equipa equipa : equipas) {
                    if(equipa.getNome().equals(e.getNome())) {
                        belongs = true;
                    }
                }
                if(belongs) {
                    jogadores.add(jogador);
                }
                else {
                    return null;
                }
            }
        }

        ej.setJogadores(jogadores);

        Jogador gk = (Jogador) utilizadorHandler.findByNomeAndType(ejDto.getNome_gk(), Jogador.class).get();
        ej.setGK(gk);

        EquipaJogo savedEj = equipaJogoRepository.save(ej);

        return savedEj;
    }

    //Verificações Feitas
    public EquipaJogo updateJogo(Long id, Jogo jogo) {

        if(jogo == null) {
            return null;
        }

        Optional<EquipaJogo> ej = equipaJogoRepository.findById(id);
        if (ej.isEmpty()) {
            return null;
        }
        EquipaJogo equipa = ej.get();
        equipa.setJogo(jogo);
        EquipaJogo savedEj = equipaJogoRepository.save(equipa);
        
        return savedEj;
    }

    //Verificações Feitas
    public EquipaJogoDto getEJById(Long ej_id) {
        Optional<EquipaJogo> ej = equipaJogoRepository.findById(ej_id);
        if (ej.isEmpty()) {
            return null;
        }
        EquipaJogoDto ejDto = mapToDto(ej.get());
        return ejDto;
    }

    //Verificações Feitas
    public List<EquipaJogoDto> getAllEJs() {
        List<EquipaJogo> ejs = equipaJogoRepository.findAll();
        List<EquipaJogoDto> ejsDto = new ArrayList<>();
        for(EquipaJogo ej : ejs){
            ejsDto.add(mapToDto(ej));
        }
        return ejsDto;
    }

    //Verificações Feitas
    public EquipaJogoDto updateEJ(Long ej_Id, EquipaJogoDto ejDto) {

        if(ejDto.getId_jogo() == 0 || ejDto.getNome_equipa() == null || ejDto.getNome_gk() == null || ejDto.getNomes_jogadores() == null || ejDto.getNomes_jogadores().size() != 5 || !ejDto.getNomes_jogadores().contains(ejDto.getNome_gk())) {
            return null;
        }

        Optional<EquipaJogo> ejOp = equipaJogoRepository.findById(ej_Id);
        if(!ejOp.isPresent()) {
            return null;
        }

        EquipaJogo ej = ejOp.get();

        Optional<Equipa> equipaOptional = equipaHandler.findByNome(ejDto.getNome_equipa());

        if (equipaOptional.isPresent()) {
            ej.setEquipa(equipaOptional.get());
        } else {
            return null;
        }
        
        Equipa e = equipaOptional.get();

        List<Jogador> jogadores = new ArrayList<Jogador>();
        for (String nome : ejDto.getNomes_jogadores()) {
            Optional<Utilizador> u = utilizadorHandler.findByNomeAndType(nome, Jogador.class);
            if(!u.isPresent()){
                return null;
            }
            else {
                Jogador jogador = (Jogador) u.get();
                List<Equipa> equipas = jogador.getEquipas();
                Boolean belongs = false;
                for (Equipa equipa : equipas) {
                    if(equipa.getNome().equals(e.getNome())) {
                        belongs = true;
                    }
                }
                if(belongs) {
                    jogadores.add(jogador);
                }
                else {
                    return null;
                }
            }
        }

        ej.setJogadores(jogadores);

        Jogador gk = (Jogador) utilizadorHandler.findByNomeAndType(ejDto.getNome_gk(), Jogador.class).get();
        ej.setGK(gk);

        EquipaJogo savedEj = equipaJogoRepository.save(ej);

        EquipaJogoDto novoEjDto = mapToDto(savedEj);

        return novoEjDto;
    }

    //Passa uma EquipaJogo para o seu Dto
    public EquipaJogoDto mapToDto(EquipaJogo ej){
        EquipaJogoDto ejDTO = new EquipaJogoDto();
        ejDTO.setId(ej.getId());

        if(ej.getEquipa() != null) {
            ejDTO.setNome_equipa(ej.getEquipa().getNome());
        }

        else {
            ejDTO.setNome_equipa(null);
        }      

        List<String> jogadores = new ArrayList<>();
        for (Jogador jogador: ej.getJogadores()) {
            jogadores.add(jogador.getNome());
        }

        ejDTO.setNomes_jogadores(jogadores);

        if(ej.getGK() != null){
            ejDTO.setNome_gk(ej.getGK().getNome());
        }

        ejDTO.setId_jogo(ej.getJogo().getId());

        return ejDTO;
    }

    public void updateEquipa(EquipaJogo ej) {
        equipaJogoRepository.save(ej);
    }

}

