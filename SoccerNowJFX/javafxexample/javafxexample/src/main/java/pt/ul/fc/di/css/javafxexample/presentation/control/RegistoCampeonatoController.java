package pt.ul.fc.di.css.javafxexample.presentation.control;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pt.ul.fc.di.css.javafxexample.presentation.dto.CampeonatoPontosDto;
import pt.ul.fc.di.css.javafxexample.presentation.model.Equipa;

public class RegistoCampeonatoController {
    @FXML
    private Button bttnRegistar;

    @FXML
    private ListView<String> listaEquipas;

    @FXML
    private TextField txtAno;

    @FXML
    private TextField txtNome;

    //Variáveis/Atributos para mudar de cena
    private Stage stage;
    private Scene scene;
    private Parent root;

    private ConnectionController connectionController = new  ConnectionController();

    //Método para registar um campeonato ao clicar no botão correspondente
    @FXML
    public void initialize() {
        try {
            listaEquipas.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);
            // Buscar equipas do servidor
            List<Equipa> equipas = connectionController.getAllEquipas();

            // Transformar a lista de equipas numa lista de strings (nomes, por exemplo)
            ObservableList<String> equipasStr = FXCollections.observableArrayList();
            for (Equipa equipa : equipas) {
                equipasStr.add(equipa.toString());
            }

            // Preencher a ListView
            listaEquipas.setItems(equipasStr);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    //Método que volta à cena optionsOrganizador ao clicar no botão "Voltar"
    @FXML
    void clickVoltar(ActionEvent event) throws Exception {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "optionsOrganizador.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Método para registar um campeonato ao clicar no botão correspondente
    @FXML
    void registar(ActionEvent event) throws Exception {
        try {

            // Verificar se o nome do campeonato está vazio
            if (txtNome.getText() == null || txtNome.getText().trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Campo Obrigatório");
                alert.setHeaderText("Nome do Campeonato Inválido");
                alert.setContentText("Por favor, insira o nome do campeonato.");
                alert.showAndWait();
                return; // Interrompe a execução do método
            }

            // Verificar se o ano do campeonato está vazio
            if (txtAno.getText() == null || txtAno.getText().trim().isEmpty() || txtAno.getText() == "0") {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Campo Obrigatório");
                alert.setHeaderText("Ano do Campeonato Inválido");
                alert.setContentText("Por favor, insira o ano do campeonato.");
                alert.showAndWait();
                return; // Interrompe a execução do método
            }

            //meter tudo no DTO
            CampeonatoPontosDto dto = new CampeonatoPontosDto();
            dto.setAno(Integer.parseInt(txtAno.getText()));
            dto.setNome(txtNome.getText());
            ObservableList<String> selectedItems = listaEquipas.getSelectionModel().getSelectedItems(); //obter equipas selecionadas da ListView
            List<String> nomes_equipas = new ArrayList<String>();

            //extrai nome de cada equipa selecionadas
            for (String item : selectedItems) {
                String nome = extractNomeFromToString(item);
                nomes_equipas.add(nome);
            }

            dto.setEquipas(nomes_equipas);

            //executa o registo do campeonato no backend
            String response = connectionController.postCampeonato(dto);

            if (response.equals("200") || response.equals("201")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Operação Bem-Sucedida");
                alert.setHeaderText(null);
                alert.setContentText("O campeonato foi registado com sucesso!");
                alert.showAndWait();
            } 
            
        } catch (Exception e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Falha ao registar o campeonato");
            alert.setContentText("Detalhes: " );
            alert.showAndWait();
        }    
    }

    //Método Auxiliar

    private String extractNomeFromToString(String equipaStr) {
        // Divide a String a partir dos "\n"
        String[] parts = equipaStr.split("\n");
        if (parts.length > 0 && parts[0].startsWith("Nome: ")) {
            return parts[0].substring(6).trim(); // Remove "Nome: "
        }
        return "";
    }
}
