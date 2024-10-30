package br.com.pedroramon.backend.mapper;

import br.com.pedroramon.backend.dto.StatusRoomRequest;
import br.com.pedroramon.backend.dto.StatusRoomResponse;
import br.com.pedroramon.backend.model.StatusRoom;

/**
 * Classe responsável pelo mapeamento entre as entidades e os objetos de transferência de dados (DTOs).
 *
 * A classe {@code StatusRoomMapper} fornece métodos estáticos para converter
 * entre diferentes representações de dados relacionadas a {@link StatusRoom}, incluindo
 * a conversão de entidades para DTOs e vice-versa.
 */
public class StatusRoomMapper {

    /**
     * Converte uma entidade {@link StatusRoom} em um objeto de resposta {@link StatusRoomResponse}.
     *
     * @param statusRoom A entidade {@code StatusRoom} a ser convertida.
     * @return Um objeto {@link StatusRoomResponse} contendo os dados da entidade.
     */
    public static StatusRoomResponse fromStatusRoomToResponse(StatusRoom statusRoom) {
        return new StatusRoomResponse(statusRoom.getStatusRoomId(), statusRoom.getDescription());
    }

    /**
     * Converte um objeto de solicitação {@link StatusRoomRequest} em uma entidade {@link StatusRoom}.
     *
     * @param request O objeto de solicitação {@code StatusRoomRequest} a ser convertido.
     * @return Uma nova instância de {@link StatusRoom} com os dados da solicitação.
     */
    public static StatusRoom fromRequestToStatusRoom(StatusRoomRequest request) {
        var statusRoom = new StatusRoom();
        statusRoom.setStatusRoomId(request.id());
        statusRoom.setDescription(request.description());
        return statusRoom;
    }

    /**
     * Converte um objeto de resposta {@link StatusRoomResponse} em uma entidade {@link StatusRoom}.
     *
     * @param response O objeto de resposta {@code StatusRoomResponse} a ser convertido.
     * @return Uma nova instância de {@link StatusRoom} com os dados da resposta.
     */
    public static StatusRoom fromResponseToStatusRoom(StatusRoomResponse response) {
        var statusRoom = new StatusRoom();
        statusRoom.setDescription(response.description());
        statusRoom.setStatusRoomId(response.id());
        return statusRoom;
    }
}
