package br.com.pedroramon.backend.dto;

import java.math.BigDecimal;
import br.com.pedroramon.backend.dto.interfaces.OnUpdate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.groups.Default;

/**
 * Data Transfer Object (DTO) para representar as informações de um quarto.
 * Este registro contém os dados necessários para a criação ou atualização de
 * um quarto na aplicação, incluindo validações para garantir a integridade
 * dos dados.
 * 
 * <p>O {@code RoomDTO} utiliza validações JSR 380 para assegurar que os
 * dados recebidos estejam em conformidade com as regras de negócio durante
 * a operação de atualização.</p>
 * 
 * @param id Identificador único do quarto. Este campo deve ser nulo durante
 *           a criação de um novo quarto e não deve ser nulo durante a
 *           atualização.
 * @param roomNumber Número do quarto. Deve ser positivo ou zero e não deve
 *                   ser nulo.
 * @param dailyRate Taxa diária do quarto. Deve ser positiva ou zero e não
 *                  deve ser nula.
 * @param typeRoomId Identificador do tipo de quarto. Deve ser positivo ou
 *                    zero e não deve ser nulo.
 * @param statusRoomId Identificador do status do quarto. Deve ser positivo ou
 *                      zero e não deve ser nulo.
 * @param typeRoom Objeto {@link TypeRomResponse} representando o tipo de
 *                 quarto associado.
 * @param statusRoom Objeto {@link StatusRoomResponse} representando o
 *                   status do quarto associado.
 * 
 * @see TypeRomResponse
 * @see StatusRoomResponse
 */
public record RoomDTO(
    @Null(groups = Default.class)
    @NotNull(groups = OnUpdate.class)
    @PositiveOrZero(groups = OnUpdate.class)
    Long id, 
    
    @PositiveOrZero(groups = {Default.class, OnUpdate.class})
    @NotNull(groups = {Default.class, OnUpdate.class})
    Integer roomNumber,

    @PositiveOrZero(groups = {Default.class, OnUpdate.class})
    @NotNull(groups = {Default.class, OnUpdate.class})
    BigDecimal dailyRate,

    @PositiveOrZero(groups = {Default.class, OnUpdate.class})
    @NotNull(groups = {Default.class, OnUpdate.class})
    Long typeRoomId,

    @PositiveOrZero(groups = {Default.class, OnUpdate.class})
    @NotNull(groups = {Default.class, OnUpdate.class})
    Long statusRoomId,
    
    TypeRomResponse typeRoom,
    StatusRoomResponse statusRoom
) {}
