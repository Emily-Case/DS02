package pt.ul.fc.css.soccernow.entities;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("GOLO")
public class Golo extends Estatisticas{

    public Golo(){}
}
