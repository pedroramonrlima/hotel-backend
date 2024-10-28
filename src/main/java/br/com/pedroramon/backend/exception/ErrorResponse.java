package br.com.pedroramon.backend.exception;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe que representa a resposta de erro retornada pela API.
 *
 * A classe {@code ErrorResponse} é utilizada para encapsular informações sobre um erro
 * que ocorreu durante a execução de uma operação na aplicação. Os dados encapsulados
 * incluem o status HTTP, uma descrição do erro, uma mensagem detalhada e o caminho
 * da requisição que gerou o erro.
 */
public class ErrorResponse {
    private int status;
    private String error;
    private String message;
    private String path;
    Map<String, String> errors;

    /**
     * Construtor da classe {@code ErrorResponse}.
     *
     * @param status O status HTTP associado ao erro.
     * @param error Uma descrição breve do erro.
     * @param message Uma mensagem detalhada sobre o erro.
     * @param path O caminho da requisição que causou o erro.
     */
    public ErrorResponse(int status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public ErrorResponse(int status, String error, String message, String path, Map<String, String> errors) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.errors = errors;
    }

    /**
     * Obtém o status HTTP do erro.
     *
     * @return O status HTTP.
     */
    @JsonProperty("status")
    public int getStatus() {
        return status;
    }

    /**
     * Obtém uma descrição do erro.
     *
     * @return A descrição do erro.
     */
    @JsonProperty("error")
    public String getError() {
        return error;
    }

    /**
     * Obtém uma mensagem detalhada sobre o erro.
     *
     * @return A mensagem do erro.
     */
    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    /**
     * Obtém o caminho da requisição que causou o erro.
     *
     * @return O caminho da requisição.
     */
    @JsonProperty("path")
    public String getPath() {
        return path;
    }
    
    @JsonProperty("errors")
    public Map<String, String> getErrors() {
        return errors;
    }
}
