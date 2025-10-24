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

public class optionsArbitroController {

    @FXML
    private Button bttnResultadoFinal;

    @FXML
    private Button bttnApagar;

    @FXML
    private Button bttnAtualizar;

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

    //Método para mudar para a cena de resultado final quando o botão correspondente é clicado
     @FXML
    void clickResultadoFinal(ActionEvent event) throws IOException {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "resultadoFinal.fxml")); //carrega a janela do resultadoFinal
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Método para apagar o perfil do árbitro logado e voltar à cena inicial do árbitro
    @FXML
    void clickApagarPerfil(ActionEvent event) throws Exception {
        //apaga o arbitro no backend com aquele ID
        connectionController.deleteArbitro(LoginMemory.getInstance().currentArbitroProperty().get().getNIF());
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "homeArbitro.fxml")); //volta para a janela principal do arbitro
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Método para abrir a cena onde o árbitro logado pode atualizar os seus dados
    @FXML
    void clickAtualizarDados(ActionEvent event) throws IOException {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "atualizarArbitro.fxml")); //Carrega a página pedida
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Método para voltar para a cena inicial do árbitro
    @FXML
    void clickVoltar(ActionEvent event) throws IOException {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "homeArbitro.fxml")); //volta para a janela principal do arbitro
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Método para carregar a lista de árbitros na cena da listaArbitros
     @FXML
    void listaArbitros(ActionEvent event) throws IOException {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "listaArbitros.fxml")); //Carrega a janela pedida
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Método para carregar a lista de campeonatos na cena da listaCampeonatos
    @FXML
    void listaCampeonatos(ActionEvent event) throws IOException {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "listaCampeonatos.fxml")); //Carrega a janela pedida
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Método para carregar a lista de equipas na cena da listaEquipas
    @FXML
    void listaEquipas(ActionEvent event) throws IOException {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "listaEquipas.fxml")); //Carrega a janela pedida
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Método para carregar a lista de jogadores na cena da listaJogadores
    @FXML
    void listaJogadores(ActionEvent event) throws IOException {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "listaJogadores.fxml")); //Carrega a janela pedida
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Método para carregar a lista de jogos na cena da listaJogos
    @FXML
    void listaJogos(ActionEvent event) throws IOException {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "listaJogos.fxml")); //Carrega a janela pedida
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
