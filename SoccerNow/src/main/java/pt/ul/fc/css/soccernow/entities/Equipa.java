package pt.ul.fc.css.soccernow.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import pt.ul.fc.css.soccernow.enums.EstadoEntidade;

@Entity
public class Equipa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Long version;

    private String nome;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Jogo> historicoJogos = new ArrayList<Jogo>();

    @OneToMany(mappedBy = "equipa", fetch = FetchType.EAGER)
    private List<EstatisticasEquipa> historicoResultados = new ArrayList<EstatisticasEquipa>();

    @ManyToMany(mappedBy = "equipas", fetch = FetchType.EAGER)
    private List<Jogador> jogadores = new ArrayList<Jogador>();

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Campeonato> campeonatos = new ArrayList<Campeonato>();

    @Enumerated(EnumType.STRING)
    private EstadoEntidade status;

    //Construtores

    public Equipa(){}

    public Equipa(String nome) {
        this.nome = nome;
    }

    //Getters e setters

    public Long getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Jogo> getHistoricoJogos() {
        return this.historicoJogos;
    }

    public void addJogo(Jogo jogo) {
        this.historicoJogos.add(jogo);
    }

    public List<EstatisticasEquipa> getHistoricoResultados() {
        return this.historicoResultados;
    }

    public void addResultado(EstatisticasEquipa resultado) {
        this.historicoResultados.add(resultado);
    }

    public void removeResultado(EstatisticasEquipa resultado) {
        this.historicoResultados.remove(resultado);
    }

    public List<Jogador> getJogadores() {
        return this.jogadores;
    }

    public void setJogador(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }

    public void addJogador(Jogador jogador) {
        this.jogadores.add(jogador);
    }

    public void removeJogador(Jogador jogador) {
        this.jogadores.remove(jogador);
    }

    public List<Campeonato> getCampeonatos() {
        return this.campeonatos;
    }

    public void addCampeonato(Campeonato campeonato) {
        this.campeonatos.add(campeonato);
    }

    public void removeCampeonato(Campeonato campeonato) {
        this.campeonatos.remove(campeonato);
    }

    public void setCampeonatos(List<Campeonato> campeonatos) {
        this.campeonatos = campeonatos;
    }

    public EstadoEntidade getEstadoEquipa() {
        return this.status;
    }

    public void setEstadoEquipa(EstadoEntidade status) {
        this.status = status;
    }
}
