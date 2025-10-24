package pt.ul.fc.di.css.javafxexample.presentation.control;

import java.util.List;

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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pt.ul.fc.di.css.javafxexample.presentation.dto.CampeonatoPontosDto;
import pt.ul.fc.di.css.javafxexample.presentation.model.Campeonato;

public class UpdateCampeonatoController {

    @FXML
    private Button bttnGuardar;

    @FXML
    private ChoiceBox<String> campeonato;

    @FXML
    private TextField txtAno;

    @FXML
    private TextField txtNome;

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
            campeonato.setItems(campeonatosStr);

            // Listener para atualizar os campos ao selecionar um campeonato
            campeonato.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    Campeonato selectedCampeonato = campeonatos.stream()
                        .filter(c -> c.getSimpleRepresentation().equals(newVal))
                        .findFirst()
                        .orElse(null);

                    if (selectedCampeonato != null) {
                        populateFields(selectedCampeonato);
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            // Lidar com erros, como exibir uma mensagem ao usuário
        }
    }

    private void populateFields(Campeonato campeonato) {
        txtNome.setText(campeonato.getNome());
        txtAno.setText(String.valueOf(campeonato.getAno()));
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
    void guardar(ActionEvent event) throws Exception {
        Integer id = Integer.parseInt(extractIdFromToString(campeonato.getValue()));
        CampeonatoPontosDto dto = new CampeonatoPontosDto();

        dto.setId(Long.valueOf(id));
        dto.setAno(Integer.parseInt(txtAno.getText()));
        dto.setNome(txtNome.getText());

        connectionController.updateCampeonato(Long.valueOf(id),dto);

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
}
