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
import pt.ul.fc.di.css.javafxexample.presentation.model.Campeonato;

public class CancelarCampeonatoController {
    @FXML
    private Button bttnVoltar;

    @FXML
    private ListView<String> listaCampeonatos;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private ConnectionController connectionController = new  ConnectionController();

    @FXML
    public void initialize() {
        try {
            // Buscar campeonatos do servidor
            List<Campeonato> campeonatos = connectionController.getAllCampeonatos();

            // Transformar a lista de campeonatos numa lista de strings (nomes, por exemplo)
            ObservableList<String> campeonatosStr = FXCollections.observableArrayList();
            for (Campeonato campeonato : campeonatos) {
                campeonatosStr.add(campeonato.toString());
            }

            // Preencher a ListView
            listaCampeonatos.setItems(campeonatosStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Quando carregamos no botão para cancelar um campeonato específico
    @FXML
    void cancelaCampeonato(ActionEvent event) {

        //Vai buscar o campeonato que foi selecionado para ser apagado
        String selectedCampeonato = listaCampeonatos.getSelectionModel().getSelectedItem();

        if (selectedCampeonato != null) {
            try {

                //Vai buscar o seu ID
                String id = extractIdFromToString(selectedCampeonato);

                //Chama o backend para cancelar o campeonato
                connectionController.cancelarCampeonato(Integer.parseInt(id));

                //Remove o campeonato da lista visual
                listaCampeonatos.getItems().remove(selectedCampeonato);

                System.out.println("Campeonato com o id " + id + " foi removido com sucesso.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Nenhum campeonato selecionado.");
        }
    }

    //Método Auxiliar

    //Extrai id do campeonato a remover
    private String extractIdFromToString(String jogoStr) {
        // Divide a String a partir dos "\n"
        String[] parts = jogoStr.split("\n");
        if (parts.length > 0 && parts[0].startsWith("Id: ")) {
            return parts[0].substring(4).trim(); // Remove "Id: "
        }
        return "";
    }

    //Caso Carregue em voltar, vai voltar para as opções a que o Organizador tem acesso
    @FXML
    void clickVoltar(ActionEvent event) throws Exception {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
            root = FXMLLoader.load(getClass().getResource(prefix + "optionsOrganizador.fxml")); //Tenho que ter info de login para saber para onde voltar
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
}
