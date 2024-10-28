package br.com.pedroramon.backend.services;

import br.com.pedroramon.backend.exception.InvalidDataException;
import br.com.pedroramon.backend.exception.ResourceNotFoundException;
import br.com.pedroramon.backend.model.IEntity;
import br.com.pedroramon.backend.repository.IRepository;
import br.com.pedroramon.backend.service.GenericService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Classe de teste para o serviço genérico {@link GenericService}.
 *
 * Esta classe contém os testes unitários para garantir que os métodos do
 * {@link GenericService} funcionem corretamente. Utiliza o framework Mockito
 * para simular o repositório e o framework JUnit para a execução dos testes.
 */
class GenericServiceTest {

    private IRepository<IEntity> repository;
    private GenericService<IEntity> service;

    /**
     * Configura o ambiente de teste antes da execução de cada teste.
     * Este método é chamado automaticamente pelo JUnit antes de cada teste
     * e inicializa o repositório simulado e a instância do serviço genérico.
     */
    @SuppressWarnings("unchecked")
    @BeforeEach
    void setUp() {
        repository = Mockito.mock(IRepository.class);
        service = new GenericService<>(repository);
    }

    /**
     * Testa se o método {@link GenericService#findAll()} retorna todas as entidades.
     * O teste verifica se a contagem das entidades retornadas é igual a 2.
     */
    @Test
    void findAll_ShouldReturnAllEntities() {
        IEntity entity1 = mock(IEntity.class);
        IEntity entity2 = mock(IEntity.class);
        when(repository.findAll()).thenReturn(Flux.just(entity1, entity2));

        Flux<IEntity> result = service.findAll();

        assertEquals(2, result.count().block());
    }

    /**
     * Testa se o método {@link GenericService#findById(Long)} retorna a entidade
     * quando ela existe. O teste verifica se a entidade retornada é igual à esperada.
     */
    @Test
    void findById_ShouldReturnEntity_WhenExists() {
        IEntity entity = mock(IEntity.class);
        when(repository.findById(1L)).thenReturn(Mono.just(entity));

        Mono<IEntity> result = service.findById(1L);

        assertEquals(entity, result.block());
    }

    /**
     * Testa se o método {@link GenericService#findById(Long)} lança uma exceção
     * {@link ResourceNotFoundException} quando a entidade não é encontrada.
     * O teste verifica se a mensagem da exceção corresponde à esperada.
     */
    @Test
    void findById_ShouldThrowResourceNotFoundException_WhenNotFound() {
        when(repository.findById(1L)).thenReturn(Mono.empty());

        Mono<IEntity> result = service.findById(1L);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, result::block);
        assertEquals("Object not found with id: 1", exception.getMessage());
    }

    /**
     * Testa se o método {@link GenericService#save(IEntity)} retorna a entidade
     * salva corretamente. O teste verifica se a entidade retornada é igual à
     * entidade que foi passada como parâmetro.
     */
    @Test
    void save_ShouldReturnSavedEntity() {
        IEntity entity = mock(IEntity.class);
        when(repository.save(entity)).thenReturn(Mono.just(entity));

        Mono<IEntity> result = service.save(entity);

        assertEquals(entity, result.block());
    }

    /**
     * Testa se o método {@link GenericService#save(IEntity)} lança uma exceção
     * {@link InvalidDataException} quando ocorre um erro durante o salvamento.
     * O teste verifica se a mensagem da exceção corresponde à esperada.
     */
    @Test
    void save_ShouldThrowInvalidDataException_WhenErrorOccurs() {
        IEntity entity = mock(IEntity.class);
        when(repository.save(entity)).thenReturn(Mono.error(new RuntimeException("Some error")));

        Mono<IEntity> result = service.save(entity);

        InvalidDataException exception = assertThrows(InvalidDataException.class, result::block);
        assertEquals("Error saving object: Some error", exception.getMessage());
    }

    /**
     * Testa se o método {@link GenericService#update(IEntity)} retorna a entidade
     * atualizada corretamente quando a entidade existe. O teste verifica se a
     * entidade retornada é igual à entidade que foi passada como parâmetro.
     */
    @Test
    void update_ShouldReturnUpdatedEntity_WhenExists() {
        IEntity existingEntity = mock(IEntity.class);
        IEntity updatedEntity = mock(IEntity.class);

        when(existingEntity.getId()).thenReturn(1L);
        when(repository.findById(1L)).thenReturn(Mono.just(existingEntity));
        when(repository.save(updatedEntity)).thenReturn(Mono.just(updatedEntity));
        when(updatedEntity.getId()).thenReturn(1L);

        Mono<IEntity> result = service.update(updatedEntity);
        assertEquals(updatedEntity, result.block());
    }

    /**
     * Testa se o método {@link GenericService#update(IEntity)} lança uma exceção
     * {@link ResourceNotFoundException} quando a entidade a ser atualizada não é encontrada.
     * O teste verifica se a mensagem da exceção corresponde à esperada.
     */
    @Test
    void update_ShouldThrowResourceNotFoundException_WhenNotFound() {
        IEntity updatedEntity = mock(IEntity.class);
        when(updatedEntity.getId()).thenReturn(1L);
        when(repository.findById(1L)).thenReturn(Mono.empty());

        Mono<IEntity> result = service.update(updatedEntity);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, result::block);
        assertEquals("Object not found with id: 1", exception.getMessage());
    }

    /**
     * Testa se o método {@link GenericService#delete(Long)} completa sem lançar exceções
     * quando a entidade existe. O teste verifica que não há exceções lançadas durante a
     * execução do método.
     */
    @Test
    void delete_ShouldComplete_WhenEntityExists() {
        when(repository.deleteById(1L)).thenReturn(Mono.empty());

        Mono<Void> result = service.delete(1L);

        assertDoesNotThrow(() -> result.block());
    }
}
