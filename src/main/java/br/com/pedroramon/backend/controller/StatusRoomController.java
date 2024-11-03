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

import br.com.pedroramon.backend.dto.StatusRoomDTO;
import br.com.pedroramon.backend.dto.interfaces.OnUpdate;
import br.com.pedroramon.backend.mapper.MapperFactory;
import br.com.pedroramon.backend.service.StatusRoomService;
import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controlador REST para gerenciar operações relacionadas ao status dos quartos.
 * Disponibiliza endpoints para operações de criação, leitura, atualização e
 * exclusão (CRUD) de objetos StatusRoom.
 * 
 * Utiliza um serviço para manipulação dos dados e um mapeador para conversão
 * entre
 * as entidades de modelo e os objetos de transferência de dados (DTOs).
 * 
 * Endpoints disponíveis:
 * - GET /api/status-rooms: Recupera todos os status dos quartos.
 * - GET /api/status-rooms/{id}: Recupera um status específico de quarto por ID.
 * - POST /api/status-rooms: Cria um novo status de quarto.
 * - PUT /api/status-rooms: Atualiza um status de quarto existente.
 * - DELETE /api/status-rooms/{id}: Exclui um status de quarto por ID.
 */
@RestController
@RequestMapping("/api/status-rooms")
public class StatusRoomController {

    private final StatusRoomService statusRoomService;
    private final MapperFactory mapperFactory;

    /**
     * Construtor para injetar o serviço de manipulação do status dos quartos.
     * 
     * @param statusRoomService Serviço para operações CRUD de status de quartos.
     */
    @Autowired
    public StatusRoomController(StatusRoomService statusRoomService, MapperFactory mapperFactory) {
        this.statusRoomService = statusRoomService;
        this.mapperFactory = mapperFactory;
    }

    /**
     * Recupera todos os status de quartos.
     * 
     * @return Um fluxo (Flux) de objetos StatusRoomDTO representando todos os
     *         status dos quartos.
     */
    @GetMapping
    public Flux<StatusRoomDTO> getAllStatusRooms() {
        return statusRoomService.findAll().map(this.mapperFactory.getStatusRoomMapper()::toDto);
    }

    /**
     * Recupera um status específico de quarto pelo seu ID.
     * 
     * @param id O ID do status do quarto a ser recuperado.
     * @return Um Mono de StatusRoomDTO representando o status de quarto com o ID
     *         especificado.
     */
    @GetMapping("/{id}")
    public Mono<StatusRoomDTO> findById(@PathVariable Long id) {
        return statusRoomService.findById(id)
                .map(this.mapperFactory.getStatusRoomMapper()::toDto);
    }

    /**
     * Cria um novo status de quarto.
     * 
     * @param request Objeto StatusRoomDTO com os dados do status de quarto a ser
     *                criado.
     * @return Um ResponseEntity contendo um Mono de StatusRoomDTO representando o
     *         status de quarto criado.
     */
    @PostMapping
    public ResponseEntity<Mono<StatusRoomDTO>> create(@Valid @RequestBody StatusRoomDTO request) {
        var statusRomResponse = statusRoomService.save(this.mapperFactory.getStatusRoomMapper().toEntity(request))
                .map(this.mapperFactory.getStatusRoomMapper()::toDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(statusRomResponse);
    }

    /**
     * Atualiza um status de quarto existente.
     * 
     * @param request Objeto StatusRoomDTO com os dados atualizados do status de
     *                quarto.
     * @return Um ResponseEntity contendo um Mono de StatusRoomDTO representando o
     *         status de quarto atualizado.
     */
    @PutMapping
    public ResponseEntity<Mono<StatusRoomDTO>> update(@Validated(OnUpdate.class) @RequestBody StatusRoomDTO request) {
        var statusRomResponse = statusRoomService.update(this.mapperFactory.getStatusRoomMapper().toEntity(request))
                .map(this.mapperFactory.getStatusRoomMapper()::toDto);
        return ResponseEntity.status(HttpStatus.OK).body(statusRomResponse);
    }

    /**
     * Exclui um status de quarto pelo seu ID.
     * 
     * @param id O ID do status de quarto a ser excluído.
     * @return Um Mono<Void> indicando a conclusão da operação.
     */
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return statusRoomService.delete(id);
    }
}
