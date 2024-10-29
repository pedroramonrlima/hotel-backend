package br.com.pedroramon.backend.repository;

import br.com.pedroramon.backend.model.StatusRoom;

/**
 * Interface de repositório para a entidade {@link StatusRoom}.
 *
 * Esta interface estende {@link IRepository} e fornece operações de persistência
 * específicas para a entidade {@code StatusRoom}. Ela herda métodos de CRUD
 * (Create, Read, Update, Delete) definidos na interface {@link IRepository}.
 * 
 * A interface pode ser utilizada para realizar operações como encontrar,
 * salvar, atualizar e excluir entidades do tipo {@code StatusRoom} em um
 * repositório reativo.
 */
public interface IStatusRoomRepository extends IRepository<StatusRoom> {

}