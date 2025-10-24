package pt.ul.fc.css.soccernow.entities;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("CARTAOVERMELHO")
public class CartaoVermelho extends Estatisticas{

    @OneToOne
    private Arbitro arbitro;

    public CartaoVermelho(){}

    public Arbitro geArbitro() {
        return this.arbitro;
    }

    public void setArbitro(Arbitro arbitro) {
        this.arbitro = arbitro;
    }
}
