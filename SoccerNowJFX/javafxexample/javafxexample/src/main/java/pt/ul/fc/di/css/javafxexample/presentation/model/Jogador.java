package pt.ul.fc.di.css.javafxexample.presentation.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Jogador {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final IntegerProperty nif = new SimpleIntegerProperty();
    private final StringProperty nome = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();
    private final IntegerProperty numJogos = new SimpleIntegerProperty();
    private final StringProperty posicao = new SimpleStringProperty();
    private final List<StringProperty> equipas = new ArrayList<>();

    public Jogador(){}

    public final IntegerProperty idProperty() {
        return this.id;
    }

    public final Integer getId() {
        return this.idProperty().get();
    }

    public final void setId(final Integer id) {
        this.idProperty().set(id);
    }

    public final IntegerProperty nifProperty() {
        return this.nif;
    }

    public final Integer getNIF() {
        return this.nifProperty().get();
    }

    public final void setNIF(final Integer nif) {
        this.nifProperty().set(nif);
    }

    public final StringProperty nomeProperty() {
        return this.nome;
    }

    public final String getNome() {
        return this.nomeProperty().get();
    }

    public final void setNome(final String nome) {
        this.nomeProperty().set(nome);
    }

    public final StringProperty passwordProperty() {
        return this.password;
    }

    public final String getPassword() {
        return this.passwordProperty().get();
    }

    public final void setPassword(final String password) {
        this.passwordProperty().set(password);
    }

    public final IntegerProperty numJogosProperty() {
        return this.numJogos;
    }

    public final Integer getNumJogos() {
        return this.numJogosProperty().get();
    }

    public final void setNumJogos(final Integer numJogos) {
        this.numJogosProperty().set(numJogos);
    }

    public final StringProperty posicaoProperty() {
        return this.posicao;
    }

    public final String getPosicao() {
        return this.posicaoProperty().get();
    }

    public final void setPosicao(final String posicao) {
        this.posicaoProperty().set(posicao);
    }

    public final List<StringProperty> equipasPropertyList() {
        return this.equipas;
    }

    public final List<String> getequipasStrings() {
        List<String> equipasStrings = new ArrayList<>();
        for (StringProperty equipa : this.equipas) {
            equipasStrings.add(equipa.get());
        }
        return equipasStrings;
    }

    public final void setEquipas(final List<String> equipas) {
        this.equipas.clear(); // Limpa a lista atual
        for (String equipa : equipas) {
            this.equipas.add(new SimpleStringProperty(equipa));
        }
    }

    public String toString(){
        String result = "NIF: "+ getNIF() + "\n" +"Nome: " + getNome() + "\n" + "Posição: " + getPosicao() + "\n" + "Equipas:" + "\n" + getequipasStrings();
        
        return  result;
    }

}
