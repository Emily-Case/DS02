package pt.ul.fc.di.css.javafxexample.presentation.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Jogo {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty data = new SimpleStringProperty();
    private final StringProperty horario = new SimpleStringProperty();
    private final StringProperty localizacao = new SimpleStringProperty();
    private final StringProperty nome_campeonato = new SimpleStringProperty();
    private final StringProperty nome_equipa1 = new SimpleStringProperty();
    private final StringProperty nome_equipa2 = new SimpleStringProperty();
    private final StringProperty nome_arbitroPrincipal = new SimpleStringProperty();
    private final List<StringProperty> nomes_arbitros = new ArrayList<>();
    private final List<StringProperty> estatisticas = new ArrayList<>();
    private final StringProperty placarFinal = new SimpleStringProperty();
    private final StringProperty nome_vencedor = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();

    public Jogo() {}

    public final IntegerProperty idProperty() {
        return this.id;
    }

    public final Integer getId() {
        return this.idProperty().get();
    }

    public final void setId(final Integer id) {
        this.idProperty().set(id);
    }

    public final StringProperty dataProperty() {
        return this.data;
    }

    public final String getData() {
        return this.dataProperty().get();
    }

    public final void setData(final String data) {
        this.dataProperty().set(data);
    }

    public final StringProperty horarioProperty() {
        return this.horario;
    }

    public final String getHorario() {
        return this.horarioProperty().get();
    }

    public final void setHorario(final String horario) {
        this.horarioProperty().set(horario);
    }

    public final StringProperty localizacaoProperty() {
        return this.localizacao;
    }

    public final String getLocalizacao() {
        return this.localizacaoProperty().get();
    }

    public final void setLocalizacao(final String localizacao) {
        this.localizacaoProperty().set(localizacao);
    }

    public final StringProperty nomeCampeonatoProperty() {
        return this.nome_campeonato;
    }

    public final String getNomeCampeonato() {
        return this.nomeCampeonatoProperty().get();
    }

    public final void setNomeCampeonato(final String nomeCampeonato) {
        this.nomeCampeonatoProperty().set(nomeCampeonato);
    }

    public final StringProperty nomeEquipa1Property() {
        return this.nome_equipa1;
    }

    public final String getNomeEquipa1() {
        return this.nomeEquipa1Property().get();
    }

    public final void setNomeEquipa1(final String nomeEquipa1) {
        this.nomeEquipa1Property().set(nomeEquipa1);
    }

    public final StringProperty nomeEquipa2Property() {
        return this.nome_equipa2;
    }

    public final String getNomeEquipa2() {
        return this.nomeEquipa2Property().get();
    }

    public final void setNomeEquipa2(final String nomeEquipa2) {
        this.nomeEquipa2Property().set(nomeEquipa2);
    }

    public final StringProperty nomeArbitroPrincipalProperty() {
        return this.nome_arbitroPrincipal;
    }

    public final String getNomeArbitroPrincipal() {
        return this.nomeArbitroPrincipalProperty().get();
    }

    public final void setNomeArbitroPrincipal(final String nomeArbitroPrincipal) {
        this.nomeArbitroPrincipalProperty().set(nomeArbitroPrincipal);
    }

    public final List<StringProperty> getNomesArbitros() {
        return this.nomes_arbitros;
    }

    public final void addNomeArbitro(final String nomeArbitro) {
        this.nomes_arbitros.add(new SimpleStringProperty(nomeArbitro));
    }

    public final void setArbitros(final List<String> arbitros) {
        this.nomes_arbitros.clear(); // Limpa a lista atual
        for (String arbitro : arbitros) {
            this.nomes_arbitros.add(new SimpleStringProperty(arbitro));
        }
    }

    public final List<StringProperty> getEstatisticas() {
        return this.estatisticas;
    }

    public final void addEstatistica(final String estatistica) {
        this.estatisticas.add(new SimpleStringProperty(estatistica));
    }

    public final void setEstatisticas(final List<String> estatisticas) {
        this.estatisticas.clear(); // Limpa a lista atual
        for (String stat : estatisticas) {
            this.estatisticas.add(new SimpleStringProperty(stat));
        }
    }

    public final StringProperty placarFinalProperty() {
        return this.placarFinal;
    }

    public final String getPlacarFinal() {
        return this.placarFinalProperty().get();
    }

    public final void setPlacarFinal(final String placarFinal) {
        this.placarFinalProperty().set(placarFinal);
    }

    public final StringProperty nomeVencedorProperty() {
        return this.nome_vencedor;
    }

    public final String getNomeVencedor() {
        return this.nomeVencedorProperty().get();
    }

    public final void setNomeVencedor(final String nomeVencedor) {
        this.nomeVencedorProperty().set(nomeVencedor);
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

    public String toString(){
        String result = "Data: " + getData() + "\n" + "Hora: " + getHorario() + "\n" + "Localização: " + getLocalizacao() + "\n"
                        + "Campeonato: " + getNomeCampeonato() + "\n" 
                        + "Equipa 1: " + getNomeEquipa1() + "\n" + "Equipa 2: " + getNomeEquipa2() + "\n" 
                        + "Árbitro Principal: " + getNomeArbitroPrincipal() + "\n" + "Estado: " + getStatus();
        
        return  result;
    }

    public String getSimpleRepresentation(){
        String result = "Id: " + getId() + "Data: " + getData() + "\n" + "Equipa 1: " + getNomeEquipa1() + "\n" + " Equipa 2: " + getNomeEquipa2();
        
        return  result;
    }

}
