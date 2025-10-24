package pt.ul.fc.di.css.javafxexample.presentation.mapper;

import pt.ul.fc.di.css.javafxexample.presentation.dto.ArbitroDto;
import pt.ul.fc.di.css.javafxexample.presentation.dto.CampeonatoPontosDto;
import pt.ul.fc.di.css.javafxexample.presentation.dto.EquipaDto;
import pt.ul.fc.di.css.javafxexample.presentation.dto.EstatisticasDto;
import pt.ul.fc.di.css.javafxexample.presentation.dto.JogadorDto;
import pt.ul.fc.di.css.javafxexample.presentation.dto.JogoDto;
import pt.ul.fc.di.css.javafxexample.presentation.model.Arbitro;
import pt.ul.fc.di.css.javafxexample.presentation.model.Campeonato;
import pt.ul.fc.di.css.javafxexample.presentation.model.Equipa;
import pt.ul.fc.di.css.javafxexample.presentation.model.Jogador;
import pt.ul.fc.di.css.javafxexample.presentation.model.Jogo;

import java.io.StringReader;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonString;

/*Para trabalhar especificamente os JSons ou fazer os Jsons que é preciso para enviar*/
public class ModelMapper {

    public Jogador mapJSONToJogador(HttpResponse<String> response) {
        //Mapeia um único JogadorDto em formato JSON para um objeto Jogador
        Jogador jogador = new Jogador();
        try (JsonReader reader = Json.createReader(new StringReader(response.body()))) {
            JsonObject jsonObject = reader.readObject();
            jogador.setId(jsonObject.getInt("id"));
            jogador.setNIF(jsonObject.getInt("nif"));
            jogador.setNome(jsonObject.getString("nome"));
            jogador.setPassword(jsonObject.getString("password"));
            jogador.setNumJogos(jsonObject.getInt("numJogos"));
            jogador.setPosicao(jsonObject.getString("posicao"));
            jogador.setEquipas(jsonObject.getJsonArray("nomesEquipas")
                                            .getValuesAs(JsonString.class).stream()
                                            .map(JsonString::getString)
                                            .toList());

        }

        return jogador;
    }

    public Arbitro mapJSONToArbitro(HttpResponse<String> response) {
        // Mapeia um único ArbitroDto em formato JSON para um objeto Arbitro
        Arbitro arbitro = new Arbitro();
        try (JsonReader reader = Json.createReader(new StringReader(response.body()))) {
            JsonObject jsonObject = reader.readObject();
            arbitro.setId(jsonObject.getInt("id"));
            arbitro.setNIF(jsonObject.getInt("nif"));
            arbitro.setNome(jsonObject.getString("nome"));
            arbitro.setPassword(jsonObject.getString("password"));
            arbitro.setNumJogos(jsonObject.getInt("numJogos"));
            arbitro.setCertificado(jsonObject.getBoolean("certificado"));
        }

        return arbitro;
    }


    public List<Jogador> mapJSONListToJogadorList(HttpResponse<String> response) {
        // Mapeia uma lista de JogadorDtos em formato JSON para uma lista de Jogadores
        List<Jogador> jogadores = new ArrayList<>();
        try (JsonReader reader = Json.createReader(new StringReader(response.body()))) {
            JsonArray jsonArray = reader.readArray();
            for (JsonObject jsonObject : jsonArray.getValuesAs(JsonObject.class)) {
                Jogador jogador = new Jogador();
                jogador.setId(jsonObject.getInt("id"));
                jogador.setNIF(jsonObject.getInt("nif"));
                jogador.setNome(jsonObject.getString("nome"));
                jogador.setPassword(jsonObject.getString("password"));
                jogador.setNumJogos(jsonObject.getInt("numJogos"));
                jogador.setPosicao(jsonObject.getString("posicao"));

                // Mapear o array de nomes de equipas para uma lista de String
                jogador.setEquipas(jsonObject.getJsonArray("nomesEquipas")
                                                        .getValuesAs(JsonString.class).stream()
                                                        .map(JsonString::getString)
                                                        .toList());
                
                jogadores.add(jogador);
            }
        }

        return jogadores;
    }

    public List<Equipa> mapJSONListToEquipaList(HttpResponse<String> response) {
        // Mapeia uma lista de EquipaDtos em formato JSON para uma lista de Equipa
        List<Equipa> equipas = new ArrayList<>();
        try (JsonReader reader = Json.createReader(new StringReader(response.body()))) {
            JsonArray jsonArray = reader.readArray();
            for (JsonObject jsonObject : jsonArray.getValuesAs(JsonObject.class)) {
                Equipa equipa = new Equipa();
                equipa.setNome(jsonObject.getString("nome"));
                
                // Mapear o array de nomes de jogadores para uma lista de String
                equipa.setJogadores(jsonObject.getJsonArray("jogadores")
                                                        .getValuesAs(JsonString.class).stream()
                                                        .map(JsonString::getString)
                                                        .toList());
                
                equipas.add(equipa);
            }
        }

        return equipas;
    }

    public List<Arbitro> mapJSONListToArbitroList(HttpResponse<String> response) {
        // Mapeia uma lista de ArbitroDtos em formato JSON para uma lista de Arbitros
        List<Arbitro> arbitros = new ArrayList<>();
        try (JsonReader reader = Json.createReader(new StringReader(response.body()))) {
            JsonArray jsonArray = reader.readArray();
            for (JsonObject jsonObject : jsonArray.getValuesAs(JsonObject.class)) {
                Arbitro arbitro = new Arbitro();
                arbitro.setId(jsonObject.getInt("id"));
                arbitro.setNIF(jsonObject.getInt("nif"));
                arbitro.setNome(jsonObject.getString("nome"));
                arbitro.setPassword(jsonObject.getString("password"));
                arbitro.setNumJogos(jsonObject.getInt("numJogos"));
                arbitro.setCertificado(jsonObject.getBoolean("certificado"));
                
                arbitros.add(arbitro);
            }
        }

        return arbitros;
    }

    public List<Jogo> mapJSONListToJogoList(HttpResponse<String> response) {
        // Mapeia uma lista de JogoDtos em formato JSON para uma lista de Jogo
        System.out.println(response.body());
        List<Jogo> jogos = new ArrayList<>();
        try (JsonReader reader = Json.createReader(new StringReader(response.body()))) {
            JsonArray jsonArray = reader.readArray();
            for (JsonObject jsonObject : jsonArray.getValuesAs(JsonObject.class)) {
                Jogo jogo = new Jogo();
                jogo.setId(jsonObject.getInt("id"));
                jogo.setData(jsonObject.getString("data"));
                jogo.setHorario(jsonObject.getString("horario"));
                jogo.setLocalizacao(jsonObject.getString("localizacao"));
                jogo.setNomeCampeonato(jsonObject.getString("nomeCampeonato", ""));
                jogo.setNomeEquipa1(jsonObject.getJsonObject("equipa1").getString("nome_equipa"));
                jogo.setNomeEquipa2(jsonObject.getJsonObject("equipa2").getString("nome_equipa"));
                jogo.setNomeArbitroPrincipal(jsonObject.getString("nomeArbitroPrincipal"));
                jogo.setArbitros(jsonObject.getJsonArray("nomesArbitros")
                        .getValuesAs(JsonString.class).stream()
                        .map(JsonString::getString)
                        .toList());
                jogo.setPlacarFinal(jsonObject.getString("placarFinal", ""));
                jogo.setNomeVencedor(jsonObject.getString("nomeVencedor",""));
                jogo.setStatus(jsonObject.getString("status"));
                
                jogos.add(jogo);
            }
        }

        return jogos;
    }

    public List<Campeonato> mapJSONListToCampeonatosList(HttpResponse<String> response) {
        // Mapeia uma lista de CampeonatoDtos em formato JSON para uma lista de Campeonato
        List<Campeonato> campeonatos = new ArrayList<>();
        try (JsonReader reader = Json.createReader(new StringReader(response.body()))) {
            JsonArray jsonArray = reader.readArray();
            for (JsonObject jsonObject : jsonArray.getValuesAs(JsonObject.class)) {
                Campeonato campeonato = new Campeonato();
                campeonato.setId(jsonObject.getInt("id"));
                campeonato.setNome(jsonObject.getString("nome"));
                campeonato.setAno(jsonObject.getInt("ano"));

                // Mapear o array de nomes de equipas para uma lista de String
                campeonato.setEquipas(jsonObject.getJsonArray("equipas")
                                                        .getValuesAs(JsonString.class).stream()
                                                        .map(JsonString::getString)
                                                        .toList());
                
                campeonato.setStatus(jsonObject.getString("estadoCampeonato"));
                
                campeonatos.add(campeonato);
            }
        }

        return campeonatos;
    }

    public EquipaDto mapJSONToEquipaDto(HttpResponse<String> response){
        // Mapeia uma Equipa formato JSON para um EquipaDto
        EquipaDto equipaDto = new EquipaDto();
        try (JsonReader reader = Json.createReader(new StringReader(response.body()))) {
            JsonObject jsonObject = reader.readObject();

            // Extrair os campos do JSON e configurar no objeto EquipaDto
            equipaDto.setNome(jsonObject.getString("nome"));

            // Mapear o array de jogadores
            JsonArray jogadoresArray = jsonObject.getJsonArray("jogadores");
            if (jogadoresArray != null) {
                for (JsonString jogador : jogadoresArray.getValuesAs(JsonString.class)) {
                    equipaDto.addJogador(jogador.getString());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao mapear JSON para EquipaDto: " + e.getMessage(), e);
        }

        return equipaDto;
    }

    public String mapJogadorDtoToJson(JogadorDto jogadorDto){
        // Mapeia uma JogadorDto para um JSON
        try {
            // Cria um builder para montar o JSON
            JsonObjectBuilder jsonBuilder = Json.createObjectBuilder()
                    .add("nif", jogadorDto.getNif())
                    .add("nome", jogadorDto.getNome())
                    .add("password", jogadorDto.getPassword())
                    .add("numJogos", jogadorDto.getNumJogos());
                    
            
            if(jogadorDto.getPosicao() == null){
                jsonBuilder.add("posicao", "");
            }else{
                jsonBuilder.add("posicao", jogadorDto.getPosicao().toString());
            }

            // Adiciona o array de equipas
            JsonArrayBuilder equipasArrayBuilder = Json.createArrayBuilder();
            if(jogadorDto.getNomesEquipas() == null){
                jsonBuilder.add("equipas", "[]");
            }else{
                jogadorDto.getNomesEquipas().forEach(equipasArrayBuilder::add);
                jsonBuilder.add("equipas", equipasArrayBuilder);
            }

            // Converte o JsonObject para string
            return jsonBuilder.build().toString();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JogadorDto para JSON: " + e.getMessage(), e);
        }
         
    }

    public String mapArbitroDtoToJson(ArbitroDto arbitroDto){
        // Mapeia uma ArbitroDto para um JSON
        try {
            JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();

            // Só adiciona o ID se ele não for null
            if (arbitroDto.getId() != null) {
                jsonBuilder.add("id", arbitroDto.getId());
            }
            // Cria um builder para montar o JSON
            jsonBuilder
                    .add("nif", arbitroDto.getNif())
                    .add("nome", arbitroDto.getNome())
                    .add("password", arbitroDto.getPassword())
                    .add("numJogos", arbitroDto.getNumJogos())
                    .add("certificado", arbitroDto.getCertificado().toString());

            // Converte o JsonObject para string
            return jsonBuilder.build().toString();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter ArbitroDto para JSON: " + e.getMessage(), e);
        }
         
    }

    public String mapEquipaDtoToJson(EquipaDto equipaDto){
        // Mapeia um EquipaDto para um JSON
        try {
            // Cria um builder para montar o JSON
            JsonObjectBuilder jsonBuilder = Json.createObjectBuilder()
                    .add("nome", equipaDto.getNome());

            // Converte o JsonObject para string
            return jsonBuilder.build().toString();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter EquipaDto para JSON: " + e.getMessage(), e);
        }
         
    }

    public String mapJogoDtoToJson(JogoDto jogoDto){
        // Mapeia uma JogoDto para um JSON
        try {
            JsonObjectBuilder equipa1Builder = Json.createObjectBuilder()
                .add("nome_equipa", jogoDto.getEquipa1() != null ? jogoDto.getEquipa1().getNome_equipa() : "")
                .add("nome_gk", jogoDto.getEquipa1() != null ? jogoDto.getEquipa1().getNome_gk() : "");
            
            JsonArrayBuilder equipa1JogadoresArrayBuilder = Json.createArrayBuilder();
            if(jogoDto.getEquipa1() != null){
                jogoDto.getEquipa1().getNomes_jogadores().forEach(equipa1JogadoresArrayBuilder::add);
            }
            equipa1Builder.add("nomes_jogadores", equipa1JogadoresArrayBuilder);

            JsonObjectBuilder equipa2Builder = Json.createObjectBuilder()
                .add("nome_equipa", jogoDto.getEquipa2() != null ? jogoDto.getEquipa2().getNome_equipa() : "")
                .add("nome_gk", jogoDto.getEquipa2() != null ? jogoDto.getEquipa2().getNome_gk() : "");

            JsonArrayBuilder equipa2JogadoresArrayBuilder = Json.createArrayBuilder();
            if(jogoDto.getEquipa2() != null){
                jogoDto.getEquipa2().getNomes_jogadores().forEach(equipa2JogadoresArrayBuilder::add);
            }
            equipa2Builder.add("nomes_jogadores", equipa2JogadoresArrayBuilder);

            // Transformar lista de EstatisticasDto em JSON
            JsonArrayBuilder estatisticasArrayBuilder = Json.createArrayBuilder();
            if (jogoDto.getEstatisticas() != null) {
                for (EstatisticasDto estatistica : jogoDto.getEstatisticas()) {
                    JsonObjectBuilder estatisticaBuilder = Json.createObjectBuilder()
                        .add("id", estatistica.getId() != null ? estatistica.getId() : 0)
                        .add("tipoDeEstatistica", estatistica.getTipoDeEstatistica().toString())
                        .add("jogador", estatistica.getJogador())
                        .add("equipa", estatistica.getEquipa())
                        .add("campeonato", estatistica.getCampeonato() != null ? estatistica.getCampeonato() : "")
                        .add("arbitro", estatistica.getArbitro());

                    estatisticasArrayBuilder.add(estatisticaBuilder);
                }
            }

            // Cria o array para nomes de árbitros
            JsonArrayBuilder arbitrosArrayBuilder = Json.createArrayBuilder();
            if (jogoDto.getNomesArbitros() != null) {
                jogoDto.getNomesArbitros().forEach(arbitrosArrayBuilder::add);
            }


            JsonObjectBuilder jsonBuilder = Json.createObjectBuilder()
                .add("id", jogoDto.getId() != null ? jogoDto.getId() : 0)
                .add("data", jogoDto.getData() != null ? jogoDto.getData().toString() : "")
                .add("horario", jogoDto.getHorario() != null ? jogoDto.getHorario().toString() : "")
                .add("localizacao", jogoDto.getLocalizacao() != null ? jogoDto.getLocalizacao() : "")
                .add("equipa1", equipa1Builder)
                .add("equipa2", equipa2Builder)
                .add("estatisticas", estatisticasArrayBuilder)
                .add("nomeArbitroPrincipal", jogoDto.getNomeArbitroPrincipal() != null ? jogoDto.getNomeArbitroPrincipal() : "")
                .add("nomesArbitros", arbitrosArrayBuilder)
                .add("placarFinal", jogoDto.getPlacarFinal() != null ? jogoDto.getPlacarFinal() : "")
                .add("nomeCampeonato", jogoDto.getNomeCampeonato() != null ? jogoDto.getNomeCampeonato() : "")
                .add("nomeVencedor", jogoDto.getNomeVencedor() != null ? jogoDto.getNomeVencedor() : "");
        
           
            // Converte o JsonObject para string
            return jsonBuilder.build().toString();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JogoDto para JSON: " + e.getMessage(), e);
        }
         
    }

    public String mapCampeonatoDtoToJson(CampeonatoPontosDto campeonatoDto){ 
        try {

            // Cria o array vazio para estatísticas
            JsonArrayBuilder estatisticasArrayBuilder = Json.createArrayBuilder(); // Sempre vazio

            // Cria o array para nomes de equipas
            JsonArrayBuilder equipasArrayBuilder = Json.createArrayBuilder();
            if (campeonatoDto.getEquipas() != null) {
                campeonatoDto.getEquipas().forEach(equipasArrayBuilder::add);
            }

            // Cria um builder para montar o JSON
            JsonObjectBuilder jsonBuilder = Json.createObjectBuilder()
                    .add("nome", campeonatoDto.getNome())
                    .add("ano", campeonatoDto.getAno())
                    .add("tabela", estatisticasArrayBuilder)
                    .add("equipas", equipasArrayBuilder);

            // Converte o JsonObject para string
            return jsonBuilder.build().toString();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter CampeonatoDto para JSON: " + e.getMessage(), e);
        }
    }

    public Equipa mapEquipaDtoToEquipa(EquipaDto dto){
        // Mapeia uma EquipaDto para uma Equipa
        Equipa equipa = new Equipa();
        equipa.setNome(dto.getNome());
        equipa.setJogadores(dto.getJogadores());

        return equipa;
    }
}

