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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pt.ul.fc.di.css.javafxexample.presentation.dto.ArbitroDto;
import pt.ul.fc.di.css.javafxexample.presentation.memory.LoginMemory;
import pt.ul.fc.di.css.javafxexample.presentation.model.Arbitro;

public class UpdateArbitroController {

    @FXML
    private Button bttnSave;

    @FXML
    private Label certificate;

    @FXML
    private TextField name;

    @FXML
    private Label nif;

    @FXML
    private Label numJogos;

    @FXML
    private PasswordField password;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private ConnectionController connectionController = new  ConnectionController();

    public void initialize() {
        Arbitro arbitro = LoginMemory.getInstance().getCurrentArbitro();

        // Preencher os campos de texto
        name.setText(arbitro.getNome());
        password.setText(arbitro.getPassword());

        // Preencher os labels
        nif.setText("NIF: " + arbitro.getNIF());
        numJogos.setText("Número de Jogos: " + arbitro.getNumJogos());
        if(arbitro.getCertificado()){
            certificate.setText("Tem certificado? Sim");
        }else{
            certificate.setText("Tem certificado? Não");
        }
    }

    @FXML
    void addCertificado(ActionEvent event) {
        certificate.setText("Tem certificado? Sim");
    }

    @FXML
    void clickSave(ActionEvent event) throws Exception {
        Arbitro arbitro = LoginMemory.getInstance().getCurrentArbitro();
        
        String nome = name.getText();
        String pass = password.getText();
        Integer nif = arbitro.getNIF();

        Boolean cert = false;
        if(certificate.getText().equals("Tem certificado? Sim")){
            cert = true;
        }

        Integer numJogos = arbitro.getNumJogos();

        ArbitroDto dto = new ArbitroDto();
        dto.setId(Long.valueOf(arbitro.getId()));
        dto.setNome(nome);
        dto.setPassword(pass);
        dto.setNif(nif);
        dto.setCertificado(cert);
        dto.setNumJogos(numJogos);

        connectionController.updateArbitro(Long.valueOf(arbitro.getId()),dto);
    }

    @FXML
    void clickVoltar(ActionEvent event) throws Exception {
        String prefix = "/pt/ul/fc/di/css/javafxexample/presentation/view/";
        root = FXMLLoader.load(getClass().getResource(prefix + "OptionsArbitro.fxml")); //Tenho que ter info de login para saber para onde voltar
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}

