package pt.ul.fc.css.soccernow.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class EstatisticasEquipa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Long version;

    @ManyToOne
    private Equipa equipa;

    @ManyToOne
    private Campeonato campeonato;

    private int pontos;

    private int numVitorias;

    private int numEmpates; 

    private int numDerrotas;

    @OneToMany
    @JoinColumn(name = "EstatisticasEquipa_id")
    private List<Estatisticas> estatisticas = new ArrayList<>();

    //Construtor

    public EstatisticasEquipa(){}

    //Getters e setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Equipa getEquipa() {
        return equipa;
    }

    public void setEquipa(Equipa equipa) {
        this.equipa = equipa;
    }

    public Campeonato getCampeonato() {
        return campeonato;
    }

    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public void addPontos(int pontos) {
        this.pontos = this.pontos + pontos;
    }

    public int getNumVitorias() {
        return numVitorias;
    }

    public void setNumVitorias(int numVitorias) {
        this.numVitorias = numVitorias;
    }

    public void incNumVitorias() {
        this.numVitorias++;
    }

    public int getNumEmpates() {
        return numEmpates;
    }

    public void setNumEmpates(int numEmpates) {
        this.numEmpates = numEmpates;
    }

    public void incNumEmpates() {
        this.numEmpates++;
    }

    public int getNumDerrotas() {
        return numDerrotas;
    }

    public void setNumDerrotas(int numDerrotas) {
        this.numDerrotas = numDerrotas;
    }

    public void incNumDerrotas() {
        this.numDerrotas++;
    }

    public List<Estatisticas> getEstatisticas() {
        return estatisticas;
    }

    public void setEstatisticas(List<Estatisticas> estatisticas) {
        this.estatisticas = estatisticas;
    }

    public void addEstatistica(Estatisticas estatistica) {
        this.estatisticas.add(estatistica);
    }
}
