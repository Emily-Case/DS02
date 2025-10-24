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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import pt.ul.fc.di.css.javafxexample.presentation.model.Jogo;

public class CancelarJogoController {

    @FXML
    private Button bttnVoltar;

    @FXML
    private ListView<String> listaJogos;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private ConnectionController connectionController = new  ConnectionController();

    @FXML
    public void initialize() {
        try {

            //Vai buscar todos os Jogos
            List<Jogo> jogos = connectionController.getAllJogos();

            ObservableList<String> jogosStr = FXCollections.observableArrayList();
            for (Jogo jogo : jogos) {
                if(jogo.getStatus().equals("COMECADO")){
                    jogosStr.add("Id: "+ jogo.getId()+"\n"+jogo.toString());
                }
            }

            //Preenche a List Observável para os Utilizadores
            listaJogos.setItems(jogosStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Caso Carregue em voltar, vai voltar para as opções a que o Organizador tem acesso
    @FXML
    void clickVoltar(ActionEvent event) throws Exception {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "optionsOrganizador.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Quando carregamos no botão para cancelar um jogo específico
    @FXML
    void cancelarJogo(ActionEvent event) {

        //Vai buscar o jogo que foi selecionado para ser apagado
        String selectedJogo = listaJogos.getSelectionModel().getSelectedItem();

        if (selectedJogo != null) {
            try {
                //Vai buscar o seu ID
                String id = extractIdFromToString(selectedJogo);

                //Chama o backend para cancelar o jogo
                connectionController.cancelarJogo(Integer.parseInt(id));

                //Remove o jogo da lista visual
                listaJogos.getItems().remove(selectedJogo);


                System.out.println("Jogo " + id + " foi removido com sucesso.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            System.out.println("Nenhum jogo selecionado.");
        }
    }

    //Método Auxiliar

    //Extrai id do jogo a remover
    private String extractIdFromToString(String jogoStr) {
        // Divide a String a partir dos "\n"
        String[] parts = jogoStr.split("\n");
        if (parts.length > 0 && parts[0].startsWith("Id: ")) {
            return parts[0].substring(4).trim(); // Remove "Id: "
        }
        return "";
    }

}
