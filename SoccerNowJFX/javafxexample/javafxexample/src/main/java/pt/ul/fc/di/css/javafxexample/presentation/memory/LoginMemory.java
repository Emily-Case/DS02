package pt.ul.fc.di.css.javafxexample.presentation.memory;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import pt.ul.fc.di.css.javafxexample.presentation.model.Arbitro;
import pt.ul.fc.di.css.javafxexample.presentation.model.Jogador;

/**
 * A classe LoginMemory é responsável por armazenar em memória as informações de login
 * de um árbitro ou jogador. Implementa o padrão Singleton para garantir uma única
 * instância global da classe durante a execução do programa.
 */
public class LoginMemory {

	// Instância única da classe (Singleton)
	private static LoginMemory instance;

	// Propriedades que armazenam o árbitro e jogador atualmente logados
    private final ObjectProperty<Arbitro> currentArbitro = new SimpleObjectProperty<>(null);
    private final ObjectProperty<Jogador> currentJogador = new SimpleObjectProperty<>(null);

	/**
     * Construtor privado para garantir que a classe só pode ser instanciada
     * dentro dela mesma (padrão Singleton).
     */
	private LoginMemory() {}

    /**
     * Método para obter a instância única da classe LoginMemory.
     * Se a instância ainda não existir, ela será criada.
     * 
     * @return A instância única de LoginMemory.
     */
    public static LoginMemory getInstance() {
        if (instance == null) {
            instance = new LoginMemory();
        }
        return instance;
    }

    // === Métodos para manipular o jogador atualmente logado ===

    /**
     * Retorna a propriedade que contém o jogador atualmente logado.
     * 
     * @return A propriedade de jogador atual.
     */
    public ObjectProperty<Jogador> currentJogadorProperty() {
		return currentJogador;
	}

    /**
     * Obtém o jogador atualmente logado.
     * 
     * @return O jogador atual ou null se nenhum jogador estiver logado.
     */
	public final Jogador getCurrentJogador() {
		return currentJogadorProperty().get();
	}

    /**
     * Define o jogador atualmente logado.
     * 
     * @param jogador O jogador que será definido como logado.
     */
	public final void setCurrentJogador(Jogador jogador) {
		currentJogadorProperty().set(jogador);
	}

    // === Métodos para manipular o árbitro atualmente logado ===

    /**
     * Retorna a propriedade que contém o árbitro atualmente logado.
     * 
     * @return A propriedade de árbitro atual.
     */
    public ObjectProperty<Arbitro> currentArbitroProperty() {
		return currentArbitro;
	}

    /**
     * Obtém o árbitro atualmente logado.
     * 
     * @return O árbitro atual ou null se nenhum árbitro estiver logado.
     */
	public final Arbitro getCurrentArbitro() {
		return currentArbitroProperty().get();
	}

    /**
     * Define o árbitro atualmente logado.
     * 
     * @param arbitro O árbitro que será definido como logado.
     */
	public final void setCurrentArbitro(Arbitro arbitro) {
		currentArbitroProperty().set(arbitro);
	}

    // === Métodos gerais ===

    /**
     * Reseta as informações de login, removendo o árbitro e jogador atualmente logados.
     */
	public final void resetLogin() {
		currentArbitro.set(null);
		currentJogador.set(null);
	}
}
