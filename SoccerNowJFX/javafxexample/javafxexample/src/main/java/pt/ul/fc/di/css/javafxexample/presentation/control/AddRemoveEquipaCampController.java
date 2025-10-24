package pt.ul.fc.di.css.javafxexample.presentation.control;

import java.util.ArrayList;
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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import pt.ul.fc.di.css.javafxexample.presentation.model.Campeonato;
import pt.ul.fc.di.css.javafxexample.presentation.model.Equipa;

public class AddRemoveEquipaCampController {

    @FXML
    private ChoiceBox<String> Campeonato;

    @FXML
    private Button bttnRemover;

    @FXML
    private ChoiceBox<String> equipaAdd;

    @FXML
    private ListView<String> listaEquipasNoCamp;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private ConnectionController connectionController = new  ConnectionController();

    @FXML
    public void initialize() {
        try {
            // Buscar campeonatos do servidor
            List<Campeonato> campeonatos = connectionController.getAllCampeonatos();

            // Transformar a lista de campeonatos numa lista de strings (nomes, por exemplo)
            ObservableList<String> campeonatosStr = FXCollections.observableArrayList();
            for (Campeonato campeonato : campeonatos) {
                campeonatosStr.add(campeonato.getSimpleRepresentation());
            }

            // Preencher a ChoiceBox
            this.Campeonato.setItems(campeonatosStr);

            // Adicionar um listener para atualizar as listas ao selecionar um campeonato
            this.Campeonato.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    Campeonato selectedCampeonato = campeonatos.stream()
                        .filter(c -> c.getSimpleRepresentation().equals(newVal))
                        .findFirst()
                        .orElse(null);

                    if (selectedCampeonato != null) {
                        updateLists(selectedCampeonato);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            // Lidar com erros, como exibir uma mensagem ao usuário
        }
    }

    private void updateLists(Campeonato selectedCampeonato) {
        try {
            // Equipas que pertencem ao campeonato
            List<String> equipasNoCampeonato = new ArrayList<>();
            for (StringProperty equipa : selectedCampeonato.getEquipas()) {
                equipasNoCampeonato.add(equipa.get());
            }

            // Todas as equipas disponíveis
            List<Equipa> todasEquipas = connectionController.getAllEquipas(); // Supondo que esse método existe
            List<String> nomesTodasEquipas = new ArrayList<>();

            for (Equipa equipa : todasEquipas) {
                nomesTodasEquipas.add(equipa.getNome());
            }

            // Equipas que não pertencem ao campeonato
            List<String> equipasForaCampeonato = new ArrayList<>(nomesTodasEquipas);
            equipasForaCampeonato.removeAll(equipasNoCampeonato);

            // Atualizar ListView e ChoiceBox
            listaEquipasNoCamp.setItems(FXCollections.observableArrayList(equipasNoCampeonato));
            equipaAdd.setItems(FXCollections.observableArrayList(equipasForaCampeonato));
        } catch (Exception e) {
            e.printStackTrace();
            // Lidar com erros
        }
    }

    @FXML
    void adicionar(ActionEvent event) throws Exception {
        String nomeEquipa = equipaAdd.getValue();
        String strCampeonato = this.Campeonato.getValue();

        Integer id = Integer.parseInt(extractIdFromToString(strCampeonato));

        connectionController.adicionarEquipaACampeonato(id,nomeEquipa);

        listaEquipasNoCamp.getItems().add(nomeEquipa.toString());
        equipaAdd.getItems().remove(nomeEquipa.toString());
    }

    private String extractIdFromToString(String campeonatoStr) {
        // Verifica se a string começa com "Id: "
        if (campeonatoStr.startsWith("Id: ")) {
            // Localiza o índice do separador " - "
            int separatorIndex = campeonatoStr.indexOf(" - ");
            if (separatorIndex != -1) {
                // Extrai a parte do ID entre "Id: " e o separador
                return campeonatoStr.substring(4, separatorIndex).trim();
            }
        }
        return ""; // Retorna vazio se o formato for inválido
    }

    @FXML
    void clickVoltar(ActionEvent event) throws Exception {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "optionsOrganizador.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void remover(ActionEvent event) throws Exception {
        String nomeEquipa = listaEquipasNoCamp.getSelectionModel().getSelectedItem();
        String strCampeonato = this.Campeonato.getValue();

        Integer id = Integer.parseInt(extractIdFromToString(strCampeonato));

        connectionController.removerEquipaDeCampeonato(id, nomeEquipa);

        listaEquipasNoCamp.getItems().remove(nomeEquipa);
        equipaAdd.getItems().add(nomeEquipa);
    }

}
