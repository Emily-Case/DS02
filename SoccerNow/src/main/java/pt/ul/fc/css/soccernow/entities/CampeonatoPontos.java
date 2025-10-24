package pt.ul.fc.css.soccernow.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class CampeonatoPontos extends Campeonato{

    @OneToMany(mappedBy = "campeonato")
    private List<EstatisticasEquipa> tabela = new ArrayList<>();   

    public CampeonatoPontos(){}

    public List<EstatisticasEquipa> getTabela() {
        return this.tabela;
    }

    public void setTabela(List<EstatisticasEquipa> tabela) {
        this.tabela = tabela;
    }

    public void addLinhaTabela(EstatisticasEquipa linha) {
        this.tabela.add(linha);
    }

    public void removeLinhaTabela(EstatisticasEquipa linha) {
        this.tabela.remove(linha);
    }
}

