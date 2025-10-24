package pt.ul.fc.di.css.javafxexample.presentation.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Estatistica {
    
    private final StringProperty tipoDeEstatistica = new SimpleStringProperty();
    private final StringProperty jogador = new SimpleStringProperty();
    private final StringProperty equipa = new SimpleStringProperty();
    private final StringProperty campeonato = new SimpleStringProperty();
    private final StringProperty arbitro = new SimpleStringProperty();

    public final StringProperty tipoProperty() {
        return this.tipoDeEstatistica;
    }

    public final String getTipo() {
        return this.tipoProperty().get();
    }

    public final void setTipo(final String tipo) {
        this.tipoProperty().set(tipo);
    }

    public final StringProperty jogadorProperty() {
        return this.jogador;
    }

    public final String getJogador() {
        return this.jogadorProperty().get();
    }

    public final void setJogador(final String jogador) {
        this.jogadorProperty().set(jogador);
    }

    public final StringProperty equipaProperty() {
        return this.equipa;
    }

    public final String getEquipa() {
        return this.equipaProperty().get();
    }

    public final void setEquipa(final String equipa) {
        this.equipaProperty().set(equipa);
    }

    public final StringProperty campeonatoProperty() {
        return this.campeonato;
    }

    public final String getCampeonato() {
        return this.campeonatoProperty().get();
    }

    public final void setCampeonato(final String campeonato) {
        this.campeonatoProperty().set(campeonato);
    }

    public final StringProperty arbitroProperty() {
        return this.arbitro;
    }

    public final String getArbitro() {
        return this.arbitroProperty().get();
    }

    public final void setArbitro(final String arbitro) {
        this.arbitroProperty().set(arbitro);
    }

    public String toString() {
        return "Tipo de Estatística: " + tipoDeEstatistica.get() + "\n" + 
        "Jogador: " + jogador.get() + "\n" +
        "Equipa: " + equipa.get() + "\n" +
        "Campeonato: " + campeonato.get() + "\n" +
        "Arbitro: " + arbitro.get();
    }
}
