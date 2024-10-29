package br.com.pedroramon.backend.controller;

import br.com.pedroramon.backend.dto.TypeRomRequest;
import br.com.pedroramon.backend.dto.TypeRomResponse;
import br.com.pedroramon.backend.exception.ResourceNotFoundException;
import br.com.pedroramon.backend.model.TypeRoom;
import br.com.pedroramon.backend.service.TypeRoomService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Testes para o controlador de tipos de quartos {@link TypeRoomController}.
 *
 * Esta classe contém testes para os endpoints da API relacionados aos tipos de quartos,
 * utilizando o framework de testes do Spring WebFlux. Os testes incluem a verificação
 * da recuperação de todos os tipos de quartos, a criação de um novo tipo de quarto e
 * o tratamento de casos inválidos de entrada.
 */
@WebFluxTest(TypeRoomController.class)
class TypeRoomControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private TypeRoomService service;

    /**
     * Testa o endpoint GET para recuperar todos os tipos de quartos.
     * Espera-se que retorne uma lista de tipos de quartos com status 200 OK.
     */
    @Test
    void getAllTypeRooms_ShouldReturnListOfRooms() {
        TypeRomResponse response1 = new TypeRomResponse(1L, "Suíte");
        TypeRomResponse response2 = new TypeRomResponse(2L, "Standard");

        LocalDateTime createdAt = LocalDateTime.now();

        TypeRoom room1 = new TypeRoom();
        room1.setTypeRoomId(1L);
        room1.setName("Suíte");
        room1.setCreatedAt(createdAt);

        TypeRoom room2 = new TypeRoom();
        room2.setTypeRoomId(2L);
        room2.setName("Standard");
        room2.setCreatedAt(createdAt);

        Mockito.when(service.findAll()).thenReturn(Flux.just(room1, room2));

        webTestClient.get().uri("/api/type-rooms")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TypeRomResponse.class)
                .hasSize(2)
                .contains(response1, response2);
    }

    /**
     * Testa o endpoint POST para criar um novo tipo de quarto.
     * Espera-se que retorne o tipo de quarto criado com status 201 Created.
     */
    @Test
    void createTypeRoom_ShouldReturnCreatedRoom() {
        TypeRomRequest request = new TypeRomRequest(null, "Deluxe");
        LocalDateTime createdAt = LocalDateTime.now();

        TypeRoom room = new TypeRoom();
        room.setId(3L);
        room.setName("Deluxe");
        room.setCreatedAt(createdAt);
        TypeRomResponse response = new TypeRomResponse(3L, "Deluxe");

        Mockito.when(service.save(Mockito.any(TypeRoom.class))).thenReturn(Mono.just(room));

        webTestClient.post().uri("/api/type-rooms")
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(TypeRomResponse.class)
                .isEqualTo(response);
    }

    /**
     * Testa o endpoint POST para criar um novo tipo de quarto com dados inválidos.
     * Espera-se que retorne um status 400 Bad Request.
     */
    @Test
    void createTypeRoom_ShouldReturnBadRequest_WhenInvalidData() {
        TypeRomRequest request = new TypeRomRequest(null, "");

        Mockito.when(service.save(Mockito.any(TypeRoom.class))).thenReturn(Mono.empty());

        webTestClient.post().uri("/api/type-rooms")
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest();
    }

    /**
     * Testa o endpoint PUT para atualizar um tipo de quarto.
     * Espera-se que retorne o tipo de quarto atualizado com status 200 OK.
     */
    @Test
    void updateTypeRoom_ShouldReturnUpdatedRoom() {
        Long roomId = 1L;
        TypeRomRequest request = new TypeRomRequest(roomId, "Suite Renovada");
        
        TypeRoom updatedRoom = new TypeRoom();
        updatedRoom.setTypeRoomId(roomId);
        updatedRoom.setName("Suite Renovada");
        
        TypeRomResponse response = new TypeRomResponse(roomId, "Suite Renovada");

        Mockito.when(service.update(Mockito.any(TypeRoom.class))).thenReturn(Mono.just(updatedRoom));

        webTestClient.put().uri("/api/type-rooms")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TypeRomResponse.class)
                .isEqualTo(response);
    }

    /**
     * Testa o endpoint PUT para atualizar um tipo de quarto que não existe.
     * Espera-se que retorne um status 404 Not Found.
     */
    @Test
    void updateTypeRoom_ShouldReturnNotFound_WhenRoomDoesNotExist() {
        Long roomId = 1L;
        TypeRomRequest request = new TypeRomRequest(roomId, "Suite Renovada");

        Mockito.when(service.update(Mockito.any(TypeRoom.class)))
           .thenThrow(new ResourceNotFoundException("Object not found with id: " + roomId));

        webTestClient.put().uri("/api/type-rooms")
                .bodyValue(request)
                .exchange()
                .expectStatus().isNotFound();
    }

}
