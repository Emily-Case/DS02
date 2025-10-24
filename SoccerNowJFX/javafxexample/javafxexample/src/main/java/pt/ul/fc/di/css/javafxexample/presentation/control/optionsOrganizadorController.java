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

public class optionsOrganizadorController {

    @FXML
    private Button bttnAtualizarCampeonato;

    @FXML
    private Button bttnCancelarCampeonato;

    @FXML
    private Button bttnCancelarJogo;

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
    private Button bttnRegistarArbitro;

    @FXML
    private Button bttnRegistarCampeonato;

    @FXML
    private Button bttnRegistarEquipa;

    @FXML
    private Button bttnRegistarJogador;

    @FXML
    private Button bttnRegistarJogo;

    @FXML
    private Button bttnRemoverEquipa;

    @FXML
    private Button bttnRemoverJogador;

    @FXML
    private Button bttnRemoverEquipaCampeonato;

    @FXML
    private Button bttnVoltar;

    //Variáveis/Atributos para mudar de cena
    private Stage stage;
    private Scene scene;
    private Parent root;

    //Quando se clica no botão, mudamos para a cena de atualizar o Campeonato
    @FXML
    void atualizarCampeonato(ActionEvent event) throws IOException {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "atualizarCampeonato.fxml")); //vai para a página esperada
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Quando se clica no botão, mudamos para a cena de cancelar um Campeonato específico
    @FXML
    void cancelarCampeonato(ActionEvent event) throws Exception {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "removerCampeonato.fxml")); //vai para a página esperada
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Quando se clica no botão, mudamos para a cena de cancelar um Jogo específico
    @FXML
    void cancelarJogo(ActionEvent event) throws Exception {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "cancelarJogo.fxml")); //vai para a página esperada
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Quando se clica no botão, voltamos para a página anterior
    @FXML
    void clickVoltar(ActionEvent event) throws IOException {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "home.fxml")); //vai para a página esperada
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Quando se clica no botão, mudamos para a cena com a lista de todos os Arbitros existentes
    @FXML
    void listaArbitros(ActionEvent event) throws IOException {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "listaArbitros.fxml")); //vai para a página esperada
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Quando se clica no botão, mudamos para a cena com a lista de todos os Campeonatos existentes
    @FXML
    void listaCampeonatos(ActionEvent event) throws IOException {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "listaCampeonatos.fxml")); //vai para a página esperada
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Quando se clica no botão, mudamos para a cena com a lista de todas as Equipas existentes
    @FXML
    void listaEquipas(ActionEvent event) throws IOException {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "listaEquipas.fxml")); //vai para a página esperada
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Quando se clica no botão, mudamos para a cena com a lista de todos os Jogadores existentes
    @FXML
    void listaJogadores(ActionEvent event) throws IOException {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "listaJogadores.fxml")); //vai para a página esperada
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Quando se clica no botão, mudamos para a cena com a lista de todos os Jogos existentes
    @FXML
    void listaJogos(ActionEvent event) throws IOException {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "listaJogos.fxml")); //vai para a página esperada
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Quando se clica no botão, mudamos para uma cena onde é possível registar um Árbitro
    @FXML
    void registarArbitro(ActionEvent event) throws IOException {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "registarArbitro.fxml")); //vai para a página esperada
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Quando se clica no botão, mudamos para uma cena onde é possível registar um Campeonato
    @FXML
    void registarCampeonato(ActionEvent event) throws Exception {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "registarCampeonato.fxml")); //vai para a página esperada
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

        //Quando se clica no botão, mudamos para uma cena onde é possível registar uma Equipa
    @FXML
    void registarEquipa(ActionEvent event) throws IOException {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "registarEquipa.fxml")); //vai para a página esperada
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Quando se clica no botão, mudamos para uma cena onde é possível registar um Jogador
    //NIF, nome, password
    @FXML
    void registarJogador(ActionEvent event) throws IOException {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "registarJogador.fxml")); //vai para a página esperada
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    //Quando se clica no botão, mudamos para uma cena onde é possível registar um Jogo
    @FXML
    void registarJogo(ActionEvent event) throws IOException {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "registarJogo.fxml")); //vai para a página esperada
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    //Quando se clica no botão, mudamos para uma cena onde é possível remover uma Equipa
    @FXML
    void removerEquipa(ActionEvent event) throws Exception {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "removerEquipa.fxml")); //vai para a página esperada
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Quando se clica no botão, mudamos para uma cena onde é possível remover uma Equipa de um Campeonato
    @FXML
    void removerEquipaDeCampeonato(ActionEvent event) throws Exception {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "adicionarRemoverEquipaCampeonato.fxml")); //vai para a página esperada
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
