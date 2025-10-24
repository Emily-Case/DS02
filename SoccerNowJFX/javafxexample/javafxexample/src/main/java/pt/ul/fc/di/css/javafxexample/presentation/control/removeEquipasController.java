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
import pt.ul.fc.di.css.javafxexample.presentation.model.Equipa;

public class removeEquipasController {

    @FXML
    private Button bttnVoltar;

    @FXML
    private ListView<String> listaEquipas;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private ConnectionController connectionController = new  ConnectionController();

    @FXML
    public void initialize() {
        try {
            // Buscar equipas do servidor
            List<Equipa> equipas = connectionController.getAllEquipas();

            // Transformar a lista de equipas numa lista de strings (nomes, por exemplo)
            ObservableList<String> equipasStr = FXCollections.observableArrayList();
            for (Equipa equipa : equipas) {
                equipasStr.add(equipa.toString());
            }

            // Preencher a ListView
            listaEquipas.setItems(equipasStr);
        } catch (Exception e) {
            e.printStackTrace();
            // Lidar com erros, como exibir uma mensagem ao usuário
        }
    }

    @FXML
    void clickVoltar(ActionEvent event) throws Exception {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "optionsOrganizador.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void removeEquipa(ActionEvent event) {

        String selectedEquipa = listaEquipas.getSelectionModel().getSelectedItem();
        
        if (selectedEquipa != null) {
            try {
                
                String equipaNome = extractNomeFromToString(selectedEquipa);
                System.out.println(equipaNome);
                // Call the deleteEquipa method with the extracted name
                connectionController.deleteEquipa(equipaNome);

                // Remove the item from the ListView
                listaEquipas.getItems().remove(selectedEquipa);

                // Optionally show confirmation to the user
                System.out.println("Equipa " + equipaNome + " foi removida com sucesso.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Optionally show an alert if no item is selected
            System.out.println("Nenhuma equipa selecionada.");
        }
    }

    private String extractNomeFromToString(String equipaStr) {
    // Divide a String a partir dos "\n"
    String[] parts = equipaStr.split("\n");
    if (parts.length > 0 && parts[0].startsWith("Nome: ")) {
        return parts[0].substring(6).trim(); // Remove "Nome: "
    }
    return "";
}

}
