package br.com.pedroramon.backend.mapper;

import org.springframework.stereotype.Component;

import br.com.pedroramon.backend.dto.*;
import br.com.pedroramon.backend.model.*;

@Component
public class MapperFactory {

    private final EntityDtoMapper<TypeRoom, TypeRoomDTO> typeRoomMapper;
    private final EntityDtoMapper<StatusRoom, StatusRoomDTO> statusRoomMapper;
    private final EntityDtoMapper<Room, RoomDTO> roomMapper;

    public MapperFactory() {
        this.typeRoomMapper = createTypeRoomMapper();
        this.statusRoomMapper = createStatusRoomMapper();
        this.roomMapper = createRoomMapper();
    }

    private EntityDtoMapper<TypeRoom, TypeRoomDTO> createTypeRoomMapper() {
        return new EntityDtoMapper<>(
            typeRoom -> new TypeRoomDTO(typeRoom.getTypeRoomId(), typeRoom.getName()),
            dto -> new TypeRoom(dto.id(), dto.name())
        );
    }

    private EntityDtoMapper<StatusRoom, StatusRoomDTO> createStatusRoomMapper() {
        return new EntityDtoMapper<>(
            statusRoom -> new StatusRoomDTO(statusRoom.getStatusRoomId(), statusRoom.getDescription()),
            dto -> new StatusRoom(dto.id(), dto.description())
        );
    }

    private EntityDtoMapper<Room, RoomDTO> createRoomMapper() {
        return new EntityDtoMapper<>(
            room -> new RoomDTO(
                room.getId(),
                room.getRoomNumber(),
                room.getDailyRate(),
                room.getTypeRoomId(),
                room.getStatusRoomId(),
                typeRoomMapper.toDto(room.getTypeRoom()),
                statusRoomMapper.toDto(room.getStatusRoom())
            ),
            dto -> new Room(dto.id(), dto.roomNumber(), dto.dailyRate(), dto.typeRoomId(), dto.statusRoomId())
        );
    }

    public EntityDtoMapper<TypeRoom, TypeRoomDTO> getTypeRoomMapper() {
        return typeRoomMapper;
    }

    public EntityDtoMapper<StatusRoom, StatusRoomDTO> getStatusRoomMapper() {
        return statusRoomMapper;
    }

    public EntityDtoMapper<Room, RoomDTO> getRoomMapper() {
        return roomMapper;
    }
}