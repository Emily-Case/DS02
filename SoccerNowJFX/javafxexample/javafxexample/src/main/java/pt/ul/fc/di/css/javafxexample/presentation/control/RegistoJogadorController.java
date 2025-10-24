package pt.ul.fc.di.css.javafxexample.presentation.control;

import java.io.IOException;

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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pt.ul.fc.di.css.javafxexample.presentation.dto.JogadorDto;
import pt.ul.fc.di.css.javafxexample.presentation.enums.Posicao;

public class RegistoJogadorController {

    ObservableList<Posicao> listaPosicoes = FXCollections.observableArrayList(Posicao.GK, Posicao.DEF, Posicao.MED, Posicao.ATQ);

    @FXML
    private Button bttnVoltar;

    @FXML
    private Button bttnRegistar;

    @FXML
    private TextField txtNIFJ;

    @FXML
    private TextField txtNomeJ;

    @FXML
    private PasswordField txtPasswordJ;

    @FXML
    private ChoiceBox<Posicao> posicao;

    //Variáveis/Atributos para mudar de cena
    private Stage stage;
    private Scene scene;
    private Parent root;

    private ConnectionController connectionController = new  ConnectionController();

    @FXML
    private void initialize(){
        posicao.setValue(Posicao.GK);
        posicao.setItems(listaPosicoes);
    }

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
            //Obter os campos do nome, password e nif a partir dos campos onde o utilizador os introduziu
            String nome = txtNomeJ.getText().trim();
            String password = txtPasswordJ.getText().trim();
            Integer nif = Integer.parseInt(txtNIFJ.getText());
            Posicao pos = posicao.getValue();

            JogadorDto dto = new JogadorDto(null, nif, nome, password, 0, pos, null);

            //fazer a operação no backend
            String response = connectionController.postJogador(dto);

            if (response.equals("200") || response.equals("201")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Operação Bem-Sucedida");
                    alert.setHeaderText(null);
                    alert.setContentText("O jogador foi registado com sucesso!");
                    alert.showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Falha ao registar o jogador");
            alert.setContentText("Detalhes: " );
            alert.showAndWait();
        }        
    }

}
