package pt.ul.fc.di.css.javafxexample.presentation.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Campeonato {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nome = new SimpleStringProperty();
    private final IntegerProperty ano = new SimpleIntegerProperty();
    private final List<StringProperty> equipas = new ArrayList<>();
    private final StringProperty status = new SimpleStringProperty();

    public Campeonato(){}

    public final IntegerProperty idProperty() {
        return this.id;
    }

    public final Integer getId() {
        return this.idProperty().get();
    }

    public final void setId(final Integer id) {
        this.idProperty().set(id);
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

    public final IntegerProperty anoProperty() {
        return this.ano;
    }

    public final Integer getAno() {
        return this.anoProperty().get();
    }

    public final void setAno(final Integer ano) {
        this.anoProperty().set(ano);
    }

    public final List<StringProperty> getEquipas() {
        return this.equipas;
    }

    public final void addNomeEquipa(final String nomeEquipa) {
        this.equipas.add(new SimpleStringProperty(nomeEquipa));
    }

    public final void setEquipas(final List<String> equipas) {
        this.equipas.clear(); // Limpa a lista atual
        for (String equipa : equipas) {
            this.equipas.add(new SimpleStringProperty(equipa));
        }
    }

    public final StringProperty statusProperty() {
        return this.status;
    }

    public final String getStatus() {
        return this.statusProperty().get();
    }

    public final void setStatus(final String status) {
        this.statusProperty().set(status);
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Id: ").append(getId()).append("\n")
            .append("Nome: ").append(getNome()).append("\n")
            .append("Ano: ").append(getAno()).append("\n")
            .append("Equipas: ");

        if (!equipas.isEmpty()) {
            for (StringProperty equipa : equipas) {
                result.append("\n  - ").append(equipa.get());
            }
        } else {
            result.append("Nenhuma equipa.");
        }

        result.append("\nEstado: ").append(getStatus());
        return result.toString();
    }

    public String getSimpleRepresentation() {
        return "Id: " + getId() + " - Nome: " + getNome();
    }
}
