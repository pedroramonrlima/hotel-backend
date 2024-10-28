package br.com.pedroramon.backend.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.pedroramon.backend.exception.ErrorResponse;
import br.com.pedroramon.backend.exception.InvalidIdException;
import br.com.pedroramon.backend.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe global para manipulação de exceções em toda a aplicação.
 *
 * A classe {@code GlobalExceptionHandler} utiliza a anotação {@code @ControllerAdvice}
 * para interceptar exceções lançadas em qualquer controlador da aplicação.
 * Ela fornece métodos para tratar exceções específicas e gerar respostas
 * adequadas para os clientes, encapsulando as informações de erro em um objeto
 * {@link ErrorResponse}.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Manipula exceções do tipo {@link ResourceNotFoundException}.
     *
     * @param ex A exceção lançada.
     * @param request O objeto {@code HttpServletRequest} que contém informações
     *                sobre a requisição que causou a exceção.
     * @return Uma {@link ResponseEntity} contendo um objeto {@link ErrorResponse}
     *         com detalhes do erro e o status HTTP 404 (Not Found).
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException ex, jakarta.servlet.http.HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Manipula exceções do tipo {@link InvalidIdException}.
     *
     * @param ex A exceção lançada.
     * @param request O objeto {@code HttpServletRequest} que contém informações
     *                sobre a requisição que causou a exceção.
     * @return Uma {@link ResponseEntity} contendo um objeto {@link ErrorResponse}
     *         com detalhes do erro e o status HTTP 400 (Bad Request).
     */
    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<ErrorResponse> handleInvalidIdException(
        InvalidIdException ex, jakarta.servlet.http.HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Manipula exceções de validação de argumento do tipo {@link MethodArgumentNotValidException}.
     *
     * @param ex A exceção lançada.
     * @param request O objeto {@code HttpServletRequest} que contém informações
     *                sobre a requisição que causou a exceção.
     * @return Uma {@link ResponseEntity} contendo um objeto {@link ErrorResponse}
     *         com detalhes do erro e o status HTTP 400 (Bad Request), incluindo
     *         um mapa de campos inválidos e suas mensagens de erro.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
        
        MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Erro de validação",
                request.getRequestURI(),
                errors
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Manipula exceções do tipo {@link IllegalArgumentException}.
     *
     * @param ex A exceção lançada.
     * @param request O objeto {@code HttpServletRequest} que contém informações
     *                sobre a requisição que causou a exceção.
     * @return Uma {@link ResponseEntity} contendo um objeto {@link ErrorResponse}
     *         com detalhes do erro e o status HTTP 400 (Bad Request).
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, jakarta.servlet.http.HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}