package br.com.pedroramon.backend.model;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A classe {@code Room} representa um quarto no sistema de gestão de um hotel.
 * Esta classe mapeia a tabela {@code rooms} no banco de dados e armazena 
 * informações detalhadas de cada quarto, como número, taxa diária e o status.
 */
@Table("rooms")
public class Room implements IEntity {

    @Id
    @Column("room_id")
    private Long roomId;
    private Integer roomNumber;
    private BigDecimal dailyRate;
    private Long typeRoomId;
    private Long statusRoomId;
    
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Transient
    private TypeRoom typeRoom;

    @Transient
    private StatusRoom statusRoom;

    /**
     * Obtém o ID do quarto.
     *
     * @return o ID do quarto
     */
    @Override
    public Long getId() {
        return this.roomId;
    }

    /**
     * Define o ID do quarto.
     *
     * @param id o novo ID do quarto
     */
    @Override
    public void setId(Long id) {
        this.roomId = id;
    }

    /**
     * Obtém o número do quarto.
     *
     * @return o número do quarto
     */
    public Integer getRoomNumber() {
        return roomNumber;
    }

    /**
     * Define o número do quarto.
     *
     * @param roomNumber o novo número do quarto
     */
    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * Obtém a taxa diária do quarto.
     *
     * @return a taxa diária
     */
    public BigDecimal getDailyRate() {
        return dailyRate;
    }

    /**
     * Define a taxa diária do quarto.
     *
     * @param dailyRate a nova taxa diária
     */
    public void setDailyRate(BigDecimal dailyRate) {
        this.dailyRate = dailyRate;
    }

    /**
     * Obtém o ID do tipo de quarto.
     *
     * @return o ID do tipo de quarto
     */
    public Long getTypeRoomId() {
        return typeRoomId;
    }

    /**
     * Define o ID do tipo de quarto.
     *
     * @param typeRoomId o novo ID do tipo de quarto
     */
    public void setTypeRoomId(Long typeRoomId) {
        this.typeRoomId = typeRoomId;
    }

    /**
     * Obtém o ID do status do quarto.
     *
     * @return o ID do status do quarto
     */
    public Long getStatusRoomId() {
        return statusRoomId;
    }

    /**
     * Define o ID do status do quarto.
     *
     * @param statusRoomId o novo ID do status do quarto
     */
    public void setStatusRoomId(Long statusRoomId) {
        this.statusRoomId = statusRoomId;
    }

    /**
     * Obtém o tipo do quarto.
     *
     * @return o tipo do quarto
     */
    public TypeRoom getTypeRoom() {
        return typeRoom;
    }

    /**
     * Define o tipo do quarto.
     *
     * @param typeRoom o novo tipo do quarto
     */
    public void setTypeRoom(TypeRoom typeRoom) {
        this.typeRoom = typeRoom;
    }

    /**
     * Obtém o status do quarto.
     *
     * @return o status do quarto
     */
    public StatusRoom getStatusRoom() {
        return statusRoom;
    }

    /**
     * Define o status do quarto.
     *
     * @param statusRoom o novo status do quarto
     */
    public void setStatusRoom(StatusRoom statusRoom) {
        this.statusRoom = statusRoom;
    }

    /**
     * Obtém a data de criação do quarto.
     *
     * @return a data de criação
     */
    @Override
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    /**
     * Define a data de criação do quarto.
     *
     * @param createdAt a nova data de criação
     */
    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Obtém a data da última modificação do quarto.
     *
     * @return a data da última modificação
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Define a data da última modificação do quarto.
     *
     * @param updatedAt a nova data da última modificação
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
