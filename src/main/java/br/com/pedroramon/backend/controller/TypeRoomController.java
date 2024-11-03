package br.com.pedroramon.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pedroramon.backend.dto.TypeRoomDTO;
import br.com.pedroramon.backend.mapper.MapperFactory;
import br.com.pedroramon.backend.service.TypeRoomService;
import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controlador REST para gerenciar operações relacionadas a tipos de quartos.
 * Disponibiliza endpoints para operações de criação, leitura, atualização e
 * exclusão (CRUD) de objetos TypeRoom.
 * 
 * Utiliza um serviço para manipulação dos dados e um mapeador para
 * conversão entre as entidades de modelo e os objetos de transferência de dados
 * (DTOs).
 * 
 * Endpoints disponíveis:
 * - GET /api/type-rooms: Recupera todos os tipos de quartos.
 * - GET /api/type-rooms/{id}: Recupera um tipo de quarto específico por ID.
 * - POST /api/type-rooms: Cria um novo tipo de quarto.
 * - PUT /api/type-rooms: Atualiza um tipo de quarto existente.
 * - DELETE /api/type-rooms/{id}: Exclui um tipo de quarto por ID.
 */
@RestController
@RequestMapping("/api/type-rooms")
public class TypeRoomController {

    private final TypeRoomService service;
    private final MapperFactory mapperFactory;

    /**
     * Construtor para injetar o serviço de manipulação de tipos de quartos.
     * 
     * @param typeRoomService Serviço para operações CRUD de tipos de quartos.
     */
    public TypeRoomController(TypeRoomService typeRoomService, MapperFactory mapperFactory) {
        this.service = typeRoomService;
        this.mapperFactory = mapperFactory;
    }

    /**
     * Recupera todos os tipos de quartos.
     * 
     * @return Um fluxo (Flux) de objetos TypeRomResponse representando todos os
     *         tipos de quartos.
     */
    @GetMapping
    public Flux<TypeRoomDTO> getAll() {
        return service.findAll().map(this.mapperFactory.getTypeRoomMapper()::toDto);
    }

    /**
     * Recupera um tipo de quarto específico pelo seu ID.
     * 
     * @param id O ID do tipo de quarto a ser recuperado.
     * @return Um Mono de TypeRomResponse representando o tipo de quarto com o ID
     *         especificado.
     */
    @GetMapping("/{id}")
    public Mono<TypeRoomDTO> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(this.mapperFactory.getTypeRoomMapper()::toDto);
    }

    /**
     * Cria um novo tipo de quarto.
     * 
     * @param request Objeto TypeRomRequest com os dados do tipo de quarto a ser
     *                criado.
     * @return Um ResponseEntity contendo um Mono de TypeRomResponse representando o
     *         tipo de quarto criado.
     */
    @PostMapping
    public ResponseEntity<Mono<TypeRoomDTO>> create(@Valid @RequestBody TypeRoomDTO request) {
        var typeRomResponse = service.save(this.mapperFactory.getTypeRoomMapper().toEntity(request))
                .map(this.mapperFactory.getTypeRoomMapper()::toDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(typeRomResponse);
    }

    /**
     * Atualiza um tipo de quarto existente.
     * 
     * @param request Objeto TypeRomRequest com os dados atualizados do tipo de
     *                quarto.
     * @return Um ResponseEntity contendo um Mono de TypeRomResponse representando o
     *         tipo de quarto atualizado.
     */
    @PutMapping
    public ResponseEntity<Mono<TypeRoomDTO>> update(@RequestBody TypeRoomDTO request) {
        var typeRomResponse = service.update(this.mapperFactory.getTypeRoomMapper().toEntity(request))
                .map(this.mapperFactory.getTypeRoomMapper()::toDto);
        return ResponseEntity.status(HttpStatus.OK).body(typeRomResponse);
    }

    /**
     * Exclui um tipo de quarto pelo seu ID.
     * 
     * @param id O ID do tipo de quarto a ser excluído.
     * @return Um Mono<Void> indicando a conclusão da operação.
     */
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}
