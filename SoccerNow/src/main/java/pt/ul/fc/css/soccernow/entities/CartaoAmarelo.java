package pt.ul.fc.css.soccernow.entities;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("CARTAOAMARELO")
public class CartaoAmarelo extends Estatisticas{

    @OneToOne
    private Arbitro arbitro;

    public CartaoAmarelo(){}

    public Arbitro geArbitro() {
        return this.arbitro;
    }

    public void setArbitro(Arbitro arbitro) {
        this.arbitro = arbitro;
    }
}
