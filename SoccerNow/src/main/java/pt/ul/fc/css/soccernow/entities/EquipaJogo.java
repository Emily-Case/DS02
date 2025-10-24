package pt.ul.fc.css.soccernow.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class EquipaJogo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Long version;

    @OneToOne
    private Equipa equipa;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Jogador> jogadores = new ArrayList<>();

    @OneToOne
    private Jogador gk;

    @ManyToOne
    private Jogo jogo;

    //Construtores

    public EquipaJogo(){}

    //Getters e setters

    public String getNome() {
        return this.equipa.getNome();
    }

    public Long getId() {
        return id;
    }

    public Equipa getEquipa(){
        return this.equipa;
    }

    public List<Jogador> getJogadores(){
        return this.jogadores;
    }

    public Jogador getGK(){
        return this.gk;
    }

    public Jogo getJogo(){
        return this.jogo;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setEquipa(Equipa equipa) {
        this.equipa = equipa;
    }

    public void setJogadores(List<Jogador> jogadores){
        this.jogadores = jogadores;
    }

    public void setGK(Jogador gk){
        this.gk = gk;
    }

    public void setJogo(Jogo jogo){
        this.jogo = jogo;
    }
}
