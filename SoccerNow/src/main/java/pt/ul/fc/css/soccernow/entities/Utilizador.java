package pt.ul.fc.css.soccernow.entities;

import jakarta.persistence.*;
import pt.ul.fc.css.soccernow.enums.EstadoEntidade;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
public abstract class Utilizador {

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Long version;

    private int nif;

    private String nome;
    
    private String password;

    private int numJogos;

    @Enumerated(EnumType.STRING)
    private EstadoEntidade status;

    //Construtores

    public Utilizador(){}

    public Utilizador(String nome, String password){
        this.nome = nome;
        this.password = password;
        this.numJogos = 0;
    }

    //Getters e setters
    
    public Long getId(){
        return this.id;
    }

    public int getNif() {
        return this.nif;
    }
    
    public String getNome(){
        return this.nome;
    }

    public String getPassword(){
        return this.password;
    }

    public int getNumJogos(){
        return this.numJogos;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public void setNome(String novoNome){
        this.nome = novoNome;
    }

    public void setNumJogos(int numJogos){
        this.numJogos = numJogos;
    }

    public void incNumJogos() {
        this.numJogos++;
    }

    public void setPassword(String novaPassword){
        this.password = novaPassword;
    }

    public EstadoEntidade getEstadoUtilizador() {
        return this.status;
    }
    
    public void setEstadoUtilizador(EstadoEntidade status) {
        this.status =  status;
    }
}
