package pt.ul.fc.css.soccernow.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import pt.ul.fc.css.soccernow.enums.EstadoEntidade;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Campeonato {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Long version;

    private String nome;

    private int ano;

    @ManyToMany(mappedBy = "campeonatos")
    private List<Equipa> equipas = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private EstadoEntidade status;

    //Construtores

    public Campeonato(){}

    public Campeonato(String nome, int ano){
        this.nome = nome;
        this.ano = ano;
    }

    //Getters e setters

    public Long getId() {
        return this.id;
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

    public int getAno() {
        return this.ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public List<Equipa> getEquipas() {
        return this.equipas;
    }

    public void setEquipas(List<Equipa> equipas) {
        this.equipas = equipas;
    }

    public void addEquipa(Equipa equipa) {
        this.equipas.add(equipa);
    }

    public void removeEquipa(Equipa equipa) {
        this.equipas.remove(equipa);
    }

    public EstadoEntidade getEstadoCampeonato() {
        return this.status;
    }

    public void setEstadoCampeonato(EstadoEntidade status) {
        this.status = status;
    }
}
