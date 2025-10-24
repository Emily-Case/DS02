package pt.ul.fc.css.soccernow.dto;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import pt.ul.fc.css.soccernow.enums.Status;

public class JogoDto {
    
    private Long id;

    private LocalDate data;

    private LocalDateTime horario;

    private String localizacao;

    private String nome_campeonato;

    private EquipaJogoDto equipa1;

    private EquipaJogoDto equipa2;

    private String nome_arbitroPrincipal;

    private List<EstatisticasDto> estatisticas = new ArrayList<>();

    private List<String> nomes_arbitros = new ArrayList<>();

    private String placarFinal;

    private String nome_vencedor;

    private Status status;

    //Construtor

    public JogoDto(){}

    //Getters e setters

    public void setId(Long id){
        this.id = id;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public void setNomeCampeonato(String nome_campeonato){
        this.nome_campeonato = nome_campeonato;
    }

    public void setEquipa1(EquipaJogoDto equipa1){
        this.equipa1 = equipa1;
    }

    public void setEquipa2(EquipaJogoDto equipa2){
        this.equipa2 = equipa2;
    }

    public void setNomeArbitroPrincipal(String nome_arbitroPrincipal){
        this.nome_arbitroPrincipal = nome_arbitroPrincipal;
    }

    public void setNomesArbitros(List<String> nomes_arbitros){
        this.nomes_arbitros = nomes_arbitros;
    }

    public void setPlacarFinal(String placarFinal) {
        this.placarFinal = placarFinal;
    }
    public void setNomeVencedor(String nome_vencedor){
        this.nome_vencedor = nome_vencedor;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setEstatisticas(List<EstatisticasDto> estatisticas) {
        this.estatisticas = estatisticas;
    }

    public Long getId(){
        return this.id;
    }

    public LocalDate getData() {
        return this.data;
    }

    public LocalDateTime getHorario() {
        return this.horario;
    }

    public String getLocalizacao() {
        return this.localizacao;
    }
    
    public String getNomeCampeonato() {
        return this.nome_campeonato;
    }

    public EquipaJogoDto getEquipa1() {
        return this.equipa1;
    }

    public EquipaJogoDto getEquipa2() {
        return this.equipa2;
    }

    public String getNomeArbitroPrincipal() {
        return this.nome_arbitroPrincipal;
    }

    public List<String> getNomesArbitros() {
        return this.nomes_arbitros;
    }

    public String getPlacarFinal() {
        return this.placarFinal;
    }

    public String getNomeVencedor(){
        return this.nome_vencedor;
    }

    public Status getStatus(){
        return this.status;
    }

    public List<EstatisticasDto> getEstatisticas() {
        return this.estatisticas;
    }

}
