package pt.ul.fc.di.css.javafxexample.presentation.dto;

import java.util.ArrayList;
import java.util.List;

import pt.ul.fc.di.css.javafxexample.presentation.enums.EstadoEntidade;
import pt.ul.fc.di.css.javafxexample.presentation.enums.Posicao;



public class JogadorDto {

    //Atributos

    private Long id;
    private int nif;
    private String nome;
    private String password;
    private int numJogos;
    private Posicao posicao;
    private List<String> nomesEquipas = new ArrayList<String>();
    private EstadoEntidade status;

    //Construtores

    public JogadorDto(){}

    public JogadorDto(Long id, int nif, String nome, String pswd, int numJogos, Posicao posicao, List<String> nomesEquipas) {

        this.id = id;
        this.nif = nif;
        this.nome = nome;
        this.password = pswd;
        this.numJogos = numJogos;
        this.posicao = posicao;
        this.nomesEquipas = nomesEquipas;

    }
    
    // Getters e Setters

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNif() {
        return this.nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nomeNovo) {
        this.nome = nomeNovo;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }

    
    public int getNumJogos() {
        return this.numJogos;
    }

    public void setNumJogos(int numJogosNovo) {
        this.numJogos = numJogosNovo;
    }

    public Posicao getPosicao() {
        return this.posicao;
    }

    public void setPosicao(Posicao posicaoNova) {
        this.posicao = posicaoNova;
    }

    public List<String> getNomesEquipas() {
        return this.nomesEquipas;
    }

    public void setNomesEquipas(List<String> nomesEquipasNovas) {
        this.nomesEquipas = nomesEquipasNovas;
    }

    public EstadoEntidade getEstadoJogador() {
        return this.status;
    }

    public void setEstadoJogador(EstadoEntidade status) {
        this.status = status;
    }
}
