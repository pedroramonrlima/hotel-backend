package br.com.pedroramon.backend.controller;

import br.com.pedroramon.backend.dto.TypeRoomDTO;
import br.com.pedroramon.backend.exception.ResourceNotFoundException;
import br.com.pedroramon.backend.mapper.EntityDtoMapper;
import br.com.pedroramon.backend.mapper.MapperFactory;
import br.com.pedroramon.backend.model.TypeRoom;
import br.com.pedroramon.backend.service.TypeRoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * Testes para o controlador de tipos de quartos {@link TypeRoomController}.
 */
@WebFluxTest(TypeRoomController.class)
class TypeRoomControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private TypeRoomService service;

    @MockBean
    private MapperFactory mapperFactory;

    @MockBean
    private EntityDtoMapper<TypeRoom, TypeRoomDTO> typeRoomMapper;

    @BeforeEach
    void setup() {
        Mockito.when(mapperFactory.getTypeRoomMapper()).thenReturn(typeRoomMapper);
    }

    /**
     * Testa o endpoint GET para recuperar todos os tipos de quartos.
     */
    @Test
    void getAllTypeRooms_ShouldReturnListOfRooms() {
        TypeRoomDTO response1 = new TypeRoomDTO(1L, "Suíte");
        TypeRoomDTO response2 = new TypeRoomDTO(2L, "Standard");

        TypeRoom room1 = new TypeRoom(1L, "Suíte");
        TypeRoom room2 = new TypeRoom(2L, "Standard");

        Mockito.when(service.findAll()).thenReturn(Flux.just(room1, room2));
        Mockito.when(typeRoomMapper.toDto(room1)).thenReturn(response1);
        Mockito.when(typeRoomMapper.toDto(room2)).thenReturn(response2);

        webTestClient.get().uri("/api/type-rooms")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TypeRoomDTO.class)
                .hasSize(2)
                .contains(response1, response2);
    }

    /**
     * Testa o endpoint POST para criar um novo tipo de quarto.
     */
    @Test
    void createTypeRoom_ShouldReturnCreatedRoom() {
        TypeRoomDTO request = new TypeRoomDTO(null, "Deluxe");
        TypeRoomDTO response = new TypeRoomDTO(3L, "Deluxe");

        TypeRoom room = new TypeRoom(3L, "Deluxe");

        Mockito.when(typeRoomMapper.toEntity(request)).thenReturn(room);
        Mockito.when(service.save(room)).thenReturn(Mono.just(room));
        Mockito.when(typeRoomMapper.toDto(room)).thenReturn(response);

        webTestClient.post().uri("/api/type-rooms")
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(TypeRoomDTO.class)
                .isEqualTo(response);
    }

    /**
     * Testa o endpoint POST para criar um novo tipo de quarto com dados inválidos.
     */
    @Test
    void createTypeRoom_ShouldReturnBadRequest_WhenInvalidData() {
        TypeRoomDTO request = new TypeRoomDTO(null, "");

        webTestClient.post().uri("/api/type-rooms")
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest();
    }

    /**
     * Testa o endpoint PUT para atualizar um tipo de quarto.
     */
    @Test
    void updateTypeRoom_ShouldReturnUpdatedRoom() {
        Long roomId = 1L;
        TypeRoomDTO request = new TypeRoomDTO(roomId, "Suite Renovada");
        TypeRoomDTO response = new TypeRoomDTO(roomId, "Suite Renovada");

        TypeRoom updatedRoom = new TypeRoom(roomId, "Suite Renovada");

        Mockito.when(typeRoomMapper.toEntity(request)).thenReturn(updatedRoom);
        Mockito.when(service.update(updatedRoom)).thenReturn(Mono.just(updatedRoom));
        Mockito.when(typeRoomMapper.toDto(updatedRoom)).thenReturn(response);

        webTestClient.put().uri("/api/type-rooms")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TypeRoomDTO.class)
                .isEqualTo(response);
    }

    /**
     * Testa o endpoint PUT para atualizar um tipo de quarto que não existe.
     */
    @Test
    void updateTypeRoom_ShouldReturnNotFound_WhenRoomDoesNotExist() {
        Long roomId = 1L;
        TypeRoomDTO request = new TypeRoomDTO(roomId, "Suite Renovada");

        Mockito.when(typeRoomMapper.toEntity(request)).thenReturn(new TypeRoom(roomId, "Suite Renovada"));
        Mockito.when(service.update(Mockito.any(TypeRoom.class)))
               .thenThrow(new ResourceNotFoundException("Object not found with id: " + roomId));

        webTestClient.put().uri("/api/type-rooms")
                .bodyValue(request)
                .exchange()
                .expectStatus().isNotFound();
    }
}
