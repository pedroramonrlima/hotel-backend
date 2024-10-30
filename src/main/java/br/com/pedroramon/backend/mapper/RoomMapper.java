package br.com.pedroramon.backend.mapper;

import br.com.pedroramon.backend.dto.RoomDTO;
import br.com.pedroramon.backend.dto.StatusRoomRequest;
import br.com.pedroramon.backend.dto.StatusRoomResponse;
import br.com.pedroramon.backend.model.Room;
import br.com.pedroramon.backend.model.StatusRoom;

/**
 * Classe responsável pelo mapeamento entre as entidades e os objetos de transferência de dados (DTOs).
 *
 * A classe {@code StatusRoomMapper} fornece métodos estáticos para converter
 * entre diferentes representações de dados relacionadas a {@link StatusRoom}, incluindo
 * a conversão de entidades para DTOs e vice-versa.
 */
public class RoomMapper {

    /**
     * Converte uma entidade {@link StatusRoom} em um objeto de resposta {@link StatusRoomResponse}.
     *
     * @param statusRoom A entidade {@code StatusRoom} a ser convertida.
     * @return Um objeto {@link StatusRoomResponse} contendo os dados da entidade.
     */
    public static RoomDTO fromRoomToResponse(Room room) {
        return new RoomDTO(room.getId(), room.getRoomNumber(), room.getDailyRate(),
                room.getTypeRoomId(), room.getStatusRoomId(),
                TypeRoomMapper.fromTypeRoomToResponse(room.getTypeRoom()),
                StatusRoomMapper.fromStatusRoomToResponse(room.getStatusRoom()));
    }

    /**
     * Converte um objeto de solicitação {@link StatusRoomRequest} em uma entidade {@link StatusRoom}.
     *
     * @param request O objeto de solicitação {@code StatusRoomRequest} a ser convertido.
     * @return Uma nova instância de {@link StatusRoom} com os dados da solicitação.
     */
    public static Room fromRequestToRoom(RoomDTO request) {
        var room = new Room();
        room.setId(request.id());
        room.setRoomNumber(request.roomNumber());
        room.setDailyRate(request.dailyRate());
        room.setTypeRoomId(request.typeRoomId());
        room.setStatusRoomId(request.statusRoomId());
        return room;
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
