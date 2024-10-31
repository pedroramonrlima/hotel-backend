package br.com.pedroramon.backend.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Representa um tipo de quarto no sistema.
 *
 * A classe {@code TypeRoom} implementa a interface {@link IEntity} e é usada
 * para armazenar informações sobre diferentes tipos de quartos disponíveis.
 * Os dados são mapeados para a tabela "type_room" no banco de dados.
 */
@Table("type_room")
public class TypeRoom implements IEntity {

    @Id
    @Column("type_rom_id")
    private Long typeRoomId;

    private String name;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public TypeRoom(long id, String name) {
        this.typeRoomId = id;
        this.name = name;
    }

    public TypeRoom(){
        
    }

    /**
     * Obtém a data de criação da entidade.
     *
     * @return A data e hora em que a entidade foi criada.
     */
    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Obtém a data da última atualização da entidade.
     *
     * @return A data e hora da última atualização da entidade.
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Obtém o ID do tipo de quarto.
     *
     * @return O ID do tipo de quarto.
     */
    @JsonIgnore
    public Long getTypeRoomId() {
        return typeRoomId;
    }

    /**
     * Define o ID do tipo de quarto.
     *
     * @param typeRoomId O novo ID do tipo de quarto.
     */
    public void setTypeRoomId(Long typeRoomId) {
        this.typeRoomId = typeRoomId;
    }

    /**
     * Obtém o nome do tipo de quarto.
     *
     * @return O nome do tipo de quarto.
     */
    public String getName() {
        return name;
    }

    /**
     * Define o nome do tipo de quarto.
     *
     * @param name O novo nome do tipo de quarto.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtém o ID da entidade, que é o ID do tipo de quarto.
     *
     * @return O ID da entidade.
     */
    @Override
    public Long getId() {
        return this.typeRoomId;
    }

    /**
     * Define o ID da entidade, que é o ID do tipo de quarto.
     *
     * @param id O novo ID da entidade.
     */
    @Override
    public void setId(Long id) {
        this.typeRoomId = id;
    }

    /**
     * Define a data de criação da entidade.
     *
     * @param createdAt A nova data de criação da entidade.
     */
    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
