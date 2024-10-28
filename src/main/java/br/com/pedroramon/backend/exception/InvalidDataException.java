package br.com.pedroramon.backend.exception;

/**
 * Exceção personalizada que indica que os dados fornecidos são inválidos.
 *
 * Esta classe estende {@link RuntimeException} e é lançada quando há
 * um erro relacionado a dados inválidos durante a execução de um método.
 * 
 * Essa exceção pode ser utilizada para sinalizar problemas de validação
 * de dados ou quando os dados não atendem aos critérios necessários
 * para uma operação específica.
 */
public class InvalidDataException extends RuntimeException {

    /**
     * Construtor da classe InvalidDataException.
     *
     * Este construtor inicializa a exceção com uma mensagem específica,
     * que descreve o erro ocorrido.
     *
     * @param message A mensagem que será associada a esta exceção,
     *                descrevendo o motivo da invalidez dos dados.
     */
    public InvalidDataException(String message) {
        super(message);
    }
}
