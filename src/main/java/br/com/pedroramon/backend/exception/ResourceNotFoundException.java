package br.com.pedroramon.backend.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * Exceção personalizada que indica que um recurso não foi encontrado.
 *
 * A classe {@code ResourceNotFoundException} é uma exceção de tempo de execução que é lançada
 * quando um recurso solicitado não pode ser encontrado. Essa exceção é associada ao status
 * HTTP 404 (Not Found) e pode ser usada para sinalizar que um recurso não está disponível.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Construtor da classe {@code ResourceNotFoundException}.
     *
     * @param message A mensagem detalhada que descreve a razão pela qual a exceção foi lançada.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
