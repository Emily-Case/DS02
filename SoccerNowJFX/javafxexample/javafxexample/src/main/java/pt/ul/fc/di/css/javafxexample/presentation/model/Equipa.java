package pt.ul.fc.di.css.javafxexample.presentation.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Equipa {
    private final StringProperty nome = new SimpleStringProperty();
    private final List<StringProperty> jogadores = new ArrayList<>();

    public final StringProperty nomeProperty() {
        return this.nome;
    }

    public final String getNome() {
        return this.nomeProperty().get();
    }

    public final void setNome(final String nome) {
        this.nomeProperty().set(nome);
    }

     public final List<StringProperty> jogadoresPropertyList() {
        return this.jogadores;
    }

    public final List<String> getJogadoresStrings() {
        List<String> jogadoresStrings = new ArrayList<>();
        for (StringProperty jogador : this.jogadores) {
            jogadoresStrings.add(jogador.get());
        }
        return jogadoresStrings;
    }

    public final void setJogadores(final List<String> jogadores) {
        this.jogadores.clear(); // Limpa a lista atual
        for (String jogador : jogadores) {
            this.jogadores.add(new SimpleStringProperty(jogador));
        }
    }

    public String toString(){
        String result = "Nome: " + getNome() + "\n" + "Jogadores: " + "\n" + getJogadoresStrings();
        
        return  result;
    }

    
}
