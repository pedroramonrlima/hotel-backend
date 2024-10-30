package br.com.pedroramon.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pedroramon.backend.dto.RoomDTO;
import br.com.pedroramon.backend.dto.interfaces.OnUpdate;
import br.com.pedroramon.backend.mapper.RoomMapper;
import br.com.pedroramon.backend.service.RoomService;
import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public Flux<RoomDTO> getAll(){
        return this.roomService.findAll().map(RoomMapper::fromRoomToResponse);
    }

    @GetMapping("/{id}")
    public Mono<RoomDTO> findById(@PathVariable Long id) {
        return roomService.findById(id)
                   .map(RoomMapper::fromRoomToResponse);
    }

    @PostMapping
    public ResponseEntity<Mono<RoomDTO>> create(@Valid @RequestBody RoomDTO request){
        var romResponse = roomService.save(RoomMapper.fromRequestToRoom(request))
                             .map(RoomMapper::fromRoomToResponse);

        return ResponseEntity.status(HttpStatus.CREATED).body(romResponse);
    }

    @PutMapping
    public ResponseEntity<Mono<RoomDTO>> update(@Validated(OnUpdate.class) @RequestBody RoomDTO request){
        var romResponse = roomService.update(RoomMapper.fromRequestToRoom(request))
                             .map(RoomMapper::fromRoomToResponse);
        return ResponseEntity.status(HttpStatus.OK).body(romResponse);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return roomService.delete(id);
    }

}
