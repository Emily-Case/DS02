package pt.ul.fc.css.soccernow.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ARBITRO")
public class Arbitro extends Utilizador{
    
    //Atributos

    private Boolean certificado;

    //Construtores

    public Arbitro(){}

    public Arbitro(String nome, String password, Boolean certificado, int numJogos){
        
        super(nome, password);

        this.certificado = certificado;
        
        this.setNumJogos(numJogos);
    }

    //Getters e setters

    public Boolean getCertificado(){
        return this.certificado;
    }

    public void setCertificado(Boolean novoCertificado){
        this.certificado = novoCertificado;
    }

    @Override
    public String toString(){
        return "Arbitro{ Nome: " + this.getNome() + "\n" +
               "Password: " + this.getPassword() + "\n" +
               "Certificado: " + stringCertificado(this.getCertificado()) + "\n" +
               "Número de jogos: " + this.getNumJogos() + "\n" +
               "Id do Árbitro: " + this.getId() + "}";
    }

    public String stringCertificado(boolean cert){
        if(cert){
            return "Válido";
        } else {
            return "Inválido";
        }    
    }

    
}
