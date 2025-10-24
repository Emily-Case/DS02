package pt.ul.fc.css.soccernow.dto;

import java.util.ArrayList;
import java.util.List;


public class EquipaJogoDto {

    private Long id;

    private String nome_equipa;

    private List<String> nomes_jogadores = new ArrayList<>();

    private String nome_gk;

    private Long id_jogo;

    //Construtor

    public EquipaJogoDto(){}
    
    //Getters e setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome_equipa() {
        return nome_equipa;
    }

    public void setNome_equipa(String nome_equipa) {
        this.nome_equipa = nome_equipa;
    }

    public List<String> getNomes_jogadores() {
        return nomes_jogadores;
    }

    public void setNomes_jogadores(List<String> nomes_jogadores) {
        this.nomes_jogadores = nomes_jogadores;
    }

    public String getNome_gk() {
        return nome_gk;
    }

    public void setNome_gk(String nome_gk) {
        this.nome_gk = nome_gk;
    }

    public Long getId_jogo() {
        return id_jogo;
    }

    public void setId_jogo(Long id_jogo) {
        this.id_jogo = id_jogo;
    }
}
