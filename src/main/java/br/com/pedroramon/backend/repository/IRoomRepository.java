package br.com.pedroramon.backend.repository;

import br.com.pedroramon.backend.model.Room;
import reactor.core.publisher.Mono;

/**
 * Interface de repositório para a entidade {@link Room}.
 *
 * Esta interface estende {@link IRepository} e fornece operações de persistência
 * específicas para a entidade {@code Room}. Ela herda métodos de CRUD
 * (Create, Read, Update, Delete) definidos na interface {@link IRepository}.
 * 
 * A interface pode ser utilizada para realizar operações como encontrar,
 * salvar, atualizar e excluir entidades do tipo {@code Room} em um
 * repositório reativo.
 */
public interface IRoomRepository extends IRepository<Room> {
    /**
     * Encontra um quarto pelo seu número.
     *
     * @param roomNumber o número do quarto a ser encontrado
     * @return um {@link Mono} que emite o quarto encontrado ou vazio caso não exista
     */
    Mono<Room> findByRoomNumber(Integer roomNumber);
}
