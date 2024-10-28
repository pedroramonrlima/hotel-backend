package br.com.pedroramon.backend.repository;

import br.com.pedroramon.backend.model.TypeRoom;

/**
 * Interface de repositório para a entidade {@link TypeRoom}.
 *
 * Esta interface estende {@link IRepository} e fornece operações de persistência
 * específicas para a entidade {@code TypeRoom}. Ela herda métodos de CRUD
 * (Create, Read, Update, Delete) definidos na interface {@link IRepository}.
 * 
 * A interface pode ser utilizada para realizar operações como encontrar,
 * salvar, atualizar e excluir entidades do tipo {@code TypeRoom} em um
 * repositório reativo.
 */
public interface ITypeRoomRepository extends IRepository<TypeRoom> {
    // Interface vazia; os métodos de CRUD são herdados de IRepoitory
}
