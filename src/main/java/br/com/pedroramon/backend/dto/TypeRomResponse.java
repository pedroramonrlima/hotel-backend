package br.com.pedroramon.backend.dto;

/**
 * Representa a resposta para informações sobre um tipo de quarto.
 *
 * O {@code TypeRomResponse} é um {@code record} que encapsula os dados 
 * de um tipo de quarto, incluindo seu ID e nome. Essa classe é utilizada 
 * para transferir informações sobre tipos de quartos entre a aplicação 
 * e os clientes (por exemplo, através de APIs).
 *
 * @param id   O identificador único do tipo de quarto.
 * @param name O nome do tipo de quarto.
 */
public record TypeRomResponse(Long id, String name) {
}
