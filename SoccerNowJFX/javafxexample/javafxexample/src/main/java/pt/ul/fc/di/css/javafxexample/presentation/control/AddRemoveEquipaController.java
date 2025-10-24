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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import pt.ul.fc.di.css.javafxexample.presentation.memory.LoginMemory;
import pt.ul.fc.di.css.javafxexample.presentation.model.Equipa;

public class AddRemoveEquipaController {
    @FXML
    private ChoiceBox<String> equipaParaAdicionar;

    @FXML
    private ListView<String> listaEquipas;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private ConnectionController connectionController = new  ConnectionController();

    @FXML
    public void initialize() {
        try {

            //Vai buscar todas as equipas
            List<Equipa> equipas = connectionController.getAllEquipas();

            //Trata de ter todas as listas de observáveis
            ObservableList<String> equipasStr = FXCollections.observableArrayList(); //equipas atuais do Jogador
            ObservableList<String> equipasNaoPertentece = FXCollections.observableArrayList(); //equipas ainda possíveis do Jogador se juntar

            //Verifica a que equipas o Jogador pertence ou não
            for (Equipa equipa : equipas) {
                if (equipa.getJogadoresStrings().contains(LoginMemory.getInstance().getCurrentJogador().getNome())) {
                    equipasStr.add(equipa.toString());
                }else{
                    equipasNaoPertentece.add(equipa.toString());
                }
            }

            //Atualiza tudo o que tem para atualizar em termos de elementos que o utilizador vai ver
            listaEquipas.setItems(equipasStr);
            equipaParaAdicionar.setItems(equipasNaoPertentece);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Caso Jogador(Utilizador) carregue em adicionar à sua lista de equipas
    @FXML
    void adicionar(ActionEvent event) throws Exception {
        //Caso determinada equipa esteja selecionada, vai buscar o nome dela
        String nomeEquipa = extractNomeFromToString(equipaParaAdicionar.getValue());
        //Vai buscar Id do Jogador autenticado
        Integer id = LoginMemory.getInstance().getCurrentJogador().getId();

        //Adicionar equipa ao Jogador no backend
        Equipa equipa = connectionController.adicionarJogadorAEquipa(id,nomeEquipa);

        //Atualiza tudo o que tem para atualizar em termos de elementos que o utilizador vai ver
        listaEquipas.getItems().add(equipa.toString());
        equipaParaAdicionar.getItems().remove(equipaParaAdicionar.getValue());
    }

    //Caso Carregue em voltar, vai voltar para as opções a que o Jogador tem acesso
    @FXML
    void clickVoltar(ActionEvent event) throws Exception {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "OptionsJogador.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Caso Jogador(Utilizador) carregue em remover para a sua lista de equipas
    @FXML
    void remover(ActionEvent event) throws Exception {
        //Caso determinada equipa esteja selecionada, vai buscar o nome dela
        String nomeEquipa = extractNomeFromToString(listaEquipas.getSelectionModel().getSelectedItem());
        //Vai buscar Id do Jogador autenticado
        Integer id = LoginMemory.getInstance().getCurrentJogador().getId();

        //Remove equipa ao Jogador no backend
        Equipa equipa = connectionController.removerJogadorAEquipa(id, nomeEquipa);

        //Atualiza tudo o que tem para atualizar em termos de elementos que o utilizador vai ver
        listaEquipas.getItems().remove(listaEquipas.getSelectionModel().getSelectedItem());
        equipaParaAdicionar.getItems().add(equipa.toString());

    }

    //Métodos Auxiliares

    //Extrai nome da Equipa para ajudar à adição ou à remoção da mesma na lista de equipas do Jogador que fez login
    private String extractNomeFromToString(String equipaStr) {
        // Divide a String a partir dos "\n"
        String[] parts = equipaStr.split("\n");
        if (parts.length > 0 && parts[0].startsWith("Nome: ")) {
            return parts[0].substring(6).trim(); // Remove "Nome: "
        }
        return "";
    }
}
