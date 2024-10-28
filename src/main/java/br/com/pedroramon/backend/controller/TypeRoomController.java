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

import br.com.pedroramon.backend.dto.TypeRomRequest;
import br.com.pedroramon.backend.dto.TypeRomResponse;
import br.com.pedroramon.backend.mapper.TypeRoomMapper;
import br.com.pedroramon.backend.model.TypeRoom;
import br.com.pedroramon.backend.service.GenericService;
import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controlador REST para gerenciar operações relacionadas a tipos de quartos.
 * Disponibiliza endpoints para operações de criação, leitura, atualização e
 * exclusão (CRUD) de objetos TypeRoom.
 * 
 * Utiliza um serviço genérico para manipulação dos dados e um mapeador para
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
    
    private final GenericService<TypeRoom> service;

    /**
     * Construtor para injetar o serviço genérico de manipulação de tipos de quartos.
     * 
     * @param typeRoomService Serviço genérico para operações CRUD de tipos de quartos.
     */
    public TypeRoomController(GenericService<TypeRoom> typeRoomService) {
        this.service = typeRoomService;
    }

    /**
     * Recupera todos os tipos de quartos.
     * 
     * @return Um fluxo (Flux) de objetos TypeRomResponse representando todos os tipos de quartos.
     */
    @GetMapping
    public Flux<TypeRomResponse> getAll() {
        return service.findAll().map(TypeRoomMapper::fromTypeRoomToResponse);
    }

    /**
     * Recupera um tipo de quarto específico pelo seu ID.
     * 
     * @param id O ID do tipo de quarto a ser recuperado.
     * @return Um Mono de TypeRomResponse representando o tipo de quarto com o ID especificado.
     */
    @GetMapping("/{id}")
    public Mono<TypeRomResponse> findById(@PathVariable Long id) {
        return service.findById(id)
                   .map(TypeRoomMapper::fromTypeRoomToResponse);
    }

    /**
     * Cria um novo tipo de quarto.
     * 
     * @param request Objeto TypeRomRequest com os dados do tipo de quarto a ser criado.
     * @return Um ResponseEntity contendo um Mono de TypeRomResponse representando o tipo de quarto criado.
     */
    @PostMapping
    public ResponseEntity<Mono<TypeRomResponse>> create(@Valid @RequestBody TypeRomRequest request){
        var typeRomResponse = service.save(TypeRoomMapper.fromRequestToTypeRoom(request)).map(TypeRoomMapper::fromTypeRoomToResponse);

        return ResponseEntity.status(HttpStatus.CREATED).body(typeRomResponse);
    }

    /**
     * Atualiza um tipo de quarto existente.
     * 
     * @param request Objeto TypeRomRequest com os dados atualizados do tipo de quarto.
     * @return Um ResponseEntity contendo um Mono de TypeRomResponse representando o tipo de quarto atualizado.
     */
    @PutMapping
    public ResponseEntity<Mono<TypeRomResponse>> update(@RequestBody TypeRomRequest request){
        var typeRomResponse = service.update(TypeRoomMapper.fromRequestToTypeRoom(request)).map(TypeRoomMapper::fromTypeRoomToResponse);
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
