package pt.ul.fc.css.soccernow;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.transaction.Transactional;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.ul.fc.css.soccernow.controller.ArbitroController;
import pt.ul.fc.css.soccernow.controller.CampeonatoController;
import pt.ul.fc.css.soccernow.controller.EquipaController;
import pt.ul.fc.css.soccernow.controller.JogadorController;
import pt.ul.fc.css.soccernow.controller.JogoController;
import pt.ul.fc.css.soccernow.dto.ArbitroDto;
import pt.ul.fc.css.soccernow.dto.EquipaDto;
import pt.ul.fc.css.soccernow.dto.EquipaJogoDto;
//import pt.ul.fc.css.soccernow.dto.EstatisticasDto;
import pt.ul.fc.css.soccernow.dto.JogadorDto;
import pt.ul.fc.css.soccernow.dto.JogoDto;
//import pt.ul.fc.css.soccernow.enums.EstatisticaTipo;
import pt.ul.fc.css.soccernow.enums.Posicao;
import pt.ul.fc.css.soccernow.enums.Status;
import pt.ul.fc.css.soccernow.repository.UtilizadorRepository;

@SpringBootApplication
public class SoccerNowApplication {

    @Autowired
    UtilizadorRepository utilizadorRepository;
    @Autowired
    EquipaController equipaController;
    @Autowired
    JogadorController jogadorController;
    @Autowired
    ArbitroController arbitroController;
    @Autowired
    JogoController jogoController;
    @Autowired
    CampeonatoController campeonatoController;

    public static void main(String[] args) {
        SpringApplication.run(SoccerNowApplication.class, args);
    }

    @Bean
    @Transactional
    public CommandLineRunner demo() {
        return (args) -> {
            System.out.println("\n" + "do some sanity tests here" + "\n");
            
            //Set to TRUE if you want to Populate the DB upon running the app
            Boolean popular = true;

            //Set to TRUE if you want to run tests upon running the app
            Boolean testar = false;

            if(popular) {
                System.out.println("\n" + "-----POPULATING DB-----" + "\n");
                System.out.println("\n" + "Creating Jogadores" + "\n");
                List<Integer> nifsJogadores = new ArrayList<Integer>(
                    Arrays.asList(123456789, 987654321, 111222333, 555444666, 777888999,
                                246813579, 135792468, 384729105, 657483920, 908172635,
                                506070809, 102938475, 765432189, 876543210, 192837465));
                List<String> nomesJogadores = new ArrayList<String>(
                    Arrays.asList("Tomás Valente", "João Martins", "Henrique Ramos", "Ricardo Cardoso", "Francisco Silva",
                                "João Maria", "António Oliveira", "Fernando Silva", "Rodrigo Costa", "Luís Santos",
                                "Pedro Hortiga", "Bernardo Vieira", "Martim Santos", "André Fernandes", "Vítor Cruz"));

                //TESTAR CRIAÇÃO JOGADOR
                for(int i = 0; i < nomesJogadores.size(); i++) {
                    JogadorDto dto = new JogadorDto();
                    dto.setNif(nifsJogadores.get(i));
                    dto.setNome(nomesJogadores.get(i));
                    dto.setPassword(RandomStringUtils.randomAlphabetic(12));
                    dto.setNumJogos(0);
                    Random r = new Random();
                    List<Posicao> posicoes = new ArrayList<Posicao>(Arrays.asList(Posicao.GK, Posicao.DEF, Posicao.MED, Posicao.ATQ));
                    dto.setPosicao(posicoes.get(r.nextInt(4)));
                    ResponseEntity<JogadorDto> eq = jogadorController.createJogador(dto);
                    Boolean success = eq.equals(ResponseEntity.ok(dto));
                    if(success) {
                        System.out.println("\n" + "Jogador Created Successfully" + "\n");
                    }
                    else {
                        System.out.println("\n" + "Error in Creating Jogador" + "\n");
                    }
                }

                System.out.println("\n" + "Creating Arbitros" + "\n");
                List<Integer> nifsArbitros = new ArrayList<Integer>(Arrays.asList(283746591, 374859602, 465768793, 546372819, 627384951));
                List<String> nomesArbitros = new ArrayList<String>(Arrays.asList("Leonardo Paredes", "Lucas Ribeiro", "Guilherme Pais", "Isabel Rodrigues", "Diana Fonseca"));

                //TESTAR CRIAÇÃO ÁRBITRO
                for(int i = 0; i < nomesArbitros.size(); i++) {
                    ArbitroDto dto = new ArbitroDto();
                    dto.setNif(nifsArbitros.get(i));
                    dto.setNome(nomesArbitros.get(i));
                    dto.setPassword(RandomStringUtils.randomAlphabetic(12));
                    dto.setNumJogos(0);
                    Random r = new Random();
                    dto.setCertificado(0 == r.nextInt(2));
                    ResponseEntity<ArbitroDto> eq = arbitroController.createArbitro(dto);
                    Boolean success = eq.equals(ResponseEntity.ok(dto));
                    if(success) {
                        System.out.println("\n" + "Arbitro Created Successfully" + "\n");
                    }
                    else {
                        System.out.println("\n" + "Error in Creating Arbitro" + "\n");
                    }
                }

                System.out.println("\n" + "Creating Equipas" + "\n");
                List<String> nomesEquipas = new ArrayList<String>(Arrays.asList("FC Porto", "SL Benfica", "Sporting CP", "SC Braga", "Boavista", "V. Guimarães", "Estoril", "Tondela"));

                //TESTAR CRIAÇÃO EQUIPA
                for(int i = 0; i < nomesEquipas.size(); i++) {
                    EquipaDto dto = new EquipaDto();
                    dto.setNome(nomesEquipas.get(i));
                    ResponseEntity<EquipaDto> eq = equipaController.createEquipa(dto);
                    Boolean success = eq.equals(ResponseEntity.ok(dto));
                    if(success) {
                        System.out.println("\n" + "Equipa Created Successfully" + "\n");
                    }
                    else {
                        System.out.println("\n" + "Error in Creating Equipa" + "\n");
                    }
                }

                System.out.println("\n" + "Adding Jogadores to Equipas" + "\n");

                //TESTAR LIGAÇÃO ENTRE JOGADOR E EQUIPA COM A ADIÇÃO DE UM JOGADOR À EQUIPA
                for(int i = 1; i < 16; i++) {
                    Random r = new Random();
                    List<Integer> used = new ArrayList<>();
                    for(int j = 0; j < 5; j++) {
                        //for each Jogador we add it to 5 different random Equipas
                        int random = r.nextInt(8);
                        while(used.contains(random)){
                            random = r.nextInt(8);
                        }
                        ResponseEntity<JogadorDto> eq = jogadorController.adicionarEquipaAoJogador(Long.valueOf(i), nomesEquipas.get(random));
                        System.out.println("\n" + eq.getBody().getNome() + " has been added to Equipa " + eq.getBody().getNomesEquipas().get(j) + "\n");
                        used.add(random);
                    }
                }

                System.out.println("\n" + "Testing 'Add Equipa to Jogador'" + "\n");

                int count = 0;
                for(int i = 1; i < 16; i++) {
                    ResponseEntity<JogadorDto> eq = jogadorController.getJogador(Long.valueOf(i));
                    JogadorDto dto = eq.getBody();
                    List<String> equipas = dto.getNomesEquipas();
                    for(int j = 0; j < 5; j++) {
                        EquipaDto eDto = equipaController.getEquipaByNome(equipas.get(j)).getBody();
                        if(eDto.getJogadores().contains(dto.getNome())) {
                            count++;
                            System.out.println("\n" + "Confirmation that whenever a Jogador adds an Equipa to its List<Equipa> that Equipa get the same Jogador added to its List<Jogador>" + "\n");
                        }
                        else {
                            System.out.println("\n" + "Error in Jogador Equipa Relationship" + "\n");
                        }
                    }
                }

                System.out.println("\n" + (count/75) * 100 + "% Success rate on test" + "\n");

                System.out.println("\n" + "Creating Jogos" + "\n");

                List<LocalDate> datas = new ArrayList<>();
                List<LocalDateTime> horas = new ArrayList<>();
                for(int i = 0; i < 5; i++) {
                    LocalDate ld = LocalDate.of(2025, 5+(i/2), 16+i);
                    datas.add(ld);
                    LocalDateTime ldt = LocalDateTime.of(2025, 5+(i/2), 16+i, 15+i, 00);
                    horas.add(ldt);
                }
                List<String> localizacoes = new ArrayList<String>(
                    Arrays.asList("Estádio da Luz", "Estádio de Alvalade", "Estádio do Dragão", "Estádio Municipal de Braga", "Estádio do Bessa Séc. XXI"));
                
                //TESTAR CRIAÇÃO JOGO
                for(int i = 0; i < 5 ; i++) {
                    JogoDto jDto = new JogoDto();
                    jDto.setData(datas.get(i));
                    jDto.setHorario(horas.get(i));
                    Random r = new Random();
                    jDto.setLocalizacao(localizacoes.get(r.nextInt(5)));
                    List<String> equipas = new ArrayList<>();
                    List<Integer> used1 = new ArrayList<>();
                    Boolean complete = false;
                    while(!complete) {
                        for(int j = 0; j < 2; j++) {
                            int random = r.nextInt(8);
                            while(used1.contains(random)){
                                random = r.nextInt(8);
                            }
                            equipas.add(nomesEquipas.get(random));
                            used1.add(random);
                        }
                        EquipaDto equipa1 = equipaController.getEquipaByNome(equipas.get(0)).getBody();
                        EquipaDto equipa2 = equipaController.getEquipaByNome(equipas.get(1)).getBody();
                        if(equipa1.getJogadores().size() >= 5 && equipa2.getJogadores().size() >= 5){
                            //fazer listas com os jogadores únicos à equipa 1 e 2 e uma lista de jogadores que ambos têm
                            List<String> jogadores1 = equipa1.getJogadores();
                            System.out.println("\n\n" + jogadores1 + " Antes de remover os partilhados" + "\n\n");
                            List<String> jogadores2 = equipa2.getJogadores();
                            System.out.println("\n\n" + jogadores2 + " Antes de remover os partilhados" + "\n\n");
                            List<String> jogadoresPartilhados = new ArrayList<>(jogadores1);
                            jogadoresPartilhados.retainAll(jogadores2);
                            jogadores1.removeAll(jogadoresPartilhados);
                            jogadores2.removeAll(jogadoresPartilhados);
                            System.out.println("\n\n" + jogadores1 + "\n\n");
                            System.out.println("\n\n" + jogadores2 + "\n\n");
                            System.out.println("\n\n" + jogadoresPartilhados + "\n\n");

                            if(((jogadores1.size()) + (jogadores2.size()) + (jogadoresPartilhados.size())) >= 10) {
                                EquipaJogoDto ejDto1 = new EquipaJogoDto();
                                List<String> jogadoresEJ1 = new ArrayList<>();
                                while(jogadoresEJ1.size() < 5) {
                                    if(jogadores1.size() >= 5) {
                                        List<Integer> used3 = new ArrayList<>();
                                        for(int l = 0; l < 5; l++) {
                                            int random1 = r.nextInt(jogadores1.size());
                                            while(used3.contains(random1)){
                                                random1 = r.nextInt(jogadores1.size());
                                            }
                                            jogadoresEJ1.add(jogadores1.get(random1));
                                            used3.add(random1);
                                        }
                                    }
                                    else {
                                        jogadoresEJ1.addAll(jogadores1);
                                        int size = jogadoresEJ1.size();
                                        List<Integer> used4 = new ArrayList<>();
                                        List<String> toRemove = new ArrayList<>();
                                        for(int m = 0; m < (5 - (size)); m++) {
                                            int random2 = r.nextInt(jogadoresPartilhados.size());
                                            while(used4.contains(random2)){
                                                random2 = r.nextInt(jogadoresPartilhados.size());
                                            }
                                            jogadoresEJ1.add(jogadoresPartilhados.get(random2));
                                            toRemove.add(jogadoresPartilhados.get(random2));
                                            used4.add(random2);
                                        }
                                        jogadoresPartilhados.removeAll(toRemove);
                                    }
                                }
                                ejDto1.setNome_equipa(equipa1.getNome());
                                ejDto1.setNomes_jogadores(jogadoresEJ1);
                                int r1 = r.nextInt(5);
                                ejDto1.setNome_gk(jogadoresEJ1.get(r1));
                                jDto.setEquipa1(ejDto1);

                                EquipaJogoDto ejDto2 = new EquipaJogoDto();
                                List<String> jogadoresEJ2 = new ArrayList<>();
                                while(jogadoresEJ2.size() < 5) {
                                    if(jogadores2.size() >= 5) {
                                        List<Integer> used5 = new ArrayList<>();
                                        for(int l = 0; l < 5; l++) {
                                            int random3 = r.nextInt(jogadores2.size());
                                            while(used5.contains(random3)){
                                                random3 = r.nextInt(jogadores2.size());
                                            }
                                            jogadoresEJ2.add(jogadores2.get(random3));
                                            used5.add(random3);
                                        }
                                    }
                                    else {
                                        jogadoresEJ2.addAll(jogadores2);
                                        int size = jogadoresEJ2.size();
                                        List<Integer> used6 = new ArrayList<>();
                                        List<String> toRemove = new ArrayList<>();
                                        for(int m = 0; m < (5 - (size)); m++) {
                                            int random4 = r.nextInt(jogadoresPartilhados.size());
                                            while(used6.contains(random4)){
                                                random4 = r.nextInt(jogadoresPartilhados.size());
                                            }
                                            jogadoresEJ2.add(jogadoresPartilhados.get(random4));
                                            toRemove.add(jogadoresPartilhados.get(random4));
                                            used6.add(random4);
                                        }
                                        jogadoresPartilhados.removeAll(toRemove);
                                    }
                                }
                                ejDto2.setNome_equipa(equipa2.getNome());
                                ejDto2.setNomes_jogadores(jogadoresEJ2);
                                int r2 = r.nextInt(5);
                                ejDto2.setNome_gk(jogadoresEJ2.get(r2));
                                jDto.setEquipa2(ejDto2);
                                complete = true;
                            }
                        }
                    }

                    jDto.setEstatisticas(null);
                    jDto.setPlacarFinal(null);
                    jDto.setStatus(Status.COMECADO);
                    jDto.setNomeCampeonato(null);
                    Random r1 = new Random();
                    int amount = r1.nextInt(5)+1;
                    List<String> arbitros = new ArrayList<>();
                    List<Integer> used2 = new ArrayList<>();
                    for(int k = 0; k < amount; k++) {
                        int random = r.nextInt(5);
                        while(used2.contains(random)){
                            random = r.nextInt(5);
                        }
                        arbitros.add(nomesArbitros.get(random));
                        used2.add(random);
                    }
                    jDto.setNomeArbitroPrincipal(arbitros.get(0));
                    arbitros.remove(0);
                    jDto.setNomesArbitros(arbitros);
                    jDto.setNomeVencedor(null);
                    ResponseEntity<JogoDto> eq = jogoController.createJogo(jDto);
                    Boolean success = eq.equals(ResponseEntity.ok(jDto));;
                    if(success) {
                        System.out.println("\n" + "Jogo Created Successfully" + "\n");
                    }
                    else {
                        System.out.println("\n" + "Error in Creating Jogo" + "\n");
                    }
                }

                System.out.println("\n" + "Jogos Created Successfully" + "\n");


                //This section cancels the first game and gives results to games two and three - NOT IN USE
                // System.out.println("\n" + "Let's Cancel the First Game" + "\n");

                // ResponseEntity<JogoDto> eq = jogoController.cancelJogo(Long.valueOf(1));
                // if(eq.getBody().getStatus() == Status.CANCELADO) {
                //     System.out.println("\n" + "Jogo Canceled Successfully" + "\n");
                // }
                // else {
                //     System.out.println("\n" + "Error while Cancelling Jogo" + "\n");
                // }

                // System.out.println("\n" + "Let's Register the Final Score of the Second and Third Jogos" + "\n");

                // for(int i = 0; i < 2; i++) {
                //     ResponseEntity<JogoDto> eq1 = jogoController.getJogo(Long.valueOf(i+2));
                //     JogoDto oldJogo = eq1.getBody();
                //     List<String> equipas = new ArrayList<>(Arrays.asList(oldJogo.getEquipa1().getNome_equipa(), oldJogo.getEquipa2().getNome_equipa()));
                //     List<String> resultados = new ArrayList<>(Arrays.asList("2-1", "1-2"));

                //     JogoDto jDto = new JogoDto();
                //     Random r = new Random();
                //     int random = r.nextInt(2);

                //     List<EstatisticasDto> estatisticas = new ArrayList<>();
                //     for(int j = 0; j < 2; j++) {
                //         EstatisticasDto stat = new EstatisticasDto();
                //         stat.setTipoDeEstatistica(EstatisticaTipo.GOLO);
                //         List<String> jogadores = new ArrayList<>();
                //         if(random == 0){
                //             jogadores = oldJogo.getEquipa1().getNomes_jogadores();
                //         }
                //         else {
                //             jogadores = oldJogo.getEquipa2().getNomes_jogadores();
                //         }

                //         Random r1 = new Random();
                //         stat.setEquipa(equipas.get(random));
                //         stat.setJogador(jogadores.get(r1.nextInt(5)));
                //         stat.setCampeonato(null);
                //         stat.setArbitro(null);
                //         estatisticas.add(stat);
                //     }
                //     for(int k = 0; k < 2; k++) {
                //         EstatisticasDto stat = new EstatisticasDto();
                //         if(k == 0) {
                //             stat.setTipoDeEstatistica(EstatisticaTipo.GOLO);
                //             stat.setArbitro(null);
                //         }
                //         else {
                //             stat.setTipoDeEstatistica(EstatisticaTipo.CARTAOAMARELO);
                //             stat.setArbitro(oldJogo.getNomeArbitroPrincipal());
                //         }
                //         List<String> jogadores = new ArrayList<>();
                //         if(random == 0){
                //             jogadores = oldJogo.getEquipa2().getNomes_jogadores();
                //             stat.setEquipa(equipas.get(1));
                //         }
                //         else {
                //             jogadores = oldJogo.getEquipa1().getNomes_jogadores();
                //             stat.setEquipa(equipas.get(0));
                //         }
                //         Random r1 = new Random();
                //         stat.setJogador(jogadores.get(r1.nextInt(5)));
                //         stat.setCampeonato(null);
                //         estatisticas.add(stat);
                //     }

                //     jDto.setEstatisticas(estatisticas);
                //     jDto.setPlacarFinal(resultados.get(random));
                //     jDto.setNomeVencedor(equipas.get(random));
                //     ResponseEntity<JogoDto> eq2 = jogoController.updateFinalScoreJogo(Long.valueOf(i+2), jDto);
                //     if(eq2.getBody().getStatus() == Status.ACABADO) {
                //         System.out.println("\n" + "Jogo Final Score Registered Successfully" + "\n");
                //     }
                //     else {
                //         System.out.println("\n" + "Error in Registering Final Score of Jogo" + "\n");
                //     }
                //  }

                    System.out.println("\n" + "-----FINISHED POPULATING DB-----" + "\n");
                }

            //TESTES MAIS ESPECÍFICOS    
            if(testar) {
                System.out.println("\n" + "-----STARTING TESTS-----" + "\n");
                System.out.println("\n" + "Starting Tests Related to Arbitro" + "\n");

                System.out.println("\n" + "Let's Start by Creating a Valid Arbitro" + "\n");
                ArbitroDto aDto1 = new ArbitroDto();
                aDto1.setNif(250901345);
                aDto1.setNome("Joaquim Santos");
                aDto1.setPassword("password1234");
                aDto1.setCertificado(true);
                ResponseEntity<ArbitroDto> eq1 = arbitroController.createArbitro(aDto1);
                Boolean success1 = eq1.equals(ResponseEntity.ok(aDto1));
                System.out.println("\n" + "Check if the Arbitro was Successfuly Created: " + success1 + "\n");
                
                System.out.println("\n" + "Now Let's Try to Create a New Arbitro With the Same NIF" + "\n");
                ArbitroDto aDto2 = new ArbitroDto();
                aDto2.setNif(250901345);
                aDto2.setNome("Maria Lucas");
                aDto2.setPassword("pass5678");
                aDto2.setCertificado(true);
                ResponseEntity<ArbitroDto> eq2 = arbitroController.createArbitro(aDto2);
                Boolean success2 = eq2.equals(ResponseEntity.status(HttpStatus.CONFLICT).build());
                System.out.println("\n" + "Creating Not Done, Since Two Utilizadores Can't Have the Same NIF: " + success2 + "\n");

                System.out.println("\n" + "Now Let's Try to Create a New Arbitro With a Missing Attribute" + "\n");
                ArbitroDto aDto3 = new ArbitroDto();
                aDto3.setNif(252376124);
                aDto3.setNome("Maria Lucas");
                aDto3.setPassword(null);
                aDto3.setCertificado(true);
                ResponseEntity<ArbitroDto> eq3 = arbitroController.createArbitro(aDto3);
                Boolean success3 = eq3.equals(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
                System.out.println("\n" + "Creating Not Done, Since There's a Key Attribute Missing: " + success3 + "\n");

                System.out.println("\n" + "Let's Try to Login as the Successfully Created Arbitro" + "\n");
                System.out.println("\n" + arbitroController.arbitroLogin("Joaquim Santos", "password1234").getBody() + "\n");

                System.out.println("\n" + "Let's Try to Login as a Non Existent Arbitro" + "\n");
                System.out.println("\n" + arbitroController.arbitroLogin("Maria Lucas", "pass5678").getBody() + "\n");

                System.out.println("\n" + "Now Let's Try to Update the Created Arbitro" + "\n");
                aDto1.setNome("Joaquim Ferreira");
                ResponseEntity<ArbitroDto> eq4 = arbitroController.updateArbitro(Long.valueOf(1), aDto1);
                Boolean success4 = eq4.getBody().getNome().equals("Joaquim Ferreira");
                System.out.println("\n" + "The Name of the Updated Arbitro was Successfully Updated: " + success4 + "\n");

                System.out.println("\n" + "Now Let's Try to Update the Created Arbitro With a DTO With a Missing Attribute" + "\n");
                ArbitroDto aDto4 = new ArbitroDto();
                aDto4.setNome("Joaquim Ferreira");
                aDto4.setPassword("password1234");
                aDto4.setCertificado(null);
                ResponseEntity<ArbitroDto> eq5 = arbitroController.updateArbitro(Long.valueOf(1), aDto4);
                Boolean success5 = eq5.equals(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
                System.out.println("\n" + "The Update Has Failed, Since There's a Key Attribute Missing: " + success5 + "\n");

                System.out.println("\n" + "Now Let's Try to Update an Arbitro that Doesn't Exist" + "\n");
                ResponseEntity<ArbitroDto> eq6 = arbitroController.updateArbitro(Long.valueOf(2), aDto1);
                Boolean success6 = eq6.equals(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
                System.out.println("\n" + "The Update Has Failed, Since the Arbitro of the Given ID Doesn't Exist: " + success6 + "\n");

                System.out.println("\n" + "Now Let's Try to Get the Arbitro We Created From the DB" + "\n");
                ResponseEntity<ArbitroDto> eq7 = arbitroController.getArbitro(Long.valueOf(1));
                Boolean success7 = eq7.equals(ResponseEntity.ok(eq7.getBody()));
                System.out.println("\n" + "Arbitro was Successfully Retrieved From the DB: " + success7 + "\n");

                System.out.println("\n" + "Now Let's Try to Get an Arbitro that Doesn't Exist From the DB" + "\n");
                ResponseEntity<ArbitroDto> eq8 = arbitroController.getArbitro(Long.valueOf(2));
                Boolean success8 = eq8.equals(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
                System.out.println("\n" + "Get Failed, Since There is No Arbitro With the Given ID in the DB: " + success8 + "\n");

                System.out.println("\n" + "Now Let's Get All Arbitros We Created From the DB" + "\n");
                ArbitroDto aDto5 = new ArbitroDto();
                aDto5.setNif(252376124);
                aDto5.setNome("Maria Lucas");
                aDto5.setPassword("pass5678");
                aDto5.setCertificado(true);
                arbitroController.createArbitro(aDto5);
                List<ArbitroDto> aDtos = new ArrayList<>(Arrays.asList(aDto1, aDto5));
                ResponseEntity<List<ArbitroDto>> eq9 = arbitroController.getAllArbitros();
                Boolean success9 = eq9.getBody().size() == aDtos.size();
                System.out.println("\n" + "All Arbitros were Successfully Retrieved From the DB: " + success9 + "\n");

                System.out.println("\n" + "Finished Tests Related to Arbitro" + "\n");

                System.out.println("\n" + "Starting Tests Related to Jogador" + "\n");

                System.out.println("\n" + "Let's Start by Creating a Valid Jogador" + "\n");
                JogadorDto jDto1 = new JogadorDto();
                jDto1.setNif(123400967);
                jDto1.setNome("Manuel Ribeiro");
                jDto1.setPassword("3456pswd");
                jDto1.setPosicao(Posicao.ATQ);
                ResponseEntity<JogadorDto> eq10 = jogadorController.createJogador(jDto1);
                Boolean success10 = eq10.equals(ResponseEntity.ok(jDto1));
                System.out.println("\n" + "Check if the Jogador was Successfuly Created: " + success10 + "\n");

                System.out.println("\n" + "Now Let's Try to Create a New Jogador With the Same NIF" + "\n");
                JogadorDto jDto2 = new JogadorDto();
                jDto2.setNif(123400967);
                jDto2.setNome("Andreia Tomás");
                jDto2.setPassword("Bobi25");
                jDto2.setPosicao(Posicao.DEF);
                ResponseEntity<JogadorDto> eq11 = jogadorController.createJogador(jDto2);
                Boolean success11 = eq11.equals(ResponseEntity.status(HttpStatus.CONFLICT).build());
                System.out.println("\n" + "Creating Not Done, Since Two Utilizadores Can't Have the Same NIF: " + success11 + "\n");

                System.out.println("\n" + "Now Let's Try to Create a New Jogador With a Missing Attribute" + "\n");
                JogadorDto jDto3 = new JogadorDto();
                jDto3.setNif(341000955);
                jDto3.setNome("Andreia Tomás");
                jDto3.setPassword("Bobi25");
                jDto3.setPosicao(null);
                ResponseEntity<JogadorDto> eq12 = jogadorController.createJogador(jDto3);
                Boolean success12 = eq12.equals(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
                System.out.println("\n" + "Creating Not Done, Since There's a Key Attribute Missing: " + success12 + "\n");

                System.out.println("\n" + "Let's Try to Login as the Successfully Created Jogador" + "\n");
                System.out.println("\n" + jogadorController.jogadorLogin("Manuel Ribeiro", "3456pswd").getBody() + "\n");

                System.out.println("\n" + "Let's Try to Login as a Non Existent Jogador" + "\n");
                System.out.println("\n" + jogadorController.jogadorLogin("Andreia Tomás", "Bobi25").getBody() + "\n");

                System.out.println("\n" + "Now Let's Try to Update the Created Jogador" + "\n");
                jDto1.setNome("Manuel Nogueira");
                ResponseEntity<JogadorDto> eq13 = jogadorController.updateJogador(Long.valueOf(3), jDto1);
                Boolean success13 = eq13.getBody().getNome().equals("Manuel Nogueira");
                System.out.println("\n" + "The Name of the Updated Jogador was Successfully Updated: " + success13 + "\n");

                System.out.println("\n" + "Now Let's Try to Update the Created Jogador With a DTO With a Missing Attribute" + "\n");
                JogadorDto jDto4 = new JogadorDto();
                jDto4.setNif(123400967);
                jDto4.setNome("Manuel Nogueira");
                jDto4.setPassword(null);
                jDto4.setPosicao(Posicao.ATQ);
                ResponseEntity<JogadorDto> eq14 = jogadorController.updateJogador(Long.valueOf(3), jDto4);
                Boolean success14 = eq14.equals(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
                System.out.println("\n" + "The Update Has Failed, Since There's a Key Attribute Missing: " + success14 + "\n");

                System.out.println("\n" + "Now Let's Try to Update a Jogador that Doesn't Exist" + "\n");
                ResponseEntity<JogadorDto> eq15 = jogadorController.updateJogador(Long.valueOf(4), jDto1);
                Boolean success15 = eq15.equals(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
                System.out.println("\n" + "The Update Has Failed, Since the Jogador of the Given ID Doesn't Exist: " + success15 + "\n");

                System.out.println("\n" + "Now Let's Try to Get the Jogador We Created From the DB" + "\n");
                ResponseEntity<JogadorDto> eq16 = jogadorController.getJogador(Long.valueOf(3));
                Boolean success16 = eq16.equals(ResponseEntity.ok(eq16.getBody()));
                System.out.println("\n" + "Jogador was Successfully Retrieved From the DB: " + success16 + "\n");

                System.out.println("\n" + "Now Let's Try to Get a Jogador that Doesn't Exist From the DB" + "\n");
                ResponseEntity<JogadorDto> eq17 = jogadorController.getJogador(Long.valueOf(4));
                Boolean success17 = eq17.equals(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
                System.out.println("\n" + "Get Failed, Since There is No Jogador With the Given ID in the DB: " + success17 + "\n");

                System.out.println("\n" + "Now Let's Get All Jogadores We Created From the DB" + "\n");
                JogadorDto jDto5 = new JogadorDto();
                jDto5.setNif(341000955);
                jDto5.setNome("Andreia Tomás");
                jDto5.setPassword("Bobi25");
                jDto5.setPosicao(Posicao.DEF);
                jogadorController.createJogador(jDto5);
                List<JogadorDto> jDtos = new ArrayList<>(Arrays.asList(jDto1, jDto5));
                ResponseEntity<List<JogadorDto>> eq18 = jogadorController.getAllJogadores();
                Boolean success18 = eq18.getBody().size() == jDtos.size();
                System.out.println("\n" + "All Jogadores were Successfully Retrieved From the DB: " + success18 + "\n");

                System.out.println("\n" + "Finished Tests Related to Jogador" + "\n");

                System.out.println("\n" + "Starting Tests Related to Equipa" + "\n");

                System.out.println("\n" + "Let's Start by Creating a Valid Equipa" + "\n");
                EquipaDto eDto1 = new EquipaDto();
                eDto1.setNome("Real Madrid");
                ResponseEntity<EquipaDto> eq19 = equipaController.createEquipa(eDto1);
                Boolean success19 = eq19.equals(ResponseEntity.ok(eDto1));
                System.out.println("\n" + "Check if the Equipa was Successfuly Created: " + success19 + "\n");

                System.out.println("\n" + "Now Let's Try to Create a New Equipa With the Same Name" + "\n");
                EquipaDto eDto2 = new EquipaDto();
                eDto2.setNome("Real Madrid");
                ResponseEntity<EquipaDto> eq20 = equipaController.createEquipa(eDto2);
                Boolean success20 = eq20.equals(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
                System.out.println("\n" + "Creating Not Done, Since Two Equipas Can't Have the Same Name: " + success20 + "\n");

                System.out.println("\n" + "Now Let's Try to Create a New Equipa With a Missing Attribute" + "\n");
                EquipaDto eDto3 = new EquipaDto();
                eDto3.setNome(null);
                ResponseEntity<EquipaDto> eq21 = equipaController.createEquipa(eDto3);
                Boolean success21 = eq21.equals(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
                System.out.println("\n" + "Creating Not Done, Since There's a Key Attribute Missing: " + success21 + "\n");

                System.out.println("\n" + "Now Let's Try to Update the Created Equipa" + "\n");
                eDto1.setNome("Real Madrid CF");
                ResponseEntity<EquipaDto> eq22 = equipaController.updateEquipa(Long.valueOf(1), eDto1);
                Boolean success22 = eq22.getBody().getNome().equals("Real Madrid CF");
                System.out.println("\n" + "The Name of the Updated Equipa was Successfully Updated: " + success22 + "\n");

                System.out.println("\n" + "Now Let's Try to Update the Created Equipa With a DTO With a Missing Attribute" + "\n");
                EquipaDto eDto4 = new EquipaDto();
                eDto4.setNome(null);
                ResponseEntity<EquipaDto> eq23 = equipaController.updateEquipa(Long.valueOf(1), eDto4);
                Boolean success23 = eq23.equals(ResponseEntity.notFound().build());
                System.out.println("\n" + "The Update Has Failed, Since There's a Key Attribute Missing: " + success23 + "\n");

                System.out.println("\n" + "Now Let's Try to Update the an Equipa that Doesn't Exist" + "\n");
                ResponseEntity<EquipaDto> eq24 = equipaController.updateEquipa(Long.valueOf(2), eDto1);
                Boolean success24 = eq24.equals(ResponseEntity.notFound().build());
                System.out.println("\n" + "The Update Has Failed, Since the Equipa of the Given ID Doesn't Exist: " + success24 + "\n");

                System.out.println("\n" + "Now Let's Try to Get the Equipa We Created From the DB" + "\n");
                ResponseEntity<EquipaDto> eq25 = equipaController.getEquipaById(Long.valueOf(1));
                Boolean success25 = eq25.equals(ResponseEntity.ok(eq25.getBody()));
                System.out.println("\n" + "Equipa was Successfully Retrieved From the DB: " + success25 + "\n");

                System.out.println("\n" + "Now Let's Try to Get an Equipa that Doesn't Exist From the DB" + "\n");
                ResponseEntity<EquipaDto> eq26 = equipaController.getEquipaById(Long.valueOf(2));
                Boolean success26 = eq26.equals(ResponseEntity.notFound().build());
                System.out.println("\n" + "Get Failed, Since There is No Equipa With the Given ID in the DB: " + success26 + "\n");

                System.out.println("\n" + "Now Let's Get All Equipas We Created From the DB" + "\n");
                EquipaDto eDto5 = new EquipaDto();
                eDto5.setNome("FC Barcelona");
                equipaController.createEquipa(eDto5);
                List<EquipaDto> eDtos = new ArrayList<>(Arrays.asList(eDto1, eDto5));
                ResponseEntity<List<EquipaDto>> eq27 = equipaController.getAllEquipas();
                Boolean success27 = eq27.getBody().size() == eDtos.size();
                System.out.println("\n" + "All Equipas were Successfully Retrieved From the DB: " + success27 + "\n");

                System.out.println("\n" + "Finished Tests Related to Equipa" + "\n");

                System.out.println("\n" + "Starting Tests Related to Adding and Removing Jogadores From Equipa" + "\n");

                System.out.println("\n" + "Let's Try to Add an Equipa to a Jogador" + "\n");
                ResponseEntity<JogadorDto> eq28 = jogadorController.adicionarEquipaAoJogador(Long.valueOf(3), "Real Madrid CF");
                Boolean success28 = eq28.equals(ResponseEntity.ok(eq28.getBody()));
                System.out.println("\n" + "The Equipa Was Successfully Added to Jogador: " + success28 + "\n");

                System.out.println("\n" + "Now Let's Try to Add an Equipa to a Jogador that Doesn't Exist" + "\n");
                ResponseEntity<JogadorDto> eq29 = jogadorController.adicionarEquipaAoJogador(Long.valueOf(5), "Real Madrid CF");
                Boolean success29 = eq29.equals(ResponseEntity.notFound().build());
                System.out.println("\n" + "Operation Failed, Since There Was No Jogador With the Given ID: " + success29 + "\n");

                System.out.println("\n" + "Now Let's Try to Add an Equipa that Doesn't Exist to a Jogador" + "\n");
                ResponseEntity<JogadorDto> eq30 = jogadorController.adicionarEquipaAoJogador(Long.valueOf(3), "Atlético Madrid");
                Boolean success30 = eq30.equals(ResponseEntity.notFound().build());
                System.out.println("\n" + "Operation Failed, Since There Was No Equipa With the Given Name: " + success30 + "\n");

                System.out.println("\n" + "Now Let's Try to Remove an Equipa From a Jogador that Doesn't Exist" + "\n");
                ResponseEntity<JogadorDto> eq31 = jogadorController.removerEquipaDoJogador(Long.valueOf(5), "Real Madrid CF");
                Boolean success31 = eq31.equals(ResponseEntity.notFound().build());
                System.out.println("\n" + "Operation Failed, Since There Was No Jogador With the Given ID: " + success31 + "\n");

                System.out.println("\n" + "Now Let's Try to Remove an Equipa that Doesn't Exist From a Jogador" + "\n");
                ResponseEntity<JogadorDto> eq32 = jogadorController.removerEquipaDoJogador(Long.valueOf(3), "Atlético Madrid");
                Boolean success32 = eq32.equals(ResponseEntity.notFound().build());
                System.out.println("\n" + "Operation Failed, Since There Was No Equipa With the Given Name: " + success32 + "\n");

                System.out.println("\n" + "Finished Tests Related to Adding and Removing Jogadores From Equipa" + "\n");

                System.out.println("\n" + "Soft Deleting Created Entities" + "\n");
                arbitroController.deleteArbitro(Long.valueOf(1));
                arbitroController.deleteArbitro(Long.valueOf(2));
                jogadorController.deleteJogador(Long.valueOf(3));
                jogadorController.deleteJogador(Long.valueOf(4));
                equipaController.deleteEquipa(Long.valueOf(1));
                equipaController.deleteEquipa(Long.valueOf(2));
                System.out.println("\n" + "Entities Deleted Successfully" + "\n");

                System.out.println("\n" + "-----TESTS FINISHED-----" + "\n");
            }
        };
    }
}
