package pt.ul.fc.di.css.javafxexample.presentation.control;

import java.util.List;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pt.ul.fc.di.css.javafxexample.presentation.dto.EquipaDto;
import pt.ul.fc.di.css.javafxexample.presentation.dto.EstatisticasDto;
import pt.ul.fc.di.css.javafxexample.presentation.dto.JogoDto;
import pt.ul.fc.di.css.javafxexample.presentation.enums.EstatisticaTipo;
import pt.ul.fc.di.css.javafxexample.presentation.memory.LoginMemory;
import pt.ul.fc.di.css.javafxexample.presentation.model.Arbitro;
import pt.ul.fc.di.css.javafxexample.presentation.model.Estatistica;
import pt.ul.fc.di.css.javafxexample.presentation.model.Jogo;

public class ResultadoFinalController {
    @FXML
    private ChoiceBox<String> Jogo;

    @FXML
    private ChoiceBox<String> arbitro;

    @FXML
    private ChoiceBox<String> equipas;

    @FXML
    private ChoiceBox<String> jogador;

    @FXML
    private ListView<String> listaEstatisticas;

    @FXML
    private TextField resultado;

    @FXML
    private ChoiceBox<String> tipoEstatistica;

    private EquipaDto equipa1;
    private EquipaDto equipa2;
    private Jogo currentJogo;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private ConnectionController connectionController = new  ConnectionController();
    private Arbitro currentArbitro = LoginMemory.getInstance().currentArbitroProperty().get();

    @FXML
    public void initialize() {
        try {
            List<Jogo> jogos = connectionController.getAllJogos();

            ObservableList<String> jogosStr = FXCollections.observableArrayList();
            for (Jogo jogo : jogos) {
                if (jogo.getNomeArbitroPrincipal().equals(currentArbitro.getNome())) {
                    jogosStr.add(jogo.getSimpleRepresentation());
                }
            }

            // Preencher a ChoiceBox
            this.Jogo.setItems(jogosStr);

            this.Jogo.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    Jogo selectedJogo = jogos.stream()
                        .filter(j -> j.getSimpleRepresentation().equals(newVal))
                        .findFirst()
                        .orElse(null);

                    if (selectedJogo != null) {
                        try {
                            this.currentJogo = selectedJogo;
                            populateFields(selectedJogo);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

        }catch (Exception e) {
            e.printStackTrace();
            // Lidar com erros, como exibir uma mensagem ao usuário
        }
    }

    private void populateFields(Jogo jogo) throws Exception {
        ObservableList<String> equipasNomes = FXCollections.observableArrayList();
        equipasNomes.add(jogo.getNomeEquipa1());
        equipasNomes.add(jogo.getNomeEquipa2());
        equipas.setItems(equipasNomes);


        EquipaDto equipa1 = connectionController.getEquipa(jogo.getNomeEquipa1());
        EquipaDto equipa2 = connectionController.getEquipa(jogo.getNomeEquipa2());

        this.equipa1 = equipa1;
        this.equipa2 = equipa2;

        ObservableList<String> jogadoresNomes = FXCollections.observableArrayList();
        for (String jogador : equipa1.getJogadores()) {
            jogadoresNomes.add(jogador + " (" + equipa1.getNome() + ")");
        }
        for (String jogador : equipa2.getJogadores()) {
            jogadoresNomes.add(jogador + " (" + equipa2.getNome()+ ")");
        }
        jogador.setItems(jogadoresNomes);

        ObservableList<String> arbitrosNomes = FXCollections.observableArrayList();
        for (StringProperty arbitro : jogo.getNomesArbitros()) {
            arbitrosNomes.add(arbitro.get());
        }
        arbitrosNomes.add(jogo.getNomeArbitroPrincipal());
        this.arbitro.setItems(arbitrosNomes);

        ObservableList<String> estatisticas = FXCollections.observableArrayList();
        estatisticas.add("Golo");
        estatisticas.add("Cartão Vermelho");
        estatisticas.add("Cartão Amarelo");
        this.tipoEstatistica.setItems(estatisticas);
    }

    @FXML
    void clickVoltar(ActionEvent event) throws Exception {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "OptionsArbitro.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void criarEstatística(ActionEvent event) {

        Estatistica stat = new Estatistica();
        String tipoTxt = tipoEstatistica.getValue();
        if (tipoTxt == null || jogador.getValue() == null || arbitro.getValue() == null) {
            // Verifique se os campos necessários estão preenchidos
            System.out.println("Por favor, preencha todos os campos para criar a estatística.");
            return;
        }

        // Define o tipo de estatística
        switch (tipoTxt) {
            case "Golo":
                stat.setTipo("GOLO");
                break;
            case "Cartão Vermelho":
                stat.setTipo("CARTAOVERMELHO");
                break;
            case "Cartão Amarelo":
                stat.setTipo("CARTAOAMARELO");
                break;
            default:
                System.out.println("Tipo de estatística inválido.");
                return;
        }
        
        stat.setJogador(jogador.getValue().replaceAll("\\s*\\(.*\\)", ""));
        stat.setArbitro(arbitro.getValue());

        if (equipa1.getJogadores().contains(jogador.getValue().replaceAll("\\s*\\(.*\\)", ""))) {
            stat.setEquipa(equipa1.getNome());
        }else if (equipa2.getJogadores().contains(jogador.getValue().replaceAll("\\s*\\(.*\\)", ""))) {
            stat.setEquipa(equipa2.getNome());
        } else {
            System.out.println("Jogador não encontrado em nenhuma equipa.");
            return;
    }

        if (this.currentJogo.getNomeCampeonato() != null) {
            stat.setCampeonato(this.currentJogo.getNomeCampeonato());
        }
        
        this.currentJogo.addEstatistica(stat.toString());
        this.listaEstatisticas.getItems().add(stat.toString());
    }


    @FXML
    void registarResultadoFinal(ActionEvent event) throws Exception {
        JogoDto dto = new JogoDto();
        dto.setNomeCampeonato(this.currentJogo.getNomeCampeonato());
        dto.setPlacarFinal(resultado.getText());
        dto.setNomeVencedor(equipas.getValue());
        List<EstatisticasDto> estatisticasDtoList = listaEstatisticas.getItems().stream()
            .map(estatisticaStr -> {
                Estatistica estatistica = parseEstatistica(estatisticaStr); // Converter string de volta para Estatistica
                return convertToDto(estatistica);
            })
            .toList();

        dto.setEstatisticas(estatisticasDtoList);

        connectionController.updateFinalScore(currentJogo.getId(), dto);
    }

    private EstatisticasDto convertToDto(Estatistica estatistica) {
        EstatisticasDto dto = new EstatisticasDto();
        dto.setTipoDeEstatistica(EstatisticaTipo.valueOf(estatistica.getTipo()));
        dto.setJogador(estatistica.getJogador());
        dto.setEquipa(estatistica.getEquipa());
        dto.setCampeonato(estatistica.getCampeonato());
        dto.setArbitro(estatistica.getArbitro());
        return dto;
    }

    private Estatistica parseEstatistica(String estatisticaStr) {
        Estatistica estatistica = new Estatistica();
        String[] parts = estatisticaStr.split("\n");
        for (String part : parts) {
            if (part.startsWith("Tipo de Estatística: ")) {
                estatistica.setTipo(part.substring(21));
            } else if (part.startsWith("Jogador: ")) {
                estatistica.setJogador(part.substring(9));
            } else if (part.startsWith("Equipa: ")) {
                estatistica.setEquipa(part.substring(8));
            } else if (part.startsWith("Campeonato: ")) {
                estatistica.setCampeonato(part.substring(12));
            } else if (part.startsWith("Arbitro: ")) {
                estatistica.setArbitro(part.substring(9));
            }
        }
        return estatistica;
    }

    
}
