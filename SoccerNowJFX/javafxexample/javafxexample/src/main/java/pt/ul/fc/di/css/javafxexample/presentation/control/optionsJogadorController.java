package pt.ul.fc.di.css.javafxexample.presentation.control;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import pt.ul.fc.di.css.javafxexample.presentation.memory.LoginMemory;

public class optionsJogadorController {

    @FXML
    private Button bttnAddRemove;

    @FXML
    private Button bttnApagar;

    @FXML
    private Button bttnAtualizar;

    @FXML
    private Button bttnListaArbitros;

    @FXML
    private Button bttnListaCampeonatos;

    @FXML
    private Button bttnListaEquipas;

    @FXML
    private Button bttnListaJogadores;

    @FXML
    private Button bttnListaJogos;

    @FXML
    private Button bttnVoltar;

    //Variáveis/Atributos para mudar de cena
    private Stage stage;
    private Scene scene;
    private Parent root;

    private ConnectionController connectionController = new  ConnectionController();

    //Quando se clica no botão, mudamos para a cena adicionarRemoverEquipa
    @FXML
    void clickAdicionarRemover(ActionEvent event) throws IOException {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "adicionarRemoverEquipa.fxml")); //vai para a página esperada
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Quando se clica no botão, mudamos para a cena apagarPerfil
    @FXML
    void clickApagarPerfil(ActionEvent event) throws Exception {
        connectionController.deleteJogador(LoginMemory.getInstance().currentJogadorProperty().get().getNIF()); //vai ao backend apagar o Jogador pedido
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "homeJogador.fxml")); //vai para a página esperada
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Quando se clica no botão, mudamos para a cena atualizar dados
    @FXML
    void clickAtualizarDados(ActionEvent event) throws IOException {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "atualizarJogador.fxml")); //vai para a página esperada
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Quando se clica no botão, voltamos para a página anterior
    @FXML
    void clickVoltar(ActionEvent event) throws IOException {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "homeJogador.fxml")); //Vai para a página esperada
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Quando se clica no botão, vamos para a página que mostra a lista de Arbitros
    @FXML
    void listaArbitros(ActionEvent event) throws IOException {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "listaArbitros.fxml")); //Vai para a página esperada
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Quando se clica no botão, vamos para a página que mostra a lista de Campeonatos
    @FXML
    void listaCampeonatos(ActionEvent event) throws IOException {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "listaCampeonatos.fxml")); //Vai para a página esperada
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Quando se clica no botão, vamos para a página que mostra a lista de Equipas
    @FXML
    void listaEquipas(ActionEvent event) throws IOException {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "listaEquipas.fxml")); //Vai para a página esperada
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Quando se clica no botão, vamos para a página que mostra a lista de Jogadores
    @FXML
    void listaJogadores(ActionEvent event) throws IOException {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "listaJogadores.fxml")); //Vai para a página esperada
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Quando se clica no botão, vamos para a página que mostra a lista de Campeonatos
    @FXML
    void listaJogos(ActionEvent event) throws IOException {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "listaJogos.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
