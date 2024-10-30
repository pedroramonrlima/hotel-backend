package br.com.pedroramon.backend.service.interfaces;

import br.com.pedroramon.backend.model.Room;
import reactor.core.publisher.Mono;

public interface IRoomService extends IService<Room> {
    Mono<Room> findByRoomNumber(Integer roomNumber); 
}
