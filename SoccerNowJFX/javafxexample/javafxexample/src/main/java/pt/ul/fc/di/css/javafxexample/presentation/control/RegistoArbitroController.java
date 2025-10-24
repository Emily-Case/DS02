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
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pt.ul.fc.di.css.javafxexample.presentation.dto.ArbitroDto;

public class RegistoArbitroController {
     @FXML
    private Button bttnRegistarA;

    @FXML
    private CheckBox certificado;

    @FXML
    private TextField txtNIFA;

    @FXML
    private TextField txtNomeA;

    @FXML
    private PasswordField txtPasswordA;

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

    //Método para registar um árbitro ao clicar no botão correspondente
    @FXML
    void registarA(ActionEvent event) throws Exception {
        try{
            //Obter os campos do nome, password e nif a partir dos campos onde o utilizador os introduziu
            String nome = txtNomeA.getText().trim();
            String password = txtPasswordA.getText().trim();
            Integer nif = Integer.parseInt(txtNIFA.getText().trim());
            ArbitroDto dto;

            //O que fazer se o campo do certificado estiver checked ou unchecked
            if(certificado.isSelected()){
                dto = new ArbitroDto(null, nif, nome, password, 0, true);
            }else{
                dto = new ArbitroDto(null, nif, nome, password, 0, false);
            }

            //ir fazer a operação ao backend
            String response = connectionController.postArbitro(dto);

            if (response.equals("200") || response.equals("201")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Operação Bem-Sucedida");
                    alert.setHeaderText(null);
                    alert.setContentText("O arbitro foi registado com sucesso!");
                    alert.showAndWait();
            } 

        } catch (Exception e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Falha ao registar o arbitro");
            alert.setContentText("Detalhes: " );
            alert.showAndWait();
        }            
    }
}
