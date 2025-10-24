package pt.ul.fc.di.css.javafxexample.presentation.control;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pt.ul.fc.di.css.javafxexample.presentation.dto.EquipaDto;

public class RegistoEquipaController {

    @FXML
    private Button bttnRegistar;

    @FXML
    private TextField txtNome;

    //Variáveis/Atributos para mudar de cena
    private Stage stage;
    private Scene scene;
    private Parent root;

    private ConnectionController connectionController = new  ConnectionController();

    //Método que volta à cena optionsOrganizador ao clicar no botão "Voltar"
    @FXML
    void clickVoltar(ActionEvent event) throws IOException {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "optionsOrganizador.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void registar(ActionEvent event) throws Exception {
        try {
            //Obter o campo do nome da equipa
            String nome = txtNome.getText().trim();

            // Verificar se o nome está vazio ou nulo
            if (nome == null || nome.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Campo Obrigatório");
                alert.setHeaderText("Nome da Equipa Inválido");
                alert.setContentText("Por favor, insira o nome da equipa.");
                alert.showAndWait();
                return; // Interrompe a execução do método
            }

            EquipaDto dto = new EquipaDto((long) 0, nome);

            ////fazer a operação no backend
            String response = connectionController.postEquipa(dto);

            if (response.equals("200") || response.equals("201")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Operação Bem-Sucedida");
                    alert.setHeaderText(null);
                    alert.setContentText("A equipa foi registada com sucesso!");
                    alert.showAndWait();
            }
            
        } catch (Exception e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Falha ao registar a equipa");
            alert.setContentText("Detalhes: " );
            alert.showAndWait();
        }        
    }

}
