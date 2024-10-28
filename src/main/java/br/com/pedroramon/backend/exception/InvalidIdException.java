package br.com.pedroramon.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção personalizada que indica que um ID inválido foi fornecido.
 *
 * A classe {@code InvalidIdException} é uma exceção de tempo de execução que é lançada
 * quando um ID fornecido em uma operação não é válido. Essa exceção é associada ao status
 * HTTP 400 (Bad Request) e pode ser usada para sinalizar erros de entrada do usuário.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidIdException extends RuntimeException {

    /**
     * Construtor da classe {@code InvalidIdException}.
     *
     * @param message A mensagem detalhada que descreve a razão pela qual a exceção foi lançada.
     */
    public InvalidIdException(String message) {
        super(message);
    }
}
