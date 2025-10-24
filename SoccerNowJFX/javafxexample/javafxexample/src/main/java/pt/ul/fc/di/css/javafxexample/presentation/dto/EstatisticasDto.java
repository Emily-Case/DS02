package pt.ul.fc.di.css.javafxexample.presentation.dto;

import pt.ul.fc.di.css.javafxexample.presentation.enums.EstatisticaTipo;

public class EstatisticasDto {
    
    private Long id;

    private EstatisticaTipo tipoDeEstatistica;

    private String jogador;

    private String equipa;

    private String campeonato;

    private String arbitro;

    //Construtor

    public EstatisticasDto() {}

    //Getters e setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EstatisticaTipo getTipoDeEstatistica() {
        return this.tipoDeEstatistica;
    }

    public void setTipoDeEstatistica(EstatisticaTipo tipoDeEstatistica) {
        this.tipoDeEstatistica = tipoDeEstatistica;
    }

    public String getJogador() {
        return this.jogador;
    }

    public void setJogador(String jogador) {
        this.jogador = jogador;
    }

    public String getEquipa() {
        return this.equipa;
    }

    public void setEquipa(String equipa) {
        this.equipa = equipa;
    }

    public String getCampeonato() {
        return this.campeonato;
    }

    public void setCampeonato(String campeonato) {
        this.campeonato = campeonato;
    }

    public String getArbitro() {
        return this.arbitro;
    }

    public void setArbitro(String arbitro) {
        this.arbitro = arbitro;
    }
}
