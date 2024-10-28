package br.com.pedroramon.backend.model;

import java.time.LocalDateTime;

/**
 * Interface que define a estrutura básica de uma entidade.
 *
 * A interface {@code IEntity} estabelece um contrato para as entidades do
 * sistema, definindo métodos para acessar e modificar o ID da entidade e
 * suas datas de criação. Qualquer classe que implemente esta interface deve
 * fornecer implementações para esses métodos, garantindo consistência
 * no tratamento de entidades.
 */
public interface IEntity {
    
    /**
     * Obtém o ID da entidade.
     *
     * @return O ID da entidade, que deve ser um valor único.
     */
    Long getId();

    /**
     * Define o ID da entidade.
     *
     * @param id O novo ID da entidade.
     */
    void setId(Long id);

    /**
     * Obtém a data de criação da entidade.
     *
     * @return A data e hora em que a entidade foi criada.
     */
    LocalDateTime getCreatedAt();

    /**
     * Define a data de criação da entidade.
     *
     * @param createdAt A nova data de criação da entidade.
     */
    void setCreatedAt(LocalDateTime createdAt);
}
