package pt.ul.fc.di.css.javafxexample.presentation.control;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pt.ul.fc.di.css.javafxexample.presentation.memory.LoginMemory;
import pt.ul.fc.di.css.javafxexample.presentation.model.Arbitro;
import pt.ul.fc.di.css.javafxexample.presentation.model.Jogador;

public class HomeController {
    @FXML
    private Button bttnArbitro;

    @FXML
    private Button bttnJogador;

    @FXML
    private Button bttnOrganizador;

    @FXML
    private Label subtitle;

    @FXML
    private Label title;

     @FXML
    private Button bttnEntrarJ;

    @FXML
    private Button bttnVoltarJ;

    @FXML
    private Label title2;

    @FXML
    private TextField txtNomeJ;

    @FXML
    private TextField txtPasswordJ;
    @FXML
    private Button bttnEntrarA;

    @FXML
    private Button bttnVoltarA;

    @FXML
    private Label title3;

    @FXML
    private TextField txtNomeA;

    @FXML
    private TextField txtPasswordA;

    //Variáveis/Atributos para mudar de cena
    private Stage stage;
    private Scene scene;
    private Parent root;

    private ConnectionController connectionController = new  ConnectionController();

    //Quando o botão é clicado, muda para a tela de login do Árbitro
    @FXML
    void clickArbitro(ActionEvent event) throws IOException {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "homeArbitro.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Quando o botão é clicado, muda para a tela de login do Jogador
    @FXML
    void clickJogador(ActionEvent event) throws IOException {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "homeJogador.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Quando o botão é clicado, muda para a tela principal do Organizador
    @FXML
    void clickOrganizador(ActionEvent event) throws IOException {
        LoginMemory.getInstance().resetLogin();
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "optionsOrganizador.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Quando o Jogador clica em entrar após preencher os dados do login
    @FXML
    void clickEntrarJ(ActionEvent event) {
        String nome = txtNomeJ.getText();
        String password = txtPasswordJ.getText();

        LoginMemory.getInstance().resetLogin(); //Limpa dados da sessão anterior se houver alguma

        //Validação para ver se algum dos campos não está preenchido
        if (nome.isEmpty() || password.isEmpty()) {
            showAlert("Erro", "Os campos Nome e Password são obrigatórios.", AlertType.ERROR);
            return;
        }

        try {
            String response = connectionController.loginJogador(nome, password); //chama método de login do backend

            //Se a resposta for positiva
            if(response.equals("Player login successful (mock)")){
                Jogador jogador = connectionController.getJogador(nome);
                LoginMemory.getInstance().setCurrentJogador(jogador);

                //Muda a página e carrega as opções do Jogador
                String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
                root = FXMLLoader.load(getClass().getResource(prefix + "optionsJogador.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }

        } catch (Exception e) {
            showAlert("Erro", e.getMessage(), AlertType.ERROR);
        }
    }

    private void showAlert(String title, String content, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    //Quando o Árbitro clica em entrar após preencher os dados do login
    @FXML
    void clickEntrarA(ActionEvent event) {
        String nome = txtNomeA.getText();
        String password = txtPasswordA.getText();

        LoginMemory.getInstance().resetLogin(); //Limpa dados da sessão anterior se houver alguma

        //Validação para ver se algum dos campos não está preenchido
        if (nome.isEmpty() || password.isEmpty()) {
            showAlert("Erro", "Os campos Nome e Password são obrigatórios.", AlertType.ERROR);
            return;
        }

        try {
            String response = connectionController.loginArbitro(nome, password); //chama método de login do backend

            //Se a resposta for positiva
            if(response.equals("Player login successful (mock)")){
                Arbitro arbitro = connectionController.getArbitro(nome); // Simula buscar info do jogador
                LoginMemory.getInstance().setCurrentArbitro(arbitro);

                String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
                root = FXMLLoader.load(getClass().getResource(prefix + "optionsArbitro.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }

        } catch (Exception e) {
            showAlert("Erro", e.getMessage(), AlertType.ERROR);
        }
    }

     //Caso Carregue em voltar, vai voltar para a página original
    @FXML
    void clickVoltar(ActionEvent event) throws IOException {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "home.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
