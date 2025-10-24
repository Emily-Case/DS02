package pt.ul.fc.css.soccernow.dto;

import java.util.ArrayList;
import java.util.List;

public class EstatisticasEquipaDto {

    private Long id;

    private String equipa;

    private String campeonato;

    private int pontos;

    private int numVitorias;

    private int numEmpates; 

    private int numDerrotas;

    private List<EstatisticasDto> estatisticas = new ArrayList<>();

    //Construtor

    public EstatisticasEquipaDto(){}

    //Getters e setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEquipa() {
        return equipa;
    }

    public void setEquipa(String equipa) {
        this.equipa = equipa;
    }

    public String getCampeonato() {
        return campeonato;
    }

    public void setCampeonato(String campeonato) {
        this.campeonato = campeonato;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public int getNumVitorias() {
        return numVitorias;
    }

    public void setNumVitorias(int numVitorias) {
        this.numVitorias = numVitorias;
    }

    public int getNumEmpates() {
        return numEmpates;
    }

    public void setNumEmpates(int numEmpates) {
        this.numEmpates = numEmpates;
    }

    public int getNumDerrotas() {
        return numDerrotas;
    }

    public void setNumDerrotas(int numDerrotas) {
        this.numDerrotas = numDerrotas;
    }

    public List<EstatisticasDto> getEstatisticas() {
        return this.estatisticas;
    }

    public void setEstatisticas(List<EstatisticasDto> estatisticas) {
        this.estatisticas = estatisticas;
    }
}
