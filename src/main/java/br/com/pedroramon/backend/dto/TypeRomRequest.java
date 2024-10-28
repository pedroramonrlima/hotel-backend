package br.com.pedroramon.backend.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Representa uma solicitação para criar ou atualizar um tipo de quarto.
 *
 * O {@code TypeRomRequest} é um {@code record} que encapsula os dados necessários
 * para criar ou atualizar um tipo de quarto, incluindo seu ID e nome. Esta classe
 * é utilizada para transferir informações entre a aplicação e os clientes (por exemplo,
 * através de APIs).
 *
 * @param id   O identificador único do tipo de quarto.
 * @param name O nome do tipo de quarto.
 */
public record TypeRomRequest(Long id, @NotBlank String name) {

}
