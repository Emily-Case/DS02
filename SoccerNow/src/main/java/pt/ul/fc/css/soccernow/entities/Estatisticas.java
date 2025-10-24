package pt.ul.fc.css.soccernow.entities;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
public abstract class Estatisticas {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Long version;

    @OneToOne
    private Jogador jogador;

    @OneToOne
    private Equipa equipa;

    @OneToOne
    private Campeonato campeonato;

    @ManyToOne
    private Jogo jogo;

    //Construtor

    public Estatisticas(){}
    
    //Getters e setters

    public Long getId() {
        return this.id;
    }

    public Jogador getJogador() {
        return this.jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public Equipa getEquipa() {
        return this.equipa;
    }

    public void setEquipa(Equipa equipa) {
        this.equipa = equipa;
    }

    public Campeonato getCampeonato() {
        return this.campeonato;
    }

    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
    }

    public Jogo getJogo() {
        return this.jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }
}

