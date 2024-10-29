package br.com.pedroramon.backend.service.interfaces;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Interface genérica para serviços de manipulação de entidades.
 *
 * A interface {@code IService<T>} define um contrato para serviços que gerenciam
 * operações CRUD (Create, Read, Update, Delete) em entidades do tipo {@code T}.
 * Os métodos fornecem uma forma reativa de manipular dados, utilizando o projeto
 * Reactor para suporte a programação reativa.
 *
 * @param <T> O tipo da entidade que será gerenciada pelos serviços.
 */
public interface IService<T> {
    
    /**
     * Recupera todas as entidades do tipo {@code T}.
     *
     * @return Um {@link Flux} contendo todas as entidades encontradas.
     */
    Flux<T> findAll();

    /**
     * Recupera uma entidade do tipo {@code T} pelo seu ID.
     *
     * @param id O ID da entidade a ser recuperada.
     * @return Um {@link Mono} contendo a entidade encontrada ou vazio se não for encontrada.
     */
    Mono<T> findById(Long id);

    /**
     * Salva uma nova entidade do tipo {@code T}.
     *
     * @param object A entidade a ser salva.
     * @return Um {@link Mono} contendo a entidade salva.
     */
    Mono<T> save(T object);

    /**
     * Atualiza uma entidade existente do tipo {@code T}.
     *
     * @param object A entidade com os dados atualizados.
     * @return Um {@link Mono} contendo a entidade atualizada.
     */
    Mono<T> update(T object);

    /**
     * Remove uma entidade do tipo {@code T} pelo seu ID.
     *
     * @param id O ID da entidade a ser removida.
     * @return Um {@link Mono<Void>} que indica quando a operação de remoção é concluída.
     */
    Mono<Void> delete(Long id);
}
