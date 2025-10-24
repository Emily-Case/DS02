package pt.ul.fc.css.soccernow.entities;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import pt.ul.fc.css.soccernow.enums.Status;

@Entity
public class Jogo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Version
    private Long version;
    
    private LocalDate data;

    private LocalDateTime horario;

    private String localizacao;

    @OneToOne
    private Campeonato campeonato;

    @OneToMany(mappedBy = "jogo", fetch = FetchType.EAGER)
    private List<EquipaJogo> equipas = new ArrayList<>();

    @OneToOne
    private Arbitro arbitroPrincipal;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Arbitro> arbitros = new ArrayList<>();

    @OneToMany(mappedBy = "jogo", fetch = FetchType.EAGER)
    private List<Estatisticas> estatisticas = new ArrayList<>();

    private String placarFinal;

    @OneToOne
    private Equipa vencedor;

    @Enumerated(EnumType.ORDINAL)
    private Status status;

    //Construtor

    public Jogo(){}

    //Getters e setters

    public void setData(LocalDate data){
        this.data = data;
    }

    public void setHorario(LocalDateTime horario){
        this.horario = horario;
    }

    public void setLocalizacao(String localizacao){
        this.localizacao = localizacao;
    }

    public void setCampeonato(Campeonato campeonato){
        this.campeonato = campeonato;
    }

    public void setEquipas(List<EquipaJogo> equipas){
        this.equipas = equipas;
    }

    public void setArbitroPrincipal(Arbitro arbitro){
        this.arbitroPrincipal = arbitro;
    }

    public void setArbitros(List<Arbitro> arbitros){
        this.arbitros = arbitros;
    }

    public void setEstatisticas(List<Estatisticas> estatisticas){
        this.estatisticas = estatisticas;
    }

    public void setPlacarFinal(String placarFinal){
        this.placarFinal = placarFinal;
    }

    public void setVencedor(Equipa vencedor){
        this.vencedor = vencedor;
    }

    public void setStatus(Status status){
        this.status = status;
    }

    public Long getId(){
        return this.id;
    }

    public LocalDate getData(){
        return this.data;
    }

    public LocalDateTime getHorario(){
        return this.horario;
    }

    public String getLocalizacao(){
        return this.localizacao;
    }

    public Campeonato getCampeonato(){
        return this.campeonato;
    }

    public List<EquipaJogo> getEquipas(){
        return this.equipas;
    }

    public Arbitro getArbitroPrincipal(){
        return this.arbitroPrincipal;
    }

    public List<Arbitro> getArbitros(){
        return this.arbitros;
    }

    public List<Estatisticas> getEstatisticas(){
        return this.estatisticas;
    }

    public String getPlacarFinal(){
        return this.placarFinal;
    }

    public Equipa getVencedor(){
        return this.vencedor;
    }

    public Status getStatus(){
        return this.status;
    }
}
