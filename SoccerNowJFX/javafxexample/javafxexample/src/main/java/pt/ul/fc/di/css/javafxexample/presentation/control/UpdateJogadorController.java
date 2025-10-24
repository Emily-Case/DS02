package pt.ul.fc.di.css.javafxexample.presentation.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import pt.ul.fc.di.css.javafxexample.presentation.dto.JogadorDto;
import pt.ul.fc.di.css.javafxexample.presentation.enums.Posicao;
import pt.ul.fc.di.css.javafxexample.presentation.memory.LoginMemory;
import pt.ul.fc.di.css.javafxexample.presentation.model.Jogador;

public class UpdateJogadorController {

    @FXML
    private RadioButton Atq;

    @FXML
    private RadioButton Def;

    @FXML
    private RadioButton GK;

    @FXML
    private RadioButton Med;

    @FXML
    private Button bttnSave;

    @FXML
    private Label nif;

    @FXML
    private Label numJogos;

    @FXML
    private TextField txtNome;

    @FXML
    private PasswordField txtPassword;

    private ToggleGroup posicaoGroup = new ToggleGroup();

    private Stage stage;
    private Scene scene;
    private Parent root;

    private ConnectionController connectionController = new  ConnectionController();

    public void initialize() {
        // Adicionar os RadioButtons ao ToggleGroup
        Atq.setToggleGroup(posicaoGroup);
        Def.setToggleGroup(posicaoGroup);
        GK.setToggleGroup(posicaoGroup);
        Med.setToggleGroup(posicaoGroup);

        Jogador jogador = LoginMemory.getInstance().getCurrentJogador();

        // Preencher os campos de texto
        txtNome.setText(jogador.getNome());
        txtPassword.setText(jogador.getPassword());

        // Preencher os labels
        nif.setText("NIF: " + jogador.getNIF());
        numJogos.setText("Número de Jogos: " + jogador.getNumJogos());

        // Selecionar o radiobutton correspondente à posição do jogador
        String posicao = jogador.getPosicao();
        switch (posicao) {
            case "ATQ":
                Atq.setSelected(true);
                break;
            case "DEF":
                Def.setSelected(true);
                break;
            case "GK":
                GK.setSelected(true);
                break;
            case "MED":
                Med.setSelected(true);
                break;
            default:
                // Nenhuma posição selecionada
                break;
        }
    }

    @FXML
    void clickSave(ActionEvent event) throws Exception {
        Jogador jogador = LoginMemory.getInstance().getCurrentJogador();

        String nome = txtNome.getText();
        String password = txtPassword.getText();
        Integer nif = jogador.getNIF();
        String posicao = new String();

        RadioButton selectedRadioButton = (RadioButton) posicaoGroup.getSelectedToggle();
        if (selectedRadioButton != null) {
            posicao = selectedRadioButton.getText(); // Obtém o texto do botão selecionado
        }

        if (Atq.isSelected()) {
            posicao = "ATQ";
        } else if (Def.isSelected()) {
            posicao = "DEF";
        } else if (GK.isSelected()) {
            posicao = "GK";
        } else if (Med.isSelected()){
            posicao = "MED";
        }

        Integer numJogos = jogador.getNumJogos();

        JogadorDto dto = new JogadorDto();
        dto.setId(Long.valueOf(jogador.getId()));
        dto.setNome(nome);
        dto.setPassword(password);
        dto.setNif(nif);
        if (posicao.equals("ATQ")) {
            dto.setPosicao(Posicao.ATQ);
        } else if (posicao.equals("DEF")) {
            dto.setPosicao(Posicao.DEF);
        } else if (posicao.equals("GK")){
            dto.setPosicao(Posicao.GK);
        } else if (posicao.equals("MED")){
            dto.setPosicao(Posicao.MED);  
        }

        dto.setNumJogos(numJogos);

        connectionController.updateJogador(Long.valueOf(jogador.getId()),dto);

    }

    @FXML
    void clickVoltar(ActionEvent event) throws Exception {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "OptionsJogador.fxml")); //Tenho que ter info de login para saber para onde voltar
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

