package br.com.pedroramon.backend.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A classe {@code StatusRoom} representa o status de um quarto no sistema.
 * Ela implementa a interface {@code IEntity} e mapeia a tabela {@code status_room}
 * no banco de dados.
 */
@Table("status_room")
public class StatusRoom implements IEntity {

    @Id
    @Column("status_rom_id")
    private Long statusRomId;

    private String description;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    /**
     * Obtém a data de criação do status do quarto.
     * 
     * @return a data de criação
     */
    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Obtém a data da última modificação do status do quarto.
     * 
     * @return a data da última modificação
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Obtém o ID do status do quarto.
     * 
     * @return o ID do status do quarto
     */
    @JsonIgnore
    public Long getStatusRoomId() {
        return statusRomId;
    }

    /**
     * Define o ID do status do quarto.
     * 
     * @param statusRoomId o novo ID do status do quarto
     */
    public void setStatusRoomId(Long statusRoomId) {
        this.statusRomId = statusRoomId;
    }

    /**
     * Obtém a descrição do status do quarto.
     * 
     * @return a descrição do status do quarto
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Define a descrição do status do quarto.
     * 
     * @param description a nova descrição do status do quarto
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Obtém o ID da entidade.
     * 
     * @return o ID da entidade
     */
    @Override
    public Long getId() {
        return this.statusRomId;
    }

    /**
     * Define o ID da entidade.
     * 
     * @param id o novo ID da entidade
     */
    @Override
    public void setId(Long id) {
        this.statusRomId = id;
    }

    /**
     * Define a data de criação da entidade.
     * 
     * @param createdAt a nova data de criação
     */
    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
