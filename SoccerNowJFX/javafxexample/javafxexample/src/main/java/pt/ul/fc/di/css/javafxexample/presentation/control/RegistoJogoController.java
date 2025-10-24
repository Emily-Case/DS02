package pt.ul.fc.di.css.javafxexample.presentation.control;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pt.ul.fc.di.css.javafxexample.presentation.dto.EquipaDto;
import pt.ul.fc.di.css.javafxexample.presentation.dto.EquipaJogoDto;
import pt.ul.fc.di.css.javafxexample.presentation.dto.JogoDto;
import pt.ul.fc.di.css.javafxexample.presentation.model.Arbitro;
import pt.ul.fc.di.css.javafxexample.presentation.model.Campeonato;
import pt.ul.fc.di.css.javafxexample.presentation.model.Equipa;

public class RegistoJogoController {

    @FXML
    private ChoiceBox<String> arbitroP;

    @FXML
    private ChoiceBox<String> campeonato;

    @FXML
    private Button bttnRegistar;

    @FXML
    private Button bttnVoltar;

    @FXML
    private TextField data;

    @FXML
    private TextField horario;

    @FXML
    private ListView<String> listaArbitros;

    @FXML
    private TextField localizacao;

    @FXML
    private ChoiceBox<String> equipa1;

    @FXML
    private ChoiceBox<String> equipa2;

     @FXML
    private ChoiceBox<String> guardaRedes1;

    @FXML
    private ChoiceBox<String> guardaRedes2;

    @FXML
    private ListView<String> jogadoresEquipa1;

    @FXML
    private ListView<String> jogadoresEquipa2;

    //Variáveis/Atributos para mudar de cena
    private Stage stage;
    private Scene scene;
    private Parent root;

    private ConnectionController connectionController = new  ConnectionController();

    //listas observáveis para depois selecionarmos o que queremos
    ObservableList<String> listaNomes = FXCollections.observableArrayList();
    ObservableList<String> listaEquipas = FXCollections.observableArrayList();
    ObservableList<String> listaCampeonatos = FXCollections.observableArrayList();

    //Inicializa todos os dados presentes na UI
    //Preenche as views com as listas e checkboxes
    //Configura listeners para atualizar listas dependentes e valida as seleções
    @FXML
    private void initialize() throws Exception{
        List<Arbitro> arbitros = connectionController.getAllArbitros();
        List<Equipa> equipas = connectionController.getAllEquipas();
        List<Campeonato> campeonatos = connectionController.getAllCampeonatos();

        for (Arbitro a : arbitros) {
            listaNomes.add(a.getNome());
        }

        for (Equipa e : equipas) {
            listaEquipas.add(e.getNome());
        }

        for (Campeonato c : campeonatos) {
            listaCampeonatos.add(c.getNome());
        }

        equipa1.setItems(listaEquipas);
        equipa2.setItems(listaEquipas);

        arbitroP.setItems(listaNomes);

        listaArbitros.setItems(listaNomes);

        campeonato.setItems(listaCampeonatos);

        // Listener para filtrar equipas por campeonato
        campeonato.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    Campeonato camp = new Campeonato();
                    for (Campeonato c : campeonatos) {
                        if(c.getNome().equals(newValue)){
                            camp = c;
                        }
                    }

                    // Atualizar as opções de equipa1 e equipa2
                    ObservableList<String> equipasFiltradas = FXCollections.observableArrayList();
                    for (StringProperty nomeEquipa : camp.getEquipas()) {
                        equipasFiltradas.add(nomeEquipa.get());
                    }

                    equipa1.setItems(equipasFiltradas);
                    equipa2.setItems(equipasFiltradas);

                    // Limpar as seleções anteriores
                    equipa1.setValue(null);
                    equipa2.setValue(null);

                } catch (Exception e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText(null);
                    alert.setContentText("Erro ao filtrar equipas para o campeonato selecionado.");
                    alert.showAndWait();
                }
            }
        });

        listaArbitros.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);
        jogadoresEquipa1.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);
        jogadoresEquipa2.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);

        // Listener para atualizar guardaRedes1 ao selecionar uma equipa
        equipa1.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    // Buscar os jogadores da equipa selecionada
                    EquipaDto equipaDto = connectionController.getEquipa(newValue);
                    ObservableList<String> jogadores = FXCollections.observableArrayList(equipaDto.getJogadores());

                    // Atualizar os itens de guardaRedes1
                    jogadoresEquipa1.setItems(jogadores);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Listener para atualizar guardaRedes1 ao selecionar uma equipa
        equipa2.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    // Buscar os jogadores da equipa selecionada
                    EquipaDto equipaDto = connectionController.getEquipa(newValue);
                    ObservableList<String> jogadores = FXCollections.observableArrayList(equipaDto.getJogadores());

                    // Atualizar os itens de guardaRedes1
                    jogadoresEquipa2.setItems(jogadores);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        jogadoresEquipa1.getSelectionModel().getSelectedItems().addListener((ListChangeListener<String>) change -> {
        ObservableList<String> selectedItems = jogadoresEquipa1.getSelectionModel().getSelectedItems();

        // Verifica se exatamente 5 jogadores estão selecionados
        if (selectedItems.size() == 5) {
            // Atualiza a ChoiceBox guardaRedes1 com os jogadores selecionados
            guardaRedes1.setItems(FXCollections.observableArrayList(selectedItems));

            // Define o primeiro jogador como padrão (opcional)
            guardaRedes1.setValue(selectedItems.get(0));
        } else if (selectedItems.size() > 5) {
            // Limita a seleção a 5 jogadores
            jogadoresEquipa1.getSelectionModel().clearSelection();
            for (int i = 0; i < 5; i++) {
                jogadoresEquipa1.getSelectionModel().select(selectedItems.get(i));
            }

            // Mostra uma mensagem de aviso (opcional)
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Seleção de Jogadores");
            alert.setHeaderText(null);
            alert.setContentText("Só pode selecionar até 5 jogadores!");
            alert.showAndWait();
        }

        ObservableList<String> allPlayersEquipa2 = jogadoresEquipa2.getItems();

        // Copiar todos os jogadores atualmente na equipa 2
        ObservableList<String> updatedPlayersEquipa2 = FXCollections.observableArrayList(allPlayersEquipa2);

        while (change.next()) {
            // Adicionar jogadores removidos da seleção da equipa 1 de volta à equipa 2
            if (change.wasRemoved()) {
                updatedPlayersEquipa2.addAll(change.getRemoved());
            }

            // Remover os jogadores selecionados da equipa 1 da equipa 2
            if (change.wasAdded()) {
                updatedPlayersEquipa2.removeAll(change.getAddedSubList());
            }
        }

        // Atualizar os itens de jogadoresEquipa2
        jogadoresEquipa2.setItems(updatedPlayersEquipa2);

        // Limpar seleção anterior da equipa 2 se não for válida
        if (!updatedPlayersEquipa2.containsAll(jogadoresEquipa2.getSelectionModel().getSelectedItems())) {
            jogadoresEquipa2.getSelectionModel().clearSelection();
        }
    });

    jogadoresEquipa2.getSelectionModel().getSelectedItems().addListener((ListChangeListener<String>) change -> {
        ObservableList<String> selectedItems = jogadoresEquipa2.getSelectionModel().getSelectedItems();

        // Verifica se exatamente 5 jogadores estão selecionados
        if (selectedItems.size() == 5) {
            // Atualiza a ChoiceBox guardaRedes2 com os jogadores selecionados
            guardaRedes2.setItems(FXCollections.observableArrayList(selectedItems));

            // Define o primeiro jogador como padrão
            guardaRedes2.setValue(selectedItems.get(0));
        } else if (selectedItems.size() > 5) {
            // Limita a seleção a 5 jogadores
            jogadoresEquipa2.getSelectionModel().clearSelection();
            for (int i = 0; i < 5; i++) {
                jogadoresEquipa2.getSelectionModel().select(selectedItems.get(i));
            }

            // Mostra uma mensagem de aviso (opcional)
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Seleção de Jogadores");
            alert.setHeaderText(null);
            alert.setContentText("Você só pode selecionar até 5 jogadores!");
            alert.showAndWait();
        }
    });
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
        try{
            if (data.getText().trim().isEmpty() || horario.getText().trim().isEmpty() || localizacao.getText().trim().isEmpty()
                || arbitroP.getValue() == null || equipa1.getValue() == null
                || equipa2.getValue() == null) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Campos Obrigatórios Não Preenchidos");
                    alert.setHeaderText("Um ou mais campos obrigatórios estão inválidos");
                    alert.setContentText("Por favor, insira a data, horario, localizacao, árbitro principal e as duas equipas.");
                    alert.showAndWait();
                    return; // Interrompe a execução do método
                }
            //colocar tudo o que vem do preenchimento do utilizador nos respetivos DTOs
            LocalDate date =LocalDate.parse(data.getText().trim());
            LocalDateTime hour = LocalDateTime.parse(horario.getText().trim());
            String location = localizacao.getText().trim();
            String principal = arbitroP.getValue();
            String camp = campeonato.getValue();
            ObservableList<String> selectedItems = listaArbitros.getSelectionModel().getSelectedItems();
            List<String> stringList = new ArrayList<>(selectedItems);

            EquipaJogoDto equipa_1 = new EquipaJogoDto();
            equipa_1.setNome_equipa(equipa1.getValue());
            //EquipaDto e1 = connectionController.getEquipa(equipa1.getValue());
            equipa_1.setNomes_jogadores(new ArrayList<>(jogadoresEquipa1.getSelectionModel().getSelectedItems()));
            equipa_1.setNome_gk(guardaRedes1.getValue());

            EquipaJogoDto equipa_2 = new EquipaJogoDto();
            equipa_2.setNome_equipa(equipa2.getValue());
            //EquipaDto e2 = connectionController.getEquipa(equipa2.getValue());
            equipa_2.setNomes_jogadores(new ArrayList<>(jogadoresEquipa2.getSelectionModel().getSelectedItems()));
            equipa_2.setNome_gk(guardaRedes2.getValue());

            JogoDto dto = new JogoDto();
            dto.setData(date);
            dto.setHorario(hour);
            dto.setLocalizacao(location);
            dto.setNomeArbitroPrincipal(principal);
            dto.setNomesArbitros(stringList);
            dto.setNomeCampeonato(camp);
            dto.setEquipa1(equipa_1);
            dto.setEquipa2(equipa_2);

            //ir ao backend realizar a operação
            String response = connectionController.postJogo(dto);

            if (response.equals("200") || response.equals("201")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Operação Bem-Sucedida");
                    alert.setHeaderText(null);
                    alert.setContentText("O jogo foi registado com sucesso!");
                    alert.showAndWait();
            } 
        
        } catch (Exception e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Falha ao registar o jogo");
            alert.setContentText("Detalhes: " );
            alert.showAndWait();
        }              
    }

}
