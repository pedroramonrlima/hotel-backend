package br.com.pedroramon.backend.mapper;

import br.com.pedroramon.backend.dto.TypeRomRequest;
import br.com.pedroramon.backend.dto.TypeRomResponse;
import br.com.pedroramon.backend.model.TypeRoom;

/**
 * Classe responsável pelo mapeamento entre as entidades e os objetos de transferência de dados (DTOs).
 *
 * A classe {@code TypeRoomMapper} fornece métodos estáticos para converter
 * entre diferentes representações de dados relacionadas a {@link TypeRoom}, incluindo
 * a conversão de entidades para DTOs e vice-versa.
 */
public class TypeRoomMapper {

    /**
     * Converte uma entidade {@link TypeRoom} em um objeto de resposta {@link TypeRomResponse}.
     *
     * @param typeRoom A entidade {@code TypeRoom} a ser convertida.
     * @return Um objeto {@link TypeRomResponse} contendo os dados da entidade.
     */
    public static TypeRomResponse fromTypeRoomToResponse(TypeRoom typeRoom) {
        return new TypeRomResponse(typeRoom.getTypeRoomId(), typeRoom.getName());
    }

    /**
     * Converte um objeto de solicitação {@link TypeRomRequest} em uma entidade {@link TypeRoom}.
     *
     * @param request O objeto de solicitação {@code TypeRomRequest} a ser convertido.
     * @return Uma nova instância de {@link TypeRoom} com os dados da solicitação.
     */
    public static TypeRoom fromRequestToTypeRoom(TypeRomRequest request) {
        var typeRoom = new TypeRoom();
        typeRoom.setName(request.name());
        return typeRoom;
    }

    /**
     * Converte um objeto de resposta {@link TypeRomResponse} em uma entidade {@link TypeRoom}.
     *
     * @param response O objeto de resposta {@code TypeRomResponse} a ser convertido.
     * @return Uma nova instância de {@link TypeRoom} com os dados da resposta.
     */
    public static TypeRoom fromResponseToTypeRoom(TypeRomResponse response) {
        var typeRoom = new TypeRoom();
        typeRoom.setName(response.name());
        typeRoom.setTypeRoomId(response.id());
        return typeRoom;
    }
}
