package br.com.pedroramon.backend.services;

import br.com.pedroramon.backend.model.Room;
import br.com.pedroramon.backend.model.StatusRoom;
import br.com.pedroramon.backend.model.TypeRoom;
import br.com.pedroramon.backend.repository.IRoomRepository;
import br.com.pedroramon.backend.service.RoomService;
import br.com.pedroramon.backend.service.StatusRoomService;
import br.com.pedroramon.backend.service.TypeRoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class RoomServiceTest {

    @Mock
    private IRoomRepository roomRepository;

    @Mock
    private TypeRoomService typeRoomService;

    @Mock
    private StatusRoomService statusRoomService;

    @InjectMocks
    private RoomService roomService;

    private Room room;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        room = new Room(1L, 101, new BigDecimal("60.00"), 1L, 1L);
    }

    @Test
    void testFindById() {
        when(roomRepository.findById(anyLong())).thenReturn(Mono.just(room));
        when(typeRoomService.findById(anyLong())).thenReturn(Mono.just(new TypeRoom()));
        when(statusRoomService.findById(anyLong())).thenReturn(Mono.just(new StatusRoom()));

        Mono<Room> result = roomService.findById(1L);

        verify(roomRepository).findById(1L);
        assert(result.block()).equals(room);
    }

   @Test
    void testFindAllRoom(){
        
        TypeRoom typeRoom = new TypeRoom(1L, "Single");
        StatusRoom statusRoom = new StatusRoom(1L, "Available");

        when(roomRepository.findAll()).thenReturn(Flux.just(room));
        when(typeRoomService.findById(room.getTypeRoomId())).thenReturn(Mono.just(typeRoom));
        when(statusRoomService.findById(room.getStatusRoomId())).thenReturn(Mono.just(statusRoom));

        StepVerifier.create(roomService.findAll())
                .expectNextMatches(r -> r.getTypeRoom().equals(typeRoom) && r.getStatusRoom().equals(statusRoom))
                .verifyComplete();

        verify(roomRepository).findAll();
        verify(typeRoomService).findById(room.getTypeRoomId());
        verify(statusRoomService).findById(room.getStatusRoomId());
    }

    @Test
    void testFindByRoomNumber() {
        when(roomRepository.findByRoomNumber(anyInt())).thenReturn(Mono.just(room));
        when(typeRoomService.findById(anyLong())).thenReturn(Mono.just(new TypeRoom()));
        when(statusRoomService.findById(anyLong())).thenReturn(Mono.just(new StatusRoom()));

        Mono<Room> result = roomService.findByRoomNumber(101);

        verify(roomRepository).findByRoomNumber(101);
        assert(result.block()).equals(room);
    }

    @Test
    void testSave() {
        
        Room newRoom = new Room(null, 102, new BigDecimal("60.00"), 1L, 1L);
        TypeRoom typeRoom = new TypeRoom(1L, "Single");
        StatusRoom statusRoom = new StatusRoom(1L, "Available");

        when(roomRepository.findByRoomNumber(newRoom.getRoomNumber())).thenReturn(Mono.empty());
        when(roomRepository.save(any(Room.class))).thenReturn(Mono.just(newRoom));
        when(typeRoomService.findById(newRoom.getTypeRoomId())).thenReturn(Mono.just(typeRoom));
        when(statusRoomService.findById(newRoom.getStatusRoomId())).thenReturn(Mono.just(statusRoom));

        StepVerifier.create(roomService.save(newRoom))
                .expectNextMatches(savedRoom -> 
                    savedRoom.getRoomNumber() == newRoom.getRoomNumber() &&
                    savedRoom.getTypeRoom().equals(typeRoom) && 
                    savedRoom.getStatusRoom().equals(statusRoom))
                .verifyComplete();

        verify(roomRepository).findByRoomNumber(newRoom.getRoomNumber());
        verify(roomRepository).save(any(Room.class));
        verify(typeRoomService).findById(newRoom.getTypeRoomId());
        verify(statusRoomService).findById(newRoom.getStatusRoomId());
    }

    @Test
    void testSaveRoomWithExistingRoomNumber() {
        
        Room newRoom = new Room(1L, 101, new BigDecimal("60.00"), 1L, 1L);
        when(roomRepository.findByRoomNumber(room.getRoomNumber())).thenReturn(Mono.just(room));
        when(typeRoomService.findById(anyLong())).thenReturn(Mono.empty());
        when(statusRoomService.findById(anyLong())).thenReturn(Mono.empty());
        
        StepVerifier.create(roomService.save(room))
                .expectErrorMatches(throwable -> throwable instanceof IllegalArgumentException &&
                        throwable.getMessage().equals("Já existe um quarto com o número informado!"))
                .verify();

        verify(roomRepository).findByRoomNumber(newRoom.getRoomNumber());
        verify(typeRoomService, never()).findById(anyLong());
        verify(statusRoomService, never()).findById(anyLong());
    }
}
