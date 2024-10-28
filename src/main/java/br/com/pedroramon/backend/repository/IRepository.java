package br.com.pedroramon.backend.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * Interface genérica para repositórios de dados reativos.
 *
 * Esta interface estende {@link ReactiveCrudRepository} e fornece uma abstração
 * para operações de CRUD (Create, Read, Update, Delete) em entidades do tipo {@code T}.
 * A anotação {@code @NoRepositoryBean} indica que essa interface não deve ser
 * instanciada diretamente, mas deve ser estendida por outras interfaces de repositório
 * que definem tipos específicos de entidades.
 *
 * @param <T> O tipo da entidade que será gerenciada por este repositório.
 */
@NoRepositoryBean
public interface IRepository<T> extends ReactiveCrudRepository<T, Long>  {
    // Interface vazia; os métodos de CRUD são herdados de ReactiveCrudRepository
}
