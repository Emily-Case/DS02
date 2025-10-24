package pt.ul.fc.css.soccernow.entities;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import pt.ul.fc.css.soccernow.enums.Posicao;  

@Entity
@DiscriminatorValue("JOGADOR")
public class Jogador extends Utilizador{ 

    //Atributos
    
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Equipa> equipas = new ArrayList<Equipa>();

    @Enumerated(EnumType.STRING)
    private Posicao posicao;

    //Construtores

    public Jogador(){}

    public Jogador(String nome, String password){
        super(nome, password);
    }

    //Getters e setters
    public Posicao getPosicao(){
        return this.posicao;
    }

    public void setPosicao(Posicao novaPosicao){
        this.posicao = novaPosicao;
    }

    public List<Equipa> getEquipas() {
        return this.equipas;
    }
    
    public void setEquipas(List<Equipa> novasEquipas) {
        this.equipas = novasEquipas;
    }
    
    public void addEquipa(Equipa equipa) {
        this.equipas.add(equipa);
    }   
    
    public void removeEquipa(Equipa equipa) {
        this.equipas.remove(equipa);
    }

    @Override
    public String toString(){
        return "Jogador{ Nome: " + this.getNome() + "\n" +
               "Password: " + this.getPassword() + "\n" +
               "Posicao: " + stringPosicao(this.getPosicao()) + "\n" +
               "Número de jogos: " + this.getNumJogos() + "\n" +
               "Id do Jogador: " + this.getId() + "}";
    }
    
    public String stringPosicao(Posicao p) {
        if (p.equals(Posicao.ATQ)){
            return "Atacante";
        } else if (p.equals(Posicao.DEF)){
            return "Defesa";
        } else if (p.equals(Posicao.MED)){
            return "Médio";
        } else if (p.equals(Posicao.GK)){
            return "Guarda Redes";
        }
        return "Sem posição";
    }
    
}