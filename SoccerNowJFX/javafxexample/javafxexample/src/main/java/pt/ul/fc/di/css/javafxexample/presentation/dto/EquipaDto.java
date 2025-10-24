package pt.ul.fc.di.css.javafxexample.presentation.dto;

import java.util.ArrayList;
import java.util.List;

import pt.ul.fc.di.css.javafxexample.presentation.enums.EstadoEntidade;

public class EquipaDto {

    private Long id;

    private String nome;

    private List<String> jogadores = new ArrayList<>();

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

    public EstadoEntidade getEstadoEquipa() {
        return this.status;
    }

    public void setEstadoEquipa(EstadoEntidade status) {
        this.status = status;
    }
}
