package pt.ul.fc.di.css.javafxexample.presentation.control;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javafx.scene.control.Alert;
import pt.ul.fc.di.css.javafxexample.presentation.dto.ArbitroDto;
import pt.ul.fc.di.css.javafxexample.presentation.dto.CampeonatoPontosDto;
import pt.ul.fc.di.css.javafxexample.presentation.dto.EquipaDto;
import pt.ul.fc.di.css.javafxexample.presentation.dto.JogadorDto;
import pt.ul.fc.di.css.javafxexample.presentation.dto.JogoDto;
import pt.ul.fc.di.css.javafxexample.presentation.mapper.ModelMapper;
import pt.ul.fc.di.css.javafxexample.presentation.model.Arbitro;
import pt.ul.fc.di.css.javafxexample.presentation.model.Campeonato;
import pt.ul.fc.di.css.javafxexample.presentation.model.Equipa;
import pt.ul.fc.di.css.javafxexample.presentation.model.Jogador;
import pt.ul.fc.di.css.javafxexample.presentation.model.Jogo;

public class ConnectionController {

    //cliente HTTP que vai enviar todas as requisições
    private final HttpClient httpClient = HttpClient.newHttpClient();

    //URL base do servidor Backend
    private final String serverUrl = "http://localhost:8080";

    //Classe onde todas as conversões entre JSONs, DTOs e Models vão acontecer
    private final ModelMapper modelMapper = new ModelMapper();

    //AUTENTICAÇÕES/////////////////////////////////////////////////////////////////////////////////////////////////

    //Autenticação do Jogador
    public String loginJogador(String nome, String password) throws Exception {
        String encodedNome = URLEncoder.encode(nome, "UTF-8"); //por causa dos espaços no meio do nome
        String url = serverUrl + "/api/jogadores/login?nome=" + encodedNome + "&password=" + password;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.noBody()) // POST sem corpo
                .build();

        //guarda a resposta
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body(); // Login bem-sucedido
        } else {
            throw new Exception("Erro ao realizar login: " + response.body());
        }
    }

    //Autenticação do Árbitro
    public String loginArbitro(String nome, String password) throws Exception {
        String encodedNome = URLEncoder.encode(nome, "UTF-8"); //por causa dos espaços no meio do nome
        String url = serverUrl + "/api/arbitros/login?nome=" + encodedNome + "&password=" + password;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.noBody()) // POST sem corpo
                .build();

        //guarda a resposta
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body(); // Login bem-sucedido
        } else {
            throw new Exception("Erro ao realizar login: " + response.body());
        }
    }

    //GETALLS/////////////////////////////////////////////////////////////////////////////////////////////////

    public List<Jogador> getAllJogadores() throws Exception {
        String url = serverUrl + "/api/jogadores";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        //guarda a resposta
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            //Mapeia a resposta JSON para uma lista de objetos Jogador
            List<Jogador> jogadores = modelMapper.mapJSONListToJogadorList(response);

            //Converter JogadorDto para Jogador
            return jogadores;
        } else {
            throw new Exception("Erro ao buscar jogadores: " + response.body());
        }
    }

    public List<Equipa> getAllEquipas() throws Exception {
        String url = serverUrl + "/api/equipas";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        //guarda a resposta
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            //Mapeia a resposta JSON para uma lista de objetos Equipas
            List<Equipa> equipas = modelMapper.mapJSONListToEquipaList(response);

            //Converter EquipaDto para Equipa
            return equipas;
        } else {
            throw new Exception("Erro ao buscar jogadores: " + response.body());
        }
    }

    public List<Arbitro> getAllArbitros() throws Exception {
        String url = serverUrl + "/api/arbitros";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        //guarda a resposta
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            //Mapeia a resposta JSON para uma lista de objetos Arbitro
            List<Arbitro> arbitros = modelMapper.mapJSONListToArbitroList(response);

            //Converter ArbitroDto para Arbitro
            return arbitros;
        } else {
            throw new Exception("Erro ao buscar jogadores: " + response.body());
        }
    }

    public List<Jogo> getAllJogos() throws Exception {
        String url = serverUrl + "/api/jogos";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        //guarda a resposta
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
             //Mapeia a resposta JSON para uma lista de objetos Jogo
            List<Jogo> jogos = modelMapper.mapJSONListToJogoList(response);

            //Converter JogoDto para Jogo
            return jogos;
        } else {
            throw new Exception("Erro ao buscar jogadores: " + response.body());
        }
    }

    public List<Campeonato> getAllCampeonatos() throws Exception {
        String url = serverUrl + "/api/campeonatos";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        //guarda a resposta
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            //Mapeia a resposta JSON para uma lista de objetos Campeonato
            List<Campeonato> campeonatos = modelMapper.mapJSONListToCampeonatosList(response);

            //Converter CampeonatoDto para Campeonato
            return campeonatos;
        } else {
            throw new Exception("Erro ao buscar jogadores: " + response.body());
        }
    }

    //GETS ESPECÍFICOS/////////////////////////////////////////////////////////////////////////////////////////////////

    public Jogador getJogador(String nome) throws Exception {
        String encodedNome = URLEncoder.encode(nome, "UTF-8"); //por causa dos espaços no meio do nome
        String url = serverUrl + "/api/jogadores/jogador?nome=" + encodedNome;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        //guarda a resposta
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response);

        if (response.statusCode() == 200) {
            //Mapeia a resposta JSON para um objeto Jogador
            Jogador jogador = modelMapper.mapJSONToJogador(response);

            // Converter JogadorDto para Jogador
            return jogador;
        } else {
            throw new Exception("Erro ao buscar jogadores: " + response.body());
        }
    }

    public Arbitro getArbitro(String nome) throws Exception {
        String encodedNome = URLEncoder.encode(nome, StandardCharsets.UTF_8.toString()); //por causa dos espaços no meio do nome
        String url = serverUrl + "/api/arbitros/arbitro?nome=" + encodedNome;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        //guarda a resposta
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            //Mapeia a resposta JSON para um objeto Arbitro
            Arbitro arbitro = modelMapper.mapJSONToArbitro(response);

            return arbitro;
        } else {
            throw new Exception("Erro ao buscar arbitros: " + response.body());
        }
    }

    public EquipaDto getEquipa(String nome) throws Exception {
        String encodedNome = URLEncoder.encode(nome, StandardCharsets.UTF_8.toString()).replace("+", "%20"); //por causa dos espaços no meio do nome
        String url = serverUrl + "/api/equipas/by-nome/" + encodedNome;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        //guarda a resposta
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            //Mapeia a resposta JSON para um objeto EquipaDto
            EquipaDto equipa = modelMapper.mapJSONToEquipaDto(response);

            return equipa;
        } else {
            throw new Exception("Erro ao buscar a equipa: " + response.body());
        }
    }

    //POSTS/////////////////////////////////////////////////////////////////////////////////////////////////

    public String postJogador(JogadorDto jogadorDto) throws Exception {
        String url = serverUrl + "/api/jogadores";
        String json = modelMapper.mapJogadorDtoToJson(jogadorDto); //Converte de DTO para um JSON

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        // Enviar a requisição e obter a resposta
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201 || response.statusCode() == 200) {
            // Alerta de sucesso
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Operação Bem-Sucedida");
            alert.setHeaderText(null);
            alert.setContentText("Jogador criado com sucesso!");
            alert.showAndWait();
            return response.body();
        } else {
            // Alerta de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Falha ao criar jogador");
            alert.setContentText("Detalhes: " + response.body());
            alert.showAndWait();
            throw new Exception("Erro ao criar jogador: " + response.body());
        }

    }

    public String postArbitro(ArbitroDto arbitroDto) throws Exception {
        String url = serverUrl + "/api/arbitros";
        String json = modelMapper.mapArbitroDtoToJson(arbitroDto); //Converte de DTO para um JSON

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        // Enviar a requisição e obter a resposta
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201 ||  response.statusCode() == 200) { // Código HTTP 201 indica criação bem-sucedida
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Operação Bem-Sucedida");
            alert.setHeaderText(null);
            alert.setContentText("A operação foi concluída com sucesso!");
            alert.showAndWait();
            return response.body(); // Retorna a resposta do servidor
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Falha ao criar árbitro");
            alert.setContentText("Detalhes: " + response.body());
            alert.showAndWait();
            throw new Exception("Erro ao criar árbitro: " + response.body());
        }

    }

    public String postEquipa(EquipaDto equipaDto) throws Exception {
        String url = serverUrl + "/api/equipas";
        String json = modelMapper.mapEquipaDtoToJson(equipaDto); //Converte de DTO para um JSON

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        // Enviar a requisição e obter a resposta
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201 ||  response.statusCode() == 200) { // Código HTTP 201 indica criação bem-sucedida
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Operação Bem-Sucedida");
            alert.setHeaderText(null);
            alert.setContentText("A operação foi concluída com sucesso!");
            alert.showAndWait();
            return response.body(); // Retorna a resposta do servidor
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Falha ao criar equipa");
            alert.setContentText("Detalhes: " + response.body());
            alert.showAndWait();
            throw new Exception("Erro ao criar equipa: " + response.body());
        }

    }

    public String postJogo(JogoDto jogoDto) throws Exception {
        String url = serverUrl + "/api/jogos/create";
        String json = modelMapper.mapJogoDtoToJson(jogoDto); //Converte de DTO para um JSON

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        // Enviar a requisição e obter a resposta
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201 ||  response.statusCode() == 200) { // Código HTTP 201 indica criação bem-sucedida
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Operação Bem-Sucedida");
            alert.setHeaderText(null);
            alert.setContentText("A operação foi concluída com sucesso!");
            alert.showAndWait();
            return response.body(); // Retorna a resposta do servidor
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Falha ao criar jogo");
            alert.setContentText("Detalhes: " + response.body());
            alert.showAndWait();
            throw new Exception("Erro ao criar jogo: " + response.body());
        }

    }

    public String postCampeonato(CampeonatoPontosDto campeonatoDto) throws Exception {
        String url = serverUrl + "/api/campeonatos";
        String json = modelMapper.mapCampeonatoDtoToJson(campeonatoDto); //Converte de DTO para um JSON

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        // Enviar a requisição e obter a resposta
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201 ||  response.statusCode() == 200) { // Código HTTP 201 indica criação bem-sucedida
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Operação Bem-Sucedida");
            alert.setHeaderText(null);
            alert.setContentText("A operação foi concluída com sucesso!");
            alert.showAndWait();
            return response.body(); // Retorna a resposta do servidor
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Falha ao criar campeonato");
            alert.setContentText("Detalhes: " + response.body());
            alert.showAndWait();
            throw new Exception("Erro ao criar campeonato: " + response.body());
        }

    }

    //DELETES/////////////////////////////////////////////////////////////////////////////////////////////////

    public String deleteArbitro(Integer nif) throws Exception {

        String url = serverUrl + "/api/arbitros/deleteByNif?nif="+ nif;

        //cria request do tipo DELETE
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();

        //fazer o pedido e guardar a resposta
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Operação Bem-Sucedida");
            alert.setHeaderText(null);
            alert.setContentText("A operação foi concluída com sucesso!");
            alert.showAndWait();
            return response.body();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Falha ao apagar árbitro");
            alert.setContentText("Detalhes: " + response.body());
            alert.showAndWait();
            throw new Exception("Erro ao apagar árbitro: " + response.body());
        }

    }

    public String deleteJogador(Integer nif) throws Exception {

        String url = serverUrl + "/api/jogadores/deleteByNif?nif="+ nif;

        //cria request do tipo DELETE
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();

        //fazer o pedido e guardar a resposta
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Operação Bem-Sucedida");
            alert.setHeaderText(null);
            alert.setContentText("A operação foi concluída com sucesso!");
            alert.showAndWait();
            return response.body();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Falha ao apagar jogador");
            alert.setContentText("Detalhes: " + response.body());
            alert.showAndWait();
            throw new Exception("Erro ao apagar jogador: " + response.body());
        }

    }

    public String deleteEquipa(String nome) throws Exception {
        String encodedNome = URLEncoder.encode(nome, StandardCharsets.UTF_8.toString()).replace("+", "%20"); //por causa dos espaços no meio do nome
        String url = serverUrl + "/api/equipas/deleteByNome?nome="+ encodedNome;

        //cria request do tipo DELETE
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();

        //fazer o pedido e guardar a resposta
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Operação Bem-Sucedida");
            alert.setHeaderText(null);
            alert.setContentText("A operação foi concluída com sucesso!");
            alert.showAndWait();
            return response.body();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Falha ao apagar equipa");
            alert.setContentText("Detalhes: " + response.body());
            alert.showAndWait();
            throw new Exception("Erro ao apagar equipa: " + response.body());
        }

    }

    //CANCELAMENTOS/////////////////////////////////////////////////////////////////////////////////////////////////

    //Cancela um Jogo com base no seu ID
    public String cancelarJogo(Integer id) throws Exception {
        String url = serverUrl + "/api/jogos/" + id +"/cancel";

        //envia request PATCH sem corpo
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/json")
            .method("PATCH", HttpRequest.BodyPublishers.noBody()) // Configura explicitamente o método PATCH
            .build();

        //faz pedido e aguarda resposta
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Operação Bem-Sucedida");
            alert.setHeaderText(null);
            alert.setContentText("A operação foi concluída com sucesso!");
            alert.showAndWait();
            return response.body();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Falha ao cancelar jogo");
            alert.setContentText("Detalhes: " + response.body());
            alert.showAndWait();
            throw new Exception("Erro ao cancelar jogo: " + response.body());
        }

    }

    //Cancela um Campeonato com base no seu ID
    public String cancelarCampeonato(Integer id) throws Exception {
        String url = serverUrl + "/api/campeonatos/" + id;

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/json")
            .DELETE()
            .build();

        //faz pedido e aguarda resposta
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Operação Bem-Sucedida");
            alert.setHeaderText(null);
            alert.setContentText("A operação foi concluída com sucesso!");
            alert.showAndWait();
            return response.body();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Falha ao cancelar Campeonato");
            alert.setContentText("Detalhes: " + response.body());
            alert.showAndWait();
            throw new Exception("Erro ao cancelar campeonato: " + response.body());
        }

    }

    //ADIÇÕES E REMOÇÕES/////////////////////////////////////////////////////////////////////////////////////////////////

    //Adiciona um Jogador a uma equipa com base no seu ID e no seu nome
    public Equipa adicionarJogadorAEquipa(Integer id, String nomeEquipa) throws Exception {
        String encodedNome = URLEncoder.encode(nomeEquipa, StandardCharsets.UTF_8.toString()).replace("+", "%20"); //por causa dos espaços no meio do nome
        String url = serverUrl + "/api/jogadores/add/equipa?id="+ id + "&nomeEquipa=" + encodedNome;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        //envia o pedido e espera pela resposta
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201 ||  response.statusCode() == 200) { // Código HTTP 201 indica criação bem-sucedida
            EquipaDto dto = getEquipa(nomeEquipa);
            Equipa equipa = modelMapper.mapEquipaDtoToEquipa(dto);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Operação Bem-Sucedida");
            alert.setHeaderText(null);
            alert.setContentText("A operação foi concluída com sucesso!");
            alert.showAndWait();
            return equipa; // Retorna a resposta do servidor
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Falha ao adicionar equipa a jogador");
            alert.setContentText("Detalhes: " + response.body());
            alert.showAndWait();
            throw new Exception("Erro ao adicionar equipa a jogador: " + response.body());
        }

    }

    //Adiciona uma Equipa a um Campeonato com base no seu ID e no seu nome
    public String adicionarEquipaACampeonato(Integer id, String nomeEquipa) throws Exception {
        String encodedNome = URLEncoder.encode(nomeEquipa, StandardCharsets.UTF_8.toString()).replace("+", "%20");//por causa dos espaços no meio do nome
        String url = serverUrl + "/api/campeonatos/" + id + "/addEquipa?nome=" + encodedNome;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();

        //envia o pedido e espera pela resposta
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201 ||  response.statusCode() == 200) { // Código HTTP 201 indica criação bem-sucedida
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Operação Bem-Sucedida");
            alert.setHeaderText(null);
            alert.setContentText("A operação foi concluída com sucesso!");
            alert.showAndWait();
            return response.body(); // Retorna a resposta do servidor
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Falha ao adicionar equipa a jogador");
            alert.setContentText("Detalhes: " + response.body());
            alert.showAndWait();
            throw new Exception("Erro ao adicionar equipa a jogador: " + response.body());
        }
    }

    //Remove um Jogador de uma equipa com base no seu ID e no seu nome
    public Equipa removerJogadorAEquipa(Integer id, String nomeEquipa) throws Exception {
        String encodedNome = URLEncoder.encode(nomeEquipa, StandardCharsets.UTF_8.toString()).replace("+", "%20");//por causa dos espaços no meio do nome
        String url = serverUrl + "/api/jogadores/remove/equipa?jogadorId="+ id + "&nomeEquipa=" + encodedNome;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();

        //envia o pedido e espera pela resposta
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201 ||  response.statusCode() == 200) { // Código HTTP 201 indica criação bem-sucedida
            EquipaDto dto = getEquipa(nomeEquipa);
            Equipa equipa = modelMapper.mapEquipaDtoToEquipa(dto);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Operação Bem-Sucedida");
            alert.setHeaderText(null);
            alert.setContentText("A operação foi concluída com sucesso!");
            alert.showAndWait();
            return equipa; // Retorna a resposta do servidor
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Falha ao adicionar equipa a jogador");
            alert.setContentText("Detalhes: " + response.body());
            alert.showAndWait();
            throw new Exception("Erro ao adicionar equipa a jogador: " + response.body());
        }

    }

    //Remove uma Equipa de um Campeonato com base no seu ID e no seu nome
    public String removerEquipaDeCampeonato(Integer id, String nomeEquipa) throws Exception {
        String encodedNome = URLEncoder.encode(nomeEquipa, StandardCharsets.UTF_8.toString()).replace("+", "%20");//por causa dos espaços no meio do nome
        String url = serverUrl + "/api/campeonatos/" + id + "/removeEquipa?nome=" + encodedNome;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();

        //envia o pedido e espera pela resposta
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201 ||  response.statusCode() == 200) { // Código HTTP 201 indica criação bem-sucedida
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Operação Bem-Sucedida");
            alert.setHeaderText(null);
            alert.setContentText("A operação foi concluída com sucesso!");
            alert.showAndWait();
            return response.body(); // Retorna a resposta do servidor
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Falha ao remover equipa a campeonato");
            alert.setContentText("Detalhes: " + response.body());
            alert.showAndWait();
            throw new Exception("Erro ao remover equipa de campeonato: " + response.body());
        }

    }

    //UPDATES/////////////////////////////////////////////////////////////////////////////////////////////////

    public String updateJogador(Long id, JogadorDto jogadorDto) throws Exception {
        String url = serverUrl + "/api/jogadores/" + id;
        String json = modelMapper.mapJogadorDtoToJson(jogadorDto);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        //envia o pedido e espera pela resposta
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201 || response.statusCode() == 200) {
            // Alerta de sucesso
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Operação Bem-Sucedida");
            alert.setHeaderText(null);
            alert.setContentText("Jogador atualizado com sucesso!");
            alert.showAndWait();
            return response.body();
        } else {
            // Alerta de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Falha ao atualizar jogador");
            alert.setContentText("Detalhes: " + response.body());
            alert.showAndWait();
            throw new Exception("Erro ao atualizar jogador: " + response.body());
        }

    }

    public String updateArbitro(Long id, ArbitroDto arbitroDto) throws Exception {
        String url = serverUrl + "/api/arbitros/" + id;
        String json = modelMapper.mapArbitroDtoToJson(arbitroDto);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        //envia o pedido e espera pela resposta
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201 || response.statusCode() == 200) {
            // Alerta de sucesso
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Operação Bem-Sucedida");
            alert.setHeaderText(null);
            alert.setContentText("Arbitro atualizado com sucesso!");
            alert.showAndWait();
            return response.body();
        } else {
            // Alerta de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Falha ao atualizar arbitro");
            alert.setContentText("Detalhes: " + response.body());
            alert.showAndWait();
            throw new Exception("Erro ao atualizar arbitro: " + response.body());
        }

    }

    public String updateCampeonato(Long id, CampeonatoPontosDto dto) throws Exception {
        String url = serverUrl + "/api/campeonatos/update/" + id;
        String json = modelMapper.mapCampeonatoDtoToJson(dto);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        //envia o pedido e espera pela resposta
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201 || response.statusCode() == 200) {
            // Alerta de sucesso
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Operação Bem-Sucedida");
            alert.setHeaderText(null);
            alert.setContentText("Campeonato atualizado com sucesso!");
            alert.showAndWait();
            return response.body();
        } else {
            // Alerta de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Falha ao atualizar campeonato");
            alert.setContentText("Detalhes: " + response.body());
            alert.showAndWait();
            throw new Exception("Erro ao atualizar campeonato: " + response.body());
        }

    }

    public String updateFinalScore(Integer id, JogoDto dto) throws Exception {
        String url = serverUrl + "/api/jogos/" + id +"/updateFinalScore";
        String json = modelMapper.mapJogoDtoToJson(dto);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/json")
            .method("PATCH", HttpRequest.BodyPublishers.ofString(json)) // Configura explicitamente o método PATCH
            .build();

        //envia o pedido e espera pela resposta
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Operação Bem-Sucedida");
            alert.setHeaderText(null);
            alert.setContentText("A operação foi concluída com sucesso!");
            alert.showAndWait();
            return response.body();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Falha ao atualizar o resultado final de um jogo");
            alert.setContentText("Detalhes: " + response.body());
            alert.showAndWait();
            throw new Exception("Erro ao atualizar o resultado final de um jogo: " + response.body());
        }

    }

}
