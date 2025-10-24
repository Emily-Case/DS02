package pt.ul.fc.di.css.javafxexample.presentation.dto;

import pt.ul.fc.di.css.javafxexample.presentation.enums.EstadoEntidade;

public class ArbitroDto {
    
    private Long id;
    private int nif;
    private String nome;
    private String password;
    private Boolean certificado;
    private int numJogos;
    private EstadoEntidade status; //Se Árbitro foi soft deleted ou não

    //Construtores

    public ArbitroDto() {}

    public ArbitroDto(Long id, int nif, String nome, String password, int numJogos, Boolean certificado) {
        this.id = id;
        this.nif = nif;
        this.nome = nome;
        this.password = password;
        this.certificado = certificado;
        this.numJogos = numJogos;
    }

    // Getters e setters
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String passwordNovo) {
        this.password = passwordNovo;
    }

    public Boolean getCertificado() {
        return certificado;
    }

    public void setCertificado(Boolean certificadoNovo) {
        this.certificado = certificadoNovo;
    }

    public int getNumJogos() {
        return numJogos;
    }

    public void setNumJogos(int numJogosNovo) {
        this.numJogos = numJogosNovo;
    }

    public EstadoEntidade getEstadoArbitro() {
        return this.status;
    }

    public void setEstadoArbitro(EstadoEntidade status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ArbitroDTO{" +
                "id= " + this.id + "\n" +
                "nome= " + this.nome + "\n" +
                "password= " + this.password + "\n" +
                "certificado= " + stringCertificado(this.certificado) + "\n" +
                "numJogos= " + this.numJogos + "\n" +
                '}';
    }

    public String stringCertificado(boolean cert){
        if(cert){
            return "Válido";
        } else {
            return "Inválido";
        }    
    }
}
