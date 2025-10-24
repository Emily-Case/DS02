package pt.ul.fc.di.css.javafxexample.presentation.control;

import java.io.IOException;
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
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import pt.ul.fc.di.css.javafxexample.presentation.memory.LoginMemory;
import pt.ul.fc.di.css.javafxexample.presentation.model.Jogo;

public class ListaJogosController {

     @FXML
    private Button bttnVoltar;

    @FXML
    private ListView<String> listaJogos;

    //Variáveis/Atributos para mudar de cena
    private Stage stage;
    private Scene scene;
    private Parent root;

    private ConnectionController connectionController = new  ConnectionController();

    @FXML
    public void initialize() {
        try {
            //Buscar equipas do servidor
            List<Jogo> jogos = connectionController.getAllJogos();

            //Transformar a lista de equipas numa lista de strings (nomes, por exemplo)
            ObservableList<String> jogosStr = FXCollections.observableArrayList();
            for (Jogo jogo : jogos) {
                jogosStr.add(jogo.toString());
            }

            //Preencher a ListView
            listaJogos.setItems(jogosStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void clickVoltar(ActionEvent event) throws IOException {
        //Verificar que tipo de Utilizador está logado para redirecionar para a scene correspondente
        if(LoginMemory.getInstance().currentJogadorProperty().getValue() != null) {
            String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
            root = FXMLLoader.load(getClass().getResource(prefix + "OptionsJogador.fxml")); //Tenho que ter info de login para saber para onde voltar
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }else if(LoginMemory.getInstance().currentArbitroProperty().getValue() != null){
            String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
            root = FXMLLoader.load(getClass().getResource(prefix + "OptionsArbitro.fxml")); //Tenho que ter info de login para saber para onde voltar
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }else{
            String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
            root = FXMLLoader.load(getClass().getResource(prefix + "optionsOrganizador.fxml")); //Tenho que ter info de login para saber para onde voltar
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

}
