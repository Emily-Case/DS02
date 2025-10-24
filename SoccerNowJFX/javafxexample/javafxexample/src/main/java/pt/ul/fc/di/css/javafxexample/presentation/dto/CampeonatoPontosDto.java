package pt.ul.fc.di.css.javafxexample.presentation.dto;

import java.util.ArrayList;
import java.util.List;

import pt.ul.fc.di.css.javafxexample.presentation.enums.EstadoEntidade;

public class CampeonatoPontosDto {

    private Long id;
    
    private String nome;

    private int ano;

    private List<String> equipas;

    private List<EstatisticasEquipaDto> tabela = new ArrayList<>();

    private EstadoEntidade status;

    public CampeonatoPontosDto(){}

    public Long getId() {
        return id;
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

    public List<String> getEquipas() {
        return this.equipas;
    }

    public void setEquipas(List<String> equipas) {
        this.equipas = equipas;
    }

    public List<EstatisticasEquipaDto> getTabela() {
        return this.tabela;
    }

    public void setTabela(List<EstatisticasEquipaDto> tabela) {
        this.tabela = tabela;
    }

    public EstadoEntidade getEstadoCampeonato() {
        return this.status;
    }

    public void setEstadoCampeonato(EstadoEntidade status) {
        this.status = status;
    }
}
