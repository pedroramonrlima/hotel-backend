package br.com.pedroramon.backend.services;

import br.com.pedroramon.backend.exception.InvalidDataException;
import br.com.pedroramon.backend.exception.ResourceNotFoundException;
import br.com.pedroramon.backend.model.StatusRoom;
import br.com.pedroramon.backend.repository.IRepository;
import br.com.pedroramon.backend.service.GenericService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

/**
 * Classe de teste para o serviço genérico {@link GenericService}.
 * 
 * Esta classe contém testes unitários para garantir que os métodos do
 * {@link GenericService} funcionem corretamente. Utiliza o framework Mockito
 * para simular o repositório e o framework JUnit para a execução dos testes,
 * além de utilizar o StepVerifier para testar comportamentos reativos.
 */
class GenericServiceTest {

    private IRepository<StatusRoom> repository;
    private GenericService<StatusRoom> service;

    /**
     * Configura o ambiente de teste antes da execução de cada teste.
     * Este método é chamado automaticamente pelo JUnit antes de cada teste
     * e inicializa o repositório simulado e a instância do serviço genérico.
     */
    @SuppressWarnings("unchecked")
    @BeforeEach
    void setUp() {
        repository = Mockito.mock(IRepository.class);
        service = new GenericService<StatusRoom>(repository) {};
    }

    /**
     * Testa se o método {@link GenericService#findAll()} retorna todas as entidades.
     * O teste verifica se as entidades retornadas correspondem às esperadas.
     */
    @Test
    void findAll_ShouldReturnAllEntities() {
        StatusRoom entity1 = new StatusRoom();
        StatusRoom entity2 = new StatusRoom();
        when(repository.findAll()).thenReturn(Flux.just(entity1, entity2));

        StepVerifier.create(service.findAll())
                .expectNext(entity1, entity2)
                .verifyComplete();
    }

    /**
     * Testa se o método {@link GenericService#findById(Long)} retorna a entidade
     * quando ela existe. O teste verifica se a entidade retornada é a esperada.
     */
    @Test
    void findById_ShouldReturnEntity_WhenExists() {
        StatusRoom entity = new StatusRoom();
        when(repository.findById(1L)).thenReturn(Mono.just(entity));

        StepVerifier.create(service.findById(1L))
                .expectNext(entity)
                .verifyComplete();
    }

    /**
     * Testa se o método {@link GenericService#findById(Long)} lança uma exceção
     * {@link ResourceNotFoundException} quando a entidade não é encontrada.
     * O teste verifica se a mensagem da exceção corresponde à esperada.
     */
    @Test
    void findById_ShouldThrowResourceNotFoundException_WhenNotFound() {
        when(repository.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(service.findById(1L))
                .expectErrorMatches(throwable -> throwable instanceof ResourceNotFoundException &&
                                                  throwable.getMessage().equals("Object not found with id: 1"))
                .verify();
    }

    /**
     * Testa se o método {@link GenericService#save(StatusRoom)} retorna a entidade
     * salva corretamente. O teste verifica se a entidade retornada é igual à
     * entidade que foi passada como parâmetro.
     */
    @Test
    void save_ShouldReturnSavedEntity() {
        StatusRoom entity = new StatusRoom();
        when(repository.save(entity)).thenReturn(Mono.just(entity));

        StepVerifier.create(service.save(entity))
                .expectNext(entity)
                .verifyComplete();
    }

    /**
     * Testa se o método {@link GenericService#save(StatusRoom)} lança uma exceção
     * {@link InvalidDataException} quando ocorre um erro durante o salvamento.
     * O teste verifica se a mensagem da exceção corresponde à esperada.
     */
    @Test
    void save_ShouldThrowInvalidDataException_WhenErrorOccurs() {
        StatusRoom entity = new StatusRoom();
        when(repository.save(entity)).thenReturn(Mono.error(new RuntimeException("Some error")));

        StepVerifier.create(service.save(entity))
                .expectErrorMatches(throwable -> throwable instanceof InvalidDataException &&
                                                  throwable.getMessage().equals("Error saving object: Some error"))
                .verify();
    }

    /**
     * Testa se o método {@link GenericService#update(StatusRoom)} retorna a entidade
     * atualizada corretamente quando a entidade existe. O teste verifica se a
     * entidade retornada é igual à entidade que foi passada como parâmetro.
     */
    @Test
    void update_ShouldReturnUpdatedEntity_WhenExists() {
        StatusRoom existingEntity = new StatusRoom();
        existingEntity.setId(1L);
        StatusRoom updatedEntity = new StatusRoom();
        updatedEntity.setId(1L);

        when(repository.findById(1L)).thenReturn(Mono.just(existingEntity));
        when(repository.save(updatedEntity)).thenReturn(Mono.just(updatedEntity));

        StepVerifier.create(service.update(updatedEntity))
                .expectNext(updatedEntity)
                .verifyComplete();
    }

    /**
     * Testa se o método {@link GenericService#update(StatusRoom)} lança uma exceção
     * {@link ResourceNotFoundException} quando a entidade a ser atualizada não é encontrada.
     * O teste verifica se a mensagem da exceção corresponde à esperada.
     */
    @Test
    void update_ShouldThrowResourceNotFoundException_WhenNotFound() {
        StatusRoom updatedEntity = new StatusRoom();
        updatedEntity.setId(1L);
        when(repository.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(service.update(updatedEntity))
                .expectErrorMatches(throwable -> throwable instanceof ResourceNotFoundException &&
                                                  throwable.getMessage().equals("Object not found with id: 1"))
                .verify();
    }

    /**
     * Testa se o método {@link GenericService#delete(Long)} completa sem lançar exceções
     * quando a entidade existe. O teste verifica que não há exceções lançadas durante a
     * execução do método.
     */
    @Test
    void delete_ShouldComplete_WhenEntityExists() {
        when(repository.deleteById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(service.delete(1L))
                .verifyComplete();
    }
}
