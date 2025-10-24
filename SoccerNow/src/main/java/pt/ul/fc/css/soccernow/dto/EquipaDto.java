package pt.ul.fc.css.soccernow.dto;

import java.util.ArrayList;
import java.util.List;

import pt.ul.fc.css.soccernow.enums.EstadoEntidade;

public class EquipaDto {

    private Long id;

    private String nome;

    private List<String> jogadores = new ArrayList<>();

    private List<String> campeonatos = new ArrayList<>();

    private EstadoEntidade status;

    //Construtores

    public EquipaDto(){}

    public EquipaDto(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    //Getters e setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getJogadores() {
        return this.jogadores;
    }

    public void addJogador(String jogador) {
        this.jogadores.add(jogador);
    }

    public List<String> getCampeonatos() {
        return this.campeonatos;
    }

    public void addCampeonato(String nome) {
        this.campeonatos.add(nome);
    }

    public EstadoEntidade getEstadoEquipa() {
        return this.status;
    }

    public void setEstadoEquipa(EstadoEntidade status) {
        this.status = status;
    }
}
